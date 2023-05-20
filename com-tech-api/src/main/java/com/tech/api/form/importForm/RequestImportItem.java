package com.tech.api.form.importForm;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RequestImportItem {
    @NotNull(message = "storeId can not be null")
    @ApiModelProperty(required = true)
    private Long storeId;

    @NotNull(message = "variantId can not be null")
    @ApiModelProperty(required = true)
    private Long variantId;

    @NotNull(message = "quantity can not be null")
    @ApiModelProperty(required = true)
    private Integer quantity;
}
