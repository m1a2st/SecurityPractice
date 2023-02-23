package com.secpractice.securitypractice.controller.view;

import com.secpractice.securitypractice.Security.LoginUser;
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
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserView {

    private String userName;
    private String authority;

    private String message;

    public UserView toView(LoginUser loginUser){
        return UserView.builder()
                .userName(loginUser.getUser().getUserId())
                .authority("A")
                .build();
    }

}
