package com.tech.api.storage.criteria;

import com.tech.api.storage.model.Customer;
import com.tech.api.storage.model.CustomerAddress;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CustomerAddressCriteria {
    private Long id;
    private Long provinceId;
    private Long districtId;
    private Long wardId;
    private String addressDetails;
    private String receiverFullName;
    private String phone;
    private Long customerId;

    public Specification<CustomerAddress> getSpecification() {
        return (root, criteriaQuery, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (getId() != null) {
                predicates.add(cb.equal(root.get("id"), getId()));
            }

            if (getProvinceId() != null) {
                predicates.add(cb.equal(root.get("province_id"), getProvinceId()));
            }

            if (getDistrictId() != null) {
                predicates.add(cb.equal(root.get("district_id"), getDistrictId()));
            }

            if (getWardId() != null) {
                predicates.add(cb.equal(root.get("ward_id"), getWardId()));
            }

            if (!StringUtils.isEmpty(getAddressDetails())) {
                predicates.add(cb.like(cb.lower(root.get("addressDetails")), "%" + getAddressDetails().toLowerCase() + "%"));
            }

            if (!StringUtils.isEmpty(getReceiverFullName())) {
                predicates.add(cb.like(cb.lower(root.get("receiver_full_name")), "%" + getReceiverFullName().toLowerCase() + "%"));
            }

            if (!StringUtils.isEmpty(getPhone())) {
                predicates.add(cb.like(cb.lower(root.get("phone")), "%" + getPhone().toLowerCase() + "%"));
            }

            if (getCustomerId() != null) {
                Join<CustomerAddress, Customer> joinCustomer = root.join("customer", JoinType.INNER);
                predicates.add(cb.equal(joinCustomer.get("id"), getCustomerId()));
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
