package com.tech.api.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CustomerViewDto {
    @JsonProperty("customer_id")
    private Long customerId;

    @JsonProperty("product_id")
    private Long productId;

    private Integer total;
    private Long timestamp;
}
