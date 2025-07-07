package com.cachemeifyoucan.econometro.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cachemeifyoucan.econometro.application.dto.SellerRequest;
import com.cachemeifyoucan.econometro.domain.model.Image;
import com.cachemeifyoucan.econometro.domain.model.Seller;
import com.cachemeifyoucan.econometro.domain.model.User;
import com.cachemeifyoucan.econometro.domain.repository.SellerRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SellerService {
    private final SellerRepository sellerRepository;
    private final UserService userService;

    public Seller createSeller(SellerRequest dto) {
        if (sellerRepository.existsByName(dto.name())) {
            throw new IllegalArgumentException("Seller with this name already exists");
        }

        User manager = userService.findById(dto.managerId());

        Seller seller = new Seller();
        seller.setName(dto.name());
        seller.setCnpj(dto.cnpj());
        seller.setImage(new Image(dto.imageContent()));
        seller.setManager(manager);
        return sellerRepository.save(seller);
    }

    public Seller getSellerById(long id) {
        return sellerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Seller not found with id: " + id));
    }

    public List<Seller> getAllSellers() {
        return sellerRepository.findAll();
    }

    public Seller updateSeller(long id, SellerRequest dto) {
        Seller seller = getSellerById(id);
        seller.setName(dto.name());
        seller.setCnpj(dto.cnpj());
        seller.setImage(new Image(dto.imageContent()));
        return sellerRepository.save(seller);

    }

    @Transactional
    public void deleteSeller(long id) {
        if (!sellerRepository.existsById(id)) {
            throw new IllegalArgumentException("Seller not found with id: " + id);
        }

        sellerRepository.deleteById(id);
    }

}
