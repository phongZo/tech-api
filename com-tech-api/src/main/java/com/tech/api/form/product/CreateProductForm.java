package com.tech.api.form.product;

import com.tech.api.form.productconfig.CreateProductConfigForm;
import com.tech.api.validation.Hashtag;
import com.tech.api.validation.ProductKind;
import com.tech.api.validation.Status;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CreateProductForm {

    @ApiModelProperty(name = "categoryId", notes = "Category có thể null do product có thể không thuộc category nào")
    private Long categoryId;

    @Hashtag
    @Size(max = 255)
    @ApiModelProperty(name = "tags")
    private String tags;

    @ApiModelProperty(name = "description")
    private String description;

    @NotBlank(message = "Product name con not be null")
    @ApiModelProperty(name = "name", required = true)
    private String name;

    private Integer saleOff;   //percent
    private Boolean isSaleOff = false;

    @NotNull(message = "Price can not be null")
    @ApiModelProperty(name = "price", required = true)
    private Double price;

    @ApiModelProperty(name = "image")
    private String image;

    @ApiModelProperty(name = "isSoldOut")
    private Boolean isSoldOut = false;

    @ApiModelProperty(name = "parentProductId")
    private Long parentProductId;

    @ProductKind
    @ApiModelProperty(name = "kind", required = true, notes = "1: product thường, 2: product nhóm")
    private Integer kind;

    @Status
    @ApiModelProperty(name = "status")
    private Integer status = 1;

    @ApiModelProperty(name = "productConfigs")
    private List<@Valid CreateProductConfigForm> productConfigs = new ArrayList<>();
}
