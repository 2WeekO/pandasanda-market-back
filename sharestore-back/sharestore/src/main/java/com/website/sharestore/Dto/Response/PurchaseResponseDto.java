package com.website.sharestore.Dto.Response;

import com.website.sharestore.Entity.PurchaseRequest;

import lombok.Data;

// 구매 요청 응답 DTO
@Data
public class PurchaseResponseDto {
    private Long requestId;
    private Long buyerId;
    private Long productId;
    private String status;
    private String message;

    public PurchaseResponseDto(PurchaseRequest purchaseRequest) {
        this.requestId = purchaseRequest.getId();
        this.buyerId = purchaseRequest.getBuyerId();
        this.productId = purchaseRequest.getProduct().getItemKey();
        this.status = purchaseRequest.getStatus();
        this.message = purchaseRequest.getMessage();
    }
}
