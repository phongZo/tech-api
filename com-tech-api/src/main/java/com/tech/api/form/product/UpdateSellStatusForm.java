package com.tech.api.form.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateSellStatusForm {
    @NotNull(message = "productId cannot be null")
    @ApiModelProperty(required = true)
    private Long productId;

    @NotNull(message = "isSoldOut cannot be null")
    @ApiModelProperty(required = true)
    private Boolean isSoldOut;
}
