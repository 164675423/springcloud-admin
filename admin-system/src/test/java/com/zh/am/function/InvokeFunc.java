package com.zh.am.function;

@FunctionalInterface
public interface InvokeFunc<T> {
  T invoke(String p1, String p2);
}
