package com.website.sharestore.Entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "PurchaseRequest")
@Getter
@Setter
public class PurchaseRequest {
  
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long id;

   private Long productId; // 상품 ID
    private Long buyerId;   // 구매자 ID
    private Long sellerId;  // 판매자 ID
    private String status;  // 요청 상태 ("PENDING", "APPROVED", "REJECTED", "COMPLETED")
    private String buyerEmail;

    @Column(length = 500)
    private String message; // 구매 메시지 (선택 사항)

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
