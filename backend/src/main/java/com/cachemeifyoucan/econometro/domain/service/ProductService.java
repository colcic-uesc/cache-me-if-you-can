package com.cachemeifyoucan.econometro.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cachemeifyoucan.econometro.application.dto.CreateProductRequest;
import com.cachemeifyoucan.econometro.application.dto.ProductDetailedResponse;
import com.cachemeifyoucan.econometro.application.dto.UpdateProductRequest;
import com.cachemeifyoucan.econometro.domain.model.Brand;
import com.cachemeifyoucan.econometro.domain.model.Category;
import com.cachemeifyoucan.econometro.domain.model.Image;
import com.cachemeifyoucan.econometro.domain.model.Offer;
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
        List<Image> images = dto.images().stream().map(content -> new Image(content)).toList();

        Product product = new Product(dto.title(), dto.description(), dto.released(), brand,
                category, images);

        return productRepository.save(product);
    }

    public Product getProductById(long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
    }

    public ProductDetailedResponse getProductDetailed(long id) {
        Product product = getProductById(id);

        List<String> images = product.getImages().stream().map(img -> img.getContent()).toList();
        List<ProductDetailedResponse.Offer> offers = product.getOffers().stream().filter(Offer::isEnabled)
                .map(offer -> new ProductDetailedResponse.Offer(offer.getSeller().getId(), offer.getSeller().getName(),
                        offer.getSeller().getImage().getContent(), offer.getPrice()))
                .toList();

        List<ProductDetailedResponse.History> priceHistory = product.getHistory().stream()
                .map(history -> new ProductDetailedResponse.History(history.getCreationDate(), history.getPrice()))
                .toList();

        return new ProductDetailedResponse(product.getTitle(), product.getDescription(), product.getReleased(),
                product.getBrand(), product.getCategory(), product.getBestPrice(), images, offers, priceHistory);
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
        product.setEnabled(dto.enabled() != null ? dto.enabled() : product.isEnabled());
        product.setBrand(brandService.getBrandById(dto.brandId()));
        product.setCategory(categoryService.getCategoryById(dto.categoryId()));

        return productRepository.save(product);
    }

    public void deleteProduct(long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
}
