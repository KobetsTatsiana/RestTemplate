package org.example.servlet.dto.advertising;

import org.example.model.Advertising;
import org.example.model.SitePage;

import org.example.servlet.dto.advertising.mapper.AdvertisingDtoMapper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

 class AdvertisingDtoMapperTest {

    @Test
     void mapAdvertisingIncomingDTO() {
        AdvertisingDtoMapper mapper = AdvertisingDtoMapper.INSTANCE;
        AdvertisingIncomingDTO incomingDTO = new AdvertisingIncomingDTO();
        incomingDTO.setInfoText("Sample Info Text");

        List<SitePage> sitePageList = new ArrayList<>();

        incomingDTO.setSitePageList(sitePageList);
        Advertising advertising = mapper.map(incomingDTO);
        assertNotNull(advertising);
        assertEquals(incomingDTO.getInfoText(), advertising.getInfoText());
        assertNotNull(advertising.getSitePageList());
    }
}
