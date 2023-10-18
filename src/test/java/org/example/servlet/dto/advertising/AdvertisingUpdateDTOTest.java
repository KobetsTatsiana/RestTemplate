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
        SitePage page = new SitePage(); // use an appropriate constructor or set values as required
        sitePageList.add(page);
        advertisingUpdateDTO.setSitePagesList(sitePageList);

        // Verify the get methods
        assertEquals(123L, advertisingUpdateDTO.getId());
        assertEquals("Test info text", advertisingUpdateDTO.getInfoText());
        assertEquals(sitePageList, advertisingUpdateDTO.getSitePagesList());
    }
}
