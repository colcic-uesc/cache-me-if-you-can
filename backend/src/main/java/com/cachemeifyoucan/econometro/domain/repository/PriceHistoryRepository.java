package com.cachemeifyoucan.econometro.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cachemeifyoucan.econometro.domain.model.PriceHistory;
import com.cachemeifyoucan.econometro.domain.model.Product;

@Repository
public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Long> {

    List<PriceHistory> findByProduct(Product product);
 
}
