package com.website.sharestore.Service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.website.sharestore.Dto.Account.AccountRegistrationDto;
import com.website.sharestore.Entity.Account;
import com.website.sharestore.Entity.User;
import com.website.sharestore.Repository.AccountRepository;
import com.website.sharestore.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public void resisterAccount(AccountRegistrationDto accountDto, String username) {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Account account = new Account();
        account.setAccountNumber(accountDto.getAccountNumber());
        account.setBankName(accountDto.getBankName());
        account.setHolderName(accountDto.getHolderName());
        account.setUser(user);

        accountRepository.save(account);
    }

}
