package com.zh.am.api;

import com.zh.web.contract.ResponseBodyWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 可匿名访问的api
 *
 * @author zh
 * @date 2020/3/5
 */
@RestController
@RequestMapping("internal/api/v1/internal")
public class InternalController {
  @GetMapping
  public ResponseBodyWrapper internal() {
    return ResponseBodyWrapper.from("internal api");
  }
}
