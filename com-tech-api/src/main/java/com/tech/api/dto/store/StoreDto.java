package com.tech.api.dto.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreDto {
    @ApiModelProperty(name = "id")
    private Long id;

    @ApiModelProperty(name = "name")
    private String name;

    @ApiModelProperty(name = "latitude")
    private Double latitude;

    @ApiModelProperty(name = "longitude")
    private Double longitude;

    @ApiModelProperty(name = "addressDetails")
    private String addressDetails;

    private String phone;
}
