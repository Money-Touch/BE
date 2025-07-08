package com.server.money_touch.global.validation.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiErrorCodeExamples {
    ApiErrorCodeExample[] value(); // 여러 개의 ApiErrorCodeExample 포함
}
