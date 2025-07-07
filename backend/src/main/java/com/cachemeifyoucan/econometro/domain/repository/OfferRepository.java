package com.cachemeifyoucan.econometro.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cachemeifyoucan.econometro.domain.model.Offer;
import com.cachemeifyoucan.econometro.domain.model.OfferId;
import com.cachemeifyoucan.econometro.domain.model.Seller;

@Repository
public interface OfferRepository extends JpaRepository<Offer, OfferId> {
    Optional<Offer> findFirstByProductIdAndEnabledOrderByPriceAsc(Long productId, boolean enabled);

    List<Offer> findBySeller(Seller seller);

}
