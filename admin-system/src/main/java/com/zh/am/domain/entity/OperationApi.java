package com.zh.am.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

public class OperationApi implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId(value = "operation_id", type = IdType.NONE)
  private String operationId;

  private String apiId;

  public String getOperationId() {
    return operationId;
  }

  public void setOperationId(String operationId) {
    this.operationId = operationId;
  }

  public String getApiId() {
    return apiId;
  }

  public void setApiId(String apiId) {
    this.apiId = apiId;
  }

  @Override
  public String toString() {
    return "OperationApi{" +
        "operationId=" + operationId +
        ", apiId=" + apiId +
        "}";
  }
}
