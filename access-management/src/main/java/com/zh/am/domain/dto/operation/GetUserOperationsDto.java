package com.zh.am.domain.dto.operation;

import java.util.ArrayList;
import java.util.List;

public class GetUserOperationsDto {
  private String code;
  private List<String> operations = new ArrayList<>();

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public List<String> getOperations() {
    return operations;
  }

  public void setOperations(List<String> operations) {
    this.operations = operations;
  }
}
