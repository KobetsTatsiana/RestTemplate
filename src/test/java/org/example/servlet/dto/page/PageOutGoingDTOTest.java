package org.example.servlet.dto.page;

import org.example.model.Advertising;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PageOutGoingDTOTest {

    private PageOutGoingDTO pageOutGoingDTO;

    @Before
    public void setUp() {
        pageOutGoingDTO = new PageOutGoingDTO();
    }

    @Test
    public void testId() {
        long id = 1L;
        pageOutGoingDTO.setId(id);

        assertEquals(id, pageOutGoingDTO.getId());
    }

    @Test
    public void testNamePage() {
        String name = "Test Page";
        pageOutGoingDTO.setNamePage(name);

        assertEquals(name, pageOutGoingDTO.getNamePage());
    }

    @Test
    public void testAdvertisingList() {
        Advertising adv1 = new Advertising(); // Initialize as per your Project.
        Advertising adv2 = new Advertising(); // Initialize as per your Project.

        List<Advertising> advList = Arrays.asList(adv1, adv2);
        pageOutGoingDTO.setAdvertisingList(advList);

        assertNotNull(pageOutGoingDTO.getAdvertisingList());
        assertEquals(2, pageOutGoingDTO.getAdvertisingList().size());
        assertEquals(advList, pageOutGoingDTO.getAdvertisingList());
    }
}
