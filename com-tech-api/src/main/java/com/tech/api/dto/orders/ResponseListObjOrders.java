package com.tech.api.dto.orders;

import com.tech.api.dto.ResponseListObj;
import lombok.Data;

@Data
public class ResponseListObjOrders extends ResponseListObj<OrdersDto> {
    private Double sumMoney;
}
