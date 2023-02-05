package com.tech.api.mapper;

import com.tech.api.dto.product.ProductAdminDto;
import com.tech.api.dto.product.ProductDto;
import com.tech.api.dto.productconfig.ProductConfigAdminDto;
import com.tech.api.dto.productvariant.ProductVariantAdminDto;
import com.tech.api.form.product.CreateProductForm;
import com.tech.api.form.product.UpdateProductForm;
import com.tech.api.storage.model.Product;
import com.tech.api.storage.model.ProductCategory;
import com.tech.api.storage.model.ProductConfig;
import com.tech.api.storage.model.ProductVariant;
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
public class ProductMapperImpl implements ProductMapper {

    @Autowired
    private ProductConfigMapper productConfigMapper;

    @Override
    public Product fromCreateProductFormToEntity(CreateProductForm createProductForm) {
        if ( createProductForm == null ) {
            return null;
        }

        Product product = new Product();

        product.setImage( createProductForm.getImage() );
        product.setKind( createProductForm.getKind() );
        product.setDescription( createProductForm.getDescription() );
        product.setTags( createProductForm.getTags() );
        product.setProductConfigs( productConfigMapper.fromCreateProductConfigFormListToEntityList( createProductForm.getProductConfigs() ) );
        product.setPrice( createProductForm.getPrice() );
        product.setName( createProductForm.getName() );
        product.setIsSoldOut( createProductForm.getIsSoldOut() );
        product.setStatus( createProductForm.getStatus() );

        return product;
    }

    @Override
    public ProductDto fromEntityToClientDto(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDto productDto = new ProductDto();

        productDto.setImage( product.getImage() );
        productDto.setTags( product.getTags() );
        productDto.setAvgStar( product.getAvgStar() );
        productDto.setProductCategoryId( productCategoryId( product ) );
        productDto.setPrice( product.getPrice() );
        productDto.setName( product.getName() );
        productDto.setIsSoldOut( product.getIsSoldOut() );
        productDto.setId( product.getId() );
        productDto.setSaleOff( product.getSaleOff() );
        productDto.setIsSaleOff( product.getIsSaleOff() );
        productDto.setSoldAmount( product.getSoldAmount() );
        productDto.setTotalReview( product.getTotalReview() );

        return productDto;
    }

    @Override
    public List<ProductDto> fromEntityListToProductClientDtoList(List<Product> products) {
        if ( products == null ) {
            return null;
        }

        List<ProductDto> list = new ArrayList<ProductDto>( products.size() );
        for ( Product product : products ) {
            list.add( fromEntityToClientDto( product ) );
        }

        return list;
    }

    @Override
    public ProductDto fromProductEntityToDto(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDto productDto = new ProductDto();

        productDto.setImage( product.getImage() );
        productDto.setParentProductId( productParentProductId( product ) );
        productDto.setKind( product.getKind() );
        productDto.setDescription( product.getDescription() );
        productDto.setProductCategoryId( productCategoryId( product ) );
        productDto.setPrice( product.getPrice() );
        productDto.setName( product.getName() );
        productDto.setIsSoldOut( product.getIsSoldOut() );
        productDto.setId( product.getId() );
        productDto.setSaleOff( product.getSaleOff() );

        return productDto;
    }

    @Override
    public List<ProductDto> fromProductEntityListToDtoList(List<Product> products) {
        if ( products == null ) {
            return null;
        }

        List<ProductDto> list = new ArrayList<ProductDto>( products.size() );
        for ( Product product : products ) {
            list.add( fromProductEntityToDto( product ) );
        }

        return list;
    }

    @Override
    public ProductDto fromProductEntityToDtoAutoComplete(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDto productDto = new ProductDto();

        productDto.setImage( product.getImage() );
        productDto.setName( product.getName() );
        productDto.setIsSoldOut( product.getIsSoldOut() );
        productDto.setId( product.getId() );

        return productDto;
    }

