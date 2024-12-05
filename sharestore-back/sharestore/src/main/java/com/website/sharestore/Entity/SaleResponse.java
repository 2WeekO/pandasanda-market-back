package com.website.sharestore.Entity;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;



@Data
@Entity
public class SaleResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private UUID sellerId;

    @Column(nullable = false)
    private UUID purchaseId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResponseStatus status = ResponseStatus.APPROVED;

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();
}
