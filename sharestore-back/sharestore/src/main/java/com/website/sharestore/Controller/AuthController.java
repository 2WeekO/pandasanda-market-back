package com.website.sharestore.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.website.sharestore.Dto.Auth.UserRequestDto;
import com.website.sharestore.Dto.Auth.UserResponseDto;
import com.website.sharestore.Dto.Token.TokenDto;
import com.website.sharestore.Service.AuthService;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody UserRequestDto requestDto) {
        return ResponseEntity.ok(authService.signup(requestDto));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<TokenDto> login(@RequestBody UserRequestDto requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));

    }
    @GetMapping("/auth/logout")
    public ResponseEntity<Void> logout() {
        System.out.println("로그아웃 성공");
        return ResponseEntity.ok().build();
    }
}
