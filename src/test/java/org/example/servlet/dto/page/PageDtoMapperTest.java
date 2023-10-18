package org.example.servlet.dto.page;

import org.example.model.SitePage;
import org.example.servlet.dto.page.mapper.PageDtoMapper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PageDtoMapperTest {

    private PageDtoMapper pageDtoMapper = PageDtoMapper.INSTANCE;

    @Test
    void mapListToDto() {
        SitePage sitePage1 = new SitePage();
        sitePage1.setNamePage("Name1");
        SitePage sitePage2 = new SitePage();
        sitePage2.setNamePage("Name2");

        List<SitePage> sitePages = new ArrayList<>();
        sitePages.add(sitePage1);
        sitePages.add(sitePage2);

        PageAllOutGoingDTO pageAllOutGoingDTO = pageDtoMapper.mapListToDto(sitePages);

        assertNotNull(pageAllOutGoingDTO);
        assertNotNull(pageAllOutGoingDTO.getSitePageList());
        assertEquals(2, pageAllOutGoingDTO.getSitePageList().size());
        assertTrue(pageAllOutGoingDTO.getSitePageList().contains(sitePage1));
        assertTrue(pageAllOutGoingDTO.getSitePageList().contains(sitePage2));
    }
}

