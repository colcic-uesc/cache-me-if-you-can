package com.cachemeifyoucan.econometro.domain.repository;

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

}
