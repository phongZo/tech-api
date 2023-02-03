package com.tech.api.form.promotion;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreatePromotionForm {
    @NotEmpty(message = "title cannot be empty")
    @ApiModelProperty(required = true)
    private String title;

    @NotEmpty(message = "description cannot be empty")
    @ApiModelProperty(required = true)
    private String description;

    @NotNull(message = "kind cannot be empty")
    @ApiModelProperty(required = true)
    private Integer kind;   // 1: money, 2:%

    private Double maxValueForPercent;    // if kind is % --> have max value in money

    @NotEmpty(message = "value cannot be empty")
    @ApiModelProperty(required = true)
    private String value;
}
