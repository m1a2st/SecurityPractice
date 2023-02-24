package com.secpractice.securitypractice.cache;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.springframework.util.StringUtils.hasText;

/**
 * @Author
 * @Date
 * @Version
 * @Description
 */
@Component
@Log4j2
public class CaptchaServiceImpl implements CaptchaService{

    private final CaptchaStorage captchaStorage;

    public CaptchaServiceImpl(CaptchaStorage captchaStorage) {
        this.captchaStorage = captchaStorage;
    }

    @Override
    public boolean sendCaptcha(String phone) {
        String existed = captchaStorage.get(phone);
        if (hasText(existed)) {
            // 如果快取中有當前手機的驗證碼，則不再發新的驗證碼
            log.warn("captcha code {} is available now",existed);
            return false;
        }
        String captchaCode = captchaStorage.put(phone);
        log.info("captcha {}", captchaCode);
        //TODO 第三方手機驗服務
        return true;
    }

    @Override
    public boolean verifyCaptcha(String phone, String code) {
        String captchaCode = captchaStorage.get(phone);
        if (Objects.equals(code, captchaCode)) {
            // 驗證通過手動過期
            captchaStorage.expire(phone);
            return true;
        }
        return false;
    }
}
