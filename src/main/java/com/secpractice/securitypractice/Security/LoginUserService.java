package com.secpractice.securitypractice.Security;

import com.secpractice.securitypractice.entity.User;
import com.secpractice.securitypractice.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
public class LoginUserService implements UserDetailsService {

    private final UserRepository userRepository;

    public LoginUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.queryById(username);
        user.orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new LoginUser(user.get());
    }
}
