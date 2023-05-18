package com.tech.api.dto.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GhnCancelOrderResponse {
    @JsonProperty("order_code")
    private String orderCode;

    private Boolean result;
    private String message;
}
