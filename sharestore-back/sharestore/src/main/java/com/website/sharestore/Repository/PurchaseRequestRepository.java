package com.website.sharestore.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.website.sharestore.Entity.PurchaseRequest;

@Repository
public interface PurchaseRequestRepository extends JpaRepository<PurchaseRequest, Long> {
  
   // 특정 판매자의 요청 리스트 가져오기
  List<PurchaseRequest> findBySellerId(Long sellerId);

   // 특정 구매자의 요청 리스트 가져오기
  List<PurchaseRequest> findByBuyerId(Long buyerId);

   // 특정 상태를 기준으로 요청 리스트 가져오기
  List<PurchaseRequest> findByStatus(String status);

   // 판매자 ID와 상태로 요청 필터링
  List<PurchaseRequest> findBySellerIdAndStatus(Long sellerId, String status);
  
}
