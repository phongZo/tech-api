package com.tech.api.controller;

import com.tech.api.constant.Constants;
import com.tech.api.dto.ApiMessageDto;
import com.tech.api.dto.ResponseListObj;
import com.tech.api.form.category.CreateCategoryForm;
import com.tech.api.form.category.UpdateCategoryForm;
import com.tech.api.service.CommonApiService;
import com.tech.api.storage.criteria.CategoryCriteria;
import com.tech.api.dto.ErrorCode;
import com.tech.api.dto.category.CategoryDto;
import com.tech.api.exception.RequestException;
import com.tech.api.mapper.CategoryMapper;
import com.tech.api.storage.model.Category;
import com.tech.api.storage.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/category")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class CategoryController extends ABasicController{

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    CommonApiService commonApiService;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListObj<CategoryDto>> list(CategoryCriteria categoryCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListObj<CategoryDto>> responseListObjApiMessageDto = new ApiMessageDto<>();

        Page<Category> listCategory = categoryRepository.findAll(categoryCriteria.getSpecification(), pageable);
        ResponseListObj<CategoryDto> responseListObj = new ResponseListObj<>();
        responseListObj.setData(categoryMapper.fromEntityListToCategoryDtoList(listCategory.getContent()));
        responseListObj.setPage(pageable.getPageNumber());
        responseListObj.setTotalPage(listCategory.getTotalPages());
        responseListObj.setTotalElements(listCategory.getTotalElements());

        responseListObjApiMessageDto.setData(responseListObj);
        responseListObjApiMessageDto.setMessage("Get list success");
        return responseListObjApiMessageDto;
    }

    @GetMapping(value = "/auto-complete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListObj<CategoryDto>> autoComplete(CategoryCriteria categoryCriteria) {
        ApiMessageDto<ResponseListObj<CategoryDto>> responseListObjApiMessageDto = new ApiMessageDto<>();
        categoryCriteria.setStatus(Constants.STATUS_ACTIVE);
        Page<Category> listCategory = categoryRepository.findAll(categoryCriteria.getSpecification(), Pageable.unpaged());
        ResponseListObj<CategoryDto> responseListObj = new ResponseListObj<>();
        responseListObj.setData(categoryMapper.fromEntityListToCategoryDtoAutoComplete(listCategory.getContent()));

        responseListObjApiMessageDto.setData(responseListObj);
        responseListObjApiMessageDto.setMessage("Get list success");
        return responseListObjApiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<CategoryDto> get(@PathVariable("id") Long id) {
        ApiMessageDto<CategoryDto> result = new ApiMessageDto<>();

        Category category = categoryRepository.findById(id).orElse(null);
        if(category == null) {
            throw new RequestException(ErrorCode.CATEGORY_ERROR_NOT_FOUND, "Not found category.");
        }
        result.setData(categoryMapper.fromEntityToAdminDto(category));
        result.setMessage("Get category success");
        return result;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> create(@Valid @RequestBody CreateCategoryForm createCategoryForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();

        Category category = categoryMapper.fromCreateCategoryFormToEntity(createCategoryForm);
        if(createCategoryForm.getParentId() != null) {
            Category parentCategory = categoryRepository.findById(createCategoryForm.getParentId()).orElse(null);
            if(parentCategory == null || parentCategory.getParentCategory() != null) {
                throw new RequestException(ErrorCode.CATEGORY_ERROR_NOT_FOUND, "Not found category parent");
            }
            category.setParentCategory(parentCategory);
        }
        categoryRepository.save(category);
        apiMessageDto.setMessage("Create category success");
        return apiMessageDto;

    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateCategoryForm updateCategoryForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Category category = categoryRepository.findById(updateCategoryForm.getId()).orElse(null);
        if(category == null) {
            throw new RequestException(ErrorCode.CATEGORY_ERROR_NOT_FOUND, "Not found category.");
        }
        if(category.getStatus().equals(updateCategoryForm.getStatus()) && category.getParentCategory() == null) {
            category.getCategoryList().forEach(child -> child.setStatus(updateCategoryForm.getStatus()));
            categoryRepository.saveAll(category.getCategoryList());
        }
        if(StringUtils.isNoneBlank(category.getImage()) && !updateCategoryForm.getCategoryImage().equals(category.getImage())) {
            commonApiService.deleteFile(category.getImage());
        }
        categoryMapper.fromUpdateCategoryFormToEntity(updateCategoryForm, category);
        categoryRepository.save(category);
        apiMessageDto.setMessage("Update category success");
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}")
    public ApiMessageDto<CategoryDto> delete(@PathVariable("id") Long id) {
        ApiMessageDto<CategoryDto> result = new ApiMessageDto<>();

        Category category = categoryRepository.findById(id).orElse(null);
        if(category == null) {
            throw new RequestException(ErrorCode.CATEGORY_ERROR_NOT_FOUND, "Not found category");
        }
        commonApiService.deleteFile(category.getImage());
        categoryRepository.delete(category);
        result.setMessage("Delete category success");
        return result;
    }
}
