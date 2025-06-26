package com.cachemeifyoucan.econometro.domain.service;

import org.springframework.stereotype.Service;

import com.cachemeifyoucan.econometro.application.dto.CreateOfferRequest;
import com.cachemeifyoucan.econometro.application.dto.UpdateOfferRequest;
import com.cachemeifyoucan.econometro.domain.model.Offer;
import com.cachemeifyoucan.econometro.domain.model.OfferId;
import com.cachemeifyoucan.econometro.domain.model.Product;
import com.cachemeifyoucan.econometro.domain.model.Seller;
import com.cachemeifyoucan.econometro.domain.repository.OfferRepository;
import com.cachemeifyoucan.econometro.domain.repository.ProductRepository;
import com.cachemeifyoucan.econometro.domain.repository.SellerRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class OfferService {
    private final OfferRepository offerRepository;
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;

    @Transactional
    public Offer createOffer(CreateOfferRequest dto) {
        if (offerRepository.existsById(new OfferId(dto.productId(), dto.sellerId()))) {
            throw new IllegalArgumentException("Offer with this name already exists");
        }

        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found for id " + dto.productId()));

        Seller seller = sellerRepository.findById(dto.sellerId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found for id " + dto.sellerId()));

        return offerRepository.save(new Offer(dto.price(), product, seller));
    }

    public Offer getOfferById(OfferId id) {
        return offerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Offer not found for product id " + id.getProductId() + ", seller id " + id.getSellerId()));
    }

    @Transactional
    public Offer updateOffer(long productId, long sellerId, UpdateOfferRequest dto) {
        Offer offer = getOfferById(new OfferId(productId, sellerId));

        offer.setPrice(dto.price());
        offer.setEnabled(dto.enabled());
        return offerRepository.save(offer);
    }

}
