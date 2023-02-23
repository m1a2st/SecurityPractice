package com.secpractice.securitypractice.repository.impl;

import com.secpractice.securitypractice.entity.User;
import com.secpractice.securitypractice.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author
 * @Date
 * @Version
 * @Description
 */
@Repository
public class UserRepositoryImpl implements UserRepository {
    @Override
    public void insert(User user) {

    }

    @Override
    public void delete(String userId) {

    }

    @Override
    public void update(User user) {

    }

    @Override
    public Optional<User> queryById(String userId) {
        return Optional.ofNullable(User.builder()
                .userId("admin")
                .pwd("000000")
                .Authorities("A")
                .build());
    }
}
