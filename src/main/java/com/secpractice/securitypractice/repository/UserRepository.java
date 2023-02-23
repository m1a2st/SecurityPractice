package com.secpractice.securitypractice.repository;

import com.secpractice.securitypractice.entity.User;

import java.util.Optional;

/**
 * @Author
 * @Date
 * @Version
 * @Description
 */
public interface UserRepository {

    void insert(User user);

    void delete(String userId);

    void update(User user);

    Optional<User> queryById(String userId);

}
