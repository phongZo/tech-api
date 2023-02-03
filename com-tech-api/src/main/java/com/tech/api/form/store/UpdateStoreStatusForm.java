package com.tech.api.form.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateStoreStatusForm {
    @NotNull(message = "isAcceptOrder can npt be null")
    @ApiModelProperty(name = "id")
    private Boolean isAcceptOrder;
}
