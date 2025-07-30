package com.server.money_touch.global.utils;

import com.server.money_touch.global.apiPayload.code.status.ErrorStatus;
import com.server.money_touch.global.apiPayload.exception.handler.ErrorHandler;
import com.server.money_touch.global.config.jwt.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtil {

    private final TokenProvider tokenProvider;

    public Long getUserIdFromRequest(HttpServletRequest request) {
        String token = TokenProvider.resolveToken(request);  // Authorization 헤더에서 토큰 추출
        if (token == null) {
            throw new ErrorHandler(ErrorStatus._BAD_REQUEST);  // 인증 실패 예외
        }
        return tokenProvider.extractUserId(token);
    }
}
