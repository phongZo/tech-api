package com.tech.api.dto.customer;

import lombok.Data;

import java.util.Date;

@Data
public class CustomerPromotionDto {
    private Long id;
    private Date expireDate;
    private Long promotionId;
    private String title;
    private String description;
    private Integer kind;   // 1: money, 2:%
    private Double maxValueForPercent;    // if kind is % --> have max value in money
    private String value;
}
