package com.zh.am.mapStruct;

import com.zh.am.dto.page.GetPageOutput;
import com.zh.am.dto.page.PageDetailsVO;
import com.zh.am.dto.page.PageVo;
import com.zh.am.dto.page.UserPageDto;
import com.zh.am.dto.role.OperationDto;
import com.zh.am.dto.role.PageRoleDto;
import com.zh.am.entity.Operation;
import com.zh.am.entity.Page;
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

  PageDetailsVO dtoToPageDetailsVO(GetPageOutput pageDTO);
}
