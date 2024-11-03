package com.website.sharestore.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.website.sharestore.Entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
  List<Product> findByUser_UserKey(Long userKey);
}