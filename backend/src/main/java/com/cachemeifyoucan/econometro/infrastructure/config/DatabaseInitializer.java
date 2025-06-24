package com.cachemeifyoucan.econometro.infrastructure.config;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.cachemeifyoucan.econometro.application.dto.BrandRequest;
import com.cachemeifyoucan.econometro.application.dto.CategoryRequest;
import com.cachemeifyoucan.econometro.application.dto.CreateProductRequest;
import com.cachemeifyoucan.econometro.application.dto.CreateUserRequest;
import com.cachemeifyoucan.econometro.application.dto.SellerRequest;
import com.cachemeifyoucan.econometro.domain.repository.BrandRepository;
import com.cachemeifyoucan.econometro.domain.repository.CategoryRepository;
import com.cachemeifyoucan.econometro.domain.repository.ProductRepository;
import com.cachemeifyoucan.econometro.domain.repository.SellerRepository;
import com.cachemeifyoucan.econometro.domain.repository.UserRepository;
import com.cachemeifyoucan.econometro.domain.service.BrandService;
import com.cachemeifyoucan.econometro.domain.service.CategoryService;
import com.cachemeifyoucan.econometro.domain.service.ProductService;
import com.cachemeifyoucan.econometro.domain.service.SellerService;
import com.cachemeifyoucan.econometro.domain.service.UserService;

@Component
@Profile("dev")
public class DatabaseInitializer implements CommandLineRunner {

    private String IMAGE_FOLDER = "images";

    private final UserRepository userRepository;
    private final UserService userService;
    private final BrandRepository brandRepository;
    private final BrandService brandService;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final SellerRepository sellerRepository;
    private final SellerService sellerService;

    public DatabaseInitializer(UserRepository userRepository, UserService userService, BrandRepository brandRepository,
            BrandService brandService, CategoryRepository categoryRepository, CategoryService categoryService,
            ProductRepository productRepository, ProductService productService, SellerService sellerService,
            SellerRepository sellerRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.brandRepository = brandRepository;
        this.brandService = brandService;
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
        this.productRepository = productRepository;
        this.productService = productService;
        this.sellerService = sellerService;
        this.sellerRepository = sellerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        createUsers();
        createBrands();
        createCategories();
        createProducts();
        createSellers();
    }

    private void createUsers() {
        if (userRepository.count() > 0) {
            return;
        }

        List<CreateUserRequest> users = new ArrayList<>();
        users.add(new CreateUserRequest("Admin", "admin@econometro.com", "admin"));
        users.add(new CreateUserRequest("Tainah", "taih.marques@uesc.br", "123"));
        users.add(new CreateUserRequest("Alice Johnson", "alice.johnson@example.com", "password123"));
        users.add(new CreateUserRequest("Bob Smith", "bob.smith@example.com", "securepass"));
        users.add(new CreateUserRequest("Charlie Lee", "charlie.lee@example.com", "charliepwd"));
        users.add(new CreateUserRequest("Diana Evans", "diana.evans@example.com", "diana2024"));
        users.add(new CreateUserRequest("Ethan Brown", "ethan.brown@example.com", "ethanpass"));

        for (CreateUserRequest user : users) {
            userService.createUser(user);
        }
        userService.makeUserAdmin("admin@econometro.com");
    }

    private void createBrands() {
        if (brandRepository.count() > 0) {
            return;
        }
        List<BrandRequest> brands = List
                .of("Samsung", "Apple", "Google", "Motorola", "Xiaomi", "Shein", "Lacoste", "Genérico",
                        "Brasterápica")
                .stream()
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
                new CategoryRequest("Geral"),
                new CategoryRequest("Eletrônicos"),
                new CategoryRequest("Smartphones", 2),
                new CategoryRequest("Tablets", 2),
                new CategoryRequest("Acessórios"),
                new CategoryRequest("Vestuário"),
                new CategoryRequest("Medicamentos"));

        for (CategoryRequest category : categories) {
            categoryService.createCategory(category);
        }

