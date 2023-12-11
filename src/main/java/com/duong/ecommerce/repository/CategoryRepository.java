package com.duong.ecommerce.repository;

import com.duong.ecommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select c from Category c where c.name = :name")
    public Category findByName(@Param("name") String name);

    @Query("select c from  Category c where c.name = :name and c.parentCategory.name = :parentCategoryName")
    public Category findByNameAndParent(@Param("name") String name, @Param("parentCategoryName") String parentCategoryName);
}
