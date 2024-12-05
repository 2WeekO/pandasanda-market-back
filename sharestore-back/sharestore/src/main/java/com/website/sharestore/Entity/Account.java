package com.website.sharestore.Entity;

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
@Entity(name = "Account")
@Table(name = "Account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountKey;

    @Column(nullable = true,name = "bank_name")
    private String bankName;

    @Column(nullable = true)
    private String accountNumber;

    @Column(nullable = true)
    private String holderName;
    
    @ManyToOne
    @JoinColumn(name = "userKey", nullable = false)
    private User user;

}