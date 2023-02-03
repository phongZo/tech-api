package com.tech.api.form.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateStoreForm {
    @NotNull(message = "isAcceptOrder can not be blank")
    @ApiModelProperty(name = "isAcceptOrder", required = true)
    private Boolean isAcceptOrder;

    @NotBlank(message = "posId can not be blank")
    @ApiModelProperty(name = "posId", required = true)
    private String posId;

    @NotBlank(message = "sessionId can not be blank")
    @ApiModelProperty(name = "sessionId", required = true)
    private String sessionId;

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
}
