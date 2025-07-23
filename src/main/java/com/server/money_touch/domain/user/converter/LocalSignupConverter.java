package com.server.money_touch.domain.user.converter;

import com.server.money_touch.domain.user.dto.UserRequest;
import com.server.money_touch.domain.user.entity.LocalLogin;
import com.server.money_touch.domain.user.entity.User;
import com.server.money_touch.domain.user.enums.AuthType;
import com.server.money_touch.domain.user.enums.Role;

// converter : client 요청으로 온 JSON 데이터를 역직렬화를 통해 엔티티로 변환
public class LocalSignupConverter {

   public User toUser(UserRequest.LocalSignUpDTO request){
      User user = new User();
      user.setNickname(request.getNickname());
      user.setAuthType(AuthType.LOCAL);
      user.setRole(Role.USER);
      return user;
   }

   public LocalLogin toLocalLogin(UserRequest.LocalSignUpDTO request, User user, String encodeedPassword) {
      LocalLogin localLogin = new LocalLogin();
      localLogin.setEmail(request.getEmail());
      localLogin.setPassword(request.getPassword());
      localLogin.setUser(user);
      return localLogin;
   }
}
