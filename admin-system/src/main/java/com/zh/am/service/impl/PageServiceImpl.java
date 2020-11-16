package com.zh.am.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.am.domain.dao.OperationMapper;
import com.zh.am.domain.dao.PageMapper;
import com.zh.am.domain.dto.page.GetPageOutput;
import com.zh.am.domain.dto.page.UserPageDto;
import com.zh.am.domain.entity.Operation;
import com.zh.am.domain.entity.Page;
import com.zh.am.domain.mapStruct.PageMapStruct;
import com.zh.am.service.PageService;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class PageServiceImpl extends ServiceImpl<PageMapper, Page> implements PageService {
  private final PageMapper pageMapper;
  private final PageMapStruct pageMapStruct;
  private final OperationMapper operationMapper;

  public PageServiceImpl(PageMapper pageMapper, PageMapStruct pageMapStruct, OperationMapper operationMapper) {
    this.pageMapper = pageMapper;
    this.pageMapStruct = pageMapStruct;
    this.operationMapper = operationMapper;
  }

  @Override
  public List<UserPageDto> getUserPages(String userId) {
    List<Page> pages = pageMapper.getPagesByUserId(userId);
    List<UserPageDto> pageDto = pageMapStruct.entitiesToDto(pages);
    //根节点
    List<UserPageDto> roots = pageDto.stream().filter(p -> p.getParentId() == null).collect(Collectors.toList());
    roots.forEach(r -> fillChildren(r, pageDto));
    return roots;
  }

  @Override
  public List<String> getPageIdsByRoleId(String roleId) {
    List<Page> pages = pageMapper.getPageByRole(roleId);
    return pages.stream().map(p -> p.getId()).collect(Collectors.toList());
  }

  @Override
  public List<String> getOperationsIdsByRoleId(String roleId) {
    List<Operation> operation = operationMapper.getOperationByRole(roleId);
    return operation.stream().map(o -> o.getId()).collect(Collectors.toList());
  }

  private void fillChildren(UserPageDto item, List<UserPageDto> all) {
    List<UserPageDto> children = all.stream().filter(p -> item.getId().equals(p.getParentId()))
        .sorted(Comparator.comparing(UserPageDto::getWeight)).collect(Collectors.toList());
    item.setChildren(children);
    for (UserPageDto userPageDto : children) {
      if (all.stream().filter(p -> userPageDto.getId().equals(p.getParentId())).collect(Collectors.toList()).size() > 0) {
        fillChildren(userPageDto, all);
      }
    }
  }

  private List<GetPageOutput> buildPageTree(List<Page> pages,
                                            List<Operation> operationsOrig, boolean isOperations, Integer maxLevel) {
    return buildPageTree(pages, operationsOrig, isOperations, maxLevel, null);
  }

  @Override
  public List<GetPageOutput> getPagesByRoleId(String roleId) {
    //TODO redis
    List<Operation> operations = operationMapper.getOperationByRole(roleId);

    // 由 role 直接关联的 page 作为叶子结点构造整棵树
    List<Page> childPages = pageMapper.getPageByRole(roleId);
    List<Page> allPages = getAllSortedPages(childPages);

    return buildPageTree(allPages, operations, true, null);
  }

  @Override
  public List<GetPageOutput> getPagesByMaxLevel(Integer maxLevel, String userId) {
    List<Page> childPages = pageMapper.selectList(new QueryWrapper<Page>().lambda().orderByAsc(Page::getLevel).orderByAsc(Page::getWeight));
    List<Page> allPages = getAllSortedPages(childPages);
    List<Operation> operations = operationMapper.selectList(null);
    return buildPageTree(allPages, operations, true, maxLevel);
  }

  @Override
  public List<GetPageOutput> getPageDetailsByPageId(String pageId, String userId) {
    Page page = pageMapper.selectById(pageId);
    List<Page> childPages = pageMapper.selectList(new QueryWrapper<Page>().lambda().orderByAsc(Page::getLevel).orderByAsc(Page::getWeight));
    List<Page> allPages = getAllSortedPages(childPages);
    List<Operation> operations = operationMapper.selectList(null);
    return buildPageTree(allPages, operations, true, null, page);
  }

  private List<GetPageOutput> buildPageTree(List<Page> pages,
                                            List<Operation> operationsOrig, boolean isOperations,
                                            Integer maxLevel, Page pageDetails) {
    List<Page> pagesRoot;
    if (pageDetails != null) {
      pagesRoot = pages.stream().filter(p -> p.getId().equals(pageDetails.getId())).collect(Collectors.toList());
    } else {
      pagesRoot = pages.stream().filter(p -> p.getLevel() == 0).collect(Collectors.toList());
    }

    List<GetPageOutput> pageList = new ArrayList<GetPageOutput>();
    for (Page page : pagesRoot) {
      GetPageOutput pageDto = pageMapStruct.entityToPageDTO(page);
      pageDto.setMaxLevel(maxLevel != null ? maxLevel - 1 : null);
      if (maxLevel == null) {
        recursion(pageDto, pages, operationsOrig, isOperations, maxLevel);
      } else {
        if (maxLevel > pageDto.getLevel()) {
          recursion(pageDto, pages, operationsOrig, isOperations, maxLevel);
        }
      }

      pageList.add(pageDto);
    }
    return pageList.size() == 0 ? null : pageList;
  }

  /**
   * 递归查询节点数据到最后一层
   * 如果isOperations 为 true 则 operationsOrig 不能为空
   *
   * @param currentPage    当前页
   * @param pagesOrig      .
   * @param operationsOrig .
   * @param isOperations   .
   * @param maxLevel       最大层数
   */
  private void recursion(GetPageOutput currentPage, List<Page> pagesOrig,
                         List<Operation> operationsOrig, boolean isOperations, Integer maxLevel) {
    if (maxLevel != null) {
      if (maxLevel <= currentPage.getLevel()) {
        return;
      }
    }
    List<Page> subPages = getPageByParentId(currentPage.getId(), pagesOrig);
    if (subPages != null && subPages.size() > 0) {
      subPages.forEach(p -> {
        GetPageOutput pageDto = pageMapStruct.entityToPageDTO(p);
        pageDto.setMaxLevel(maxLevel != null ? maxLevel - 1 : null);
        currentPage.add(pageDto);
        recursion(pageDto, pagesOrig, operationsOrig, isOperations, maxLevel);
      });
    } else {
      currentPage.setItems(null);
      if (isOperations) {
        List<Operation> operations = operationsOrig.stream()
            .filter(o -> o.getPageId().equals(currentPage.getId())).collect(Collectors.toList());
        if (operations != null && operations.size() > 0) {
          currentPage.setOperations(pageMapStruct.entityToOperationDTOList(operations));
        }
      }
    }
  }

  /**
   * 根据父节点获取子节点集合
   *
   * @param parentId 父节点Id
   * @param pages    .
   * @return list
   */
  private List<Page> getPageByParentId(String parentId, List<Page> pages) {
    StopWatch stopWatch = new StopWatch();
    stopWatch.start("获取子节点数据");
    List<Page> result = pages.stream().filter(p -> p.getParentId() != null && p.getParentId().equals(parentId))
        .collect(Collectors.toList());
    // ASC
    if (result != null && result.size() > 0) {
      result.sort(Comparator.comparing(Page::getWeight));
    }
    stopWatch.stop();
    return result;
  }

  private List<Page> getAllSortedPages(List<Page> childPages) {
    if (childPages.size() == 0) {
      return childPages;
    }
    List<Page> allPages = new ArrayList<>();
    allPages.addAll(childPages);
    allPages.addAll(getAncestorPages(childPages));
    List<Page> noDuplePages = removeDuplicatedById(allPages);
    noDuplePages.sort(Comparator.comparing(Page::getLevel).thenComparing(Page::getWeight));
    return noDuplePages;
  }

  private List<Page> removeDuplicatedById(List<Page> persons) {
    Set<Page> personSet = new TreeSet<>(Comparator.comparing(Page::getId));
    personSet.addAll(persons);
    return new ArrayList<>(personSet);
  }

  private List<Page> getAncestorPages(List<Page> childPages) {
    List<Page> ancestorPages = new ArrayList<>();
    List<Integer> levels = new ArrayList<>();
    for (Page page : childPages) {
      levels.add(page.getLevel());
    }
    int maxLevel = Collections.max(levels);
    List<Page> currentPages = childPages;
    for (int i = 0; i < maxLevel; i++) {
      List<Page> parentPages = getParentPages(currentPages);
      ancestorPages.addAll(parentPages);
      currentPages = parentPages;
    }
    return ancestorPages;
  }

  private List<Page> getParentPages(List<Page> currentPages) {
    QueryWrapper<Page> queryWrapper = new QueryWrapper<>();
    List<String> parentIds = new ArrayList<>();
    for (Page page : currentPages) {
      parentIds.add(page.getParentId());
    }
    queryWrapper.lambda().in(Page::getId, parentIds);
    List<Page> parentPages = pageMapper.selectList(queryWrapper);
    return parentPages;
  }
}
