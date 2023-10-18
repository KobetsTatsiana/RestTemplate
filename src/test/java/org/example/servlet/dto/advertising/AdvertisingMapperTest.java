package org.example.servlet.dto.advertising;

import org.example.model.Advertising;
import org.example.model.SitePage;
import org.example.servlet.dto.advertising.AdvertisingIncomingDTO;
import org.example.servlet.dto.advertising.AdvertisingAllOutGoingDTO;
import org.example.servlet.dto.advertising.mapper.AdvertisingMapper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AdvertisingMapperTest {

    @Test
    public void mapListToDto() {
        AdvertisingMapper mapper = AdvertisingMapper.INSTANCE;

        List<Advertising> advertisingList = new ArrayList<>();
        advertisingList.add(new Advertising()); // Initialize as per your project
        advertisingList.add(new Advertising()); // Initialize as per your project

        AdvertisingAllOutGoingDTO containsList = mapper.mapListToDto(advertisingList);

        assertNotNull(containsList);
        assertNotNull(containsList.getAdvertisingList());
        assertEquals(2, containsList.getAdvertisingList().size());
        assertEquals(advertisingList, containsList.getAdvertisingList());
    }

    @Test
    public void mapAdvertisingIncomingDTO() {
        AdvertisingMapper mapper = AdvertisingMapper.INSTANCE;

        AdvertisingIncomingDTO incomingDTO = new AdvertisingIncomingDTO();

        List<SitePage> sitePageList = new ArrayList<>();


        incomingDTO.setSitePageList(sitePageList);

        Advertising advertising = mapper.map(incomingDTO);

        assertNotNull(advertising);

    }
}
