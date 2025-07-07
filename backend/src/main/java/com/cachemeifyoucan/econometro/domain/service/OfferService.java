package com.cachemeifyoucan.econometro.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cachemeifyoucan.econometro.application.dto.CreateOfferRequest;
import com.cachemeifyoucan.econometro.application.dto.OfferResponse;
import com.cachemeifyoucan.econometro.application.dto.UpdateOfferRequest;
import com.cachemeifyoucan.econometro.domain.model.Offer;
import com.cachemeifyoucan.econometro.domain.model.OfferId;
import com.cachemeifyoucan.econometro.domain.model.Product;
import com.cachemeifyoucan.econometro.domain.model.Seller;
import com.cachemeifyoucan.econometro.domain.model.User;
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
    private final UserService userService;

    @Transactional
    public Offer createOffer(CreateOfferRequest dto) {
        Seller seller = getSellerForLoggedInUser();

        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found for id " + dto.productId()));

        if (offerRepository.existsById(new OfferId(dto.productId(), seller.getId()))) {
            throw new IllegalArgumentException("Offer with this name already exists");
        }

        return offerRepository.save(new Offer(dto.price(), product, seller));
    }

    public Offer getOfferById(OfferId id) {
        return offerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Offer not found for product id " + id.getProductId() + ", seller id " + id.getSellerId()));
    }

    @Transactional
    public Offer updateOffer(UpdateOfferRequest dto) {
        Seller seller = getSellerForLoggedInUser();
        Offer offer = getOfferById(new OfferId(dto.productId(), seller.getId()));

        offer.setPrice(dto.price());
        offer.setEnabled(dto.enabled());
        return offerRepository.save(offer);
    }

    private Seller getSellerForLoggedInUser() {
        User user = userService.getLoggedInUser();
        return sellerRepository.findByManager(user)
                .orElseThrow(() -> new IllegalArgumentException("Seller not found for user " + user.getEmail()));

    }

    public List<OfferResponse> getAllOffersFromSeller() { 
         Seller seller = getSellerForLoggedInUser();
        return offerRepository.findBySeller(seller).stream()
        .map(offer -> new OfferResponse(offer.getProduct().getId(), offer.getProduct().getTitle(), offer.getPrice(), offer.isEnabled()))
        .toList();      
    }

}
