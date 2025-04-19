package com.scaler.productservicejan31capstone.repositories;

import com.scaler.productservicejan31capstone.models.Category;
import com.scaler.productservicejan31capstone.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

// JPARepository: 1st Argument: Table name;
// 2nd Argument: Type of primary key of the Table
public interface ProductRepository extends JpaRepository<Product, Long>
{
    Product save(Product product);

    List<Product> findAll();

    Optional<Product> findById(long id);
    
    List<Product> findByCategory(Category category);
    
    List<Product> findByCategory_Name(String categoryName);

    @Query("select p from Product p where p.category.name= :categoryName")
    List<Product> getProductByCategoryName(@Param("categoryName") String categoryName);

    @Query(value = CustomQuery.GET_PRODUCTS_FROM_CATEGORY_NAME, nativeQuery = true)
    List<Product> getProductsByCategoryNameNative(@Param("categoryName") String categoryName);

    Page<Product> findByNameContaining(String query, Pageable pageable);

    @Query("select p from Product p where p.category=:category and p.price between :minPrice and :maxPrice and p.id != :excludeId")
    List<Product> findSimilarInCategory(@Param("category") Category category,
                                        @Param("minPrice") double minPrice,
                                        @Param("maxPrice") double maxPrice,
                                        @Param("excludeId") long excludeId);
}
