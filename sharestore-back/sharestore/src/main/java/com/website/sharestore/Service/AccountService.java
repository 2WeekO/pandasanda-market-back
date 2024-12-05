package com.website.sharestore.Service;


import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.website.sharestore.Dto.Request.AccountRequestDto;
import com.website.sharestore.Dto.Response.AccountResponseDto;
import com.website.sharestore.Entity.Account;
import com.website.sharestore.Entity.User;
import com.website.sharestore.Repository.AccountRepository;
import com.website.sharestore.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    // 계좌 등록
    public AccountResponseDto registerAccount( String username, AccountRequestDto accountRequestDto) {
        User user = userRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다."));

        // 중복 계좌 체크
        if (accountRepository.findByUser(user).isPresent()) {
            throw new IllegalStateException("당신은 이미 계좌를 가지고 있습니다.");
        }

        Account account = new Account();
        account.setBankName(accountRequestDto.getBankName());
        account.setAccountNumber(accountRequestDto.getAccountNumber());
        account.setHolderName(accountRequestDto.getHolderName());
        account.setUser(user);

        Account savedAccount = accountRepository.save(account);

        // Response DTO 반환
        return new AccountResponseDto(
            savedAccount.getAccountKey(),
            savedAccount.getBankName(),
            savedAccount.getAccountNumber(),
            savedAccount.getHolderName()
        );
    }

    // 계좌 조회
    public AccountResponseDto getAccount(String username) {
        User user = userRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다."));

        Account account = accountRepository.findByUser(user)
            .orElseThrow(() -> new IllegalStateException("계좌를 찾을 수 없습니다."));

        // Response DTO 반환
        return new AccountResponseDto(
            account.getAccountKey(),
            account.getBankName(),
            account.getAccountNumber(),
            account.getHolderName()
        );
    }

    // 계좌 수정
    public AccountResponseDto updateAccount(Long accountKey, AccountRequestDto accountRequestDto, String username) {

        Account account = accountRepository.findById(accountKey)
            .orElseThrow(() -> new IllegalArgumentException("계좌를 찾을 수 없습니다."));

        if (!account.getUser().getEmail().equals(username)) {
            throw new IllegalStateException("계좌가 중복되었습니다.");
        }
        account.setAccountKey(accountKey);
        account.setBankName(accountRequestDto.getBankName());
        account.setAccountNumber(accountRequestDto.getAccountNumber());
        account.setHolderName(accountRequestDto.getHolderName());

        Account updatedAccount = accountRepository.save(account);

        // Response DTO 반환
        return new AccountResponseDto(
            updatedAccount.getAccountKey(),
            updatedAccount.getBankName(),
            updatedAccount.getAccountNumber(),
            updatedAccount.getHolderName()
        );
    }


    // 계좌 삭제
    public void deleteAccount(Long accountKey, String username) {
        Account account = accountRepository.findById(accountKey)
            .orElseThrow(() -> new IllegalArgumentException("게좌를 찾을 수 없습니다."));

        if (!account.getUser().getEmail().equals(username)) {
            throw new IllegalStateException("계좌를 삭제할 수 없습니다.");
        }

        accountRepository.delete(account);
    }
}
