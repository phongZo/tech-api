package com.tech.api.form.account;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
@Data
public class RequestValidateEmailForm {
    @NotEmpty(message = "email can not be empty.")
    @ApiModelProperty(name = "email", required = true)
    private String email;
}
