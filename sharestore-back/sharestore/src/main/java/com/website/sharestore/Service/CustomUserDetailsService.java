package com.website.sharestore.Service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.website.sharestore.Entity.User;
import com.website.sharestore.Repository.UserRepository;

import lombok.RequiredArgsConstructor;
import java.util.Collections;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " 을 DB에서 찾을 수 없습니다"));

        return createUserDetails(user);
    }

    private UserDetails createUserDetails(User user) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getAuthority().toString());

        // Spring Security의 User 클래스를 사용합니다.
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),  // 사용자 이름 (예: 이메일)
                user.getPassword(),
                Collections.singleton(grantedAuthority) // GrantedAuthority로 권한 설정
        );
    }
}
