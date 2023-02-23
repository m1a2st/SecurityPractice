package com.secpractice.securitypractice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author
 * @Date
 * @Version
 * @Description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String userId;
    private String pwd;
    private String Authorities;
}
