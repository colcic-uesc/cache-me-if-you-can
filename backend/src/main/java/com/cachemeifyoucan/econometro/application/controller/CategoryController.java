package com.cachemeifyoucan.econometro.application.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cachemeifyoucan.econometro.application.dto.CategoryRequest;
import com.cachemeifyoucan.econometro.application.dto.CategoryResponse;
import com.cachemeifyoucan.econometro.domain.model.Category;
import com.cachemeifyoucan.econometro.domain.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    @Operation(summary = "Create Category", description = " Creates a category with the provided information")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody CategoryRequest request) {
        Category categories = categoryService.createCategory(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", "/categories/" + categories.getId())
                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Category by ID", description = "Retrieves a category by its ID")
    public ResponseEntity<?> getById(@PathVariable long id) {
        Category category = categoryService.getCategoryById(id);
        CategoryResponse dto = new CategoryResponse(category.getId(), category.getName(),
                        category.getParent() == null ? 0 : category.getParent().getId());
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    @Operation(summary = "Get All Categories", description = "Retrieves all categories")
    public ResponseEntity<?> getAll() {
        List<CategoryResponse> dto = categoryService.getAllCategories().stream()
                .map(category -> new CategoryResponse(category.getId(), category.getName(),
                        category.getParent() == null ? 0 : category.getParent().getId()))
                .toList();
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Category", description = "Updates a category with the provided information")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(long id, @Valid @RequestBody CategoryRequest request) {
        categoryService.updateCategory(id, request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Category", description = "Deletes a category by its ID")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
