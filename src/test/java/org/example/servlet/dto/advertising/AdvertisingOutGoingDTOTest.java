package org.example.servlet.dto.advertising;

import org.example.model.SitePage;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdvertisingOutGoingDTOTest {

    @Test
    public void testGettersAndSetters() {

        AdvertisingOutGoingDTO dto = new AdvertisingOutGoingDTO();

        long idTest = 1L;
        String infoTextTest = "Test info";
        List<SitePage> sitePagesListTest = new ArrayList<>();

        dto.setId(idTest);
        dto.setInfoText(infoTextTest);
        dto.setSitePagesList(sitePagesListTest);

        assertEquals(idTest, dto.getId());
        assertEquals(infoTextTest, dto.getInfoText());
        assertEquals(sitePagesListTest, dto.getSitePagesList());
    }
}
