package com.cachemeifyoucan.econometro.domain.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cachemeifyoucan.econometro.domain.model.Category;
import com.cachemeifyoucan.econometro.domain.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByTitle(String title);

    void deleteByBrand_Id(long id);

    @Modifying
    @Query("update Product p set p.category = :newCategory where p.category = :oldCategory")
    void updateCategory(Category oldCategory, Category newCategory);

    @Query("select p from Product p where "
            + "(:query IS NULL OR "
                + "p.title ilike concat('%', :query, '%')"
                + "or p.category.name ilike concat('%', :query, '%')"
                + "or p.brand.name ilike concat('%', :query, '%')"
            +") "
            + "AND (:categoryIds IS NULL OR p.category.id IN (:categoryIds)) "
            + "AND (:brandId = 0 OR p.brand.id = :brandId)"
            + "AND p.enabled = :enabled")
    List<Product> search(String query, Set<Long> categoryIds, long brandId, boolean enabled);

    List<Product> findByEnabled(boolean enabled);

}
