package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Advertising {
    private long id;
    private String infoText;
    private List<SitePage> sitePageList;

    public Advertising() {
        sitePageList = new ArrayList<>();
    }

    public Advertising(long id, String infoText, List<SitePage> sitePageList) {
        this.id = id;
        this.infoText = infoText;
        this.sitePageList = sitePageList;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInfoText() {
        return infoText;
    }

    public void setInfoText(String infoText) {
        this.infoText = infoText;
    }

    public List<SitePage> getSitePageList() {
        return sitePageList;
    }

    public void setSitePageList(List<SitePage> sitePageList) {
        this.sitePageList = sitePageList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Advertising that = (Advertising) o;
        return id == that.id && Objects.equals(infoText, that.infoText) && Objects.equals(sitePageList, that.sitePageList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, infoText, sitePageList);
    }

    @Override
    public String toString() {
        return "Advertising{" +
                "id=" + id +
                ", infoText='" + infoText + '\'' +
                ", sitePagesList=" + sitePageList +
                '}';
    }
}
