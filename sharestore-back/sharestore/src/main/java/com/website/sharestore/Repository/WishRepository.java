package com.website.sharestore.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.website.sharestore.Entity.Product;
import com.website.sharestore.Entity.User;
import com.website.sharestore.Entity.Wish;

public interface WishRepository extends JpaRepository<Wish, Long> {
  Optional<Wish> findByUserAndProduct(User user, Product product);
  boolean existsByUserAndProduct(User user, Product product);
  long countByProduct(Product product);
}
