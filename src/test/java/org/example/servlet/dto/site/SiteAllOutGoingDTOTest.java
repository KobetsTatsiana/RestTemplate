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
        // When
        List<UserSite> result = dto.getUserSiteList();

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetSetUserSiteList() {
        // Given
        List<UserSite> userSites = new ArrayList<>();
        userSites.add(new UserSite());
        userSites.add(new UserSite());

        // When
        dto.setUserSiteList(userSites);
        List<UserSite> result = dto.getUserSiteList();

        // Then
        assertEquals(userSites, result);
    }
}

