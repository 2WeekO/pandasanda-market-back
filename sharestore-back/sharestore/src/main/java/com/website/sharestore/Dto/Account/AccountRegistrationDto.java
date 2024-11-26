package com.website.sharestore.Dto.Account;

import lombok.Data;

@Data
public class AccountRegistrationDto {
  private String accountNumber; // 계좌 번호
  private String bankName; // 은행 이름
  private String holderName; // 소유주 이름

}
