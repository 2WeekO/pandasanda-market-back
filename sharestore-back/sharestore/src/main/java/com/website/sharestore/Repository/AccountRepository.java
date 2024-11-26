package com.website.sharestore.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.website.sharestore.Entity.Account;
import com.website.sharestore.Entity.User;
public interface AccountRepository extends JpaRepository<Account, Long > {
  Optional<Account> findByUser(User user);
}
