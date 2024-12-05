package com.website.sharestore.Repository;

import com.website.sharestore.Entity.PurchaseRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface PurchaseRequestRepository extends JpaRepository<PurchaseRequest, UUID> {
    List<PurchaseRequest> findByBuyerId(UUID buyerId);
    List<PurchaseRequest> findByProductId(UUID productId);
}
