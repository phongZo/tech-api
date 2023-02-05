package com.tech.api.mapper;

import com.tech.api.dto.productconfig.ProductConfigAdminDto;
import com.tech.api.dto.productconfig.ProductConfigDto;
import com.tech.api.form.productconfig.CreateProductConfigForm;
import com.tech.api.form.productconfig.UpdateProductConfigForm;
import com.tech.api.storage.model.ProductConfig;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-05T13:12:03+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.13 (Oracle Corporation)"
)
@Component
public class ProductConfigMapperImpl implements ProductConfigMapper {

    @Autowired
    private ProductVariantMapper productVariantMapper;

    @Override
    public ProductConfig fromCreateProductConfigFormToEntity(CreateProductConfigForm createProductConfigForm) {
        if ( createProductConfigForm == null ) {
            return null;
        }

        ProductConfig productConfig = new ProductConfig();

        productConfig.setIsRequired( createProductConfigForm.getIsRequired() );
        productConfig.setChoiceKind( createProductConfigForm.getChoiceKind() );
        productConfig.setName( createProductConfigForm.getName() );
        productConfig.setVariants( productVariantMapper.fromCreateProductVariantFormListToEntityList( createProductConfigForm.getVariants() ) );
        productConfig.setStatus( createProductConfigForm.getStatus() );

        return productConfig;
    }

    @Override
    public List<ProductConfig> fromCreateProductConfigFormListToEntityList(List<CreateProductConfigForm> createProductConfigForms) {
        if ( createProductConfigForms == null ) {
            return null;
        }

        List<ProductConfig> list = new ArrayList<ProductConfig>( createProductConfigForms.size() );
        for ( CreateProductConfigForm createProductConfigForm : createProductConfigForms ) {
            list.add( fromCreateProductConfigFormToEntity( createProductConfigForm ) );
        }

        return list;
    }

    @Override
    public ProductConfigDto fromProductConfigEntityToDto(ProductConfig productConfig) {
        if ( productConfig == null ) {
            return null;
        }

        ProductConfigDto productConfigDto = new ProductConfigDto();

        productConfigDto.setName( productConfig.getName() );
        productConfigDto.setIsRequired( productConfig.getIsRequired() );
        productConfigDto.setChoiceKind( productConfig.getChoiceKind() );
        productConfigDto.setId( productConfig.getId() );
        productConfigDto.setVariants( productVariantMapper.fromProductVariantEntityListToDtoList( productConfig.getVariants() ) );

        return productConfigDto;
    }

    @Override
    public List<ProductConfigDto> fromProductConfigEntityListToDtoList(List<ProductConfig> productConfigs) {
        if ( productConfigs == null ) {
            return null;
        }

        List<ProductConfigDto> list = new ArrayList<ProductConfigDto>( productConfigs.size() );
        for ( ProductConfig productConfig : productConfigs ) {
            list.add( fromProductConfigEntityToDto( productConfig ) );
        }

        return list;
    }

    @Override
    public ProductConfigAdminDto fromProductConfigEntityToAdminDto(ProductConfig productConfig) {
        if ( productConfig == null ) {
            return null;
        }

        ProductConfigAdminDto productConfigAdminDto = new ProductConfigAdminDto();

        productConfigAdminDto.setName( productConfig.getName() );
        productConfigAdminDto.setIsRequired( productConfig.getIsRequired() );
        productConfigAdminDto.setChoiceKind( productConfig.getChoiceKind() );
        productConfigAdminDto.setId( productConfig.getId() );
        productConfigAdminDto.setVariants( productVariantMapper.fromProductVariantEntityListToAdminDtoList( productConfig.getVariants() ) );
        productConfigAdminDto.setStatus( productConfig.getStatus() );
        productConfigAdminDto.setModifiedDate( productConfig.getModifiedDate() );
        productConfigAdminDto.setCreatedDate( productConfig.getCreatedDate() );
        productConfigAdminDto.setModifiedBy( productConfig.getModifiedBy() );
        productConfigAdminDto.setCreatedBy( productConfig.getCreatedBy() );

        return productConfigAdminDto;
    }

    @Override
    public List<ProductConfigAdminDto> fromProductConfigEntityListToAdminDtoList(List<ProductConfig> productConfigs) {
        if ( productConfigs == null ) {
            return null;
        }

        List<ProductConfigAdminDto> list = new ArrayList<ProductConfigAdminDto>( productConfigs.size() );
        for ( ProductConfig productConfig : productConfigs ) {
            list.add( fromProductConfigEntityToAdminDto( productConfig ) );
        }

        return list;
    }

    @Override
    public ProductConfig frommUpdateProductConfigFormToEntity(UpdateProductConfigForm updateProductConfigForm) {
        if ( updateProductConfigForm == null ) {
            return null;
        }

        ProductConfig productConfig = new ProductConfig();

        productConfig.setIsRequired( updateProductConfigForm.getIsRequired() );
        productConfig.setChoiceKind( updateProductConfigForm.getChoiceKind() );
        productConfig.setName( updateProductConfigForm.getName() );
        productConfig.setId( updateProductConfigForm.getId() );
        productConfig.setVariants( productVariantMapper.fromUpdateProductVariantFormListToEntityList( updateProductConfigForm.getVariants() ) );
        productConfig.setStatus( updateProductConfigForm.getStatus() );

        return productConfig;
    }

    @Override
    public List<ProductConfig> frommUpdateProductConfigFormListToEntityList(List<UpdateProductConfigForm> updateProductConfigForms) {
        if ( updateProductConfigForms == null ) {
            return null;
        }

        List<ProductConfig> list = new ArrayList<ProductConfig>( updateProductConfigForms.size() );
        for ( UpdateProductConfigForm updateProductConfigForm : updateProductConfigForms ) {
            list.add( frommUpdateProductConfigFormToEntity( updateProductConfigForm ) );
        }

        return list;
    }
}
