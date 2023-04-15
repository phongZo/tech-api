package com.tech.api.scheduler;

import com.tech.api.storage.repository.OrdersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderScheduler {
    @Autowired
    OrdersRepository ordersRepository;

    @Scheduled(cron = "0 0 0 * * ?", zone = "UTC")  //0am
    public void archiveOrders(){
        ordersRepository.updateArchive();
    }
}
