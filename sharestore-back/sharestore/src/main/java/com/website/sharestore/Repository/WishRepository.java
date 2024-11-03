package com.website.sharestore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.website.sharestore.Entity.Wish;

public interface WishRepository extends JpaRepository<Wish, Long> {
}
