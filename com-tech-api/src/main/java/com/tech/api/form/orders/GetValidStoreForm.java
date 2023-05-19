package com.tech.api.form.orders;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class GetValidStoreForm {
    @NotEmpty(message = "createOrdersDetailFormList cannot be empty")
    @ApiModelProperty(required = true)
    private List<CreateOrdersDetailForm> createOrdersDetailFormList;

    @NotNull(message = "Yêu cầu điền địa chỉ")
    @ApiModelProperty(required = true)
    private Long customerAddressId;
}
