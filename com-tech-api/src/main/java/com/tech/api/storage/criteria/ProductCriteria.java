package com.tech.api.storage.criteria;

import com.tech.api.storage.model.Product;
import com.tech.api.storage.model.ProductCategory;
import com.tech.api.storage.model.ProductConfig;
import com.tech.api.validation.ProductKind;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ProductCriteria {
    private Long id;
    private Long categoryId;
    private Boolean isSaleOff;
    private List<String> tags;
    private String description;
    private String name;
    private Double fromPrice;
    private Double toPrice;
    private Boolean isSoldOut;
    private Long parentProduct;
    private Long customerId;
    private Boolean isLike;
    @ProductKind
    private Integer kind;
    private List<String> variantNames;
    private Date from;
    private Date to;
    private Long storeId;
    private Integer status;

    public Specification<Product> getSpecification() {
        return (root, criteriaQuery, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (getId() != null) {
                predicates.add(cb.equal(root.get("id"), getId()));
            }

            if (getCategoryId() != null) {
                Join<Product, ProductCategory> productCategoryJoin = root.join("category", JoinType.INNER);
                predicates.add(cb.equal(productCategoryJoin.get("id"), getCategoryId()));
            }

            if (getDescription() != null) {
                predicates.add(cb.like(cb.lower(root.get("description")), "%" + getDescription().toLowerCase() + "%"));
            }

            if (getName() != null) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + getName().toLowerCase() + ""));
            }
            if(getStatus() != null){
                predicates.add(cb.equal(root.get("status"), getStatus()));
            }

            if (getIsSaleOff() != null){
                criteriaQuery.orderBy(cb.desc(root.get("soldAmount")));
                predicates.add(cb.equal(root.get("isSaleOff"), getIsSaleOff()));
            }

            if (getFromPrice() != null) {
                predicates.add(cb.ge(root.get("price"), getFromPrice()));
            }

            if (getToPrice() != null) {
                predicates.add(cb.le(root.get("price"), getToPrice()));
            }

            if (getIsSoldOut() != null) {
                predicates.add(cb.equal(root.get("isSoldOut"), getIsSoldOut()));
            }

            if (getParentProduct() != null) {
                predicates.add(cb.equal(root.get("parentProduct"), getParentProduct()));
            } else {
                predicates.add(cb.isNull(root.get("parentProduct")));
            }

            if (getKind() != null) {
                predicates.add(cb.equal(root.get("kind"), getKind()));
            }

            if (getVariantNames() != null) {
                Join<Product, ProductConfig> productConfigJoin = root.join("productConfigs", JoinType.INNER);
                this.variantNames = getVariantNames().stream().map(v -> v.toLowerCase().trim()).collect(Collectors.toList());
                predicates.add(cb.lower(productConfigJoin.join("variants").get("name")).in(getVariantNames()));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
