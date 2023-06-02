package com.tech.api.dto.productvariant;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductVariantDto {

    @ApiModelProperty(name = "id")
    private Long id;

    @ApiModelProperty(name = "name")
    private String name;

    @ApiModelProperty(name = "price")
    private Double price;

    @ApiModelProperty(name = "description")
    private String description;

    @ApiModelProperty(name = "image")
    private String image;

    private List<VariantStockDto> variantStockDtoList;

    private String color;

    private boolean isCopied = false;

    private Integer status;
    private Integer totalInStock;

    public ProductVariantDto(Long id, String name, Double price, Integer totalInStock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.totalInStock = totalInStock;
    }

    public ProductVariantDto() {
    }
}
