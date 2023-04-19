package com.tech.api.storage.repository;

import com.tech.api.storage.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StockRepository extends JpaRepository<Stock, Long>, JpaSpecificationExecutor<Stock> {
    Stock findFirstByProductVariantIdAndStoreId(Long productId, Long storeId);
}
