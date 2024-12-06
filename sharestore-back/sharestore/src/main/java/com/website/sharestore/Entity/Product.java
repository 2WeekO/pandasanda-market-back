package com.website.sharestore.Entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Product")
@Table(name = "Product")
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_key")
    private Long itemKey;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private String category1;

    @Column(nullable = false)
    private String category2;

    @Column(nullable = false)
    private String category3;

    @ElementCollection
    private List<String> imageUrls;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String productCondition;  // 제품 상태

    @Column(nullable = false)
    private String tradeMethod; // 거래 방법 (예: 직거래, 택배)
    
    @Column(nullable = false)
    private String description;     // 제품 설명

    @Column(nullable = false)
    private int quantity;           // 수량

    @Column(nullable = true)
    private String wayComment; // 직거래 시 전달 사항

    @Column(nullable = true)
    private String shippingMethod;  // 배송 유형 (예: 직접 배송, 택배 배송)

    @Column(nullable = false)
    private int viewCount = 0;

    
    private LocalDateTime productRegisterDate;

    @ManyToOne
    @JoinColumn(name = "userKey", nullable = false)
    private User user;
    

    @PrePersist
    protected void onCreate() {
        this.productRegisterDate = LocalDateTime.now();
    }

}
