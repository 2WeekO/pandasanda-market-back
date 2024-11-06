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

@CrossOrigin("http://pandasanda.shop:3000")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody UserRequestDto requestDto) {
        return ResponseEntity.ok(authService.signup(requestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody UserRequestDto requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));

    }
    @GetMapping("/logout")
    public ResponseEntity<Void> logout() {
        System.out.println("로그아웃 성공");
        return ResponseEntity.ok().build();
    }
}
