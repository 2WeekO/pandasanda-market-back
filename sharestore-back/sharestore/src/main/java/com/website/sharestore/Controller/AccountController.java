package com.website.sharestore.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.website.sharestore.Dto.Account.AccountRegistrationDto;
import com.website.sharestore.Service.AccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(
        @RequestBody AccountRegistrationDto accountDto,
        @AuthenticationPrincipal UserDetails userDetails) {
            String username = userDetails.getUsername();

            accountService.resisterAccount(accountDto, username);
            return ResponseEntity.ok("성공적으로 계좌가 등록되었습니다.");
        }
}
