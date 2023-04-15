package com.tech.api.storage.repository;

import com.tech.api.storage.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Long>, JpaSpecificationExecutor<Orders> {
    Orders findOrdersByIdAndCustomerId(Long ordersId, Long customerId);
    @Query("SELECT sum(o.totalMoney) FROM Orders o WHERE o IN :list GROUP BY o.id")
    Double sumMoney(@Param("list") List<Orders> orders);

    @Query("SELECT MAX(o.id) FROM Orders o")
    Long findMaxId();

    @Transactional()
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE tech_orders SET state = 5 WHERE state = 3 OR state = 4",nativeQuery = true)
    void updateArchive();
}

