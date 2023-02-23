package com.secpractice.securitypractice.controller;

import com.secpractice.securitypractice.controller.view.UserView;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author
 * @Date
 * @Version
 * @Description
 */
@RestController
@RequestMapping("/api/v1")
public class ApiLoginController {
    @PostMapping("/login")
    public UserView login() {
        return new UserView();
    }
}
