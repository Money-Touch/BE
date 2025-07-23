package com.server.money_touch.domain.user.converter;

import com.server.money_touch.domain.user.dto.UserRequest;
import com.server.money_touch.domain.user.entity.SocialLogin;
import com.server.money_touch.domain.user.entity.User;
import com.server.money_touch.domain.user.enums.AuthType;
import com.server.money_touch.domain.user.enums.Role;

import java.time.LocalDateTime;

public class SocialSignupConverter {

    public User toUser(UserRequest.KakaoSignUpDto request){
        User user = new User();
        user.setNickname(request.getNickname());
        user.setAuthType(AuthType.KAKAO); // 또는 request로 받은 provider로 매핑
        user.setRole(Role.USER);
        return user;
    }

    public SocialLogin toSocialLogin(UserRequest.KakaoSignUpDto request, User user){
        SocialLogin socialLogin = new SocialLogin();
        socialLogin.setKakaoKey(request.getKakaoKey());
        socialLogin.setConnectedAt(LocalDateTime.now());
        socialLogin.setUser(user);
        return socialLogin;
    }
}
