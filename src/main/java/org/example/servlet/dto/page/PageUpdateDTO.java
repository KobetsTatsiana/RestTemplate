package org.example.servlet.dto.page;

import org.example.model.Advertising;

import java.util.List;

public class PageUpdateDTO {

    private long id;
    private String namePage;
    private List<Advertising> advertisingList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNamePage() {
        return namePage;
    }

    public void setNamePage(String namePage) {
        this.namePage = namePage;
    }

    public List<Advertising> getAdvertisingList() {
        return advertisingList;
    }

    public void setAdvertisingList(List<Advertising> advertisingList) {
        this.advertisingList = advertisingList;
    }
}
