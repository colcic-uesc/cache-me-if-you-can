package com.example.cache_me_if_you_can.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cache_me_if_you_can.domain.model.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

}
