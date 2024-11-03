package com.website.sharestore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.website.sharestore.Entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
