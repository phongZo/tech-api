package com.tech.api.storage.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = TablePrefix.PREFIX_TABLE + "product")
public class Product extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private ProductCategory category;

    @Column(name = "tag")
    private String tags;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "avg_star")
    private Integer avgStar = 0;
    private Integer totalReview = 0;

    @Column(name = "sold_amount")
    private Integer soldAmount = 0;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    private Integer saleOff;   //percent
    private Boolean isSaleOff = false;

    @Column(name = "image")
    private String image;

    @Column(name = "is_sold_out")
    private Boolean isSoldOut = false;

    @Column(name = "kind")
    private Integer kind;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    private Product parentProduct;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = ProductConfig.class)
    @JoinColumn(name = "product_id")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    List<ProductConfig> productConfigs = new ArrayList<>();


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(name = TablePrefix.PREFIX_TABLE+"customer_favorites",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id",
                    referencedColumnName = "id"))
    private List<Customer> customersLiked = new ArrayList<>();
}
