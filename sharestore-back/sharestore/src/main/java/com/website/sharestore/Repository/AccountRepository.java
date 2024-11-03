package com.website.sharestore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.website.sharestore.Entity.Account;
public interface AccountRepository extends JpaRepository<Account, Long > {
  
}
