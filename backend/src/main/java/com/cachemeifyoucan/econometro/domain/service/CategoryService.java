package com.cachemeifyoucan.econometro.domain.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.cachemeifyoucan.econometro.application.dto.CategoryRequest;
import com.cachemeifyoucan.econometro.domain.model.Category;
import com.cachemeifyoucan.econometro.domain.repository.CategoryRepository;
import com.cachemeifyoucan.econometro.domain.repository.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CategoryService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public Category createCategory(CategoryRequest dto) {
        if (categoryRepository.existsByName(dto.name())) {
            throw new IllegalArgumentException("Category with this name already exists");
        }

        Category category = new Category(dto.name());
        if (dto.parentId() > 0) {
            Category parentCategory = getCategoryById(dto.parentId());
            category.setParent(parentCategory);
        }
        return categoryRepository.save(category);
    }

    public Category getCategoryById(long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + id));
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category updateCategory(long id, CategoryRequest dto) {
        Category category = getCategoryById(id);
        if (category.getName().equals(dto.name())) {
            return category;
        }
        if (categoryRepository.existsByName(dto.name())) {
            throw new IllegalArgumentException("Category with this name already exists");
        }
        category.setName(dto.name());
        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(long id) {
        if (!categoryRepository.existsById(id)) {
            throw new IllegalArgumentException("Category not found with id: " + id);
        }
        Category category = getCategoryById(id);
        if (category.isSystem()) {
            throw new IllegalArgumentException("Can not remove system category: " + category.getName());
        }
        Long newCategoryId = category.getParent() == null ? Category.DEFAULT_CATEGORY : category.getParent().getId();
        Category newCategory = getCategoryById(newCategoryId);
        productRepository.updateCategory(category, newCategory);
        categoryRepository.deleteById(id);
    }

    public void makeCategorySystem(long id) {
        Category category = getCategoryById(id);
        category.setSystem(true);
        categoryRepository.save(category);
    }

    public Set<Long> findRelatedCategoryIds(long parentId) {
        if (parentId == 0) {
            return null;
        }
        Set<Long> relatedIds = new HashSet<>();
        relatedIds.add(parentId);
        relatedIds.addAll(categoryRepository.findAllChildren(parentId));
        return relatedIds;
    }
}
