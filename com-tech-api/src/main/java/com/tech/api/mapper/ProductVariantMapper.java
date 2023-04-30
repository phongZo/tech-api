package com.tech.api.mapper;

import com.tech.api.dto.productvariant.ProductVariantAdminDto;
import com.tech.api.dto.productvariant.ProductVariantDto;
import com.tech.api.form.productvariant.CreateProductVariantForm;
import com.tech.api.storage.model.ProductVariant;
import com.tech.api.form.productvariant.UpdateProductVariantForm;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ProductVariantMapper {

    @Named("fromCreateProductVariantFormToEntityMapper")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "name", target = "name")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "image", target = "image")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "color", target = "color")
    ProductVariant fromCreateProductVariantFormToEntity(CreateProductVariantForm createProductVariantForm);

    @Named("fromCreateProductVariantFormListToEntityListMapper")
    @IterableMapping(elementTargetType = ProductVariant.class, qualifiedByName = "fromCreateProductVariantFormToEntityMapper")
    List<ProductVariant> fromCreateProductVariantFormListToEntityList(List<CreateProductVariantForm> createProductVariantForms);

    @Named("fromProductVariantEntityToDtoMapper")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "color", target = "color")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "image", target = "image")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "description", target = "description")
    ProductVariantDto fromProductVariantEntityToDto(ProductVariant productVariant);

    @Named("fromProductVariantEntityListToDtoListMapper")
    @IterableMapping(elementTargetType = ProductVariantDto.class, qualifiedByName = "fromProductVariantEntityToDtoMapper")
    List<ProductVariantDto> fromProductVariantEntityListToDtoList(List<ProductVariant> variants);


    @Named("fromProductVariantEntityToDtoAutoComplete")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "image", target = "image")
    ProductVariantDto fromProductVariantEntityToDtoAutoComplete(ProductVariant productVariant);

    @Named("fromProductVariantEntityListToDtoListAutoComplete")
    @IterableMapping(elementTargetType = ProductVariantDto.class, qualifiedByName = "fromProductVariantEntityToDtoAutoComplete")
    List<ProductVariantDto> fromProductVariantEntityListToDtoAutoComplete(List<ProductVariant> variants);


    @Named("fromProductVariantEntityToAdminDtoMapper")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "image", target = "image")
    @Mapping(source = "description", target = "description")
    ProductVariantAdminDto fromProductVariantEntityToAdminDto(ProductVariant productVariant);

    @Named("fromProductVariantEntityListToAdminDtoListMapper")
    @IterableMapping(elementTargetType = ProductVariantAdminDto.class, qualifiedByName = "fromProductVariantEntityToAdminDtoMapper")
    List<ProductVariantAdminDto> fromProductVariantEntityListToAdminDtoList(List<ProductVariant> variants);

    @Named("fromUpdateProductVariantFormToEntityMapper")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "image", target = "image")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "status", target = "status")
    ProductVariant fromUpdateProductVariantFormToEntity(UpdateProductVariantForm updateProductVariantForm);

    @Named("fromUpdateProductVariantFormListToEntityListMapper")
    @BeanMapping(ignoreByDefault = true)
    @InheritConfiguration(name = "fromUpdateProductVariantFormToEntity")
    @IterableMapping(elementTargetType = ProductVariant.class, qualifiedByName = "fromUpdateProductVariantFormToEntityMapper")
    List<ProductVariant> fromUpdateProductVariantFormListToEntityList(List<UpdateProductVariantForm> updateProductVariantForm);

}
