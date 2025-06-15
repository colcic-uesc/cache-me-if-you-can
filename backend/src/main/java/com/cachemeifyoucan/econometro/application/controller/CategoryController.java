package com.cachemeifyoucan.econometro.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cachemeifyoucan.econometro.application.dto.CategoryRequest;
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
    public ResponseEntity<?> create(@Valid @RequestBody CategoryRequest request) {
        Category categories = categoryService.createCategory(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", "/categories/" + categories.getId())
                .body(categories.getId());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Category by ID", description = "Retrieves a category by its ID")
    public ResponseEntity<?> getById(long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @GetMapping
    @Operation(summary = "Get All Categories", description = "Retrieves all categories")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Category", description = "Updates a category with the provided information")
    public ResponseEntity<?> update(long id, @Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoryService.updateCategory(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Category", description = "Deletes a category by its ID")
    public ResponseEntity<?> delete(long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
