package com.tech.api.form.orders;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class EmployeeGetDeliveryServiceForm {
    @NotNull(message = "provinceId cannot be empty")
    @ApiModelProperty(required = true)
    private Integer provinceId;

    @NotNull(message = "districtId cannot be empty")
    @ApiModelProperty(required = true)
    private Integer districtId;

    @NotEmpty(message = "wardCode cannot be empty")
    @ApiModelProperty(required = true)
    private String wardCode;
}
