package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SitePage {
    private Long id;
    private String namePage;
    private List<Advertising> advertisingList;

    public SitePage() {
        advertisingList = new ArrayList<>();
    }

    public SitePage(Long id,  String namePage) {
        this.id = id;
        this.namePage = namePage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SitePage sitePage = (SitePage) o;
        return Objects.equals(id, sitePage.id) && Objects.equals(namePage, sitePage.namePage) && Objects.equals(advertisingList, sitePage.advertisingList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, namePage, advertisingList);
    }

    @Override
    public String toString() {
        return "SitePage{" +
                "id=" + id +
                ", namePage='" + namePage + '\'' +
                ", advertisingList=" + advertisingList +
                '}';
    }
}
