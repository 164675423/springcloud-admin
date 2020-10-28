package com.zh.am.function;

@FunctionalInterface
public interface AfterFunc<T> {
  T doAfter(T t);
}
