package com.tech.api.controller;

import com.tech.api.constant.Constants;
import com.tech.api.form.store.CreateStoreForm;
import com.tech.api.dto.ApiMessageDto;
import com.tech.api.dto.ErrorCode;
import com.tech.api.dto.ResponseListObj;
import com.tech.api.dto.store.StoreDto;
import com.tech.api.dto.store.VerifyStoreDto;
import com.tech.api.exception.RequestException;
import com.tech.api.form.store.UpdateStoreStatusForm;
import com.tech.api.form.store.VerifyDeviceForm;
import com.tech.api.intercepter.MyAuthentication;
import com.tech.api.jwt.JWTUtils;
import com.tech.api.jwt.UserJwt;
import com.tech.api.mapper.StoreMapper;
import com.tech.api.storage.criteria.StoreCriteria;
import com.tech.api.storage.model.Store;
import com.tech.api.storage.repository.StoreRepository;
import com.tech.api.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListObj<StoreDto>> list(StoreCriteria storeCriteria, Pageable pageable) {
        Page<Store> storePage = storeRepository.findAll(storeCriteria.getSpecification(), pageable);
        List<StoreDto> storeDtoList = storeMapper.fromStoreEntityListToDtoList(storePage.getContent());
        return new ApiMessageDto<>(new ResponseListObj<>(storeDtoList, storePage), "Get list successfully");
    }

    @GetMapping(value = "/client-list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListObj<StoreDto>> clientList(StoreCriteria storeCriteria, Pageable pageable) {
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

        //check pos id unique
        Store store = storeRepository.findByPosId(createStoreForm.getPosId());
        if(store != null){
            throw new RequestException(ErrorCode.STORE_ERROR_BAD_REQUEST, "Store existed.");
        }

        store = storeMapper.fromCreateStoreFormToEntity(createStoreForm);
        storeRepository.save(store);
        apiMessageDto.setMessage("Create store success");
        return apiMessageDto;
    }

/*    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateStoreForm updateStoreForm, BindingResult bindingResult) {
        Store store = storeRepository.findById(updateStoreForm.getId())
                .orElseThrow(() -> new RequestException(ErrorCode.STORE_ERROR_NOT_FOUND, "Store not found"));
        Location ward = locationRepository.findById(updateStoreForm.getWardId())
                .orElseThrow(() -> new RequestException(ErrorCode.LOCATION_ERROR_NOTFOUND, "Ward not found"));
        Location district = ward.getParent();
        Location province = district.getParent();
        if (!Objects.equals(district.getId(), updateStoreForm.getDistrictId()))
            throw new RequestException(ErrorCode.LOCATION_ERROR_INVALID, "Invalid district");
        if (!Objects.equals(province.getId(), updateStoreForm.getProvinceId()))
            throw new RequestException(ErrorCode.LOCATION_ERROR_INVALID, "Invalid province");
        storeMapper.fromUpdateStoreFormToEntity(updateStoreForm, store);
        store.setProvince(province);
        store.setDistrict(district);
        store.setWard(ward);
        storeRepository.save(store);
        return new ApiMessageDto<>("Update store successfully");
    }*/

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> delete(@PathVariable(name = "id") Long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new RequestException(ErrorCode.STORE_ERROR_NOT_FOUND, "Store not found"));
        storeRepository.delete(store);
        return new ApiMessageDto<>("Delete store successfully");
    }
}
