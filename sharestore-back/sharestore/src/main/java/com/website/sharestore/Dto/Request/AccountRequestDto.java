package com.website.sharestore.Dto.Request;


import lombok.Data;

@Data
public class AccountRequestDto {
    
    private String bankName;

    private String accountNumber;

    private String holderName;
}