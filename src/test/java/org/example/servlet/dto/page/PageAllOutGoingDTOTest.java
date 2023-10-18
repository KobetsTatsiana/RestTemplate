package org.example.servlet.dto.page;

import org.example.servlet.dto.page.PageAllOutGoingDTO;
import org.example.model.SitePage;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class PageAllOutGoingDTOTest {

    @Mock
    private PageAllOutGoingDTO pageAllOutGoingDTO;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetSetSitePageList() {
        List<SitePage> sitePageList = new ArrayList<>();
        SitePage sitePage1 = new SitePage(1L, "Page1");
        SitePage sitePage2 = new SitePage(2L, "Page2");
        sitePageList.add(sitePage1);
        sitePageList.add(sitePage2);

        when(pageAllOutGoingDTO.getSitePageList()).thenReturn(sitePageList);
        assertEquals(sitePageList, pageAllOutGoingDTO.getSitePageList());
    }
}