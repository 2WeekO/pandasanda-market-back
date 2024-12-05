package com.website.sharestore.Controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.website.sharestore.Dto.Request.AccountRequestDto;
import com.website.sharestore.Dto.Response.AccountResponseDto;
import com.website.sharestore.Service.AccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    // 계좌 등록
    @PostMapping("/register")
    public ResponseEntity<AccountResponseDto> registerAccount(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody AccountRequestDto accountRequestDto) {
        AccountResponseDto accountResponse = accountService.registerAccount(userDetails.getUsername(), accountRequestDto);
        return ResponseEntity.ok(accountResponse);
    }

    // 계좌 수정
    @PutMapping("/update/{accountKey}")
    public ResponseEntity<AccountResponseDto> updateAccount(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("accountKey") Long accountKey,
            @RequestBody AccountRequestDto accountRequestDto) {
        AccountResponseDto updatedAccount = accountService.updateAccount(accountKey, accountRequestDto, userDetails.getUsername());
        return ResponseEntity.ok(updatedAccount);
    }

    // 계좌 조회
    @GetMapping("/get")
    public ResponseEntity<AccountResponseDto> getAccount(
            @AuthenticationPrincipal UserDetails userDetails) {
        AccountResponseDto accountResponse = accountService.getAccount(userDetails.getUsername());
        return ResponseEntity.ok(accountResponse);
    }

    // 계좌 삭제
    @DeleteMapping("/delete/{accountKey}")
    public ResponseEntity<String> deleteAccount(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("accountKey") Long accountKey) {
        accountService.deleteAccount(accountKey, userDetails.getUsername());
        return ResponseEntity.ok("Account deleted successfully.");
    }
}
