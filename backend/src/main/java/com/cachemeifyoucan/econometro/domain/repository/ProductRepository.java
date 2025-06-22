package com.cachemeifyoucan.econometro.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cachemeifyoucan.econometro.domain.model.Category;
import com.cachemeifyoucan.econometro.domain.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByTitle(String title);

    void deleteByBrand_Id(long id);

    @Modifying
    @Query("update Product p set p.category = :newCategory where p.category = :oldCategory")
    void updateCategory(@Param("oldCategory") Category oldCategory, @Param("newCategory") Category newCategory);

    @Query("select p from Product p where p.title ilike concat('%', :query, '%')"
            + "or p.category.name ilike concat('%', :query, '%')"
            + "or p.brand.name ilike concat('%', :query, '%')")
    List<Product> findByTextQuery(@Param("query") String query);
}
