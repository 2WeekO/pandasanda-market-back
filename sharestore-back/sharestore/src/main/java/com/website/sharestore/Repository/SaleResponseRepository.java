package com.website.sharestore.Repository;

import com.website.sharestore.Entity.SaleResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface SaleResponseRepository extends JpaRepository<SaleResponse, UUID> {
    List<SaleResponse> findBySellerId(UUID sellerId);
    List<SaleResponse> findByPurchaseId(UUID purchaseId);
}
