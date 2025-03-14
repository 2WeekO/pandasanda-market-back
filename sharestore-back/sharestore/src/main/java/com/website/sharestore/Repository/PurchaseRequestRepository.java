package com.website.sharestore.Repository;

import com.website.sharestore.Entity.PurchaseRequest;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRequestRepository extends JpaRepository<PurchaseRequest, Long> {
    List<PurchaseRequest> findByBuyerId(Long buyerId);
    List<PurchaseRequest> findByProduct_SellerId(Long sellerId);
}