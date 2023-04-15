package com.tech.api.storage.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = TablePrefix.PREFIX_TABLE + "promotion")
public class Promotion extends Auditable<String>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer kind;   // 1: money, 2:%
    private Double maxValueForPercent;    // if kind is % --> have max value in money
    private String value;

    private Double point = 0d;
    private Boolean exchangeable = false;
}
