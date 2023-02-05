package com.tech.api.mapper;

import com.tech.api.dto.productvariant.ProductVariantAdminDto;
import com.tech.api.dto.productvariant.ProductVariantDto;
import com.tech.api.form.productvariant.CreateProductVariantForm;
import com.tech.api.form.productvariant.UpdateProductVariantForm;
import com.tech.api.storage.model.ProductVariant;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-05T13:12:01+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.13 (Oracle Corporation)"
)
@Component
public class ProductVariantMapperImpl implements ProductVariantMapper {

    @Override
    public ProductVariant fromCreateProductVariantFormToEntity(CreateProductVariantForm createProductVariantForm) {
        if ( createProductVariantForm == null ) {
            return null;
        }

        ProductVariant productVariant = new ProductVariant();

        productVariant.setImage( createProductVariantForm.getImage() );
        if ( createProductVariantForm.getOrderSort() != null ) {
            productVariant.setOrderSort( String.valueOf( createProductVariantForm.getOrderSort() ) );
        }
        productVariant.setPrice( createProductVariantForm.getPrice() );
        productVariant.setName( createProductVariantForm.getName() );
        productVariant.setDescription( createProductVariantForm.getDescription() );
        productVariant.setStatus( createProductVariantForm.getStatus() );

        return productVariant;
    }

    @Override
    public List<ProductVariant> fromCreateProductVariantFormListToEntityList(List<CreateProductVariantForm> createProductVariantForms) {
        if ( createProductVariantForms == null ) {
            return null;
        }

        List<ProductVariant> list = new ArrayList<ProductVariant>( createProductVariantForms.size() );
        for ( CreateProductVariantForm createProductVariantForm : createProductVariantForms ) {
            list.add( fromCreateProductVariantFormToEntity( createProductVariantForm ) );
        }

        return list;
    }

    @Override
    public ProductVariantDto fromProductVariantEntityToDto(ProductVariant productVariant) {
        if ( productVariant == null ) {
            return null;
        }

        ProductVariantDto productVariantDto = new ProductVariantDto();

        productVariantDto.setImage( productVariant.getImage() );
        productVariantDto.setOrderSort( productVariant.getOrderSort() );
        productVariantDto.setPrice( productVariant.getPrice() );
        productVariantDto.setName( productVariant.getName() );
        productVariantDto.setDescription( productVariant.getDescription() );
        productVariantDto.setId( productVariant.getId() );

        return productVariantDto;
    }

    @Override
    public List<ProductVariantDto> fromProductVariantEntityListToDtoList(List<ProductVariant> variants) {
        if ( variants == null ) {
            return null;
        }

        List<ProductVariantDto> list = new ArrayList<ProductVariantDto>( variants.size() );
        for ( ProductVariant productVariant : variants ) {
            list.add( fromProductVariantEntityToDto( productVariant ) );
        }

        return list;
    }

    @Override
    public ProductVariantDto fromProductVariantEntityToDtoAutoComplete(ProductVariant productVariant) {
        if ( productVariant == null ) {
            return null;
        }

        ProductVariantDto productVariantDto = new ProductVariantDto();

        productVariantDto.setName( productVariant.getName() );
        productVariantDto.setImage( productVariant.getImage() );
        productVariantDto.setId( productVariant.getId() );
        productVariantDto.setPrice( productVariant.getPrice() );

        return productVariantDto;
    }

    @Override
    public List<ProductVariantDto> fromProductVariantEntityListToDtoAutoComplete(List<ProductVariant> variants) {
        if ( variants == null ) {
            return null;
        }

        List<ProductVariantDto> list = new ArrayList<ProductVariantDto>( variants.size() );
        for ( ProductVariant productVariant : variants ) {
            list.add( fromProductVariantEntityToDtoAutoComplete( productVariant ) );
        }

        return list;
    }

    @Override
    public ProductVariantAdminDto fromProductVariantEntityToAdminDto(ProductVariant productVariant) {
        if ( productVariant == null ) {
            return null;
        }

        ProductVariantAdminDto productVariantAdminDto = new ProductVariantAdminDto();

        productVariantAdminDto.setImage( productVariant.getImage() );
        productVariantAdminDto.setOrderSort( productVariant.getOrderSort() );
        productVariantAdminDto.setPrice( productVariant.getPrice() );
        productVariantAdminDto.setName( productVariant.getName() );
        productVariantAdminDto.setDescription( productVariant.getDescription() );
        productVariantAdminDto.setId( productVariant.getId() );
        productVariantAdminDto.setStatus( productVariant.getStatus() );
        productVariantAdminDto.setModifiedDate( productVariant.getModifiedDate() );
        productVariantAdminDto.setCreatedDate( productVariant.getCreatedDate() );
        productVariantAdminDto.setModifiedBy( productVariant.getModifiedBy() );
        productVariantAdminDto.setCreatedBy( productVariant.getCreatedBy() );

        return productVariantAdminDto;
    }

    @Override
    public List<ProductVariantAdminDto> fromProductVariantEntityListToAdminDtoList(List<ProductVariant> variants) {
        if ( variants == null ) {
            return null;
        }

        List<ProductVariantAdminDto> list = new ArrayList<ProductVariantAdminDto>( variants.size() );
        for ( ProductVariant productVariant : variants ) {
            list.add( fromProductVariantEntityToAdminDto( productVariant ) );
        }

        return list;
    }

    @Override
    public ProductVariant fromUpdateProductVariantFormToEntity(UpdateProductVariantForm updateProductVariantForm) {
        if ( updateProductVariantForm == null ) {
            return null;
        }

        ProductVariant productVariant = new ProductVariant();

        productVariant.setImage( updateProductVariantForm.getImage() );
        if ( updateProductVariantForm.getOrderSort() != null ) {
            productVariant.setOrderSort( String.valueOf( updateProductVariantForm.getOrderSort() ) );
        }
        productVariant.setPrice( updateProductVariantForm.getPrice() );
        productVariant.setName( updateProductVariantForm.getName() );
        productVariant.setDescription( updateProductVariantForm.getDescription() );
        productVariant.setId( updateProductVariantForm.getId() );
        productVariant.setStatus( updateProductVariantForm.getStatus() );

        return productVariant;
    }

    @Override
    public List<ProductVariant> fromUpdateProductVariantFormListToEntityList(List<UpdateProductVariantForm> updateProductVariantForm) {
        if ( updateProductVariantForm == null ) {
            return null;
        }

        List<ProductVariant> list = new ArrayList<ProductVariant>( updateProductVariantForm.size() );
        for ( UpdateProductVariantForm updateProductVariantForm1 : updateProductVariantForm ) {
            list.add( fromUpdateProductVariantFormToEntity( updateProductVariantForm1 ) );
        }

        return list;
    }
}
