package com.tech.api.form.review;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ClientCreateProductReviewForm {
    @NotNull(message = "productId cannot be empty")
    @ApiModelProperty(required = true)
    private Long productId;

    @NotNull(message = "star cannot be empty")
    @ApiModelProperty(required = true)
    private Integer star;

    @NotNull(message = "ordersDetailId cannot be empty")
    @ApiModelProperty(required = true)
    private Long ordersDetailId;

    private String content;
}
