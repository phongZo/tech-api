package com.tech.api.form.employee;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@ApiModel
public class UpdateProfileEmployeeForm {
    @ApiModelProperty(name = "password")
    private String password;
    @ApiModelProperty(name = "oldPassword")
    private String oldPassword;
    @ApiModelProperty(name = "avatar")
    private String avatar;

}
