package com.zh.storage.constant;

public class Enums {
  // 文件类型
  public enum FileType {
    Temp(1),
    Domain(2);

    private int index;

    FileType(int index) {
      this.index = index;
    }

    public int getIndex() {
      return index;
    }
  }

}


