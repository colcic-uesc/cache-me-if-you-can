package com.cachemeifyoucan.econometro.infrastructure.config;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.cachemeifyoucan.econometro.application.dto.CreateUserRequest;
import com.cachemeifyoucan.econometro.domain.model.Brand;
import com.cachemeifyoucan.econometro.domain.model.Category;
import com.cachemeifyoucan.econometro.domain.model.Product;
import com.cachemeifyoucan.econometro.domain.repository.BrandRepository;
import com.cachemeifyoucan.econometro.domain.repository.CategoryRepository;
import com.cachemeifyoucan.econometro.domain.repository.ProductRepository;
import com.cachemeifyoucan.econometro.domain.repository.UserRepository;
import com.cachemeifyoucan.econometro.domain.service.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
@Profile("dev")
public class DatabaseInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserService userService;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    private List<Brand> brands;
    private List<Category> categories;
    private List<Product> products;

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
        brands = List.of("Samsung", "Apple", "Google", "Motorola", "Xiaomi").stream()
                .map(brand -> new Brand(brand)).toList();

        brands = brandRepository.saveAll(brands);
    }

    private void createCategories() {
        if (categoryRepository.count() > 0) {
            return;
        }

        categories = List.of(
                "Eletronics",
                "Smartphones",
                "Tablets",
                "Accessories").stream()
                .map(category -> new Category(category)).toList();

        categories.get(1).setParent(categories.get(0));

        categories = categoryRepository.saveAll(categories);
    }

    private void createProducts() {
        products = new ArrayList<>();
        products.add(new Product("Galaxy S24", "Description for product 1", BigDecimal.valueOf(10.99), 100,
                brands.get(0), categories.get(2)));
        products.add(new Product("iPhone 15", "Description for product 2", BigDecimal.valueOf(20.99), 200,
                brands.get(1), categories.get(2)));
        products.add(new Product("Pixel 8", "Description for product 3", BigDecimal.valueOf(30.99), 300, brands.get(2),
                categories.get(2)));
        products.add(new Product("Moto G15", "Description for product 4", BigDecimal.valueOf(40.99), 400, brands.get(3),
                categories.get(2)));
        products.add(new Product("Redmi 13", "Description for product 5", BigDecimal.valueOf(50.99), 500, brands.get(4),
                categories.get(2)));

        products = productRepository.saveAll(products);
    }
}
