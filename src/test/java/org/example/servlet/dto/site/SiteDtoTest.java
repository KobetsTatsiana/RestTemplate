package org.example.servlet.dto.site;

import org.example.model.SitePage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SiteDtoTest {

    private SiteDto siteDto;

    @BeforeEach
    public void setUp() {
        siteDto = new SiteDto();
    }

    @Test
    public void testId() {
        long expectedId = 123L;
        siteDto.setId(expectedId);
        long actualId = siteDto.getId();
        assertEquals(expectedId, actualId);
    }

    @Test
    public void testNameSite() {
        String expectedName = "Test Site";
        siteDto.setNameSite(expectedName);
        String actualName = siteDto.getNameSite();
        assertEquals(expectedName, actualName);
    }

    @Test
    public void testSitePageList() {
        SitePage page1 = new SitePage();
        SitePage page2 = new SitePage();
        List<SitePage> expectedList = Arrays.asList(page1, page2);
        siteDto.setSitePageList(expectedList);
        List<SitePage> actualList = siteDto.getSitePageList();
        assertEquals(expectedList, actualList);
    }
}