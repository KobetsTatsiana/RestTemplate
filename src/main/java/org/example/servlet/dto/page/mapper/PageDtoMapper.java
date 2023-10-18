package org.example.servlet.dto.page.mapper;

import org.example.model.SitePage;
import org.example.servlet.dto.page.PageAllOutGoingDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PageDtoMapper {
    PageDtoMapper INSTANCE = Mappers.getMapper(PageDtoMapper.class);

    default PageAllOutGoingDTO mapListToDto(List<SitePage> sitePageList) {
        PageAllOutGoingDTO pageAllOutGoingDTO = new PageAllOutGoingDTO();
        pageAllOutGoingDTO.setSitePageList(sitePageList);
        return pageAllOutGoingDTO;
    }

}
