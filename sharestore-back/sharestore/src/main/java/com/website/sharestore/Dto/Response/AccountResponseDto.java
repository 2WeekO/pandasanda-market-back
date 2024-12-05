package com.website.sharestore.Dto.Response;

import lombok.Data;

@Data
public class AccountResponseDto {
    private Long accountKey;
    private String bankName;
    private String accountNumber;
    private String holderName;

    // 생성자
    public AccountResponseDto(Long accountKey, String bankName, String accountNumber, String holderName) {
        this.accountKey = accountKey;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.holderName = holderName;
    }
}