    @Override
    public List<ProductDto> fromProductEntityListToDtoListAutoComplete(List<Product> products) {
        if ( products == null ) {
            return null;
        }

        List<ProductDto> list = new ArrayList<ProductDto>( products.size() );
        for ( Product product : products ) {
            list.add( fromProductEntityToDtoAutoComplete( product ) );
        }

        return list;
    }

    @Override
    public ProductDto fromProductEntityToDtoDetails(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDto productDto = new ProductDto();

        productDto.setImage( product.getImage() );
        productDto.setParentProductId( productParentProductId( product ) );
        productDto.setKind( product.getKind() );
        productDto.setDescription( product.getDescription() );
        productDto.setTags( product.getTags() );
        productDto.setProductCategoryId( productCategoryId( product ) );
        productDto.setProductConfigs( productConfigMapper.fromProductConfigEntityListToDtoList( product.getProductConfigs() ) );
        productDto.setPrice( product.getPrice() );
        productDto.setName( product.getName() );
        productDto.setIsSoldOut( product.getIsSoldOut() );
        productDto.setId( product.getId() );

        return productDto;
    }

    @Override
    public ProductAdminDto fromProductEntityToAdminDto(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductAdminDto productAdminDto = new ProductAdminDto();

        productAdminDto.setImage( product.getImage() );
        productAdminDto.setPrice( product.getPrice() );
        productAdminDto.setKind( product.getKind() );
        productAdminDto.setName( product.getName() );
        productAdminDto.setIsSoldOut( product.getIsSoldOut() );
        productAdminDto.setId( product.getId() );
        productAdminDto.setIsSaleOff( product.getIsSaleOff() );
        productAdminDto.setSaleOff( product.getSaleOff() );
        productAdminDto.setTags( product.getTags() );
        productAdminDto.setStatus( product.getStatus() );
        productAdminDto.setModifiedDate( product.getModifiedDate() );
        productAdminDto.setCreatedDate( product.getCreatedDate() );
        productAdminDto.setModifiedBy( product.getModifiedBy() );
        productAdminDto.setCreatedBy( product.getCreatedBy() );
        productAdminDto.setDescription( product.getDescription() );
        productAdminDto.setProductConfigs( productConfigListToProductConfigAdminDtoList( product.getProductConfigs() ) );

        return productAdminDto;
    }

    @Override
    public List<ProductAdminDto> fromProductEntityListToAdminDtoList(List<Product> products) {
        if ( products == null ) {
            return null;
        }

        List<ProductAdminDto> list = new ArrayList<ProductAdminDto>( products.size() );
        for ( Product product : products ) {
            list.add( fromProductEntityToAdminDto( product ) );
        }

        return list;
    }

    @Override
    public void fromUpdateProductFormToEntity(UpdateProductForm updateProductForm, Product product) {
        if ( updateProductForm == null ) {
            return;
        }

        if ( updateProductForm.getImage() != null ) {
            product.setImage( updateProductForm.getImage() );
        }
        if ( updateProductForm.getDescription() != null ) {
            product.setDescription( updateProductForm.getDescription() );
        }
        if ( updateProductForm.getTags() != null ) {
            product.setTags( updateProductForm.getTags() );
        }
        if ( product.getProductConfigs() != null ) {
            List<ProductConfig> list = productConfigMapper.frommUpdateProductConfigFormListToEntityList( updateProductForm.getProductConfigs() );
            if ( list != null ) {
                product.getProductConfigs().clear();
                product.getProductConfigs().addAll( list );
            }
        }
        else {
            List<ProductConfig> list = productConfigMapper.frommUpdateProductConfigFormListToEntityList( updateProductForm.getProductConfigs() );
            if ( list != null ) {
                product.setProductConfigs( list );
            }
        }
        if ( updateProductForm.getPrice() != null ) {
            product.setPrice( updateProductForm.getPrice() );
        }
        if ( updateProductForm.getName() != null ) {
            product.setName( updateProductForm.getName() );
        }
        if ( updateProductForm.getIsSoldOut() != null ) {
            product.setIsSoldOut( updateProductForm.getIsSoldOut() );
        }
        if ( updateProductForm.getStatus() != null ) {
            product.setStatus( updateProductForm.getStatus() );
        }
    }

