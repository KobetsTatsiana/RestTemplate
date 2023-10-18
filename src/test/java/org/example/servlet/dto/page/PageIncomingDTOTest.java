package org.example.servlet.dto.page;
import org.example.model.Advertising;
import org.example.servlet.dto.page.PageIncomingDTO;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PageIncomingDTOTest {

    private PageIncomingDTO pageIncomingDTO;

    @Before
    public void setUp() {
        pageIncomingDTO = new PageIncomingDTO();
    }

    @Test
    public void testGetNamePage() {
        String expectedNamePage = "Sample Page";
        pageIncomingDTO.setNamePage(expectedNamePage);
        assertEquals(expectedNamePage, pageIncomingDTO.getNamePage());
    }

    @Test
    public void testGetSetAdvertisingList() {
        List<Advertising> expectedAdvertisingList = new ArrayList<>();
        Advertising advertising1 = new Advertising(1L, "Info1", null);
        Advertising advertising2 = new Advertising(2L, "Info2", null);
        expectedAdvertisingList.add(advertising1);
        expectedAdvertisingList.add(advertising2);

        pageIncomingDTO.setAdvertisingList(expectedAdvertisingList);
        assertEquals(expectedAdvertisingList, pageIncomingDTO.getAdvertisingList());
    }
}
