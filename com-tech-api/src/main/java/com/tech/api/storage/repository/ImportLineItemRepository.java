package com.tech.api.storage.repository;

import com.tech.api.storage.model.ImportLineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ImportLineItemRepository extends JpaRepository<ImportLineItem, Long>, JpaSpecificationExecutor<ImportLineItem> {
    ImportLineItem findFirstByVariantIdAndAnImportId(Long variantId, Long importId);

    List<ImportLineItem> findByAnImportId(Long id);
}
