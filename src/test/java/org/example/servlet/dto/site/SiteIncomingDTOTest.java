package org.example.servlet.dto.site;

import org.example.model.SitePage;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class SiteIncomingDTOTest {
    private SiteIncomingDTO siteIncomingDTO;

    @Before
    public void setup() {
        siteIncomingDTO = new SiteIncomingDTO();
    }

    @Test
    public void testGetNameSite() {
        String name = "Test site";
        siteIncomingDTO.setNameSite(name);
        assertEquals(name, siteIncomingDTO.getNameSite());
    }

    @Test
    public void testGetSitePageList() {
        List<SitePage> list = Arrays.asList(new SitePage(), new SitePage());
        siteIncomingDTO.setSitePageList(list);
        assertEquals(list, siteIncomingDTO.getSitePageList());
    }

    @Test
    public void testGetSitePageListEmpty() {
        assertTrue(siteIncomingDTO.getSitePageList().isEmpty());
    }

    @Test
    public void testSetSitePageList() {
        List<SitePage> list = new ArrayList<>();
        list.add(new SitePage());
        siteIncomingDTO.setSitePageList(list);
        assertEquals(list, siteIncomingDTO.getSitePageList());
    }}
