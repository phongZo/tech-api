package com.tech.api.form.orders;

import com.tech.api.validation.PaymentMethod;
import com.tech.api.validation.Status;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreateOrdersForm {
    @NotNull(message = "saleOff cannot be null")
    @ApiModelProperty(required = true)
    private Integer saleOff;

    @NotEmpty(message = "customerName cannot be empty")
    @ApiModelProperty(required = true)
    private String customerName;

    @NotEmpty(message = "customerPhone cannot be empty")
    @ApiModelProperty(required = true)
    private String customerPhone;

    @NotNull(message = "isDelivery cannot be empty")
    @ApiModelProperty(required = true)
    private Boolean isDelivery;

    private String receiverName;
    private String receiverPhone;
    private String address;
    private Long provinceId;
    private Long districtId;
    private String wardCode;
    private Integer serviceId;
    private Integer serviceType;
    private Double deliveryFee;

    @NotNull(message = "state cannot be null")
    @ApiModelProperty(required = true)
    private Integer state;

    @PaymentMethod
    @NotNull(message = "paymentMethod cannot be null")
    @ApiModelProperty(required = true)
    private Integer paymentMethod;

    @Status
    @NotNull(message = "status cannot be null")
    @ApiModelProperty(required = true)
    private Integer status;

    @NotEmpty(message = "createOrdersDetailFormList cannot be empty")
    @ApiModelProperty(required = true)
    private List<CreateOrdersDetailForm> createOrdersDetailFormList;

}
