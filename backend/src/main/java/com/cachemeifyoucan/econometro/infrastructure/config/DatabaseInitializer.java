package com.cachemeifyoucan.econometro.infrastructure.config;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.cachemeifyoucan.econometro.application.dto.BrandRequest;
import com.cachemeifyoucan.econometro.application.dto.CategoryRequest;
import com.cachemeifyoucan.econometro.application.dto.CreateProductRequest;
import com.cachemeifyoucan.econometro.application.dto.CreateUserRequest;
import com.cachemeifyoucan.econometro.domain.repository.BrandRepository;
import com.cachemeifyoucan.econometro.domain.repository.CategoryRepository;
import com.cachemeifyoucan.econometro.domain.repository.ProductRepository;
import com.cachemeifyoucan.econometro.domain.repository.UserRepository;
import com.cachemeifyoucan.econometro.domain.service.BrandService;
import com.cachemeifyoucan.econometro.domain.service.CategoryService;
import com.cachemeifyoucan.econometro.domain.service.ProductService;
import com.cachemeifyoucan.econometro.domain.service.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
@Profile("dev")
public class DatabaseInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserService userService;
    private final BrandRepository brandRepository;
    private final BrandService brandService;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;
    private final ProductRepository productRepository;
    private final ProductService productService;

    @Override
    public void run(String... args) throws Exception {
        createUsers();
        createBrands();
        createCategories();
        createProducts();
    }

    private void createUsers() {
        if (userRepository.count() > 0) {
            return;
        }
        List<CreateUserRequest> users = new ArrayList<>();
        users.add(new CreateUserRequest("Tainah", "taih.marques@uesc.br", "123"));
        users.add(new CreateUserRequest("Alice Johnson", "alice.johnson@example.com", "password123"));
        users.add(new CreateUserRequest("Bob Smith", "bob.smith@example.com", "securepass"));
        users.add(new CreateUserRequest("Charlie Lee", "charlie.lee@example.com", "charliepwd"));
        users.add(new CreateUserRequest("Diana Evans", "diana.evans@example.com", "diana2024"));
        users.add(new CreateUserRequest("Ethan Brown", "ethan.brown@example.com", "ethanpass"));

        for (CreateUserRequest user : users) {
            userService.createUser(user);
        }
    }

    private void createBrands() {
        if (brandRepository.count() > 0) {
            return;
        }
        List<BrandRequest> brands = List.of("Samsung", "Apple", "Google", "Motorola", "Xiaomi").stream()
                .map(brand -> new BrandRequest(brand)).toList();

        for (BrandRequest brand : brands) {
            brandService.createBrand(brand);
        }
    }

    private void createCategories() {
        if (categoryRepository.count() > 0) {
            return;
        }

        List<CategoryRequest> categories = List.of(
                new CategoryRequest("Eletronics"),
                new CategoryRequest("Smartphones", 1),
                new CategoryRequest("Tablets", 1),
                new CategoryRequest("Accessories"));

        for (CategoryRequest category : categories) {
            categoryService.createCategory(category);
        }
    }

    private void createProducts() {
        if (productRepository.count() > 0) {
            return;
        }

        List<CreateProductRequest> products = List.of(
            new CreateProductRequest("Galaxy S24", "Description for product 1", BigDecimal.valueOf(10.99), 100, 1, 2),
            new CreateProductRequest("iPhone 15", "Description for product 2", BigDecimal.valueOf(20.99), 200, 2, 2),
            new CreateProductRequest("Pixel 8", "Description for product 3", BigDecimal.valueOf(30.99), 300, 3, 2),
            new CreateProductRequest("Moto G15", "Description for product 4", BigDecimal.valueOf(40.99), 400, 4, 2),
            new CreateProductRequest("Redmi 13", "Description for product 5", BigDecimal.valueOf(50.99), 500, 5, 2)
        );

        for (CreateProductRequest product : products) {
            productService.createProduct(product);
        }
    }
}
