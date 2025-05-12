package com.website.sharestore.Service;

import static java.rmi.server.LogStream.log;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.website.sharestore.Config.SecurityUtil;
import com.website.sharestore.Dto.Auth.UserResponseDto;
import com.website.sharestore.Entity.User;
import com.website.sharestore.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    //내 정보 가져오기
    public UserResponseDto getMyInfoBySecurity() {
        String email = SecurityUtil.getCurrentUsername();

        return userRepository.findByEmail(email)
            .map(UserResponseDto::of)
            .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }

    // 닉네임 변경
    @Transactional
    public UserResponseDto changeUserNickname(String newNickname) {
        String email = SecurityUtil.getCurrentUsername();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("로그인한 유저의 정보가 없습니다."));
        user.setNickname(newNickname);
        return UserResponseDto.of(user);
    }

    public boolean isNicknameDuplicate(String newNickname) {
        return userRepository.existsByNickname(newNickname);
    }

    // 유저 비밀번호 변경
    @Transactional
    public UserResponseDto changeUserPassword(String email, String exPassword, String newPassword) {
        User user = userRepository.findById(SecurityUtil.getCurrentUserId()).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
        if (!passwordEncoder.matches(exPassword, user.getPassword())) {
            throw new RuntimeException("비밀번호가 맞지 않습니다");
        }
        user.setPassword(passwordEncoder.encode((newPassword)));
        return UserResponseDto.of(userRepository.save(user));
    }
    
    // 유저키 가져오기
    public Long getUserKey(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        return user.getUserKey();
    }

    // 회원 탈퇴
    @Transactional
    public void deleteUserByEmail() {
        String email = SecurityUtil.getCurrentUsername();
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("해당 사용자를 찾을 수 없습니다."));

        userRepository.delete(user);
    }

    
}