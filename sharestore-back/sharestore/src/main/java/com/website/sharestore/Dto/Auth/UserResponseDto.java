package com.website.sharestore.Dto.Auth;

import com.website.sharestore.Entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {
    private String username;
    private String email;
    private String nickname;
    private String address;
    private String addressDetail;
    private String phoneNumber;
    

    public static UserResponseDto of(User user) {
        return UserResponseDto.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .username(user.getUsername())
                .address(user.getAddress())
                .addressDetail(user.getAddressDetail())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}