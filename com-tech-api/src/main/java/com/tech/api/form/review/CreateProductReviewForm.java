package com.tech.api.form.review;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateProductReviewForm {
    @NotNull(message = "productId cannot be empty")
    @ApiModelProperty(required = true)
    private Long productId;

    @NotNull(message = "customerId cannot be empty")
    @ApiModelProperty(required = true)
    private Long customerId;

    @NotNull(message = "star cannot be empty")
    @ApiModelProperty(required = true)
    private Integer star;

    private String content;
}