    private Long productCategoryId(Product product) {
        if ( product == null ) {
            return null;
        }
        ProductCategory category = product.getCategory();
        if ( category == null ) {
            return null;
        }
        Long id = category.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long productParentProductId(Product product) {
        if ( product == null ) {
            return null;
        }
        Product parentProduct = product.getParentProduct();
        if ( parentProduct == null ) {
            return null;
        }
        Long id = parentProduct.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected ProductVariantAdminDto productVariantToProductVariantAdminDto(ProductVariant productVariant) {
        if ( productVariant == null ) {
            return null;
        }

        ProductVariantAdminDto productVariantAdminDto = new ProductVariantAdminDto();

        productVariantAdminDto.setId( productVariant.getId() );
        productVariantAdminDto.setStatus( productVariant.getStatus() );
        productVariantAdminDto.setModifiedDate( productVariant.getModifiedDate() );
        productVariantAdminDto.setCreatedDate( productVariant.getCreatedDate() );
        productVariantAdminDto.setModifiedBy( productVariant.getModifiedBy() );
        productVariantAdminDto.setCreatedBy( productVariant.getCreatedBy() );
        productVariantAdminDto.setName( productVariant.getName() );
        productVariantAdminDto.setPrice( productVariant.getPrice() );
        productVariantAdminDto.setDescription( productVariant.getDescription() );
        productVariantAdminDto.setImage( productVariant.getImage() );
        productVariantAdminDto.setOrderSort( productVariant.getOrderSort() );

        return productVariantAdminDto;
    }

    protected List<ProductVariantAdminDto> productVariantListToProductVariantAdminDtoList(List<ProductVariant> list) {
        if ( list == null ) {
            return null;
        }

        List<ProductVariantAdminDto> list1 = new ArrayList<ProductVariantAdminDto>( list.size() );
        for ( ProductVariant productVariant : list ) {
            list1.add( productVariantToProductVariantAdminDto( productVariant ) );
        }

        return list1;
    }

    protected ProductConfigAdminDto productConfigToProductConfigAdminDto(ProductConfig productConfig) {
        if ( productConfig == null ) {
            return null;
        }

        ProductConfigAdminDto productConfigAdminDto = new ProductConfigAdminDto();

        productConfigAdminDto.setId( productConfig.getId() );
        productConfigAdminDto.setStatus( productConfig.getStatus() );
        productConfigAdminDto.setModifiedDate( productConfig.getModifiedDate() );
        productConfigAdminDto.setCreatedDate( productConfig.getCreatedDate() );
        productConfigAdminDto.setModifiedBy( productConfig.getModifiedBy() );
        productConfigAdminDto.setCreatedBy( productConfig.getCreatedBy() );
        productConfigAdminDto.setChoiceKind( productConfig.getChoiceKind() );
        productConfigAdminDto.setIsRequired( productConfig.getIsRequired() );
        productConfigAdminDto.setName( productConfig.getName() );
        productConfigAdminDto.setVariants( productVariantListToProductVariantAdminDtoList( productConfig.getVariants() ) );

        return productConfigAdminDto;
    }

    protected List<ProductConfigAdminDto> productConfigListToProductConfigAdminDtoList(List<ProductConfig> list) {
        if ( list == null ) {
            return null;
        }

        List<ProductConfigAdminDto> list1 = new ArrayList<ProductConfigAdminDto>( list.size() );
        for ( ProductConfig productConfig : list ) {
            list1.add( productConfigToProductConfigAdminDto( productConfig ) );
        }

        return list1;
    }
}
