package com.tech.api.form.customer;

import com.tech.api.validation.Gender;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class UpdateProfileCustomerForm {

    @NotBlank(message = "Name can not be blank")
    @ApiModelProperty(value = "fullName", required = true)
    private String fullName;

    @Gender
    @ApiModelProperty(value = "gender", required = true)
    private Integer gender;

    @ApiModelProperty("avatar")
    private String avatar;

    @NotNull
    @ApiModelProperty(value = "birthday", required = true)
    private LocalDate birthday;
}
