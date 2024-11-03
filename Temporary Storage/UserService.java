package com.website.sharestore.Service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.website.sharestore.Dto.Auth.UserDto;
import com.website.sharestore.Entity.User;
import com.website.sharestore.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;  // BCryptPasswordEncoder 대신 PasswordEncoder 사용

    // 회원가입 메서드
    public String registerUser(UserDto userDto) {

        // 중복 이메일 체크
        if (userRepository.findByEmail(userDto.getEmail()) != null) {
            return "이미 사용 중인 이메일입니다.";
        }

        // 비밀번호 확인
        if (!userDto.getPassword().equals(userDto.getPasswordCheck())) {
            return "비밀번호가 일치하지 않습니다.";
        }

        // 사용자 생성
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setNickname(userDto.getNickname());
        user.setAddress(userDto.getAddress());
        user.setAddressDetail(userDto.getAddressDetail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(userDto.getPassword())); // 비밀번호 암호화
        user.setPassword(passwordEncoder.encode(userDto.getPasswordCheck()));
        user.setSignupDate(LocalDateTime.now());
    
        userRepository.save(user);
        return "회원가입 완료";
    }

    // 로그인 인증 메서드
    public User getUserInfo(String email) {
        return userRepository.findByEmail(email);
    }
}