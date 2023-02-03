package com.tech.api.form.cart;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateCartQuantity {
    @NotNull(message = "lineItemId can not be null")
    @ApiModelProperty(name = "lineItemId", required = true)
    private Long lineItemId;

    @NotNull(message = "quantity can not be null")
    @ApiModelProperty(name = "quantity", required = true)
    private Integer quantity;
}
