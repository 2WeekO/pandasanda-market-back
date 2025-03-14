package com.website.sharestore.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.website.sharestore.Entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
  List<Product> findByUser_UserKey(Long userKey);

  @Modifying
  @Query("UPDATE Product p SET p.viewCount = p.viewCount + 1 WHERE p.itemKey = :itemKey ")
  void incrementViewCount(@Param("itemKey") Long itemKey);

  List<Product> findByProductNameContaining(@Param("productName") String keyword);
  
  List<Product> findByCategory1(@Param("category") String category);

}