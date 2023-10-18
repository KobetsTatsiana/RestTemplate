package org.example.servlet.dto.advertising;


import org.example.model.Advertising;
import org.example.model.SitePage;
import org.example.servlet.dto.advertising.AdvertisingIncomingDTO;
import org.example.servlet.dto.advertising.mapper.AdvertisingDtoMapper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AdvertisingDtoMapperTest {

    @Test
    public void mapAdvertisingIncomingDTO() {
        AdvertisingDtoMapper mapper = AdvertisingDtoMapper.INSTANCE;

        // Initialize AdvertisingIncomingDTO object
        AdvertisingIncomingDTO incomingDTO = new AdvertisingIncomingDTO();
        incomingDTO.setInfoText("Sample Info Text");

        List<SitePage> sitePageList = new ArrayList<>();
        // You should create a valid SitePage object according to your data structure
        //sitePageList.add(new SitePage());

        incomingDTO.setSitePageList(sitePageList);

        // Mapping
        Advertising advertising = mapper.map(incomingDTO);

        // Checks
        assertNotNull(advertising);
        assertEquals(incomingDTO.getInfoText(), advertising.getInfoText());
        assertNotNull(advertising.getSitePageList());
        // Perform other checks as necessary
    }}
