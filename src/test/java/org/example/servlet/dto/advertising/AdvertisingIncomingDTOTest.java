package org.example.servlet.dto.advertising;

import org.example.model.SitePage;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdvertisingIncomingDTOTest {

    @Test
    void testGettersAndSetters() {

        AdvertisingIncomingDTO dto = new AdvertisingIncomingDTO();

        String infoTextTest = "Test info";
        List<SitePage> sitePageListTest = new ArrayList<>();

        dto.setInfoText(infoTextTest);
        dto.setSitePageList(sitePageListTest);

        assertEquals(infoTextTest, dto.getInfoText());
        assertEquals(sitePageListTest, dto.getSitePageList());
    }
}
