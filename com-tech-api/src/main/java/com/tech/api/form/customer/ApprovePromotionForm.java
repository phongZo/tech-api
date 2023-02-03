package com.tech.api.form.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class ApprovePromotionForm {
    @NotNull(message = "customerId can not be null")
    @ApiModelProperty(name = "customerId", required = true)
    private Long customerId;

    @NotNull(message = "promotionId can not be null")
    @ApiModelProperty(name = "promotionId", required = true)
    private Long promotionId;

    @NotNull(message = "expiryDate can not be null")
    @ApiModelProperty(name = "expiryDate", required = true)
    private Date expiryDate;
}
