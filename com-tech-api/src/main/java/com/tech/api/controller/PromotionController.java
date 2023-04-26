package com.tech.api.controller;

import com.tech.api.constant.Constants;
import com.tech.api.dto.ResponseListObj;
import com.tech.api.dto.promotion.PromotionDto;
import com.tech.api.mapper.PromotionMapper;
import com.tech.api.dto.ApiMessageDto;
import com.tech.api.dto.ErrorCode;
import com.tech.api.exception.RequestException;
import com.tech.api.form.promotion.CreatePromotionForm;
import com.tech.api.storage.criteria.PromotionCriteria;
import com.tech.api.storage.model.Promotion;
import com.tech.api.storage.repository.CustomerRepository;
import com.tech.api.storage.repository.PromotionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/promotion")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class PromotionController extends ABasicController{
    @Autowired
    PromotionMapper promotionMapper;

    @Autowired
    PromotionRepository promotionRepository;

    @Autowired
    CustomerRepository customerRepository;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> create(@Valid @RequestBody CreatePromotionForm createPromotionForm, BindingResult bindingResult) {
        if(!isAdmin()){
            throw new RequestException(ErrorCode.PROMOTION_ERROR_UNAUTHORIZED, "Not allowed to create.");
        }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Promotion promotion = promotionMapper.fromCreateFormToEntity(createPromotionForm);
        if(promotion.getExchangeable() && promotion.getPoint() == null){
            throw new RequestException(ErrorCode.PROMOTION_ERROR_BAD_REQUEST, "Please fill the point.");
        }
        if(!promotion.getExchangeable() && promotion.getLoyaltyLevel() == null){
            throw new RequestException(ErrorCode.PROMOTION_ERROR_BAD_REQUEST, "Please fill the loyalty level.");
        }
        if(promotion.getKind().equals(Constants.PROMOTION_KIND_PERCENT)){
            int value = Integer.parseInt(promotion.getValue());
            if(value < 0 || value > 100){
                throw new RequestException(ErrorCode.PROMOTION_ERROR_BAD_REQUEST, "Value invalid.");
            }
        } else{
            if(promotion.getMaxValueForPercent() != null){
                throw new RequestException(ErrorCode.PROMOTION_ERROR_BAD_REQUEST, "Kind money not have max value.");
            }
        }
        promotionRepository.save(promotion);
        apiMessageDto.setMessage("Create promotion success");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListObj<PromotionDto>> list(PromotionCriteria criteria, Pageable pageable) {
        if(!isAdmin()){
            throw new RequestException(ErrorCode.PROMOTION_ERROR_UNAUTHORIZED, "Not allowed to list promotion.");
        }
        ApiMessageDto<ResponseListObj<PromotionDto>> apiMessageDto = new ApiMessageDto<>();
        Page<Promotion> list = promotionRepository.findAll(criteria.getSpecification(), pageable);
        ResponseListObj<PromotionDto> responseListObj = new ResponseListObj<>();
        responseListObj.setData(promotionMapper.fromEntityListToPromotionListDto(list.getContent()));
        responseListObj.setPage(list.getTotalPages());
        responseListObj.setTotalElements(list.getTotalElements());
        responseListObj.setTotalPage(list.getTotalPages());

        apiMessageDto.setData(responseListObj);
        apiMessageDto.setMessage("Get list success");
        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<PromotionDto> get(@PathVariable("id") Long id) {
        if(!isAdmin()){
            throw new RequestException(ErrorCode.PROMOTION_ERROR_UNAUTHORIZED, "Not allowed to get promotion.");
        }
        ApiMessageDto<PromotionDto> apiMessageDto = new ApiMessageDto<>();
        Promotion promotion = promotionRepository.findById(id).orElseThrow(
                () -> new RequestException(ErrorCode.PROMOTION_ERROR_NOT_FOUND, "Not found promotion"));
        apiMessageDto.setData(promotionMapper.fromEntityToPromotionDto(promotion));
        apiMessageDto.setMessage("Get list success");
        return apiMessageDto;
    }

    @GetMapping(value = "/client-list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListObj<PromotionDto>> clientListPromotion(PromotionCriteria criteria, BindingResult bindingResult) {
        if(!isCustomer()){
            throw new RequestException(ErrorCode.CUSTOMER_ERROR_UNAUTHORIZED, "Not allowed to list promotion.");
        }
        ApiMessageDto<ResponseListObj<PromotionDto>> apiMessageDto = new ApiMessageDto<>();
        criteria.setExchangeable(true);
        List<Promotion> list = promotionRepository.findAll(criteria.getSpecification());
        ResponseListObj<PromotionDto> responseListObj = new ResponseListObj<>();
        responseListObj.setData(promotionMapper.fromEntityListToPromotionListDto(list));
        apiMessageDto.setData(responseListObj);
        apiMessageDto.setMessage("Get list success");
        return apiMessageDto;
    }
}
