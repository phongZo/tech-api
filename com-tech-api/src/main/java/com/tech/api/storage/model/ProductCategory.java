package com.tech.api.storage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "product_category")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategory extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    private ProductCategory parentCategory;    // no need

    @Column(name = "name")
    private String name;

    @Column(name = "order_sort")
    private Integer orderSort;

    @Column(name = "icon")
    private String icon;

    @Column(name = "note")
    private String note;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ProductCategory> productCategoryList;
}
