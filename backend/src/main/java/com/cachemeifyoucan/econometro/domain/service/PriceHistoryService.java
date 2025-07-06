package com.cachemeifyoucan.econometro.domain.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cachemeifyoucan.econometro.domain.model.PriceHistory;
import com.cachemeifyoucan.econometro.domain.model.Product;
import com.cachemeifyoucan.econometro.domain.repository.PriceHistoryRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PriceHistoryService {
    private final PriceHistoryRepository priceHistoryRepository;
   
    public void create(Product product, BigDecimal price){
        priceHistoryRepository.save(new PriceHistory(product, price));
    }

    public List<PriceHistory> findByProduct(Product product) {
        return priceHistoryRepository.findByProduct(product);
    }
}
