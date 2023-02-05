package com.tech.api.mapper;

import com.tech.api.dto.customer.CustomerPromotionDto;
import com.tech.api.form.customer.ApprovePromotionForm;
import com.tech.api.storage.model.Customer;
import com.tech.api.storage.model.CustomerPromotion;
import com.tech.api.storage.model.Promotion;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-05T13:12:02+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.13 (Oracle Corporation)"
)
@Component
public class CustomerPromotionMapperImpl implements CustomerPromotionMapper {

    @Override
    public CustomerPromotion fromApprovePromotionFormToEntity(ApprovePromotionForm approvePromotionForm) {
        if ( approvePromotionForm == null ) {
            return null;
        }

        CustomerPromotion customerPromotion = new CustomerPromotion();

        customerPromotion.setCustomer( approvePromotionFormToCustomer( approvePromotionForm ) );
        customerPromotion.setExpireDate( approvePromotionForm.getExpiryDate() );

        return customerPromotion;
    }

    @Override
    public CustomerPromotionDto fromCustomerPromotionEntityToDtoMapper(CustomerPromotion customerPromotion) {
        if ( customerPromotion == null ) {
            return null;
        }

        CustomerPromotionDto customerPromotionDto = new CustomerPromotionDto();

        customerPromotionDto.setKind( customerPromotionPromotionKind( customerPromotion ) );
        customerPromotionDto.setDescription( customerPromotionPromotionDescription( customerPromotion ) );
        customerPromotionDto.setExpireDate( customerPromotion.getExpireDate() );
        customerPromotionDto.setMaxValueForPercent( customerPromotionPromotionMaxValueForPercent( customerPromotion ) );
        customerPromotionDto.setId( customerPromotion.getId() );
        customerPromotionDto.setTitle( customerPromotionPromotionTitle( customerPromotion ) );
        customerPromotionDto.setValue( customerPromotionPromotionValue( customerPromotion ) );
        customerPromotionDto.setPromotionId( customerPromotionPromotionId( customerPromotion ) );

        return customerPromotionDto;
    }

    @Override
    public List<CustomerPromotionDto> fromListCustomerPromotionEntityToListDtoMapper(List<CustomerPromotion> customerPromotions) {
        if ( customerPromotions == null ) {
            return null;
        }

        List<CustomerPromotionDto> list = new ArrayList<CustomerPromotionDto>( customerPromotions.size() );
        for ( CustomerPromotion customerPromotion : customerPromotions ) {
            list.add( fromCustomerPromotionEntityToDtoMapper( customerPromotion ) );
        }

        return list;
    }

    protected Customer approvePromotionFormToCustomer(ApprovePromotionForm approvePromotionForm) {
        if ( approvePromotionForm == null ) {
            return null;
        }

        Customer customer = new Customer();

        customer.setId( approvePromotionForm.getCustomerId() );

        return customer;
    }

    private Integer customerPromotionPromotionKind(CustomerPromotion customerPromotion) {
        if ( customerPromotion == null ) {
            return null;
        }
        Promotion promotion = customerPromotion.getPromotion();
        if ( promotion == null ) {
            return null;
        }
        Integer kind = promotion.getKind();
        if ( kind == null ) {
            return null;
        }
        return kind;
    }

    private String customerPromotionPromotionDescription(CustomerPromotion customerPromotion) {
        if ( customerPromotion == null ) {
            return null;
        }
        Promotion promotion = customerPromotion.getPromotion();
        if ( promotion == null ) {
            return null;
        }
        String description = promotion.getDescription();
        if ( description == null ) {
            return null;
        }
        return description;
    }

    private Double customerPromotionPromotionMaxValueForPercent(CustomerPromotion customerPromotion) {
        if ( customerPromotion == null ) {
            return null;
        }
        Promotion promotion = customerPromotion.getPromotion();
        if ( promotion == null ) {
            return null;
        }
        Double maxValueForPercent = promotion.getMaxValueForPercent();
        if ( maxValueForPercent == null ) {
            return null;
        }
        return maxValueForPercent;
    }

    private String customerPromotionPromotionTitle(CustomerPromotion customerPromotion) {
        if ( customerPromotion == null ) {
            return null;
        }
        Promotion promotion = customerPromotion.getPromotion();
        if ( promotion == null ) {
            return null;
        }
        String title = promotion.getTitle();
        if ( title == null ) {
            return null;
        }
        return title;
    }

    private String customerPromotionPromotionValue(CustomerPromotion customerPromotion) {
        if ( customerPromotion == null ) {
            return null;
        }
        Promotion promotion = customerPromotion.getPromotion();
        if ( promotion == null ) {
            return null;
        }
        String value = promotion.getValue();
        if ( value == null ) {
            return null;
        }
        return value;
    }

    private Long customerPromotionPromotionId(CustomerPromotion customerPromotion) {
        if ( customerPromotion == null ) {
            return null;
        }
        Promotion promotion = customerPromotion.getPromotion();
        if ( promotion == null ) {
            return null;
        }
        Long id = promotion.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
