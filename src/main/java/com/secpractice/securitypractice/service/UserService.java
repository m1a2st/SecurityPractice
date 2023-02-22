package com.secpractice.securitypractice.service;

import com.secpractice.securitypractice.entity.LoginUser;
import com.secpractice.securitypractice.repository.LoginUserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author
 * @Date
 * @Version
 * @Description
 */
@Service
public class UserService {

    private final LoginUserRepository repository;

    public UserService(LoginUserRepository repository) {
        this.repository = repository;
    }

    public LoginUser queryByUserName(String userName) {
        return repository.findById(userName)
                .orElseThrow(() ->new UsernameNotFoundException("can't find user"));
    }
}
