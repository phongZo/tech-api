package com.tech.api.form.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class RegisterCustomerForm {
    @NotBlank(message = "Username can not be null")
    @ApiModelProperty(name = "username", required = true)
    private String username;

    @NotEmpty(message = "Password cant not be null")
    @ApiModelProperty(name = "Password", required = true)
    private String password;

    @Email(message = "Email is invalid")
    @ApiModelProperty(name = "email", required = true)
    private String email;

    @NotEmpty(message = "Full name can not be null")
    @ApiModelProperty(name = "fullName", required = true)
    private String fullName;
}
