package com.tech.api.form.orders;

import com.tech.api.validation.AmountOrdersDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateOrdersDetailForm {

    @NotNull(message = "variantId cannot be null")
    @ApiModelProperty(required = true)
    private Long variantId;

    @AmountOrdersDetail
    @NotNull(message = "amount cannot be null")
    @ApiModelProperty(required = true)
    private Integer amount;

    private String note;

}
