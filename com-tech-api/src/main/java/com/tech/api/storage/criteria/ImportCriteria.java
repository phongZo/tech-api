package com.tech.api.storage.criteria;

import com.tech.api.storage.model.*;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class ImportCriteria {
    private Long storeId;
    public Specification<Import> getSpecification() {
        return new Specification<Import>() {
            private static final long seriatechersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Import> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                query.orderBy(cb.desc(root.get("createdDate")));
                if(getStoreId() != null) {
                    Join<Store, Import> joinStore = root.join("store", JoinType.INNER);
                    predicates.add(cb.equal(joinStore.get("id"), getStoreId()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
