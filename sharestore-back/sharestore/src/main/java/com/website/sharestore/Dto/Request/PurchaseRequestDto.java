package com.website.sharestore.Dto.Request;


import lombok.Data;

// 구매 요청 DTO
@Data
public class PurchaseRequestDto {
    private Long buyerId;
    private Long productId;
    private Long sellerId;
    private String message;
}

