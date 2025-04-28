package com.website.sharestore.Dto.Auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.website.sharestore.Entity.Authority;
import com.website.sharestore.Entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDto {
    private String email;
    private String username;
    private String nickname;
    private String address;
    private String addressDetail;
    private String phoneNumber;
    private String password;
    private String passwordCheck;
    private LocalDateTime SignupDate;

    

    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(email)
                .username(username)
                .address(address)
                .addressDetail(addressDetail)
                .phoneNumber(phoneNumber)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .signupDate(SignupDate)
                .authority(Authority.ROLE_USER)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
