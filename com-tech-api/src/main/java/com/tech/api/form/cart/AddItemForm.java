package com.tech.api.form.cart;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddItemForm {
    @NotNull(message = "productId can not be null")
    @ApiModelProperty(name = "productId", required = true)
    private Long productId;

    @NotNull(message = "productVariantId can not be null")
    @ApiModelProperty(name = "productVariantId", required = true)
    private Long productVariantId;
}
