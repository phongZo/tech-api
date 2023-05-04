package com.tech.api.form.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UpdateStoreForm {

    @NotNull(message = "Id can npt be null")
    @ApiModelProperty(name = "id")
    private Long id;

    @NotBlank(message = "Name can not be blank")
    @ApiModelProperty(name = "name")
    private String name;

    @ApiModelProperty(name = "latitude")
    private Double latitude;

    @ApiModelProperty(name = "longitude")
    private Double longitude;

    @NotBlank(message = "Address details can not be blank")
    @ApiModelProperty(name = "addressDetails")
    private String addressDetails;

    private String phone;

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
