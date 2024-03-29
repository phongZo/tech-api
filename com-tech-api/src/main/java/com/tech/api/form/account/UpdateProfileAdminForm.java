package com.tech.api.form.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@ApiModel
public class UpdateProfileAdminForm {
    @ApiModelProperty(name = "password")
    private String password;
    @NotEmpty(message = "oldPassword is required")
    @ApiModelProperty(name = "oldPassword", required = true)
    private String oldPassword;
    @NotEmpty(message = "fullName is required")
    @ApiModelProperty(name = "fullName", required = true)
    private String fullName;
    @ApiModelProperty(name = "avatar")
    private String avatar;

}
