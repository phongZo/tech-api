package com.tech.api.form.orders;

import com.tech.api.validation.PaymentMethod;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreateOrdersClientForm {
    @NotNull(message = "Yêu cầu điền địa chỉ")
    @ApiModelProperty(required = true)
    private Long customerAddressId;

    @PaymentMethod
    @NotNull(message = "Yêu cầu chọn phương thức thanh toán")
    @ApiModelProperty(required = true)
    private Integer paymentMethod;

    private Long promotionId;
    private Double saleOff = 0d;

    @NotNull(message = "deliveryFee cannot be empty")
    @ApiModelProperty(required = true)
    private Double deliveryFee = 0d;

    @NotEmpty(message = "createOrdersDetailFormList cannot be empty")
    @ApiModelProperty(required = true)
    private List<CreateOrdersDetailForm> createOrdersDetailFormList;

}
