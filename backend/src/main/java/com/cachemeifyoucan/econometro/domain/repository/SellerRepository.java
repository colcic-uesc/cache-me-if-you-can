package com.cachemeifyoucan.econometro.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cachemeifyoucan.econometro.domain.model.Seller;
import com.cachemeifyoucan.econometro.domain.model.User;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

    boolean existsByName(String name);

    Optional<Seller> findByManager(User manager);

}
