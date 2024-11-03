package com.website.sharestore.Dto.Request;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDto {
    
    private List<String> images;        // 이미지 URL 배열 images;
    private String category1;       // 1분류
    private String category2;       // 2분류
    private String category3;       // 3분류
    private String productName;     // 상품명
    private String productCondition;// 상품 상태
    private String description;     // 제품 설명
    private int price;              // 가격
    private int quantity;           // 수량
    private String wayComment;      // 직거래시 전달 사항
    private String tradeMethod;     // 거래 방법 (예: 직거래, 택배)
    private String shippingMethod;  // 배송비 유형 (예: 직접 배송, 택배 배송)

    
    
}