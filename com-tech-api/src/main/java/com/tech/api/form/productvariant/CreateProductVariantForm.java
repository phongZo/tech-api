package com.tech.api.form.productvariant;

import com.tech.api.validation.Status;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateProductVariantForm {

    @NotBlank(message = "Product variant name can not be blank")
    @ApiModelProperty(name = "name", required = true)
    private String name;

    @NotNull(message = "Price can not be null")
    @ApiModelProperty(name = "price", required = true)
    private Double price;

    @ApiModelProperty(name = "image")
    private String image;

    @ApiModelProperty(name = "description")
    private String description;

    private Integer totalInStock = 0;

    @Status
    @ApiModelProperty(name = "status")
    private Integer status = 1;

    private Boolean isCopied = false;

    private String color;
}
