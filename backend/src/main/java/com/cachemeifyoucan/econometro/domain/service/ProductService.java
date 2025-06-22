package com.cachemeifyoucan.econometro.domain.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cachemeifyoucan.econometro.application.dto.CreateProductRequest;
import com.cachemeifyoucan.econometro.application.dto.UpdateProductRequest;
import com.cachemeifyoucan.econometro.domain.model.Brand;
import com.cachemeifyoucan.econometro.domain.model.Category;
import com.cachemeifyoucan.econometro.domain.model.Product;
import com.cachemeifyoucan.econometro.domain.repository.ProductRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final BrandService brandService;
    private final CategoryService categoryService;

    public Product createProduct(CreateProductRequest dto) {
        if (productRepository.existsByTitle(dto.title())) {
            throw new IllegalArgumentException("Product with this title already exists");
        }

        Brand brand = brandService.getBrandById(dto.brandId());
        Category category = categoryService.getCategoryById(dto.categoryId());

        Product product = new Product(dto.title(), dto.description(), dto.price(), dto.stockQuantity(), brand,
                category);

        return productRepository.save(product);
    }

    public Product getProductById(long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
    }

    public List<Product> getAllProducts(String query) {
        if (query == null || query.isEmpty()) {
            return productRepository.findAll();
        }
        return productRepository.findByTextQuery(query);
    }

    public Product updateProduct(long id, UpdateProductRequest dto) {
        Product product = getProductById(id);
        product.setTitle(dto.title());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setStockQuantity(dto.stockQuantity());
        product.setEnabled(dto.enabled() != null ? dto.enabled() : product.isEnabled());
        product.setBrand(brandService.getBrandById(dto.brandId()));
        product.setCategory(categoryService.getCategoryById(dto.categoryId()));
        product.setUpdatedAt(LocalDateTime.now());

        return productRepository.save(product);
    }

    public void deleteProduct(long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
}
