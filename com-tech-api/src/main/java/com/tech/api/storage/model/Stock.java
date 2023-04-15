package com.tech.api.storage.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "stock")
public class Stock extends Auditable<String>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id")
    private ProductVariant productVariant;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "store_id")
    private Store store;

    private Integer total = 0;
}
