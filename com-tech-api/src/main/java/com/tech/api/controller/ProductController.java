package com.tech.api.controller;

import com.tech.api.constant.Constants;
import com.tech.api.dto.ApiMessageDto;
import com.tech.api.dto.ErrorCode;
import com.tech.api.dto.ResponseListObj;
import com.tech.api.dto.product.ProductAdminDto;
import com.tech.api.dto.product.ProductDto;
import com.tech.api.form.product.UpdateFavoriteForm;
import com.tech.api.form.product.UpdateProductForm;
import com.tech.api.form.product.UpdateSellStatusForm;
import com.tech.api.mapper.ProductMapper;
import com.tech.api.service.CommonApiService;
import com.tech.api.storage.criteria.ProductCriteria;
import com.tech.api.storage.model.Customer;
import com.tech.api.storage.model.Product;
import com.tech.api.storage.model.ProductCategory;
import com.tech.api.storage.repository.CustomerRepository;
import com.tech.api.storage.repository.ProductRepository;
import com.tech.api.exception.RequestException;
import com.tech.api.form.product.CreateProductForm;
import com.tech.api.storage.model.*;
import com.tech.api.storage.repository.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/v1/product")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RequiredArgsConstructor
public class ProductController extends ABasicController {
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductMapper productMapper;
    private final CommonApiService commonApiService;

    @Autowired
    CustomerRepository customerRepository;

    // for store
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListObj<ProductAdminDto>> list(@Valid ProductCriteria productCriteria, BindingResult bindingResult, Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(productCriteria.getSpecification(), pageable);
        List<ProductAdminDto> productAdminDtoList = productMapper.fromProductEntityListToAdminDtoList(productPage.getContent());
        return new ApiMessageDto<>(
                new ResponseListObj<>(
                        productAdminDtoList,
                        productPage
                ),
                "Get list product successfully"
        );
    }


    // need to login customer
    @GetMapping(value = "/client-list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListObj<ProductDto>> clientList(ProductCriteria productCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListObj<ProductDto>> responseListObjApiMessageDto = new ApiMessageDto<>();

        Page<Product> productList = productRepository.findAll(productCriteria.getSpecification(), pageable);
        ResponseListObj<ProductDto> responseListObj = new ResponseListObj<>();
        if(productCriteria.getCustomerId() != null){
            List<Product> modifiableList = new ArrayList<Product>(productList.getContent());
            Collections.reverse(modifiableList);
            responseListObj.setData(productMapper.fromEntityListToProductClientDtoList(modifiableList));
        }
        else if(isCustomer()){
            List<ProductDto> dto = new ArrayList<>();
            Long customerId = getCurrentCustomer().getId();
            for (Product product : productList){
                ProductDto productDto = productMapper.fromEntityToClientDto(product);
                for(Customer customer : product.getCustomersLiked()){
                    if(customer.getId().equals(customerId)){
                        productDto.setIsLike(true);
                        break;
                    }
                }
                dto.add(productDto);
            }
            responseListObj.setData(dto);
        }
        responseListObj.setPage(pageable.getPageNumber());
        responseListObj.setTotalPage(productList.getTotalPages());
        responseListObj.setTotalElements(productList.getTotalElements());

        responseListObjApiMessageDto.setData(responseListObj);
        responseListObjApiMessageDto.setMessage("Get list success");
        return responseListObjApiMessageDto;
    }



    @PutMapping(value = "/favorite/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> updateFavorite(@Valid @RequestBody UpdateFavoriteForm updateFavoriteForm, BindingResult bindingResult) {
        if(!isCustomer()){
            throw new RequestException(ErrorCode.CART_ERROR_UNAUTHORIZED, "Not allowed to add item.");
        }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Product product = productRepository.findById(updateFavoriteForm.getProductId()).orElse(null);
        if(product == null || product.getIsSoldOut()|| !product.getStatus().equals(Constants.STATUS_ACTIVE)){
            throw new RequestException(ErrorCode.PRODUCT_NOT_FOUND, "Not found product.");
        }
        Customer customer = getCurrentCustomer();
        boolean isFound = false;
        for(Customer customerCheck : product.getCustomersLiked()){
            if(customerCheck.getId().equals(customer.getId())){
                product.getCustomersLiked().remove(customerCheck);
                isFound = true;
                break;
            }
        }
        if(!isFound){
            product.getCustomersLiked().add(customer);
        }
        productRepository.save(product);
        apiMessageDto.setMessage("Update favorite success");
        return apiMessageDto;
    }

    // For store to update sell status
    @PutMapping(value = "/update-sell-status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> updateSellStatus(@Valid @RequestBody UpdateSellStatusForm updateSellStatusForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Product product = productRepository.findById(updateSellStatusForm.getProductId()).orElse(null);
        if(product == null || !product.getStatus().equals(Constants.STATUS_ACTIVE)){
            throw new RequestException(ErrorCode.PRODUCT_NOT_FOUND, "Not found product.");
        }
        product.setIsSoldOut(updateSellStatusForm.getIsSoldOut());
        productRepository.save(product);
        apiMessageDto.setMessage("Update status success");
        return apiMessageDto;
    }

