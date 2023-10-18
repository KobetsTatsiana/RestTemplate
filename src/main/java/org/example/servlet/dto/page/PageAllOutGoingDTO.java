package org.example.servlet.dto.page;

import org.example.model.SitePage;

import java.util.List;

public class PageAllOutGoingDTO {

    List<SitePage> sitePageList;

    public List<SitePage> getSitePageList() {
        return sitePageList;
    }

    public void setSitePageList(List<SitePage> sitePageList) {
        this.sitePageList = sitePageList;
    }
}
