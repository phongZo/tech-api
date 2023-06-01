package com.tech.api.dto.product;

import com.tech.api.dto.productconfig.ProductConfigDto;
import com.tech.api.dto.productvariant.ProductVariantDto;
import com.tech.api.dto.productvariant.VariantStockDto;
import com.tech.api.storage.model.ProductVariant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductDto {

    @ApiModelProperty(name = "id")
    private Long id;

    private Integer saleOff;   //percent
    private Boolean isSaleOff;

    private Boolean isLike = false;

    @ApiModelProperty(name = "productCategoryId")
    private Long productCategoryId;

    private Integer totalReview;

    @ApiModelProperty(name = "description")
    private String description;

    @ApiModelProperty(name = "name")
    private String name;

    @ApiModelProperty(name = "price")
    private BigDecimal price;

    @ApiModelProperty(name = "image")
    private String image;

    private Integer totalInStock;

    @ApiModelProperty(name = "isSoldOut")
    private Boolean isSoldOut;

    @ApiModelProperty(name = "parentProductId")
    private Long parentProductId;

    private Integer avgStar;

    private Integer soldAmount;

    @ApiModelProperty(name = "productConfigs")
    private List<ProductConfigDto> productConfigs;

    @ApiModelProperty(name = "productVariantDtoList")
    private List<ProductVariantDto> productVariantDtoList;

    private List<VariantStockDto> stockDtoList;

    public ProductDto(Long id, Integer saleOff, Boolean isSaleOff, String name, BigDecimal price, Integer totalInStock, Boolean isSoldOut, String image) {
        this.id = id;
        this.saleOff = saleOff;
        this.isSaleOff = isSaleOff;
        this.name = name;
        this.price = price;
        this.totalInStock = totalInStock;
        this.isSoldOut = isSoldOut;
        this.image = image
    }

    public ProductDto() {
    }
}
