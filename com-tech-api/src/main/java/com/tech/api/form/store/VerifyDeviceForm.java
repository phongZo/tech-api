package com.tech.api.form.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class VerifyDeviceForm {
    @NotEmpty(message = "posId can not be blank")
    @ApiModelProperty(name = "posId", required = true)
    private String posId;

    @NotEmpty(message = "sessionId can not be blank")
    @ApiModelProperty(name = "sessionId", required = true)
    private String sessionId;
}
