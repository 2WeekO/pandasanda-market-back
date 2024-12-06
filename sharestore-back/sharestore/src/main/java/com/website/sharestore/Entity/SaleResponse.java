package com.website.sharestore.Entity;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;



@Data
@Entity
public class SaleResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long sellerId;

    @Column(nullable = false)
    private Long purchaseId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResponseStatus status = ResponseStatus.APPROVED;

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();
}
