package com.tech.api.storage.repository;

import com.tech.api.storage.model.CustomerViewProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerViewProductRepository extends JpaRepository<CustomerViewProduct, Long>{
    CustomerViewProduct findFirstByCustomerIdAndProductId(Long customerId, Long productId);
}
