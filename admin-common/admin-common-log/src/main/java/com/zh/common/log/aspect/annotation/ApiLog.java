package com.zh.common.log.aspect.annotation;

public @interface ApiLog {
  String value() default "";

  String description() default "";
}
