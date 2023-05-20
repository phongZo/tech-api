package com.tech.api.controller;

import com.tech.api.constant.Constants;
import com.tech.api.dto.orders.CreateOrdersGhnDto;
import com.tech.api.dto.store.ResponseCreateStore;
import com.tech.api.form.employee.CreateEmployeeForm;
import com.tech.api.form.store.*;
import com.tech.api.dto.ApiMessageDto;
import com.tech.api.dto.ErrorCode;
import com.tech.api.dto.ResponseListObj;
import com.tech.api.dto.store.StoreDto;
import com.tech.api.dto.store.VerifyStoreDto;
import com.tech.api.exception.RequestException;
import com.tech.api.intercepter.MyAuthentication;
import com.tech.api.jwt.JWTUtils;
import com.tech.api.jwt.UserJwt;
import com.tech.api.mapper.EmployeeMapper;
import com.tech.api.mapper.StoreMapper;
import com.tech.api.service.RestService;
import com.tech.api.storage.criteria.StoreCriteria;
import com.tech.api.storage.model.Employee;
import com.tech.api.storage.model.Group;
import com.tech.api.storage.model.Store;
import com.tech.api.storage.repository.AccountRepository;
import com.tech.api.storage.repository.EmployeeRepository;
import com.tech.api.storage.repository.GroupRepository;
import com.tech.api.storage.repository.StoreRepository;
import com.tech.api.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/v1/store")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RequiredArgsConstructor
public class StoreController extends ABasicController {
    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    RestService restService;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListObj<StoreDto>> list(StoreCriteria storeCriteria, Pageable pageable) {
        Page<Store> storePage = storeRepository.findAll(storeCriteria.getSpecification(), pageable);
        List<StoreDto> storeDtoList = storeMapper.fromStoreEntityListToDtoList(storePage.getContent());
        return new ApiMessageDto<>(new ResponseListObj<>(storeDtoList, storePage), "Get list successfully");
    }

    @GetMapping(value = "/client-list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListObj<StoreDto>> clientList(StoreCriteria storeCriteria, Pageable pageable) {
        storeCriteria.setClientSide(true);
        Page<Store> storePage = storeRepository.findAll(storeCriteria.getSpecification(), pageable);
        List<StoreDto> storeDtoList = storeMapper.fromStoreEntityListToDtoList(storePage.getContent());
        return new ApiMessageDto<>(new ResponseListObj<>(storeDtoList, storePage), "Get list successfully");
    }

    @GetMapping(value = "/auto-complete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<List<StoreDto>> autoComplete(StoreCriteria storeCriteria) {
        Page<Store> storePage = storeRepository.findAll(storeCriteria.getSpecification(), Pageable.unpaged());
        return new ApiMessageDto<>(
                storeMapper.fromStoreEntityListToDtoList(storePage.getContent()),
                "Get list successfully"
        );
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<StoreDto> get(@PathVariable(name = "id") Long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new RequestException(ErrorCode.STORE_ERROR_NOT_FOUND, "Store not found"));
        return new ApiMessageDto<>(storeMapper.fromStoreEntityToDto(store), "Get store successfully");
    }

    @PutMapping(value = "/update-status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> updateStatus(@Valid @RequestBody UpdateStoreStatusForm updateStoreStatusForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Store store = storeRepository.findById(getCurrentStoreId()).orElse(null);
        if (store == null || !Objects.equals(store.getStatus() , Constants.STATUS_ACTIVE)) {
            throw new RequestException(ErrorCode.STORE_ERROR_NOT_FOUND, "Not found store");
        }
        store.setIsAcceptOrder(updateStoreStatusForm.getIsAcceptOrder());
        storeRepository.save(store);
        apiMessageDto.setMessage("Update store status success");
        return apiMessageDto;
    }

