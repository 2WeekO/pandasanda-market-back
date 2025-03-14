package com.website.sharestore.Dto.Response;

import com.website.sharestore.Entity.PurchaseRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// 구매 요청 응답 DTO
@Data
@Getter
@Setter
@AllArgsConstructor

public class PurchaseResponseDto {
    private Long requestId;
    private Long buyerId;
    private Long sellerId;
    private Long productId;
    private String status;
    private String message;
    private String productName;
    private int productPrice;
    

    public PurchaseResponseDto(PurchaseRequest purchaseRequest) {
        this.requestId = purchaseRequest.getRequestId();
        this.buyerId = purchaseRequest.getBuyerId();
        this.sellerId = purchaseRequest.getProduct().getSellerId();
        this.productId = purchaseRequest.getProduct().getItemKey();
        this.status = purchaseRequest.getProduct().getStatus();
        this.message = purchaseRequest.getMessage();
        this.productName = purchaseRequest.getProduct().getProductName();
        this.productPrice = purchaseRequest.getProduct().getPrice();
    }

    
}
