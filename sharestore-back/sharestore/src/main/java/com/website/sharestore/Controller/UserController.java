package com.website.sharestore.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.website.sharestore.Dto.Auth.UserRequestDto;
import com.website.sharestore.Dto.Auth.UserResponseDto;
import com.website.sharestore.Dto.Response.ProductResponseDto;
import com.website.sharestore.Dto.User.ChangePasswordRequestDto;
import com.website.sharestore.Service.ProductService;
import com.website.sharestore.Service.UserService;


@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    
    @Autowired
    private ProductService productService;

    public UserController(UserService userService) {
        this.userService = userService; // 생성자에서 초기화
    }


    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMyUserInfo() {
        UserResponseDto myInfoBySecurity = userService.getMyInfoBySecurity();
        return ResponseEntity.ok((myInfoBySecurity));
        
    }

    @PostMapping("/change-nickname")
    public ResponseEntity<UserResponseDto> setUserNickname(@RequestBody UserRequestDto request) {
        return ResponseEntity.ok(userService.changeUserNickname(request.getNickname()));
    }

    @GetMapping("/check-nickname")
    public ResponseEntity<Boolean> checkNicknameDuplicate(@RequestParam(name = "nickname") String nickname) {
        return ResponseEntity.ok(userService.isNicknameDuplicate(nickname));
    }

    @PostMapping("/change-password")
    public ResponseEntity<UserResponseDto> setUserPassword(@RequestBody ChangePasswordRequestDto request) {
        return ResponseEntity.ok(userService.changeUserPassword(request.getEmail(),request.getExPassword(), request.getNewPassword()));
    }

    @GetMapping("/key") // 키(Key)값 반환 메서드
    public ResponseEntity<Long> getUserKey(Authentication authentication) {
        Long userKey = userService.getUserKey(authentication.getName());
        return ResponseEntity.ok(userKey);
    }


    @GetMapping("/mystore/{userKey}")
    public ResponseEntity<List<ProductResponseDto>> getProductsByUser(@PathVariable(name = "userKey") Long userKey) {
        List<ProductResponseDto> products = productService.getProductsByUserKey(userKey);
        return ResponseEntity.ok(products);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser() {
        userService.deleteUserByEmail();
        return ResponseEntity.ok("회원 탈퇴 완료");
    }
}