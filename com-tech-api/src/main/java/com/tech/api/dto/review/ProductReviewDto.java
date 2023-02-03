package com.tech.api.dto.review;

import com.tech.api.dto.ABasicAdminDto;
import com.tech.api.dto.customer.CustomerDto;
import lombok.Data;

@Data
public class ProductReviewDto extends ABasicAdminDto {
    private CustomerDto customerDto;
    private Integer star;
    private String content;
}
