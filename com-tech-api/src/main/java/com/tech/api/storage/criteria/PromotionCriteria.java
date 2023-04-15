package com.tech.api.storage.criteria;

import com.tech.api.storage.model.Promotion;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
@Data
public class PromotionCriteria {
    private Long id;
    private Boolean exchangeable;
    public Specification<Promotion> getSpecification() {
        return new Specification<Promotion>() {
            private static final long seriatechersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Promotion> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if(getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if(getExchangeable() != null && getExchangeable()){
                    predicates.add(cb.equal(root.get("exchangeable"), true));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
