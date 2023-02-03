package com.tech.api.dto.cart;

import com.tech.api.dto.ABasicAdminDto;
import com.tech.api.dto.product.ProductDto;
import com.tech.api.dto.productvariant.ProductVariantDto;
import lombok.Data;

@Data
public class LineItemDto extends ABasicAdminDto {
    private ProductDto productDto;
    private ProductVariantDto productVariantDto;
    private Integer quantity;
}
