package com.tech.api.dto.orders;

import com.tech.api.dto.ABasicAdminDto;
import com.tech.api.dto.product.ProductDto;
import com.tech.api.dto.productvariant.ProductVariantDto;
import lombok.Data;

@Data
public class OrdersDetailDto extends ABasicAdminDto {
    private OrdersDto ordersDto;
    private ProductDto productDto;
    private ProductVariantDto productVariantDto;
    private Double price;
    private Integer amount;
    private String note;
    private Boolean isReviewed;
}
