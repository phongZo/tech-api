package com.tech.api.dto.orders;

import lombok.Data;

import java.util.List;

@Data
public class CheckValidOrderItemsDto {
    private List<OrdersDetailDto> ordersDetailDtoList;
}
