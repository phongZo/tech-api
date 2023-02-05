package com.tech.api.storage.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = TablePrefix.PREFIX_TABLE + "product_config")
public class ProductConfig extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "choice_kind")
    private Integer choiceKind;

    @Column(name = "is_required")
    private Boolean isRequired;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = ProductVariant.class)
    @JoinColumn(name = "product_config_id")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @OrderBy(value = "orderSort")
    List<ProductVariant> variants = new ArrayList<>();
}
