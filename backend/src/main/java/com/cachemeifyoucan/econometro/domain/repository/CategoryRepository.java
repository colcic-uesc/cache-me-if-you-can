package com.cachemeifyoucan.econometro.domain.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cachemeifyoucan.econometro.domain.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByName(String name);

    @Query("select c.id from Category c where c.parent.id = :parentId")
    Set<Long> findAllChildren(long parentId);

}
