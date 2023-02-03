package com.tech.api.storage.repository;

import com.tech.api.storage.model.LineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface LineItemRepository extends JpaRepository<LineItem, Long>, JpaSpecificationExecutor<LineItem> {
    LineItem findByCartIdAndVariantId(Long cartId, Long productVariantId);
    List<LineItem> findByCartId(Long cartId);
}
