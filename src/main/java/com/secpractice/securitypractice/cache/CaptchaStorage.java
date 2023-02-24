package com.secpractice.securitypractice.cache;

public interface CaptchaStorage {

    /**
     * 驗證碼放入快取
     *
     * @param phone the phone
     * @return the string
     */
    String put(String phone);

    /**
     * 從緩存提取驗證碼
     *
     * @param phone the phone
     * @return the string
     */
    String get(String phone);

    /**
     * 驗證碼手動過期
     *
     * @param phone the phone
     */
    void expire(String phone);

}
