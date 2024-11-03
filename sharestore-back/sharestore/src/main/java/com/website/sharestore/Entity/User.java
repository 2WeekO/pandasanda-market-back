package com.website.sharestore.Entity;

import java.time.LocalDateTime;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "User")
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userKey", nullable = false)
    private Long userKey;

    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "addressDetail", nullable = false)
    private String addressDetail;

    @Column(name = "phoneNumber", nullable = false)
    private String phoneNumber;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "signupDate", nullable = false)
    private LocalDateTime signupDate;

    @Column
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @PrePersist
    protected void onCreate() {
        this.signupDate = LocalDateTime.now();
    }

    @Builder
    public User(Long userKey, String email, String username, String nickname, String address, String addressDetail,
                String phoneNumber, String password, LocalDateTime signupDate, Authority authority) {
        this.userKey = userKey;
        this.email = email;
        this.username = username;
        this.nickname = nickname;
        this.address = address;
        this.addressDetail = addressDetail;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.signupDate = signupDate;
        this.authority = authority;
    }
}
