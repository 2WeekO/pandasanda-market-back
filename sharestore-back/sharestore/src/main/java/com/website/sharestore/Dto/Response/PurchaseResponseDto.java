package com.website.sharestore.Dto.Response;



import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// 구매 요청 응답 DTO
@Data
@Getter
@Setter
public class PurchaseResponseDto {
    private Long requestId;
    private Long buyerId;
    private Long productId;
    private String status;
    private String message;

    public PurchaseResponseDto(Long requesId, Long buyerId,Long productId, String status,String message) {

        this.requestId = requestId;
        this.buyerId = buyerId;
        this.productId = productId;
        this.status = status;
        this.message = message;
    }
}
