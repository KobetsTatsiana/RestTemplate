package org.example.servlet.dto.advertising;

import org.example.model.SitePage;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AdvertisingUpdateDTOTest {

    @Test
    public void testGetterAndSetter() {
        AdvertisingUpdateDTO advertisingUpdateDTO = new AdvertisingUpdateDTO();
        advertisingUpdateDTO.setId(123L);
        advertisingUpdateDTO.setInfoText("Test info text");

        List<SitePage> sitePageList = new ArrayList<>();
        SitePage page = new SitePage();
        sitePageList.add(page);
        advertisingUpdateDTO.setSitePagesList(sitePageList);

        assertEquals(123L, advertisingUpdateDTO.getId());
        assertEquals("Test info text", advertisingUpdateDTO.getInfoText());
        assertEquals(sitePageList, advertisingUpdateDTO.getSitePagesList());
    }
}
