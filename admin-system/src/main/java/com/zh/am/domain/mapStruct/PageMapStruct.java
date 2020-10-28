package com.zh.am.domain.mapStruct;

import com.zh.am.domain.dto.page.GetPageOutput;
import com.zh.am.domain.dto.page.PageDetailsVo;
import com.zh.am.domain.dto.page.PageVo;
import com.zh.am.domain.dto.page.UserPageDto;
import com.zh.am.domain.dto.role.OperationDto;
import com.zh.am.domain.dto.role.PageRoleDto;
import com.zh.am.domain.entity.Operation;
import com.zh.am.domain.entity.Page;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PageMapStruct {
  List<UserPageDto> entitiesToDto(List<Page> pages);

  UserPageDto entityToDto(Page page);

  GetPageOutput entityToPageDTO(Page pages);

  OperationDto entityToOperationDTO(Operation operation);

  List<OperationDto> entityToOperationDTOList(List<Operation> operation);

  List<PageRoleDto> pageDTOToRolePage(List<GetPageOutput> pageDTOList);

  List<PageVo> dtoToPageVOList(List<GetPageOutput> pageDTOList);

  PageDetailsVo dtoToPageDetailsVO(GetPageOutput pageDTO);
}
