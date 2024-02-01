package com.resel.ecommerce.repository;

import com.resel.ecommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findByName(String name);

// previous
//    @Query("SELECT c FROM Category c WHERE c.name= :name AND c.parentCategory.name= :parantCategoryName")
//    Category findByNameAndParant(@Param("name") String name,@Param("parentCategoryName")String parantCategoryName);
// present
    @Query("SELECT c FROM Category c LEFT JOIN c.parentCategory pc WHERE c.name = :name AND pc.name = :parentCategoryName")
    Category findByNameAndParant(@Param("name") String name, @Param("parentCategoryName") String parentCategoryName);


}
