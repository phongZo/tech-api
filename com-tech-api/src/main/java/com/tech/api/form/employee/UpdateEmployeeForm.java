package com.tech.api.form.employee;

import com.tech.api.validation.Status;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UpdateEmployeeForm {
    @NotNull(message = "Id can not be null")
    @ApiModelProperty(value = "id", required = true)
    private Long id;

    @NotBlank(message = "Password cant not be blank")
    @ApiModelProperty(name = "Password", required = true)
    private String password;

    @Email(message = "Email is invalid")
    @ApiModelProperty(name = "email", required = true)
    private String email;

    @NotBlank(message = "Phone can not be blank")
    @ApiModelProperty(value = "phone", required = true)
    private String phone;

    @NotBlank(message = "Full name can not be blank")
    @ApiModelProperty(name = "fullName", required = true)
    private String fullName;

    @ApiModelProperty(name = "avatar")
    private String avatar;

    @Status
    private Integer status = 1;

    @NotNull(message = "Department can not be blank")
    @ApiModelProperty(name = "departmentId")
    private Long departmentId;

    @NotNull(message = "Job can not be blank")
    @ApiModelProperty(name = "jobId")
    private Long jobId;

    @ApiModelProperty(name = "note")
    private String note;
}
