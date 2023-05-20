package com.tech.api.storage.repository;

import com.tech.api.storage.model.Import;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;

public interface ImportRepository extends JpaRepository<Import, Long>, JpaSpecificationExecutor<Import> {
    Import findFirstByDateAndStoreIdAndState(LocalDate date, Long storeId, Integer state);
}
