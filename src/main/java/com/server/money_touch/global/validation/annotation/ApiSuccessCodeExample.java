package com.server.money_touch.global.validation.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiSuccessCodeExample {
    Class<?> resultClass(); // DTO 클래스
}
