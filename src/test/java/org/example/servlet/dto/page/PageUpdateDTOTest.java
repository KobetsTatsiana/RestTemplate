package org.example.servlet.dto.page;

import org.example.model.Advertising;
import org.example.servlet.dto.page.PageUpdateDTO;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PageUpdateDTOTest {

    private PageUpdateDTO pageUpdateDTO;

    @Before
    public void setUp() {
        pageUpdateDTO = new PageUpdateDTO();
    }

    @Test
    public void testGetSetId() {
        long expectedId = 123L;
        pageUpdateDTO.setId(expectedId);
        assertEquals(expectedId, pageUpdateDTO.getId());
    }

    @Test
    public void testGetNamePage() {
        String expectedNamePage = "Sample Page";
        pageUpdateDTO.setNamePage(expectedNamePage);
        assertEquals(expectedNamePage, pageUpdateDTO.getNamePage());
    }

    @Test
    public void testGetSetAdvertisingList() {
        List<Advertising> expectedAdvertisingList = new ArrayList<>();
        Advertising advertising1 = new Advertising(1L, "Info1", null);
        Advertising advertising2 = new Advertising(2L, "Info2", null);
        expectedAdvertisingList.add(advertising1);
        expectedAdvertisingList.add(advertising2);

        pageUpdateDTO.setAdvertisingList(expectedAdvertisingList);
        assertEquals(expectedAdvertisingList, pageUpdateDTO.getAdvertisingList());
    }
}