    private Customer getCurrentCustomer() {
        Long userId = getCurrentUserId();
        Customer customer = customerRepository.findCustomerByAccountId(userId);
        if(customer == null || !customer.getStatus().equals(Constants.STATUS_ACTIVE)){
            throw new RequestException(ErrorCode.CUSTOMER_ERROR_NOT_FOUND, "Not found current customer.");
        }
        return customer;
    }

    @GetMapping(value = "/auto-complete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<List<ProductDto>> autoComplete(@Valid ProductCriteria productCriteria) {
        Page<Product> productPage = productRepository.findAll(productCriteria.getSpecification(), Pageable.unpaged());
        List<ProductDto> productDtoList = productMapper.fromProductEntityListToDtoListAutoComplete(productPage.getContent());
        return new ApiMessageDto<>(productDtoList, "Get list successfully");
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ProductAdminDto> get(@PathVariable(name = "id") Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RequestException(ErrorCode.PRODUCT_NOT_FOUND, "Product not found"));
        ProductAdminDto productAdminDto = productMapper.fromProductEntityToAdminDto(product);
        return new ApiMessageDto<>(productAdminDto, "Get product successfully");
    }

    @Transactional
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> create(@Valid @RequestBody CreateProductForm createProductForm, BindingResult bindingResult) {
        Product product = productMapper.fromCreateProductFormToEntity(createProductForm);
        if (createProductForm.getCategoryId() != null) {
            ProductCategory category = productCategoryRepository.findById(createProductForm.getCategoryId())
                    .orElseThrow(() -> new RequestException(ErrorCode.PRODUCT_CATEGORY_ERROR_NOT_FOUND, "Product category not found"));
            product.setCategory(category);
        }
        if (createProductForm.getParentProductId() != null) {
            Product parentProduct = productRepository.findById(createProductForm.getParentProductId())
                    .orElseThrow(() -> new RequestException(ErrorCode.PRODUCT_NOT_FOUND, "Parent product not found"));
            if (!parentProduct.getKind().equals(Constants.PRODUCT_KIND_GROUP)) {
                parentProduct.setKind(Constants.PRODUCT_KIND_GROUP);
                parentProduct = productRepository.saveAndFlush(parentProduct);
            }
            product.setParentProduct(parentProduct);
        }
        productRepository.save(product);
        return new ApiMessageDto<>("Create product successfully");
    }

    @Transactional
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateProductForm updateProductForm, BindingResult bindingResult) {
        Product product = productRepository.findById(updateProductForm.getId())
                .orElseThrow(() -> new RequestException(ErrorCode.PRODUCT_NOT_FOUND, "Product not found"));
        Map<Long, String> imageMap = new HashMap<>();
        for (var productConfig : product.getProductConfigs()) {
            for (var productVariant : productConfig.getVariants()) {
                if (productVariant.getImage() != null)
                    imageMap.put(productVariant.getId(), productVariant.getImage());
            }
        }
        for (var productConfig : updateProductForm.getProductConfigs()) {
            for (var productVariant : productConfig.getVariants()) {
                if (productVariant.getId() != null) {
                    String image = imageMap.get(productVariant.getId());
                    if (image != null && !image.equals(productVariant.getImage()))
                        commonApiService.deleteFile(image);
                }
            }
        }
        if (updateProductForm.getCategoryId() != null) {
            ProductCategory category = productCategoryRepository.findById(updateProductForm.getCategoryId())
                    .orElseThrow(() -> new RequestException(ErrorCode.PRODUCT_CATEGORY_ERROR_NOT_FOUND, "Product category not found"));
            product.setCategory(category);
        } else {
            product.setCategory(null);
        }

        if (updateProductForm.getParentProductId() != null) {
            Product parentProduct = productRepository.findById(updateProductForm.getParentProductId())
                    .orElseThrow(() -> new RequestException(ErrorCode.PRODUCT_NOT_FOUND, "Parent product not found"));
            if (!parentProduct.getKind().equals(Constants.PRODUCT_KIND_GROUP)) {
                parentProduct.setKind(Constants.PRODUCT_KIND_GROUP);
                parentProduct = productRepository.saveAndFlush(parentProduct);
            }
            product.setParentProduct(parentProduct);
        } else {
            product.setParentProduct(null);
        }
        productMapper.fromUpdateProductFormToEntity(updateProductForm, product);
        productRepository.save(product);
        return new ApiMessageDto<>("Update product successfully");
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> delete(@PathVariable(name = "id") Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RequestException(ErrorCode.PRODUCT_NOT_FOUND, "Product not found"));

        productRepository.delete(product);
        return new ApiMessageDto<>("Delete product successfully");
    }
}
