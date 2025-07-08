// 에러 코드 예시 어노테이션
package com.server.money_touch.global.validation.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ApiErrorCodeExamples.class)
public @interface ApiErrorCodeExample {
    Class<? extends Enum<?>> value(); // 에러 코드 enum 클래스 (예: ErrorStatus.class)
    String name(); // 에러 코드 상수 이름 (예: "USER_NOT_FOUND")
}
