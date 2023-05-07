package com.tech.api.mapper;

import com.tech.api.dto.productconfig.ProductConfigAdminDto;
import com.tech.api.dto.productconfig.ProductConfigDto;
import com.tech.api.form.productconfig.CreateProductConfigForm;
import com.tech.api.form.productconfig.UpdateProductConfigForm;
import com.tech.api.storage.model.ProductConfig;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {ProductVariantMapper.class}
)
public interface ProductConfigMapper {

    @Named("fromCreateProductConfigFormToEntityMapper")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "name", target = "name")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "variants", target = "variants", qualifiedByName = "fromCreateProductVariantFormListToEntityListMapper")
    ProductConfig fromCreateProductConfigFormToEntity(CreateProductConfigForm createProductConfigForm);

    @Named("fromCreateProductConfigFormListToEntityListMapper")
    @IterableMapping(elementTargetType = ProductConfig.class, qualifiedByName = "fromCreateProductConfigFormToEntityMapper")
    List<ProductConfig> fromCreateProductConfigFormListToEntityList(List<CreateProductConfigForm> createProductConfigForms);

    @Named("fromProductConfigEntityToDtoMapper")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "variants", target = "variants", qualifiedByName = "fromProductVariantEntityListToDtoListMapper")
    ProductConfigDto fromProductConfigEntityToDto(ProductConfig productConfig);

    @Named("fromProductConfigEntityListToDtoListMapper")
    @IterableMapping(elementTargetType = ProductConfigDto.class, qualifiedByName = "fromProductConfigEntityToDtoMapper")
    List<ProductConfigDto> fromProductConfigEntityListToDtoList(List<ProductConfig> productConfigs);

    @Named("fromProductConfigEntityToAdminDtoMapper")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "variants", target = "variants", qualifiedByName = "fromProductVariantEntityListToDtoAutoComplete")
    ProductConfigAdminDto fromProductConfigEntityToAdminDto(ProductConfig productConfig);

    @Named("fromProductConfigEntityListToAdminDtoListMapper")
    @IterableMapping(elementTargetType = ProductConfigAdminDto.class, qualifiedByName = "fromProductConfigEntityToAdminDtoMapper")
    List<ProductConfigAdminDto> fromProductConfigEntityListToAdminDtoList(List<ProductConfig> productConfigs);

    @Named("fromUpdateProductConfigFormToEntityMapper")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "variants", target = "variants", qualifiedByName = "fromUpdateProductVariantFormListToEntityListMapper")
    ProductConfig frommUpdateProductConfigFormToEntity(UpdateProductConfigForm updateProductConfigForm);

    @Named("frommUpdateProductConfigFormListToEntityListMapper")
    @IterableMapping(elementTargetType = ProductConfig.class, qualifiedByName = "fromUpdateProductConfigFormToEntityMapper")
    List<ProductConfig> frommUpdateProductConfigFormListToEntityList(List<UpdateProductConfigForm> updateProductConfigForms);
}
