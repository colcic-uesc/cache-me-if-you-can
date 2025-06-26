package com.cachemeifyoucan.econometro.infrastructure.scheduler;

import java.util.List;
import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cachemeifyoucan.econometro.domain.model.Offer;
import com.cachemeifyoucan.econometro.domain.model.Product;
import com.cachemeifyoucan.econometro.domain.repository.OfferRepository;
import com.cachemeifyoucan.econometro.domain.repository.ProductRepository;
import com.cachemeifyoucan.econometro.domain.service.PriceHistoryService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class PriceHistoryScheduler {

    private final OfferRepository offerRepository;
    private final ProductRepository productRepository;
    private final PriceHistoryService priceHistoryService;

    @Scheduled(cron = "${history.price.cron:0 0 12 * * ?}") // This cron expression schedules the task to run every day at 12 PM
    public void createPriceHistory() {
        List<Product> products = productRepository.findByEnabled(true);

        for (Product product : products) {
            Optional<Offer> optOffer = offerRepository.findFirstByProductIdAndEnabledOrderByPriceAsc(product.getId(), true);
            optOffer.ifPresent(offer -> {
                priceHistoryService.create(product, offer.getPrice());
            });
        }
    }
}
