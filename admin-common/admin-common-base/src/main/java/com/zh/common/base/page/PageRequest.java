package com.zh.common.base.page;

/**
 * 前端传入的分页对象
 *
 * @author zh
 * @date 2020/3/1
 */
public class PageRequest {
  public static final int DEFAULT_PAGE_INDEX = 1;
  public static final int DEFAULT_PAGE_SIZE = 20;
  private Integer pageIndex;
  private Integer pageSize;
  private String sortField;
  private boolean isDesc;

  public Integer getPageIndex() {
    return pageIndex == null ? DEFAULT_PAGE_INDEX : pageIndex + 1;
  }

  public void setPageIndex(Integer pageIndex) {
    this.pageIndex = pageIndex;
  }

  public Integer getPageSize() {
    return pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public String getSortField() {
    return sortField;
  }

  public void setSortField(String sortField) {
    this.sortField = sortField;
  }

  public boolean getIsDesc() {
    return isDesc;
  }

  public void setIsDesc(boolean isDesc) {
    isDesc = isDesc;
  }

}
