package com.secpractice.securitypractice.cache;

public interface CaptchaService {

    boolean sendCaptcha(String phone);
    boolean verifyCaptcha(String phone,String code);
}
