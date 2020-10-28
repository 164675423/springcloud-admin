package com.zh.am.domain.dto.page;

import java.util.ArrayList;
import java.util.List;

public class PageDto {
  private List<UserPageDto> pages = new ArrayList<>();
  private List<FullUserPageDto> reversePages = new ArrayList<>();

  public List<UserPageDto> getPages() {
    return pages;
  }

  public void setPages(List<UserPageDto> pages) {
    this.pages = pages;
  }

  public List<FullUserPageDto> getReversePages() {
    return reversePages;
  }

  public void setReversePages(List<FullUserPageDto> reversePages) {
    this.reversePages = reversePages;
  }
}
