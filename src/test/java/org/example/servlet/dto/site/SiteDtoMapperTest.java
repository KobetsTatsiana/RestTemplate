package org.example.servlet.dto.site;

import org.example.model.UserSite;
import org.example.servlet.dto.site.mapper.SiteDtoMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class SiteDtoMapperTest {

    @InjectMocks
    private SiteDtoMapper mapper = SiteDtoMapper.INSTANCE;

    @Test
    public void shouldMapUserSiteListToSiteAllOutGoingDTO() {

        List<UserSite> userSiteList = new ArrayList<>();
        UserSite userSite = Mockito.mock(UserSite.class);
        userSiteList.add(userSite);


        SiteAllOutGoingDTO dto = mapper.mapListToDto(userSiteList);

        Assertions.assertEquals(userSiteList, dto.getUserSiteList());
    }
}
