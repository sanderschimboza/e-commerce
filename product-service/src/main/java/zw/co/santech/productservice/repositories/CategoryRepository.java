package zw.co.santech.productservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.santech.productservice.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
