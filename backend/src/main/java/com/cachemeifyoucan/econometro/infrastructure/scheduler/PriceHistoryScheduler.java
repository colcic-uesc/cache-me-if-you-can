package com.cachemeifyoucan.econometro.infrastructure.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cachemeifyoucan.econometro.domain.service.PriceHistoryService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class PriceHistoryScheduler {
 
    private final PriceHistoryService priceHistoryService;

    @Scheduled(cron = "${history.price.cron:0 0 12 * * ?}") // This cron expression schedules the task to run every day at 12 PM
    public void createPriceHistory() {
        priceHistoryService.createPriceHistory();
    }
}
