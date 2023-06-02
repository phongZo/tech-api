package com.tech.api.dto.importDto;

import com.tech.api.dto.productvariant.ProductVariantDto;
import lombok.Data;

@Data
public class ImportLineItemDto {
    private Long id;
    private ProductVariantDto variantDto;
    private Integer quantity;
    private String productName;
}
