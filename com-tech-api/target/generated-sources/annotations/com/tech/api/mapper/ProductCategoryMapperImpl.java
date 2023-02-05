package com.tech.api.mapper;

import com.tech.api.dto.productcategory.ProductCategoryDto;
import com.tech.api.form.productcategory.CreateProductCategoryForm;
import com.tech.api.form.productcategory.UpdateProductCategoryForm;
import com.tech.api.storage.model.ProductCategory;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-05T13:12:04+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.13 (Oracle Corporation)"
)
@Component
public class ProductCategoryMapperImpl implements ProductCategoryMapper {

    @Override
    public ProductCategory fromCreateProductCategoryFormToEntity(CreateProductCategoryForm createProductCategoryForm) {
        if ( createProductCategoryForm == null ) {
            return null;
        }

        ProductCategory productCategory = new ProductCategory();

        productCategory.setNote( createProductCategoryForm.getNote() );
        productCategory.setOrderSort( createProductCategoryForm.getOrderSort() );
        productCategory.setName( createProductCategoryForm.getName() );
        productCategory.setIcon( createProductCategoryForm.getIcon() );
        productCategory.setStatus( createProductCategoryForm.getStatus() );

        return productCategory;
    }

    @Override
    public ProductCategoryDto fromProductCategoryEntityToDto(ProductCategory productCategory) {
        if ( productCategory == null ) {
            return null;
        }

        ProductCategoryDto productCategoryDto = new ProductCategoryDto();

        productCategoryDto.setNote( productCategory.getNote() );
        productCategoryDto.setOrderSort( productCategory.getOrderSort() );
        productCategoryDto.setName( productCategory.getName() );
        productCategoryDto.setIcon( productCategory.getIcon() );
        productCategoryDto.setId( productCategory.getId() );
        productCategoryDto.setParentId( productCategoryParentCategoryId( productCategory ) );
        productCategoryDto.setStatus( productCategory.getStatus() );

        return productCategoryDto;
    }

    @Override
    public List<ProductCategoryDto> fromProductCategoryListToDtoList(List<ProductCategory> productCategories) {
        if ( productCategories == null ) {
            return null;
        }

        List<ProductCategoryDto> list = new ArrayList<ProductCategoryDto>( productCategories.size() );
        for ( ProductCategory productCategory : productCategories ) {
            list.add( fromProductCategoryEntityToDto( productCategory ) );
        }

        return list;
    }

    @Override
    public void fromUpdateProductCategoryFormToEntity(UpdateProductCategoryForm updateProductCategoryForm, ProductCategory productCategory) {
        if ( updateProductCategoryForm == null ) {
            return;
        }

        if ( updateProductCategoryForm.getNote() != null ) {
            productCategory.setNote( updateProductCategoryForm.getNote() );
        }
        if ( updateProductCategoryForm.getOrderSort() != null ) {
            productCategory.setOrderSort( updateProductCategoryForm.getOrderSort() );
        }
        if ( updateProductCategoryForm.getName() != null ) {
            productCategory.setName( updateProductCategoryForm.getName() );
        }
        if ( updateProductCategoryForm.getIcon() != null ) {
            productCategory.setIcon( updateProductCategoryForm.getIcon() );
        }
        if ( updateProductCategoryForm.getStatus() != null ) {
            productCategory.setStatus( updateProductCategoryForm.getStatus() );
        }
    }

    private Long productCategoryParentCategoryId(ProductCategory productCategory) {
        if ( productCategory == null ) {
            return null;
        }
        ProductCategory parentCategory = productCategory.getParentCategory();
        if ( parentCategory == null ) {
            return null;
        }
        Long id = parentCategory.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
