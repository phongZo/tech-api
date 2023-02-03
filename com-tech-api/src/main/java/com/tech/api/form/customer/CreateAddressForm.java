package com.tech.api.form.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateAddressForm {

    @NotBlank(message = "Address details can not be blank")
    @ApiModelProperty(name = "addressDetails", required = true)
    private String addressDetails;

    @NotBlank(message = "Receiver full name can not be blank")
    @ApiModelProperty(name = "receiverFullName", required = true)
    private String receiverFullName;

    @NotBlank(message = "Phone number can not be blank")
    @ApiModelProperty(name = "phone", required = true)
    private String phone;

    @NotNull(message = "Default address can not be null")
    @ApiModelProperty(name = "isDefault", required = true)
    private Boolean isDefault;

    private Integer typeAddress;
}
