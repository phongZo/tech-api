package com.tech.api.dto.customer;

import com.tech.api.dto.ResponseListObj;
import lombok.Data;

@Data
public class CustomerPromotionListObj extends ResponseListObj<CustomerPromotionDto> {
    private Integer point;
}
