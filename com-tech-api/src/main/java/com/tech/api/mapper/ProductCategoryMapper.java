package com.tech.api.mapper;

import com.tech.api.dto.productcategory.ProductCategoryDto;
import com.tech.api.form.productcategory.CreateProductCategoryForm;
import com.tech.api.form.productcategory.UpdateProductCategoryForm;
import com.tech.api.storage.model.ProductCategory;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ProductCategoryMapper {
    @Named("fromCreateProductCategoryFormToEntityMapper")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "name", target = "name")
    @Mapping(source = "orderSort", target = "orderSort")
    @Mapping(source = "icon", target = "icon")
    @Mapping(source = "note", target = "note")
    @Mapping(source = "status", target = "status")
    ProductCategory fromCreateProductCategoryFormToEntity(CreateProductCategoryForm createProductCategoryForm);

    @Named("fromProductCategoryEntityToDtoMapper")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "parentCategory.id", target = "parentId")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "orderSort", target = "orderSort")
    @Mapping(source = "icon", target = "icon")
    @Mapping(source = "note", target = "note")
    @Mapping(source = "status", target = "status")
    ProductCategoryDto fromProductCategoryEntityToDto(ProductCategory productCategory);

    @Named("fromProductCategoryListToDtoListMapper")
    @IterableMapping(elementTargetType = ProductCategoryDto.class, qualifiedByName = "fromProductCategoryEntityToDtoMapper")
    List<ProductCategoryDto> fromProductCategoryListToDtoList(List<ProductCategory> productCategories);

    @Named("fromUpdateProductCategoryFormToEntityMapper")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "name", target = "name")
    @Mapping(source = "orderSort", target = "orderSort")
    @Mapping(source = "icon", target = "icon")
    @Mapping(source = "note", target = "note")
    @Mapping(source = "status", target = "status")
    void fromUpdateProductCategoryFormToEntity(UpdateProductCategoryForm updateProductCategoryForm, @MappingTarget ProductCategory productCategory);
}
