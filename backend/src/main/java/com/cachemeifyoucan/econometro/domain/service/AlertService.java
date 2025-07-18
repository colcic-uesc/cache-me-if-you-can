package com.cachemeifyoucan.econometro.domain.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cachemeifyoucan.econometro.application.dto.AlertRequest;
import com.cachemeifyoucan.econometro.application.dto.AlertResponse;
import com.cachemeifyoucan.econometro.domain.model.Alert;
import com.cachemeifyoucan.econometro.domain.model.AlertId;
import com.cachemeifyoucan.econometro.domain.model.Offer;
import com.cachemeifyoucan.econometro.domain.model.Product;
import com.cachemeifyoucan.econometro.domain.model.Seller;
import com.cachemeifyoucan.econometro.domain.model.User;
import com.cachemeifyoucan.econometro.domain.repository.AlertRepository;
import com.cachemeifyoucan.econometro.domain.repository.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AlertService {
    private final AlertRepository alertRepository;
    private final ProductRepository productRepository;
    private final UserService userService;

    @Transactional
    public Alert createAlert(AlertRequest dto) {
        User user = userService.getLoggedInUser();

        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found for id " + dto.productId()));

        if (alertRepository.existsById(new AlertId(dto.productId(), user.getId()))) {
            throw new IllegalArgumentException("You already have an alert for this product");
        }

        return alertRepository.save(new Alert(dto.desiredPrice(), user, product));
    } 

    public Alert getAlertById(AlertId id){
        return alertRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Alert not found for user " + id.getUserId() + " and product " + id.getProductId()));
    }

    @Transactional
    public Alert updateAlert(AlertRequest dto) {
        User user = userService.getLoggedInUser();

         Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found for id " + dto.productId()));

        Alert alert = getAlertById(new AlertId(product.getId(), user.getId()));

        alert.setDesiredPrice(dto.desiredPrice()); 
        return alertRepository.save(alert);
    }

    public List<AlertResponse> getAllAlertsForUser() { 
         User user = userService.getLoggedInUser();
        List<Alert> alerts = alertRepository.findByUser(user);
        
        return alerts.stream()
        .map(alert -> {
            Offer offer = alert.getProduct().getBestOffer();
            AlertResponse.Offer fullfillingOffer = null;
            BigDecimal bestPrice = null;
            if (offer != null) {
                bestPrice = offer.getPrice();
                if (alert.getDesiredPrice().compareTo(offer.getPrice()) >= 0) {
                    Seller seller = offer.getSeller();
                    String sellerImage = seller.getImage() == null ? null : seller.getImage().getContent();
                    fullfillingOffer = new AlertResponse.Offer(seller.getId(), seller.getName(), sellerImage, offer.getPrice());
                }
            }
            String imageContent = null;
            if(alert.getProduct().getImages()!= null && !alert.getProduct().getImages().isEmpty()){
                imageContent = alert.getProduct().getImages().get(0).getContent();
            }
            return new AlertResponse(alert.getProduct().getId(), alert.getProduct().getTitle(), alert.getDesiredPrice(), bestPrice, imageContent, fullfillingOffer);
        })
        .toList();
    }

    @Transactional
    public void deleteAlert(long productId) {
        User user = userService.getLoggedInUser();

        AlertId id = new AlertId(productId, user.getId());
        if (!alertRepository.existsById(id)) {
            throw new IllegalArgumentException("Alert not found for product " + productId);
        }

         alertRepository.deleteById(id);
     
    }

}
