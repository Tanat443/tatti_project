package org.example.productservice.service;

import org.example.productservice.model.Category;
import org.example.productservice.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Flux<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Mono<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Mono<Category> createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Mono<Category> updateCategory(Long id, Category updatedCategory) {
        return categoryRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Category not found")))
                .flatMap(existingCategory -> {
                    existingCategory.setName(updatedCategory.getName());
                    return categoryRepository.save(existingCategory);
                });
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
