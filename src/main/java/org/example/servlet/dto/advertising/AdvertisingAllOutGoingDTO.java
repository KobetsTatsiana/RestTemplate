package org.example.servlet.dto.advertising;

import org.example.model.Advertising;

import java.util.List;

public class AdvertisingAllOutGoingDTO {

    List<Advertising> advertisingList;

    public List<Advertising> getAdvertisingList() {
        return advertisingList;
    }

    public void setAdvertisingList(List<Advertising> advertisingList) {
        this.advertisingList = advertisingList;
    }
}
