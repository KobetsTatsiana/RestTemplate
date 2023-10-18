package org.example.servlet.dto.page;

import org.example.model.Advertising;
import org.example.model.SitePage;
import org.example.servlet.dto.page.PageAllOutGoingDTO;
import org.example.servlet.dto.page.PageIncomingDTO;
import org.example.servlet.dto.page.PageOutGoingDTO;
import org.example.servlet.dto.page.PageUpdateDTO;
import org.example.servlet.dto.page.mapper.PageMapper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PageMapperTest {

    private final PageMapper pageMapper = PageMapper.INSTANCE;

    @Test
    public void testMapListToDto() {
        //Arrange
        SitePage sitePage1 = new SitePage();
        SitePage sitePage2 = new SitePage();

        List<SitePage> sitePageList = Arrays.asList(sitePage1, sitePage2);

        //Act
        PageAllOutGoingDTO result = pageMapper.mapListToDto(sitePageList);

        //Assert
        assertNotNull(result);
        assertNotNull(result.getSitePageList());
        assertEquals(2, result.getSitePageList().size());
    }

    @Test
    public void testMapPageIncomingDTOToSitePage() {
        //Arrange
        PageIncomingDTO pageIncomingDTO = new PageIncomingDTO();
        pageIncomingDTO.setNamePage("TestPage");
        Advertising advertising = new Advertising();
        pageIncomingDTO.setAdvertisingList(Arrays.asList(advertising));

        //Act
        SitePage result = pageMapper.map(pageIncomingDTO);

        //Assert
        assertNotNull(result);
        assertEquals("TestPage", result.getNamePage());
        assertNotNull(result.getAdvertisingList());
        assertEquals(1, result.getAdvertisingList().size());
    }
}
