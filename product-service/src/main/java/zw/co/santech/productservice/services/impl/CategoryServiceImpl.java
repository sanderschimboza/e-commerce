package zw.co.santech.productservice.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zw.co.santech.productservice.dto.CategoryDto;
import zw.co.santech.productservice.models.Category;
import zw.co.santech.productservice.repositories.CategoryRepository;
import zw.co.santech.productservice.services.CategoryService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Long addCategory(CategoryDto categoryDto) {
        Category category = Category.builder()
                .categoryName(categoryDto.getCategoryName())
                .categoryDescription(categoryDto.getCategoryDescription())
                .build();

        categoryRepository.save(category);
        return category.getCategoryId();
    }

    @Override
    public void removeCategory(Long categoryId) {

    }

    @Override
    public Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow();
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
