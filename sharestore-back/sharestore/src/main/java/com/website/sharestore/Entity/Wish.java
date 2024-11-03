package com.website.sharestore.Entity;

import java.time.LocalDateTime;
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
@Entity(name = "Wish")
@Table(name = "Wish")
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishNumber;

    private LocalDateTime wishDate;

    @ManyToOne
    @JoinColumn(name = "userKey", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "itemKey", nullable = false)
    private Product product;


}