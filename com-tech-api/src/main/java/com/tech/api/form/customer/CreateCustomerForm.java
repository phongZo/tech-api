package com.tech.api.form.customer;

import com.tech.api.validation.Gender;
import com.tech.api.validation.Status;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class CreateCustomerForm {
    @Email(message = "Email is invalid")
    @ApiModelProperty(name = "email", required = true)
    private String email;

    @NotBlank(message = "Phone can not be empty")
    @ApiModelProperty(value = "phone", required = true)
    private String phone;

    @NotEmpty(message = "Full name can not be null")
    @ApiModelProperty(name = "fullName", required = true)
    private String fullName;

    @Gender
    @ApiModelProperty(name = "gender", required = true)
    private Integer gender;

    @NotNull(message = "Birthday can not be null")
    @ApiModelProperty(name = "birthday", required = true)
    private LocalDate birthday;

    @Status
    private Integer status = 1;
}
