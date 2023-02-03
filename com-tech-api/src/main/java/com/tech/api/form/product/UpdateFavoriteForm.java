package com.tech.api.form.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateFavoriteForm {
    @NotNull(message = "productId cannot be empty")
    @ApiModelProperty(required = true)
    private Long productId;
}
