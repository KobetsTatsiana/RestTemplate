package org.example.servlet.dto.site;

import org.example.model.SitePage;

import java.util.List;

public class SiteDto {

    private long id;
    private String nameSite;

    private List<SitePage> sitePageList;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNameSite() {
        return nameSite;
    }

    public void setNameSite(String nameSite) {
        this.nameSite = nameSite;
    }

    public List<SitePage> getSitePageList() {
        return sitePageList;
    }

    public void setSitePageList(List<SitePage> sitePageList) {
        this.sitePageList = sitePageList;
    }
}
