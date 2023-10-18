package org.example.servlet.dto.page.mapper;

import org.example.model.Advertising;
import org.example.model.SitePage;
import org.example.servlet.dto.page.PageAllOutGoingDTO;
import org.example.servlet.dto.page.PageIncomingDTO;
import org.example.servlet.dto.page.PageOutGoingDTO;
import org.example.servlet.dto.page.PageUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface PageMapper {

    PageMapper INSTANCE = Mappers.getMapper(PageMapper.class);

    PageOutGoingDTO map(SitePage sitePage);

    SitePage map(PageUpdateDTO pageUpdateDTO);

    default PageAllOutGoingDTO mapListToDto(List<SitePage> sitePageList) {
        PageAllOutGoingDTO pageAllOutGoingDTO = new PageAllOutGoingDTO();
        pageAllOutGoingDTO.setSitePageList(sitePageList);
        return pageAllOutGoingDTO;
    }

    default SitePage map(PageIncomingDTO pageIncomingDTO) {
        SitePage sitePage = new SitePage();
        sitePage.setNamePage(pageIncomingDTO.getNamePage());
        List<Advertising> advertisingList = new ArrayList<>();
        for (Advertising advertising : pageIncomingDTO.getAdvertisingList()) {
            advertisingList.add(advertising);
        }
        sitePage.setAdvertisingList(advertisingList);
        return sitePage;
    }
}