    @PostMapping(value = "/verify-device", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<VerifyStoreDto> verifyDevice(@Valid @RequestBody VerifyDeviceForm verifyDeviceForm, BindingResult bindingResult) {
        ApiMessageDto<VerifyStoreDto> apiMessageDto = new ApiMessageDto<>();
        Store store = storeRepository.findByPosId(verifyDeviceForm.getPosId());
        if (store == null || !verifyDeviceForm.getSessionId().equals(store.getSessionId()) || !Objects.equals(store.getStatus() , Constants.STATUS_ACTIVE)) {
            throw new RequestException(ErrorCode.STORE_ERROR_VERIFY, "Login fail, check your posId or sessionId");
        }
        VerifyStoreDto dto = new VerifyStoreDto();
        dto.setToken(generateJWT(store));
        dto.setId(store.getId());
        dto.setName(store.getName());
        dto.setIsAcceptOrder(store.getIsAcceptOrder());
        apiMessageDto.setData(dto);
        apiMessageDto.setMessage("Login store success");
        return apiMessageDto;
    }

    @PostMapping(value = "/create_manager", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> create(@Valid @RequestBody CreateEmployeeForm createEmployeeForm, BindingResult bindingResult) {
        if (accountRepository.countAccountByUsernameOrEmailOrPhone(
                createEmployeeForm.getUsername(), createEmployeeForm.getEmail(), createEmployeeForm.getPhone()
        ) > 0)
            throw new RequestException(ErrorCode.ACCOUNT_ERROR_EXISTED, "Account is existed");
        Group groupStoreManager = groupRepository.findFirstByKind(Constants.GROUP_KIND_STORE_MANAGER);
        if(groupStoreManager == null){
            throw new RequestException(ErrorCode.GENERAL_ERROR_NOT_FOUND, "Group store manager not found");
        }
        Store store = storeRepository.findById(createEmployeeForm.getStoreId())
                .orElseThrow(() -> new RequestException(ErrorCode.STORE_ERROR_NOT_FOUND, "Store not found"));
        Employee employee = employeeMapper.fromCreateEmployeeFormToEntity(createEmployeeForm);
        employee.getAccount().setGroup(groupStoreManager);
        employee.getAccount().setKind(Constants.GROUP_KIND_STORE_MANAGER);
        employee.setStore(store);
        employeeRepository.save(employee);
        return new ApiMessageDto<>("Create employee successfully");
    }

    private String generateJWT(Store store) {
        LocalDate parsedDate = LocalDate.now();
        parsedDate = parsedDate.plusDays(7);

        UserJwt qrJwt = new UserJwt();
        qrJwt.setPosId(store.getId());
        String appendStringRole = Constants.POS_DEVICE_PERMISSION;
        qrJwt.setDeviceId(store.getPosId());
        qrJwt.setPemission(appendStringRole);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(new MyAuthentication(qrJwt));

        log.info("jwt user ne: {}", qrJwt);
        return JWTUtils.createJWT(JWTUtils.ALGORITHMS_HMAC, "authenticationToken.getId().toString()", qrJwt, DateUtils.convertToDateViaInstant(parsedDate));

    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> create(@Valid @RequestBody CreateStoreForm createStoreForm, BindingResult bindingResult) {
        if(!isAdmin()){
            throw new RequestException(ErrorCode.STORE_ERROR_UNAUTHORIZED, "Not allowed to create.");
        }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Store store = storeMapper.fromCreateStoreFormToEntity(createStoreForm);

        // call to ghn api to create store
        Long shopId = requestToCreateStoreGhn(store);
        store.setShopId(shopId);
        storeRepository.save(store);
        apiMessageDto.setMessage("Create store success");
        return apiMessageDto;
    }

    private Long requestToCreateStoreGhn(Store store) {
        GhnCreateStoreForm form = new GhnCreateStoreForm();
        form.setDistrictId(store.getDistrictCode());
        form.setWardCode(store.getWardCode());
        form.setName(store.getName());
        form.setPhone(store.getPhone());
        form.setAddress(store.getAddressDetails().split(",")[0]);

        String base = "/shiip/public-api/v2/shop/register";
        ApiMessageDto<ResponseCreateStore> result = restService.POST(null,form,base,null, ResponseCreateStore.class);
        if(result != null && result.getData() != null){
            return result.getData().getShopId();
        }
        return null;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateStoreForm updateStoreForm, BindingResult bindingResult) {
        if(!isAdmin()){
            throw new RequestException(ErrorCode.STORE_ERROR_UNAUTHORIZED, "Not allowed to update.");
        }
        Store store = storeRepository.findById(updateStoreForm.getId())
                .orElseThrow(() -> new RequestException(ErrorCode.STORE_ERROR_NOT_FOUND, "Store not found"));
        storeMapper.fromUpdateStoreFormToEntity(updateStoreForm, store);
        storeRepository.save(store);
        return new ApiMessageDto<>("Update store successfully");
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> delete(@PathVariable(name = "id") Long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new RequestException(ErrorCode.STORE_ERROR_NOT_FOUND, "Store not found"));
        /*storeRepository.delete(store);*/
        store.setStatus(Constants.STATUS_LOCK);
        storeRepository.save(store);
        return new ApiMessageDto<>("Delete store successfully");
    }
}
