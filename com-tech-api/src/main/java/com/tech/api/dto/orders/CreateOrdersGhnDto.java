package com.tech.api.dto.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateOrdersGhnDto {
    @JsonProperty("order_code")
    private String orderCode;
}
