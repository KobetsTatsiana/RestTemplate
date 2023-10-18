package org.example.servlet.dto.advertising;

import org.example.model.Advertising;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdvertisingAllOutGoingDTOTest {

    @Test
    public void getAndSetAdvertisingList() {
        // Prepare test data
        List<Advertising> advertisingList = new ArrayList<>();
        Advertising advertising = new Advertising(); // Use an appropriate constructor or setters for initialization
        advertisingList.add(advertising);

        // Test the setters and getters
        AdvertisingAllOutGoingDTO advertisingAllOutGoingDTO = new AdvertisingAllOutGoingDTO();
        advertisingAllOutGoingDTO.setAdvertisingList(advertisingList);

        // Verify the result
        assertEquals(advertisingList, advertisingAllOutGoingDTO.getAdvertisingList());
    }
}
