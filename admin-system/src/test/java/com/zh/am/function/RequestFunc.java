package com.zh.am.function;

@FunctionalInterface
public interface RequestFunc<T> {
  T run();
}
