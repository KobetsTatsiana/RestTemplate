package org.example.servlet.dto.advertising;

import org.example.model.SitePage;

import java.util.List;

public class AdvertisingOutGoingDTO {

    private long id;
    private String infoText;
    private List<SitePage> sitePagesList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInfoText() {
        return infoText;
    }

    public void setInfoText(String infoText) {
        this.infoText = infoText;
    }

    public List<SitePage> getSitePagesList() {
        return sitePagesList;
    }

    public void setSitePagesList(List<SitePage> sitePagesList) {
        this.sitePagesList = sitePagesList;
    }
}
