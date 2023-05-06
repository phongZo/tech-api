package com.tech.api.form.employee;

import com.tech.api.validation.Status;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateEmployeeForm {
    @NotBlank(message = "Username can not be null")
    @ApiModelProperty(name = "username", required = true)
    private String username;

    @NotEmpty(message = "Password cant not be null")
    @ApiModelProperty(name = "Password", required = true)
    private String password;

    @Email(message = "Email is invalid")
    @ApiModelProperty(name = "email", required = true)
    private String email;

    @NotBlank(message = "Phone can not be empty")
    @ApiModelProperty(value = "phone", required = true)
    private String phone;

    @NotEmpty(message = "Full name can not be null")
    @ApiModelProperty(name = "fullName", required = true)
    private String fullName;

    @ApiModelProperty(name = "avatar")
    private String avatar;

    @Status
    private Integer status = 1;

    @NotNull(message = "storeId can not be null")
    @ApiModelProperty(name = "storeId")
    private Long storeId;

    @ApiModelProperty(name = "note")
    private String note;
}
