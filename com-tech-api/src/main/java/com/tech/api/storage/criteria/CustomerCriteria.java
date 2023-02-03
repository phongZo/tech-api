package com.tech.api.storage.criteria;

import com.tech.api.storage.model.Account;
import com.tech.api.storage.model.Customer;
import com.tech.api.validation.Gender;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CustomerCriteria {
    private Long id;
    private Integer kind;
    private String username;
    private String email;
    private String fullName;
    @Gender
    private Integer gender;
    private Long groupId;
    private String phone;
    private Integer status;
    private LocalDate birthday;
    private String note;

    public Specification<Customer> getSpecification() {
        return (root, criteriaQuery, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Join<Customer, Account> joinAccount = root.join("account", JoinType.INNER);

            if (getId() != null) {
                predicates.add(cb.equal(root.get("id"), getId()));
            }

            if (getKind() != null) {
                predicates.add(cb.equal(joinAccount.get("kind"), getKind()));
            }

            if (StringUtils.isNoneBlank(getUsername())) {
                predicates.add(cb.like(cb.lower(joinAccount.get("username")), "%" + getUsername().toLowerCase() + "%"));
            }

            if (StringUtils.isNoneBlank(getEmail())) {
                predicates.add(cb.like(cb.lower(joinAccount.get("email")), "%" + getEmail().toLowerCase() + "%"));
            }

            if (StringUtils.isNoneBlank(getFullName())) {
                predicates.add(cb.like(cb.lower(joinAccount.get("fullName")), "%" + getFullName().toLowerCase() + "%"));
            }

            if (getGender() != null) {
                predicates.add(cb.equal(root.get("gender"), getGender()));
            }

            if (getGroupId() != null) {
                predicates.add(cb.equal(joinAccount.join("group").get("id"), getGroupId()));
            }

            if (StringUtils.isNoneBlank(getPhone())) {
                predicates.add(cb.like(cb.lower(joinAccount.get("phone")), "%" + getPhone().toLowerCase() + "%"));
            }

            if (getStatus() != null) {
                predicates.add(cb.equal(joinAccount.get("status"), getStatus()));
            }

            if (getBirthday() != null) {
                predicates.add(cb.equal(root.get("birthday"), getBirthday()));
            }

            if (StringUtils.isNoneBlank(getNote())) {
                predicates.add(cb.like(root.get("note"), "%" + getNote().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
