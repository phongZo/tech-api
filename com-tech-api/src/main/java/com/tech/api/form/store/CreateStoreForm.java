package com.tech.api.form.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateStoreForm {
    @NotBlank(message = "Name can not be blank")
    @ApiModelProperty(name = "name", required = true)
    private String name;

    @NotNull(message = "latitude can not be blank")
    @ApiModelProperty(name = "latitude", required = true)
    private Double latitude;

    @NotNull(message = "longitude can not be blank")
    @ApiModelProperty(name = "longitude", required = true)
    private Double longitude;

    @NotBlank(message = "Address details can not be blank")
    @ApiModelProperty(name = "addressDetails", required = true)
    private String addressDetails;

    @NotBlank(message = "phone details can not be blank")
    @ApiModelProperty(name = "phone", required = true)
    private String phone;

    @NotNull(message = "shopId can not be null")
    @ApiModelProperty(name = "shopId", required = true)
    private Long shopId;

    @NotNull(message = "provinceCode can not be null")
    @ApiModelProperty(name = "provinceCode", required = true)
    private Long provinceCode;

    @NotNull(message = "districtCode can not be null")
    @ApiModelProperty(name = "districtCode", required = true)
    private Long districtCode;

    @NotNull(message = "wardCode can not be null")
    @ApiModelProperty(name = "wardCode", required = true)
    private Long wardCode;
}
