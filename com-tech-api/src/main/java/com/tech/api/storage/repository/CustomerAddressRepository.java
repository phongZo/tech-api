package com.tech.api.storage.repository;

import com.tech.api.storage.model.CustomerAddress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, Long>, JpaSpecificationExecutor<CustomerAddress> {
    Page<CustomerAddress> findCustomerAddressByCustomerId(Long customerId, Pageable pageable);
    CustomerAddress findCustomerAddressByCustomerIdAndIsDefault(Long customerId, Boolean isDefault);
    CustomerAddress findByLatitudeAndLongitudeAndCustomerId(double lat, double longitude, Long id);
}
