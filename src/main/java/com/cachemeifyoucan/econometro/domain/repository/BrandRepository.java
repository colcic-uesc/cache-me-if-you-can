package com.cachemeifyoucan.econometro.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cachemeifyoucan.econometro.domain.model.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

}
