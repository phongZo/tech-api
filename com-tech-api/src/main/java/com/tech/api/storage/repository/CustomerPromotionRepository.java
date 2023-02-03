package com.tech.api.storage.repository;

import com.tech.api.storage.model.CustomerPromotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CustomerPromotionRepository extends JpaRepository<CustomerPromotion, Long>, JpaSpecificationExecutor<CustomerPromotion> {
}
