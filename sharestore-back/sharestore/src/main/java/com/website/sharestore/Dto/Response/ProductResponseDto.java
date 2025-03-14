package com.website.sharestore.Dto.Response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDto {
    
    private Long itemKey;           // 상품 키
    private List<String> images;    // 이미지 파일들의 Base64 인코딩된 문자열 리스트
    private String category1;       // 1분류
    private String category2;       // 2분류
    private String category3;       // 3분류
    private String productName;     // 상품명
    private String productCondition;       // 상품 상태 (예: 매우 좋음, 좋음, 보통 등)
    private String description;     // 제품 설명
    private int price;              // 가격
    private int quantity;           // 수량
    private String wayComment;      // 직거래시 전달 사항
    private String tradeMethod;     // 거래 방법 (예: 직거래, 택배)
    private String shippingMethod;  // 배송비 유형 (예: 직접 배송, 택배 배송)
    private int viewCount;          // 조회수
    private LocalDateTime productRegisterDate;  // 등록일자
    private String userEmail;       // 등록자 이메일
    private String userNickname;
    private String userAddress;
    private Long userKey;
    

    public ProductResponseDto(Long itemKey, String productName, String category1, String category2,
                            String category3, int price, String productCondition, String description,
                            int quantity, String tradeMethod, String wayComment, String shippingMethod,
                            int viewCount, LocalDateTime productRegisterDate, String userEmail,String userNickname, String userAddress, Long userKey) {
        this.itemKey = itemKey;
        this.productName = productName;
        this.category1 = category1;
        this.category2 = category2;
        this.category3 = category3;
        this.price = price;
        this.productCondition = productCondition;
        this.description = description;
        this.quantity = quantity;
        this.tradeMethod = tradeMethod;
        this.wayComment = wayComment;
        this.shippingMethod = shippingMethod;
        this.viewCount = viewCount;
        this.productRegisterDate = productRegisterDate;
        this.userEmail = userEmail;
        this.userNickname = userNickname;
        this.userAddress = userAddress;
        this.userKey = userKey;
        
    }
}
