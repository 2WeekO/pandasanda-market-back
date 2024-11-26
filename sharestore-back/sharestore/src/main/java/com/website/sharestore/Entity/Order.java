package com.website.sharestore.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "`Order`")
@Table(name = "`Order`")
public class Order { // 제품 구입 신청했을 때 필요한 Entity

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderID;

    @Column(nullable = false)
    private String orderStatus; // 구매 신청 상태

    @Column(nullable = false)
    private LocalDateTime orderDate;

    @Column(nullable = false)
    private String shippingAddress;

    @Column(nullable = false)
    private String shippingAddressDetail;

    @Column(nullable = false)
    private String paymentMethod; // 구매 방법

    @Column(nullable = false)
    private String paymentStatus; // 구매 상황

    private String trackingNumber; //

    private LocalDateTime deliveryDate;

    @Column(nullable = false)
    private Integer totalPrice;

    @Column(nullable = false)
    private String orderNotes;

    @ManyToOne
    @JoinColumn(name = "userKey", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "itemKey", nullable = false)
    private Product product;


}