package com.secpractice.securitypractice.controller;

import com.secpractice.securitypractice.cache.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author
 * @Date
 * @Version
 * @Description
 */
@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    private final CaptchaService captchaService;

    public CaptchaController(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    @GetMapping("/{phone}")
    public ResponseEntity<?> captchaByMobile(@PathVariable String phone){
        if(captchaService.sendCaptcha(phone)){
            return ResponseEntity.ok("send success");
        }
        return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.BAD_REQUEST);
    }
}
