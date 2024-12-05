package com.website.sharestore.Dto.Request;


import lombok.Data;

@Data
public class PurchaseRequestDto {
  private Long purchaseId;
  private Long buyerId;
  private String buyerEmail;
  private String message; // 구매 메시지
  
  private String status;


}
