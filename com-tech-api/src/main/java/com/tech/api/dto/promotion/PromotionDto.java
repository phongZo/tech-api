package com.tech.api.dto.promotion;

import com.tech.api.dto.ABasicAdminDto;
import lombok.Data;

@Data
public class PromotionDto extends ABasicAdminDto {
    private Long id;
    private String title;
    private String description;
    private Integer kind;   // 1: money, 2:%
    private Double maxValueForPercent;    // if kind is % --> have max value in money
    private String value;
    private double point;
    private boolean exchangeable;
}
