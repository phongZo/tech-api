package com.tech.api.storage.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = TablePrefix.PREFIX_TABLE + "product_variant")
public class ProductVariant extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "color")
    private String color;

    private Boolean isCopied = false;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @Column(name = "total_in_stock")
    private Integer totalInStock;

    @ManyToOne(targetEntity = ProductConfig.class)
    @JoinColumn(name = "product_config_id", insertable = false, updatable = false)
    private ProductConfig productConfig;
}
