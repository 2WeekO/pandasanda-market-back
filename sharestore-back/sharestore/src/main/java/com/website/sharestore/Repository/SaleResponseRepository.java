package com.website.sharestore.Repository;

import com.website.sharestore.Entity.SaleResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleResponseRepository extends JpaRepository<SaleResponse, Long> {
    List<SaleResponse> findBySellerId(Long sellerId);
}
