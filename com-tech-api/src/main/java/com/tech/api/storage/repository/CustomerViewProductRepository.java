package com.tech.api.storage.repository;

import com.tech.api.storage.model.CustomerViewProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerViewProductRepository extends JpaRepository<CustomerViewProduct, Long>{
    CustomerViewProduct findFirstByCustomerIdAndProductId(Long customerId, Long productId);

    @Query("SELECT c FROM CustomerViewProduct c WHERE c.customer.id = :customerId " +
            "AND c.total = (SELECT MAX(c2.total) FROM CustomerViewProduct c2 WHERE c2.customer.id = :customerId) " +
            "ORDER BY c.timestamp DESC")
    List<CustomerViewProduct> findBestProduct(@Param("customerId") Long customerId);
}
