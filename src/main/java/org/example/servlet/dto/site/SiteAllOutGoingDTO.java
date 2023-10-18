package org.example.servlet.dto.site;

import org.example.model.UserSite;

import java.util.ArrayList;
import java.util.List;

public class SiteAllOutGoingDTO {
    private List<UserSite> userSiteList;

    public List<UserSite> getUserSiteList() {
        if (this.userSiteList == null) {
            this.userSiteList = new ArrayList<>();
        }
        return this.userSiteList;
    }

    public void setUserSiteList(List<UserSite> userSiteList) {
        this.userSiteList = userSiteList;
    }
}

