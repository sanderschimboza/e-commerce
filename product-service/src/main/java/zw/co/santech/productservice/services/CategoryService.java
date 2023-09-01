package zw.co.santech.productservice.services;

import zw.co.santech.productservice.dto.CategoryDto;
import zw.co.santech.productservice.models.Category;

import java.util.List;

public interface CategoryService {
    Long addCategory(CategoryDto categoryDto);

    void removeCategory(Long categoryId);

    Category findCategoryById(Long categoryId);

    List<Category> findAll();
}
