package com.tech.api.mapper;

import com.tech.api.dto.review.ProductReviewDto;
import com.tech.api.form.review.ClientCreateProductReviewForm;
import com.tech.api.form.review.CreateProductReviewForm;
import com.tech.api.storage.model.Product;
import com.tech.api.storage.model.ProductReview;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-03T19:16:11+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.13 (Oracle Corporation)"
)
@Component
public class ProductReviewMapperImpl implements ProductReviewMapper {

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public ProductReview fromCreateFormToEntity(CreateProductReviewForm createProductReviewForm) {
        if ( createProductReviewForm == null ) {
            return null;
        }

        ProductReview productReview = new ProductReview();

        productReview.setStar( createProductReviewForm.getStar() );
        productReview.setContent( createProductReviewForm.getContent() );

        return productReview;
    }

    @Override
    public ProductReview fromClientCreateFormToEntity(ClientCreateProductReviewForm createProductReviewForm) {
        if ( createProductReviewForm == null ) {
            return null;
        }

        ProductReview productReview = new ProductReview();

        productReview.setProduct( clientCreateProductReviewFormToProduct( createProductReviewForm ) );
        productReview.setStar( createProductReviewForm.getStar() );
        productReview.setContent( createProductReviewForm.getContent() );

        return productReview;
    }

    @Override
    public ProductReviewDto fromEntityToDto(ProductReview productReview) {
        if ( productReview == null ) {
            return null;
        }

        ProductReviewDto productReviewDto = new ProductReviewDto();

        productReviewDto.setCustomerDto( customerMapper.fromEntityToAdminDtoAutoComplete( productReview.getCustomer() ) );
        productReviewDto.setCreatedDate( productReview.getCreatedDate() );
        productReviewDto.setStar( productReview.getStar() );
        productReviewDto.setId( productReview.getId() );
        productReviewDto.setContent( productReview.getContent() );

        return productReviewDto;
    }

    @Override
    public List<ProductReviewDto> fromListEntityToListDto(List<ProductReview> productReviewList) {
        if ( productReviewList == null ) {
            return null;
        }

        List<ProductReviewDto> list = new ArrayList<ProductReviewDto>( productReviewList.size() );
        for ( ProductReview productReview : productReviewList ) {
            list.add( fromEntityToDto( productReview ) );
        }

        return list;
    }

    protected Product clientCreateProductReviewFormToProduct(ClientCreateProductReviewForm clientCreateProductReviewForm) {
        if ( clientCreateProductReviewForm == null ) {
            return null;
        }

        Product product = new Product();

        product.setId( clientCreateProductReviewForm.getProductId() );

        return product;
    }
}
