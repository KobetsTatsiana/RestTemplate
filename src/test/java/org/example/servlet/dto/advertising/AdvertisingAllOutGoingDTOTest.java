package org.example.servlet.dto.advertising;

import org.example.model.Advertising;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdvertisingAllOutGoingDTOTest {

    @Test
     void testGetAndSetAdvertisingList() {

        List<Advertising> advertisingList = new ArrayList<>();
        Advertising advertising = new Advertising();
        advertisingList.add(advertising);

        AdvertisingAllOutGoingDTO advertisingAllOutGoingDTO = new AdvertisingAllOutGoingDTO();
        advertisingAllOutGoingDTO.setAdvertisingList(advertisingList);

        assertEquals(advertisingList, advertisingAllOutGoingDTO.getAdvertisingList());
    }
}
