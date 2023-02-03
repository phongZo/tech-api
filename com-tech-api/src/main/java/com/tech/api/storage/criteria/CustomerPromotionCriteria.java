package com.tech.api.storage.criteria;

import com.tech.api.storage.model.Customer;
import com.tech.api.storage.model.CustomerPromotion;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Data
public class CustomerPromotionCriteria {
    private Boolean inUse;
    private Long customerId;

    public Specification<CustomerPromotion> getSpecification() {
        return (root, criteriaQuery, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (getCustomerId() != null && getInUse() != null && !getInUse()){
                Join<CustomerPromotion, Customer> joinPromotion = root.join("customer", JoinType.INNER);
                predicates.add(cb.and(cb.equal(root.get("isInUse"), false),cb.equal(joinPromotion.get("id"), getCustomerId())));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
