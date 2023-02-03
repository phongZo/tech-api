package com.tech.api.storage.criteria;

import com.tech.api.storage.model.Store;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class StoreCriteria {
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;
    private Long provinceId;
    private Long districtId;
    private Long wardId;
    private String addressDetails;

    public Specification<Store> getSpecification() {
        return (root, criteriaQuery, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (getId() != null) {
                predicates.add(cb.equal(root.get("id"), getId()));
            }

            if (StringUtils.isNoneBlank(getName())) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + getName().toLowerCase() + "%"));
            }

            if (getLatitude() != null) {
                predicates.add((cb.equal(root.get("latitude"), getLatitude())));
            }

            if (getLongitude() != null) {
                predicates.add((cb.equal(root.get("longitude"), getLatitude())));
            }

            if (getProvinceId() != null) {
                predicates.add(cb.equal(root.get("province_id"), getProvinceId()));
            }

            if (getDistrictId() != null) {
                predicates.add(cb.equal(root.get("district_id"), getDistrictId()));
            }

            if (getProvinceId() != null) {
                predicates.add(cb.equal(root.get("ward_id"), getProvinceId()));
            }

            if (!org.springframework.util.StringUtils.isEmpty(getAddressDetails())) {
                predicates.add(cb.like(cb.lower(root.get("addressDetails")), "%" + getAddressDetails().toLowerCase() + "%"));
            }
            return cb.and(predicates.toArray(new javax.persistence.criteria.Predicate[predicates.size()]));
        };
    }
}
