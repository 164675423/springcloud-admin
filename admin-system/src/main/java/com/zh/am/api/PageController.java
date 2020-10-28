package com.zh.am.api;

import com.zh.am.domain.dto.page.GetPageOutput;
import com.zh.am.domain.dto.page.PageDetailsVo;
import com.zh.am.domain.dto.page.PageVo;
import com.zh.am.domain.mapStruct.PageMapStruct;
import com.zh.am.service.IPageService;
import com.zh.common.context.LoginUser;
import com.zh.common.contract.ResponseBodyWrapper;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "菜单信息")
@RestController
@RequestMapping("api/v1/pages")
public class PageController {
  private final IPageService pageService;
  private final PageMapStruct pageMapStruct;

  public PageController(IPageService pageService, PageMapStruct pageMapStruct) {
    this.pageService = pageService;
    this.pageMapStruct = pageMapStruct;
  }

  /**
   * 获取page列表.
   *
   * @param maxLevel 最大层数
   * @return resultInfo
   */
  @GetMapping()
  public ResponseBodyWrapper getPages(@RequestParam(required = false) Integer maxLevel,
                                      @RequestAttribute LoginUser user) {
    //方便计算返回结构中的 itemCount 或 operationCount
    if (maxLevel != null) {
      maxLevel += 1;
    }
    List<GetPageOutput> pagesDTO = pageService.getPagesByMaxLevel(maxLevel, user.getId());
    List<PageVo> pagesVO = pageMapStruct.dtoToPageVOList(pagesDTO);
    return ResponseBodyWrapper.from(pagesVO);
  }

  /**
   * 获取page详情.
   *
   * @param id 页面Id
   * @return 指定页面的信息
   */
  @GetMapping(value = "{id}")
  public ResponseBodyWrapper getPages(@PathVariable String id,
                                      @RequestAttribute LoginUser user) {
    List<GetPageOutput> pagesDTO = pageService.getPageDetailsByPageId(id, user.getId());
    PageDetailsVo pageDetailsVO = pageMapStruct.dtoToPageDetailsVO(pagesDTO.get(0));
    return ResponseBodyWrapper.from(pageDetailsVO);
  }
}