        categoryService.makeCategorySystem(1);
    }

    private void createProducts() {
        if (productRepository.count() > 0) {
            return;
        }

        List<CreateProductRequest> products = List.of(
                new CreateProductRequest("Galaxy S24",
                        "Smartphone Samsung Galaxy S24 com tela AMOLED e câmera tripla",
                        BigDecimal.valueOf(10.99), 100, 1, 3,
                        findAndConvertImages(List.of("galaxyS24.png", "galaxyS24-2.jpg"))),
                new CreateProductRequest("iPhone 15",
                        "Apple iPhone 15 com processador A17 Bionic e câmera avançada",
                        BigDecimal.valueOf(20.99), 200, 2, 3, findAndConvertImages(List.of())),
                new CreateProductRequest("Pixel 8",
                        "Google Pixel 8 com Android puro e câmera de alta resolução",
                        BigDecimal.valueOf(30.99), 300, 3, 3, findAndConvertImages(List.of())),
                new CreateProductRequest("Moto G15",
                        "Motorola Moto G15 com bateria de longa duração e tela grande",
                        BigDecimal.valueOf(40.99), 400, 4, 3, findAndConvertImages(List.of())),
                new CreateProductRequest("Redmi 13",
                        "Xiaomi Redmi 13 com ótimo custo-benefício e desempenho eficiente",
                        BigDecimal.valueOf(50.99), 500, 5, 3, findAndConvertImages(List.of())),

                new CreateProductRequest("iPad Pro",
                        "Tablet avançado da Apple com tela Liquid Retina e chip M2",
                        BigDecimal.valueOf(60.99), 150, 2, 4, findAndConvertImages(List.of())),
                new CreateProductRequest("Galaxy Tab S9",
                        "Tablet Samsung Galaxy Tab S9 com S Pen e tela AMOLED",
                        BigDecimal.valueOf(55.99), 120, 1, 4, findAndConvertImages(List.of())),
                new CreateProductRequest("Pixel Tablet",
                        "Tablet Google Pixel com integração ao ecossistema Android",
                        BigDecimal.valueOf(45.99), 80, 3, 4, findAndConvertImages(List.of())),

                new CreateProductRequest("Capa Protetora",
                        "Capa protetora resistente para smartphones de diversas marcas",
                        BigDecimal.valueOf(9.99), 300,
                        1, 5, findAndConvertImages(List.of())),
                new CreateProductRequest("Fone de Ouvido Bluetooth",
                        "Fone de ouvido Bluetooth com cancelamento de ruído e bateria duradoura",
                        BigDecimal.valueOf(19.99), 250, 4, 5, findAndConvertImages(List.of())),

                new CreateProductRequest("Camiseta Básica",
                        "Camiseta básica de algodão confortável e disponível em várias cores",
                        BigDecimal.valueOf(29.99), 400, 7, 6, findAndConvertImages(List.of())),
                new CreateProductRequest("Calça Jeans",
                        "Calça jeans masculina de alta qualidade e modelagem moderna",
                        BigDecimal.valueOf(59.99), 200, 6, 6, findAndConvertImages(List.of())),

                new CreateProductRequest("Dipirona",
                        "Dipirona sódica 500mg, medicamento para dor e febre",
                        BigDecimal.valueOf(5.99), 500, 8, 7, findAndConvertImages(List.of())),
                new CreateProductRequest("Paracetamol",
                        "Paracetamol 750mg, analgésico e antitérmico para adultos",
                        BigDecimal.valueOf(6.99), 450, 9, 7, findAndConvertImages(List.of())));

        for (CreateProductRequest product : products) {
            productService.createProduct(product);
        }
    }

    private void createSellers() {
        if (sellerRepository.count() > 0) {
            return;
        }
        List<SellerRequest> sellers = List.of(
                new SellerRequest("Americanas", "00.776.574/0006-60", findAndConvertImage("logos/americanas.svg")),
                new SellerRequest("Magazine Luiza", "47.960.950/0001-21", findAndConvertImage("logos/magazine.svg")),
                new SellerRequest("Mercado Livre", "03.007.331/0001-41",
                        findAndConvertImage("logos/mercado-livre.svg")),
                new SellerRequest("Amazon", "15.436.940/0001-03", findAndConvertImage("logos/amazon.svg")),
                new SellerRequest("Submarino", "00.776.574/0006-60", findAndConvertImage("logos/submarino.svg")));

        for (SellerRequest seller : sellers) {
            sellerService.createSeller(seller);
        }
    }

    private List<String> findAndConvertImages(List<String> paths) {
        List<String> results = new ArrayList<>();
        for (String path : paths) {
            results.add(findAndConvertImage(path));
        }
        return results;
    }

    private String findAndConvertImage(String path) {
        try {
            ClassPathResource resource = new ClassPathResource(IMAGE_FOLDER + File.separator + path);
            byte[] imageBytes = resource.getInputStream().readAllBytes();
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e) {
            System.err.println(String.format("Image %s not found", path));
            return null;
        }
    }
}
