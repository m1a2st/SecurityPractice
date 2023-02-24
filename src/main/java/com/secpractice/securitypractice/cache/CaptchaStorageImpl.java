package com.secpractice.securitypractice.cache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Random;

import static java.lang.String.valueOf;

/**
 * @Author
 * @Date
 * @Version
 * @Description
 */
@Component
public class CaptchaStorageImpl implements CaptchaStorage {

    private static final String SMS_CAPTCHA_CACHE = "captcha";
    private static final Random random = new Random();

    @CachePut(cacheNames = SMS_CAPTCHA_CACHE,key = "#phone")
    @Override
    public String put(String phone) {
        return valueOf(random.nextInt(5));
    }
    @Cacheable(cacheNames = SMS_CAPTCHA_CACHE,key = "#phone")
    @Override
    public String get(String phone) {
        return null;
    }
    @CacheEvict(cacheNames = SMS_CAPTCHA_CACHE,key = "#phone")
    @Override
    public void expire(String phone) {

    }
}
