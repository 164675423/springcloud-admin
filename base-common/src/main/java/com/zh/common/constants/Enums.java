package com.zh.common.constants;

public final class Enums {
  // 基础数据状态
  public enum BaseDataStatus implements BaseEnum {
    有效(1),
    作废(-1);

    private int index;

    BaseDataStatus(int index) {
      this.index = index;
    }

    @Override
    public int getIndex() {
      return index;
    }

  }

  // 权限类型
  public enum RelatedType {
    页面(1),
    按钮(2);

    private int index;

    RelatedType(int index) {
      this.index = index;
    }

    public int getIndex() {
      return index;
    }
  }
}


