package com.tech.api.form.wallet;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RechargeForm {
    @NotNull(message = "totalMoney cannot be empty")
    @ApiModelProperty(required = true)
    private Double totalMoney;

    @NotNull(message = "rechargeMoney cannot be empty")
    @ApiModelProperty(required = true)
    private Double rechargeMoney;
}
