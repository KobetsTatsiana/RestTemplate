package org.example.servlet.dto.site.mapper;

import org.example.model.UserSite;
import org.example.servlet.dto.site.SiteAllOutGoingDTO;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SiteDtoMapper {

    SiteDtoMapper INSTANCE = Mappers.getMapper(SiteDtoMapper.class);

    default SiteAllOutGoingDTO mapListToDto(List<UserSite> userSiteList) {
        SiteAllOutGoingDTO siteAllOutGoingDTO = new SiteAllOutGoingDTO();
        siteAllOutGoingDTO.setUserSiteList(userSiteList);
        return siteAllOutGoingDTO;
    }
}
