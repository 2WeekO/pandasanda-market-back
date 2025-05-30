package com.website.sharestore.Config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {



  public static Long getCurrentUserId() {
      final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      if (authentication == null || authentication.getName() == null) {
          throw new RuntimeException("Security Context에 인증 정보가 없습니다.");
      }

      return Long.getLong(authentication.getName()); // 수정 필요
  }

  public static String getCurrentUsername() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || authentication.getName() == null) {
        throw new RuntimeException("Security Context에 인증 정보가 없습니다.");
    }

    return authentication.getName(); // 이제 username(email) 반환
  }
}
