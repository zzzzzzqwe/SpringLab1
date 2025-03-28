package com.example.library.service;

import com.example.library.dto.CategoryDTO;
import com.example.library.model.Category;
import com.example.library.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> getAll() {
        return categoryRepository.findAll().stream()
                .map(category -> new CategoryDTO(category.getId(), category.getName()))
                .collect(Collectors.toList());
    }

    public CategoryDTO getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return new CategoryDTO(category.getId(), category.getName());
    }

    public CategoryDTO create(CategoryDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        Category saved = categoryRepository.save(category);
        return new CategoryDTO(saved.getId(), saved.getName());
    }

    public CategoryDTO update(Long id, CategoryDTO dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(dto.getName());
        Category updated = categoryRepository.save(category);
        return new CategoryDTO(updated.getId(), updated.getName());
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
