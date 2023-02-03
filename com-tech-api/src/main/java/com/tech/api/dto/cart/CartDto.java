package com.tech.api.dto.cart;

import com.tech.api.dto.ABasicAdminDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CartDto extends ABasicAdminDto {
    private Long customerId;
    private Double totalMoney = 0d;
    private List<LineItemDto> lineItemDtoList = new ArrayList<>();
}
