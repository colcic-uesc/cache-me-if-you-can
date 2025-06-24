package com.cachemeifyoucan.econometro.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cachemeifyoucan.econometro.domain.model.Seller;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

    boolean existsByName(String name);

}
