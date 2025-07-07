package com.cachemeifyoucan.econometro.infrastructure.config;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.cachemeifyoucan.econometro.application.dto.BrandRequest;
import com.cachemeifyoucan.econometro.application.dto.CategoryRequest;
import com.cachemeifyoucan.econometro.application.dto.CreateOfferRequest;
import com.cachemeifyoucan.econometro.application.dto.CreateProductRequest;
import com.cachemeifyoucan.econometro.application.dto.CreateUserRequest;
import com.cachemeifyoucan.econometro.application.dto.SellerRequest;
import com.cachemeifyoucan.econometro.domain.enums.UserRole;
import com.cachemeifyoucan.econometro.domain.model.PriceHistory;
import com.cachemeifyoucan.econometro.domain.model.Product;
import com.cachemeifyoucan.econometro.domain.model.User;
import com.cachemeifyoucan.econometro.domain.repository.BrandRepository;
import com.cachemeifyoucan.econometro.domain.repository.CategoryRepository;
import com.cachemeifyoucan.econometro.domain.repository.OfferRepository;
import com.cachemeifyoucan.econometro.domain.repository.PriceHistoryRepository;
import com.cachemeifyoucan.econometro.domain.repository.ProductRepository;
import com.cachemeifyoucan.econometro.domain.repository.SellerRepository;
import com.cachemeifyoucan.econometro.domain.repository.UserRepository;
import com.cachemeifyoucan.econometro.domain.service.BrandService;
import com.cachemeifyoucan.econometro.domain.service.CategoryService;
import com.cachemeifyoucan.econometro.domain.service.OfferService;
import com.cachemeifyoucan.econometro.domain.service.ProductService;
import com.cachemeifyoucan.econometro.domain.service.SellerService;
import com.cachemeifyoucan.econometro.domain.service.UserService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
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
    private final SellerRepository sellerRepository;
    private final SellerService sellerService;
    private final OfferRepository offerRepository;
    private final OfferService offerService;
    private final PriceHistoryRepository historyRepository;

    private List<Product> products;

    @Override
    public void run(String... args) throws Exception {
        createUsers();
        createBrands();
        createCategories();
        createProducts();
        createSellers();
        createOffers();
        createPriceHistory();
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
        userService.changeUserRole("admin@econometro.com", UserRole.ADMIN);
        userService.changeUserRole("alice.johnson@example.com", UserRole.SELLER);
        userService.changeUserRole("bob.smith@example.com", UserRole.SELLER);
        userService.changeUserRole("charlie.lee@example.com", UserRole.SELLER);
        userService.changeUserRole("diana.evans@example.com", UserRole.SELLER);
        userService.changeUserRole("ethan.brown@example.com", UserRole.SELLER);

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

        products = new ArrayList<>();

        List<CreateProductRequest> productsToCreate = List.of(
                new CreateProductRequest("Galaxy S24",
                        "Smartphone Samsung Galaxy S24 com tela AMOLED e câmera tripla",
                        LocalDate.now(), 1, 3,
                        findAndConvertImages(List.of("galaxyS24.png", "galaxyS24-2.jpg"))),
                new CreateProductRequest("iPhone 15",
                        "Apple iPhone 15 com processador A17 Bionic e câmera avançada",
                        LocalDate.now(), 2, 3, findAndConvertImages(List.of())),
                new CreateProductRequest("Pixel 8",
                        "Google Pixel 8 com Android puro e câmera de alta resolução",
                        LocalDate.now(), 3, 3, findAndConvertImages(List.of())),
                new CreateProductRequest("Moto G15",
                        "Motorola Moto G15 com bateria de longa duração e tela grande",
                        LocalDate.now(), 4, 3, findAndConvertImages(List.of())),
                new CreateProductRequest("Redmi 13",
                        "Xiaomi Redmi 13 com ótimo custo-benefício e desempenho eficiente",
                        LocalDate.now(), 5, 3, findAndConvertImages(List.of())),

                new CreateProductRequest("iPad Pro",
                        "Tablet avançado da Apple com tela Liquid Retina e chip M2",
                        LocalDate.now(), 2, 4, findAndConvertImages(List.of())),
                new CreateProductRequest("Galaxy Tab S9",
                        "Tablet Samsung Galaxy Tab S9 com S Pen e tela AMOLED",
                        LocalDate.now(), 1, 4, findAndConvertImages(List.of())),
                new CreateProductRequest("Pixel Tablet",
                        "Tablet Google Pixel com integração ao ecossistema Android",
                        LocalDate.now(), 3, 4, findAndConvertImages(List.of())),

                new CreateProductRequest("Capa Protetora",
                        "Capa protetora resistente para smartphones de diversas marcas",
                        LocalDate.now(),
                        1, 5, findAndConvertImages(List.of())),
                new CreateProductRequest("Fone de Ouvido Bluetooth",
                        "Fone de ouvido Bluetooth com cancelamento de ruído e bateria duradoura",
                        LocalDate.now(), 4, 5, findAndConvertImages(List.of())),

                new CreateProductRequest("Camiseta Básica",
                        "Camiseta básica de algodão confortável e disponível em várias cores",
                        LocalDate.now(), 7, 6, findAndConvertImages(List.of())),
                new CreateProductRequest("Calça Jeans",
                        "Calça jeans masculina de alta qualidade e modelagem moderna",
                        LocalDate.now(), 6, 6, findAndConvertImages(List.of())),

                new CreateProductRequest("Dipirona",
                        "Dipirona sódica 500mg, medicamento para dor e febre",
                        LocalDate.now(), 8, 7, findAndConvertImages(List.of())),
                new CreateProductRequest("Paracetamol",
                        "Paracetamol 750mg, analgésico e antitérmico para adultos",
                        LocalDate.now(), 9, 7, findAndConvertImages(List.of())));

        for (CreateProductRequest product : productsToCreate) {
            products.add(productService.createProduct(product));
        }
    }

    private void createSellers() {
        if (sellerRepository.count() > 0) {
            return;
        }
        List<SellerRequest> sellers = List.of(
                new SellerRequest("Amazon", "15.436.940/0001-03", 3, findAndConvertImage("logos/amazon.svg")),
                new SellerRequest("Americanas", "00.776.574/0006-60", 4, findAndConvertImage("logos/americanas.svg")),
                new SellerRequest("Magazine Luiza", "47.960.950/0001-21", 5, findAndConvertImage("logos/magazine.svg")),
                new SellerRequest("Mercado Livre", "03.007.331/0001-41", 6,
                        findAndConvertImage("logos/mercado-livre.svg")),
                new SellerRequest("Submarino", "00.776.574/0006-60", 7, findAndConvertImage("logos/submarino.svg")));

        for (SellerRequest seller : sellers) {
            sellerService.createSeller(seller);
        }
    }

    private void createOffers() {
        if (offerRepository.count() > 0) {
            return;
        }

        var sellerOffers = new ArrayList<SellerOffers>();

        var offers = List.of(
                new CreateOfferRequest(new BigDecimal(3999.90), 1),
                new CreateOfferRequest(new BigDecimal(6999.00), 2),
                new CreateOfferRequest(new BigDecimal(1599.00), 4),
                new CreateOfferRequest(new BigDecimal(8980.00), 6),
                new CreateOfferRequest(new BigDecimal(6005.00), 7),
                new CreateOfferRequest(new BigDecimal(39.90), 11),
                new CreateOfferRequest(new BigDecimal(13.00), 13));
        sellerOffers.add(new SellerOffers(3, offers));

        offers = List.of(
                new CreateOfferRequest(new BigDecimal(3989.00), 1),
                new CreateOfferRequest(new BigDecimal(6980.00), 2),
                new CreateOfferRequest(new BigDecimal(4985.00), 3),
                new CreateOfferRequest(new BigDecimal(1299.00), 5),
                new CreateOfferRequest(new BigDecimal(5999.00), 7),
                new CreateOfferRequest(new BigDecimal(199.90), 10),
                new CreateOfferRequest(new BigDecimal(99.90), 12),
                new CreateOfferRequest(new BigDecimal(14.50), 14));
        sellerOffers.add(new SellerOffers(4, offers));

        offers = List.of(
                new CreateOfferRequest(new BigDecimal(4005.50), 1),
                new CreateOfferRequest(new BigDecimal(4999.00), 3),
                new CreateOfferRequest(new BigDecimal(1305.00), 5),
                new CreateOfferRequest(new BigDecimal(3999.00), 8),
                new CreateOfferRequest(new BigDecimal(48.00), 9),
                new CreateOfferRequest(new BigDecimal(12.90), 13));
        sellerOffers.add(new SellerOffers(5, offers));

        offers = List.of(
                new CreateOfferRequest(new BigDecimal(7009.99), 2),
                new CreateOfferRequest(new BigDecimal(5020.00), 3),
                new CreateOfferRequest(new BigDecimal(8999.00), 6),
                new CreateOfferRequest(new BigDecimal(4010.00), 8),
                new CreateOfferRequest(new BigDecimal(202.00), 10),
                new CreateOfferRequest(new BigDecimal(101.00), 12));
        sellerOffers.add(new SellerOffers(6, offers));

        offers = List.of(
                new CreateOfferRequest(new BigDecimal(1580.00), 4),
                new CreateOfferRequest(new BigDecimal(49.90), 9),
                new CreateOfferRequest(new BigDecimal(38.00), 11),
                new CreateOfferRequest(new BigDecimal(14.90), 14));
        sellerOffers.add(new SellerOffers(7, offers));

        for (var sellerOffer : sellerOffers) {
            setLoggedInUser(sellerOffer.managerId());
            for (CreateOfferRequest offer : sellerOffer.offers()) {
                offerService.createOffer(offer);
            }
        }
    }

    private record SellerOffers(long managerId, List<CreateOfferRequest> offers) {
    }

    private void setLoggedInUser(long id) {
        Optional<User> optUser = userRepository.findById(id);
        optUser.ifPresent(user -> {
            var auth = new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    List.of(new SimpleGrantedAuthority(user.getRole().name())));
            SecurityContextHolder.getContext().setAuthentication(auth);
        });
    }

    private void createPriceHistory() {
        if (historyRepository.count() > 0) {
            return;
        }

        for (Product product : products) {
            // gera histórico para os últimos 60 dias
            for (int i = 59; i >= 0; i--) {
                LocalDate date = LocalDate.now().minusDays(i);
                BigDecimal bestPrice = Objects.requireNonNullElse(product.getBestPrice(), new BigDecimal(1500));
                // Gera um fator aleatório entre -0.6 e +0.6
                double randomFactor = (Math.random() * 1.2) - 0.6;
                BigDecimal variation = bestPrice.multiply(BigDecimal.valueOf(randomFactor));
                BigDecimal price = bestPrice.add(variation).max(BigDecimal.valueOf(1.0));
                historyRepository.save(new PriceHistory(product, price, date));
            }
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
            ClassPathResource resource = new ClassPathResource("images" + File.separator + path);
            byte[] imageBytes = resource.getInputStream().readAllBytes();
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e) {
            System.err.println(String.format("Image %s not found", path));
            return null;
        }
    }
}
