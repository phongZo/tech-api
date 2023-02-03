package com.tech.api.storage.repository;

import com.tech.api.storage.model.Orders;
import com.tech.api.storage.model.OrdersDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrdersDetailRepository extends JpaRepository<OrdersDetail, Long>, JpaSpecificationExecutor<OrdersDetail> {
    List<OrdersDetail> findAllByOrdersId(Long id);

    @Query("SELECT d FROM OrdersDetail d JOIN Orders o ON d.orders = o" +
            " WHERE o IN :list")
    List<OrdersDetail> findFirstByListOrders(@Param("list")List<Orders> list);
}
