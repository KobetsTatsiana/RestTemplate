package org.example.servlet.dto.advertising;

import org.example.model.SitePage;

import java.util.List;

public class AdvertisingIncomingDTO {

    private String infoText;
    private List<SitePage> sitePageList;

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
}
