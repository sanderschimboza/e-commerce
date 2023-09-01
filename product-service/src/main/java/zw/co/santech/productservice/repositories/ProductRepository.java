package zw.co.santech.productservice.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.santech.productservice.models.Category;
import zw.co.santech.productservice.models.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
    Page<Product> findProductByProductCategory(Category productCategory, Pageable pageable);

    Page<Product> findByProductCategory_CategoryId(Long categoryId, Pageable pageable);

}
