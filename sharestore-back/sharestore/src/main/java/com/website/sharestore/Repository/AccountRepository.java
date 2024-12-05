package com.website.sharestore.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.website.sharestore.Entity.Account;
import com.website.sharestore.Entity.User;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long > {
  Optional<Account> findByUser(User user);
  List<Account> findAllByUser(User user); // 여러 계좌 지원 시
}
