package com.tech.api.controller;

import com.tech.api.constant.Constants;
import com.tech.api.dto.ApiMessageDto;
import com.tech.api.dto.ErrorCode;
import com.tech.api.dto.ResponseListObj;
import com.tech.api.dto.group.GroupAdminDto;
import com.tech.api.dto.importDto.ImportDto;
import com.tech.api.exception.RequestException;
import com.tech.api.form.importForm.*;
import com.tech.api.mapper.ImportMapper;
import com.tech.api.storage.criteria.GroupCriteria;
import com.tech.api.storage.criteria.ImportCriteria;
import com.tech.api.storage.model.*;
import com.tech.api.storage.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/import")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class ImportController extends ABasicController{
    @Autowired
    ImportRepository importRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ImportMapper importMapper;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    ImportLineItemRepository importLineItemRepository;

    @Autowired
    ProductVariantRepository productVariantRepository;

    @Autowired
    StockRepository stockRepository;

    @Autowired
    ProductRepository productRepository;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListObj<ImportDto>> getList(ImportCriteria importCriteria){
        if(!isAdmin() && !isManager()){
            throw new RequestException(ErrorCode.IMPORT_ERROR_UNAUTHORIZED);
        }

        ApiMessageDto<ResponseListObj<ImportDto>> apiMessageDto = new ApiMessageDto<>();
        if(isManager()){
            importCriteria.setIsManagerShow(true);
            Employee employee = employeeRepository.findById(getCurrentUserId()).orElseThrow(() -> new RequestException(ErrorCode.EMPLOYEE_ERROR_NOT_FOUND));
            importCriteria.setStoreId(employee.getStore().getId());
        }
        Page<Import> importPage = importRepository.findAll(importCriteria.getSpecification(), Pageable.unpaged());

        ResponseListObj<ImportDto> responseListObj = new ResponseListObj<>();
        responseListObj.setData(importMapper.fromEntityListToImportDtoList(importPage.getContent()));
        apiMessageDto.setData(responseListObj);
        apiMessageDto.setMessage("List import success");
        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ImportDto> get(@PathVariable("id") Long id){
        if(!isAdmin() && !isManager()){
            throw new RequestException(ErrorCode.IMPORT_ERROR_UNAUTHORIZED);
        }
        ApiMessageDto<ImportDto> apiMessageDto = new ApiMessageDto<>();
        Import importData = importRepository.findById(id).orElseThrow(() -> new RequestException(ErrorCode.IMPORT_ERROR_NOT_FOUND));
        List<ImportLineItem> lineItemList = importLineItemRepository.findByAnImportId(importData.getId());
        ImportDto importDto = importMapper.fromEntityToImportDto(importData);
        importDto.setItems(importMapper.fromEntityListToImportLineItemDtoList(lineItemList));
        apiMessageDto.setData(importDto);
        apiMessageDto.setMessage("Get import success");
        return apiMessageDto;
    }

    @PostMapping(value = "/request-import", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> requestImport(@RequestBody @Valid RequestImportForm requestImportForm, BindingResult bindingResult){
        if(!isAdmin()){
            throw new RequestException(ErrorCode.IMPORT_ERROR_UNAUTHORIZED);
        }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        LocalDate date = LocalDate.now();
        for(RequestImportItem item : requestImportForm.getImportItems()){
            if (item.getQuantity().equals(0)) continue;
            Store store = storeRepository.findById(item.getStoreId()).orElse(null);
            if(store == null) continue;
            Import importData = importRepository.findFirstByDateAndStoreIdAndState(date,store.getId(), Constants.IMPORT_STATE_PENDING);
            boolean isNewImport = false;
            if(importData == null){
                // not exist -> create
                isNewImport = true;
                importData = new Import();
                importData.setDate(date);
                importData.setStore(store);
                importData.setTotal(item.getQuantity());
                importData.setState(Constants.IMPORT_STATE_PENDING);
                importData = importRepository.save(importData);
            }
            ProductVariant variant = productVariantRepository.findById(item.getVariantId()).orElse(null);
            if(variant == null) continue;
            ImportLineItem importLineItem = importLineItemRepository.findFirstByVariantIdAndAnImportId(variant.getId(),importData.getId());
            if(importLineItem == null){
                // not exist -> add
                importLineItem = new ImportLineItem();
                importLineItem.setVariant(variant);
                importLineItem.setAnImport(importData);
                importLineItem.setQuantity(item.getQuantity());
                importLineItemRepository.save(importLineItem);
            } else{
                // existed -> increase quantity
                importLineItem.setQuantity(importLineItem.getQuantity() + item.getQuantity());
                importLineItemRepository.save(importLineItem);
            }
            if (!isNewImport) importData.setTotal(importData.getTotal() + item.getQuantity());
            importRepository.save(importData);
        }
        apiMessageDto.setMessage("Request import success");
        return apiMessageDto;
    }

    @PostMapping(value = "/accept", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> acceptImport(@RequestBody @Valid AcceptImportForm acceptImportForm, BindingResult bindingResult){
        if(!isManager()){
            throw new RequestException(ErrorCode.IMPORT_ERROR_UNAUTHORIZED);
        }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Employee employee = employeeRepository.findById(getCurrentUserId()).orElseThrow(() -> new RequestException(ErrorCode.EMPLOYEE_ERROR_NOT_FOUND));
        Import importData = importRepository.findById(acceptImportForm.getImportId()).orElseThrow(() -> new RequestException(ErrorCode.IMPORT_ERROR_NOT_FOUND));
        if(!importData.getStore().getId().equals(employee.getStore().getId()) || !importData.getState().equals(Constants.IMPORT_STATE_PENDING)){
            throw new RequestException(ErrorCode.IMPORT_ERROR_NOT_FOUND);
        }
        List<ImportLineItem> lineItemList = importLineItemRepository.findByAnImportId(importData.getId());
        for (ImportLineItem item : lineItemList){
            Stock stock = stockRepository.findFirstByProductVariantIdAndStoreId(item.getVariant().getId(),employee.getStore().getId());
            if(stock == null){
                // not exist -> create
                stock = new Stock();

                Store store = storeRepository.findById(employee.getStore().getId()).orElse(null);
                if(store == null) continue;
                stock.setStore(store);
                stock.setProductVariant(item.getVariant());
                stock.setTotal(item.getQuantity());
                stockRepository.save(stock);
            } else{
                stock.setTotal(stock.getTotal() + item.getQuantity());
                stockRepository.save(stock);
            }
            Product product = productRepository.findById(item.getVariant().getProductConfig().getProduct().getId()).orElse(null);
            if(product != null) {
                product.setTotalInStock(product.getTotalInStock() + item.getQuantity());
                productRepository.save(product);
            }
        }
        importData.setState(Constants.IMPORT_STATE_ACCEPTED);
        importRepository.save(importData);
        apiMessageDto.setMessage("Accept import success");
        return apiMessageDto;
    }

    @PostMapping(value = "/deny", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> denyImport(@RequestBody @Valid DenyImportForm denyImportForm, BindingResult bindingResult){
        if(!isManager()){
            throw new RequestException(ErrorCode.IMPORT_ERROR_UNAUTHORIZED);
        }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Employee employee = employeeRepository.findById(getCurrentUserId()).orElseThrow(() -> new RequestException(ErrorCode.EMPLOYEE_ERROR_NOT_FOUND));
        Import importData = importRepository.findById(denyImportForm.getImportId()).orElseThrow(() -> new RequestException(ErrorCode.IMPORT_ERROR_NOT_FOUND));
        if(!importData.getStore().getId().equals(employee.getStore().getId()) || !importData.getState().equals(Constants.IMPORT_STATE_PENDING)){
            throw new RequestException(ErrorCode.IMPORT_ERROR_NOT_FOUND);
        }
        importData.setState(Constants.IMPORT_STATE_DENIED);
        importRepository.save(importData);
        apiMessageDto.setMessage("Deny import success");
        return apiMessageDto;
    }

    @PostMapping(value = "/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> cancelImport(@RequestBody @Valid CancelImportForm cancelImportForm, BindingResult bindingResult){
        if(!isAdmin()){
            throw new RequestException(ErrorCode.IMPORT_ERROR_UNAUTHORIZED);
        }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Import importData = importRepository.findById(cancelImportForm.getImportId()).orElseThrow(() -> new RequestException(ErrorCode.IMPORT_ERROR_NOT_FOUND));
        if(!importData.getState().equals(Constants.IMPORT_STATE_PENDING)){
            throw new RequestException(ErrorCode.IMPORT_ERROR_NOT_FOUND, "Wrong state");
        }
        importData.setState(Constants.IMPORT_STATE_CANCELED);
        importRepository.save(importData);
        apiMessageDto.setMessage("Deny import success");
        return apiMessageDto;
    }
}
