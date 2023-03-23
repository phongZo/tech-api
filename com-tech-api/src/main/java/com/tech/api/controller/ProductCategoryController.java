package com.tech.api.controller;

import com.tech.api.dto.ApiMessageDto;
import com.tech.api.dto.ErrorCode;
import com.tech.api.dto.ResponseListObj;
import com.tech.api.dto.productcategory.ProductCategoryDto;
import com.tech.api.form.productcategory.CreateProductCategoryForm;
import com.tech.api.form.productcategory.UpdateProductCategoryForm;
import com.tech.api.mapper.ProductCategoryMapper;
import com.tech.api.service.CommonApiService;
import com.tech.api.storage.criteria.ProductCategoryCriteria;
import com.tech.api.storage.model.ProductCategory;
import com.tech.api.exception.RequestException;
import com.tech.api.storage.repository.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/product-category")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RequiredArgsConstructor
public class ProductCategoryController extends ABasicController {
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductCategoryMapper productCategoryMapper;
    private final CommonApiService commonApiService;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListObj<ProductCategoryDto>> list(ProductCategoryCriteria productCategoryCriteria) {
        List<ProductCategory> categoryList = productCategoryRepository.findAll(productCategoryCriteria.getSpecification(), Sort.by(Sort.Order.asc("orderSort")));
        return new ApiMessageDto<>(
                new ResponseListObj<>(productCategoryMapper.fromProductCategoryListToDtoList(categoryList)),
                "Get list successfully"
        );
    }

    @GetMapping(value = "/auto-complete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListObj<ProductCategoryDto>> autoComplete(ProductCategoryCriteria productCategoryCriteria) {
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll(productCategoryCriteria.getSpecification(), Sort.by(Sort.Order.asc("name")));
        return new ApiMessageDto<>(
                new ResponseListObj<>(productCategoryMapper.fromProductCategoryListToDtoList(productCategoryList)),
                "Get list successfully"
        );
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ProductCategoryDto> get(@PathVariable(name = "id") Long id) {
        ProductCategory productCategory = productCategoryRepository.findById(id)
                .orElseThrow(() -> new RequestException(ErrorCode.PRODUCT_CATEGORY_ERROR_NOT_FOUND, "Product category not found"));
        return new ApiMessageDto<>(productCategoryMapper.fromProductCategoryEntityToDto(productCategory), "Get product category successfully");
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> create(@Valid @RequestBody CreateProductCategoryForm createProductCategoryForm, BindingResult bindingResult) {
        ProductCategory productCategory = productCategoryMapper.fromCreateProductCategoryFormToEntity(createProductCategoryForm);
        if (createProductCategoryForm.getParentId() != null) {
            ProductCategory parent = productCategoryRepository.findById(createProductCategoryForm.getParentId())
                    .orElseThrow(() -> new RequestException(ErrorCode.PRODUCT_CATEGORY_ERROR_NOT_FOUND, "Parent not found"));
            productCategory.setParentCategory(parent);
        }
        productCategoryRepository.save(productCategory);
        return new ApiMessageDto<>("Create product category successfully");
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateProductCategoryForm updateProductCategoryForm, BindingResult bindingResult) {
        ProductCategory productCategory = productCategoryRepository.findById(updateProductCategoryForm.getId())
                .orElseThrow(() -> new RequestException(ErrorCode.PRODUCT_CATEGORY_ERROR_NOT_FOUND, "Parent not found"));
        if (updateProductCategoryForm.getParentId() != null) {
            ProductCategory parent = productCategoryRepository.findById(updateProductCategoryForm.getParentId())
                    .orElseThrow(() -> new RequestException(ErrorCode.PRODUCT_CATEGORY_ERROR_NOT_FOUND, "Parent not found"));
            productCategory.setParentCategory(parent);
        }
        if (StringUtils.isNoneBlank(productCategory.getIcon()) && !updateProductCategoryForm.getIcon().equals(productCategory.getIcon())) {
            commonApiService.deleteFile(productCategory.getIcon());
        }
        productCategoryMapper.fromUpdateProductCategoryFormToEntity(updateProductCategoryForm, productCategory);
        productCategoryRepository.save(productCategory);
        return new ApiMessageDto<>("Update product category successfully");
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> delete(@PathVariable(name = "id") Long id) {
        ProductCategory productCategory = productCategoryRepository.findById(id)
                .orElseThrow(() -> new RequestException(ErrorCode.PRODUCT_CATEGORY_ERROR_NOT_FOUND, "Parent not found"));
        commonApiService.deleteFile(productCategory.getIcon());
        productCategoryRepository.delete(productCategory);
        return new ApiMessageDto<>("Delete product category successfully");
    }
}
