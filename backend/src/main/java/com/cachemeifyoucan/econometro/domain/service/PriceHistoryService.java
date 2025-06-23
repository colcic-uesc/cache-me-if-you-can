package com.cachemeifyoucan.econometro.domain.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cachemeifyoucan.econometro.domain.model.Offer;
import com.cachemeifyoucan.econometro.domain.model.PriceHistory;
import com.cachemeifyoucan.econometro.domain.model.Product;
import com.cachemeifyoucan.econometro.domain.repository.OfferRepository;
import com.cachemeifyoucan.econometro.domain.repository.PriceHistoryRepository;
import com.cachemeifyoucan.econometro.domain.repository.ProductRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PriceHistoryService {
    private final PriceHistoryRepository priceHistoryRepository;
    private final ProductRepository productRepository;
    private final OfferRepository offerRepository;
   
    public List<PriceHistory> findByProduct(Product product) {
        return priceHistoryRepository.findByProduct(product);
    }

     public void createPriceHistory() {
        List<Product> products = productRepository.findByEnabled(true);

        for (Product product : products) {
            Optional<Offer> optOffer = offerRepository.findFirstByProductIdAndEnabledOrderByPriceAsc(product.getId(), true);
            optOffer.ifPresent(offer -> { 
                priceHistoryRepository.save(new PriceHistory(product, offer.getPrice()));
            });
        }
    }
}
