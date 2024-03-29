package com.tech.api.storage.repository;

import com.tech.api.storage.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PromotionRepository extends JpaRepository<Promotion, Long>, JpaSpecificationExecutor<Promotion> {
    Promotion findFirstByTitle(String title);
    List<Promotion> findAllByLoyaltyLevel(int loyaltyLevel);
}
