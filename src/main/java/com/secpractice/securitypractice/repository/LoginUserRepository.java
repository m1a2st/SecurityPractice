package com.secpractice.securitypractice.repository;

import com.secpractice.securitypractice.entity.LoginUser;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author
 * @Date
 * @Version
 * @Description
 */
@Repository
public class LoginUserRepository {


    public Optional<LoginUser> findById(String userName) {
        return Optional.ofNullable(LoginUser.builder()
                .userName("admin")
                .pwd("00000")
                .build());
    }
}
