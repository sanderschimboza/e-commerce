package zw.co.santech.productservice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.santech.productservice.dto.CategoryDto;
import zw.co.santech.productservice.enums.EnumRoles;
import zw.co.santech.productservice.models.Category;
import zw.co.santech.productservice.services.CategoryService;
import zw.co.santech.productservice.utils.Constants;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Object> addCategory(@RequestBody CategoryDto categoryDto, @RequestHeader String username,
                                              @RequestHeader String roles) {

        if (Objects.isNull(username) || !roles.equals(EnumRoles.SYS_ADMIN.name()))
            return new ResponseEntity<>(Constants.INVALID_ADMIN_RIGHTS_MSG, HttpStatus.UNAUTHORIZED);

        Long categoryId = categoryService.addCategory(categoryDto);
        return new ResponseEntity<>(categoryId, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Category>> findAllCategories() {
        List<Category> categoryList = categoryService.findAll();
        return new ResponseEntity<>(categoryList, HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> findCategoryById(@PathVariable("categoryId") Long categoryId) {
        Category category = categoryService.findCategoryById(categoryId);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
}
