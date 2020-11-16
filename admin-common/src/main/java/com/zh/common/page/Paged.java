package com.zh.common.page;

import com.github.pagehelper.Page;

import java.util.List;

public class Paged<T> {
  private Integer pageIndex;
  private Integer pageSize;
  private Integer totalPages;
  private Long totalElements;
  private List<T> content;

  public Paged(Integer pageIndex, Integer pageSize, Integer totalPages, Long totalElements, List<T> content) {
    this.pageIndex = pageIndex;
    this.pageSize = pageSize;
    this.totalPages = totalPages;
    this.totalElements = totalElements;
    this.content = content;
  }

  public static <T> Paged<T> From(Page page, List<T> content) {
    return new Paged<>(page.getPageNum() - 1, page.getPageSize(), page.getPages(), page.getTotal(), content);
  }

  public Integer getPageIndex() {
    return pageIndex;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public Integer getTotalPages() {
    return totalPages;
  }

  public Long getTotalElements() {
    return totalElements;
  }

  public List<T> getContent() {
    return content;
  }
}
