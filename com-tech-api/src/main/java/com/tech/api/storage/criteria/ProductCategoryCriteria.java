package com.tech.api.storage.criteria;

import com.tech.api.storage.model.ProductCategory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProductCategoryCriteria {
    private Long id;
    private Long parentId;
    private String name;
    private Integer order;
    private String note;
    private Integer status;

    public Specification<ProductCategory> getSpecification() {
        return (root, criteriaQuery, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (getId() != null) {
                predicates.add(cb.equal(root.get("id"), getId()));
            }

            if (getParentId() != null) {
                predicates.add(cb.equal(root.get("parentCategory"), getParentId()));
            } else {
                predicates.add(cb.isNull(root.get("parentCategory")));
            }

            if (getName() != null) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + getName().toLowerCase() + "%"));
            }

            if (getOrder() != null) {
                predicates.add(cb.equal(root.get("orderSort"), getOrder()));
            }

            if (getNote() != null) {
                predicates.add(cb.like(cb.lower(root.get("note")), getNote()));
            }

            if (getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), getStatus()));
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
