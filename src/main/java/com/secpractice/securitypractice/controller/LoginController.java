package com.secpractice.securitypractice.controller;

import com.secpractice.securitypractice.controller.view.UserView;
import com.secpractice.securitypractice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author
 * @Date
 * @Version
 * @Description
 */
//@RestController
//@RequestMapping("/login")
//public class LoginController {
//
//    private final UserService userService;
//
//    public LoginController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @PostMapping("/failure")
//    public ResponseEntity<UserView> loginFail(){
//        return new ResponseEntity<>(UserView.builder()
//                .message("login fail....")
//                .build(), HttpStatus.UNAUTHORIZED);
//    }
//
//    @PostMapping("/success")
//    public ResponseEntity<UserView> loginSuccess(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println(authentication.getName());
//        System.out.println(authentication.getAuthorities());
//        System.out.println(authentication.getCredentials());
//        System.out.println(authentication.getDetails());
//        System.out.println(authentication.getPrincipal());
//        return new ResponseEntity<>(new UserView(),HttpStatus.OK);
//    }
//}
