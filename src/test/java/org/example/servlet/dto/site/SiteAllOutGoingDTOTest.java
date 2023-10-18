package org.example.servlet.dto.site;

import org.example.model.UserSite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SiteAllOutGoingDTOTest {
    private SiteAllOutGoingDTO dto;

    @BeforeEach
    public void setup() {
        dto = new SiteAllOutGoingDTO();
    }

    @Test
    public void testGetUserSiteList_default() {

        List<UserSite> result = dto.getUserSiteList();

        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetSetUserSiteList() {

        List<UserSite> userSites = new ArrayList<>();
        userSites.add(new UserSite());
        userSites.add(new UserSite());

        dto.setUserSiteList(userSites);
        List<UserSite> result = dto.getUserSiteList();

        assertEquals(userSites, result);
    }
}

