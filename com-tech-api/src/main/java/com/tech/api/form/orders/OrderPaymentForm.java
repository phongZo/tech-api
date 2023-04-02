package com.tech.api.form.orders;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class OrderPaymentForm {
    @NotNull(message = "orderId cannot be null")
    @ApiModelProperty(required = true)
    private Long orderId;

    @NotEmpty(message = "returnUrl cannot be empty")
    @ApiModelProperty(required = true)
    private String returnUrl;
}
