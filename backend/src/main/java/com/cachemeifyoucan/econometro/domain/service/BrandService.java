package com.cachemeifyoucan.econometro.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cachemeifyoucan.econometro.application.dto.BrandRequest;
import com.cachemeifyoucan.econometro.domain.model.Brand;
import com.cachemeifyoucan.econometro.domain.repository.BrandRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BrandService {
    private final BrandRepository brandRepository;

    public Brand createBrand(BrandRequest dto) {
        if (brandRepository.existsByName(dto.name())) {
            throw new IllegalArgumentException("Brand with this name already exists");
        }

        Brand brand = new Brand(dto.name());
        return brandRepository.save(brand);
    }

    public Brand getBrandById(long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Brand not found with id: " + id));
    }

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public Brand updateBrand(long id, BrandRequest dto) {
        Brand brand = getBrandById(id);
        if (brand.getName().equals(dto.name())) {
            return brand;
        }
        if (brandRepository.existsByName(dto.name())) {
            throw new IllegalArgumentException("Brand with this name already exists");
        }
        brand.setName(dto.name());
        return brandRepository.save(brand);
    }

    public void deleteBrand(long id) {
        if (!brandRepository.existsById(id)) {
            throw new IllegalArgumentException("Brand not found with id: " + id);
        }
        brandRepository.deleteById(id);
    }

}
