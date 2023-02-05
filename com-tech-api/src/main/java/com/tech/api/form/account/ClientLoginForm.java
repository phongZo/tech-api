package com.tech.api.form.account;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ClientLoginForm {
    @ApiModelProperty(name = "usernameOrPhone",required = true)
    @NotEmpty(message = "usernameOrPhone cannot be null")
    private String usernameOrPhone;

    @ApiModelProperty(name = "password",required = true)
    @NotEmpty(message = "password cannot be null")
    private String password;
}
