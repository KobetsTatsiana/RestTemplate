package org.example.servlet.dto.site;

import org.example.model.SitePage;

import java.util.ArrayList;
import java.util.List;

public class SiteIncomingDTO {

    private String nameSite;
    private List<SitePage> sitePageList;

    public String getNameSite() {
        return nameSite;
    }

    public void setNameSite(String nameSite) {
        this.nameSite = nameSite;
    }

    public List<SitePage> getSitePageList() {
        if (this.sitePageList == null) {
            this.sitePageList = new ArrayList<>();
        }
        return this.sitePageList;
    }

    public void setSitePageList(List<SitePage> sitePageList) {
        this.sitePageList = sitePageList;
    }
}
