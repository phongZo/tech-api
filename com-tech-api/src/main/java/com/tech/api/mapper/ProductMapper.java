package com.tech.api.mapper;

import com.tech.api.dto.product.ProductAdminDto;
import com.tech.api.dto.product.ProductDto;
import com.tech.api.form.product.CreateProductForm;
import com.tech.api.form.product.UpdateProductForm;
import com.tech.api.storage.model.Product;
import org.mapstruct.*;

import java.text.DecimalFormat;
import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {ProductConfigMapper.class}
)
public interface ProductMapper {

    @Named("fromCreateProductFormToEntityMapper")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "description", target = "description")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "image", target = "image")
    @Mapping(source = "isSoldOut", target = "isSoldOut")
    @Mapping(source = "isSaleOff", target = "isSaleOff")
    @Mapping(source = "saleOff", target = "saleOff")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "productConfigs", target = "productConfigs", qualifiedByName = "fromCreateProductConfigFormListToEntityListMapper")
    Product fromCreateProductFormToEntity(CreateProductForm createProductForm);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "saleOff", target = "saleOff")
    @Mapping(source = "isSaleOff", target = "isSaleOff")
    @Mapping(source = "image", target = "image")
    @Mapping(source = "isSoldOut", target = "isSoldOut")
    @Mapping(source = "category.id", target = "productCategoryId")
    @Mapping(source = "avgStar", target = "avgStar")
    @Mapping(source = "totalReview", target = "totalReview")
    @Mapping(source = "soldAmount", target = "soldAmount")
    @Mapping(source = "price", target = "price")
    @BeanMapping(ignoreByDefault = true)
    @Named("clientGetMapping")
    ProductDto fromEntityToClientDto(Product product);

    @IterableMapping(elementTargetType = ProductDto.class, qualifiedByName = "clientGetMapping")
    List<ProductDto> fromEntityListToProductClientDtoList(List<Product> products);

    @Named("fromProductEntityToDtoMapper")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "category.id", target = "productCategoryId")
    @Mapping(source = "saleOff", target = "saleOff")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "image", target = "image")
    @Mapping(source = "isSoldOut", target = "isSoldOut")
    @Mapping(source = "parentProduct.id", target = "parentProductId")
    ProductDto fromProductEntityToDto(Product product);

    @Named("fromProductEntityListToDtoListMapper")
    @IterableMapping(elementTargetType = ProductDto.class, qualifiedByName = "fromProductEntityToDtoMapper")
    List<ProductDto> fromProductEntityListToDtoList(List<Product> products);

    @Named("fromProductEntityToDtoAutoCompleteMapper")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "image", target = "image")
    @Mapping(source = "isSoldOut", target = "isSoldOut")
    ProductDto fromProductEntityToDtoAutoComplete(Product product);

    @Named("fromProductEntityListToDtoListAutoCompleteMapper")
    @IterableMapping(elementTargetType = ProductDto.class, qualifiedByName = "fromProductEntityToDtoAutoCompleteMapper")
    List<ProductDto> fromProductEntityListToDtoListAutoComplete(List<Product> products);

    @Named("fromProductEntityToDtoDetails")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "category.id", target = "productCategoryId")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "image", target = "image")
    @Mapping(source = "isSoldOut", target = "isSoldOut")
    @Mapping(source = "parentProduct.id", target = "parentProductId")
    @Mapping(source = "productConfigs", target = "productConfigs", qualifiedByName = "fromProductConfigEntityListToDtoListMapper")
    ProductDto fromProductEntityToDtoDetails(Product product);

    @Named("fromProductEntityToAdminDtoMapper")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "isSaleOff", target = "isSaleOff")
    @Mapping(source = "saleOff", target = "saleOff")
    @Mapping(source = "image", target = "image")
    @Mapping(source = "isSoldOut", target = "isSoldOut")
    ProductAdminDto fromProductEntityToAdminDto(Product product);

    @Named("fromProductEntityListToAdminDtoListMapper")
    @IterableMapping(elementTargetType = ProductAdminDto.class, qualifiedByName = "fromProductEntityToAdminDtoMapper")
    List<ProductAdminDto> fromProductEntityListToAdminDtoList(List<Product> products);

    @Named("fromUpdateProductFormToEntityMapper")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "description", target = "description")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "image", target = "image")
    @Mapping(source = "isSoldOut", target = "isSoldOut")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "productConfigs", target = "productConfigs", qualifiedByName = "frommUpdateProductConfigFormListToEntityListMapper")
    void fromUpdateProductFormToEntity(UpdateProductForm updateProductForm, @MappingTarget Product product);
}
