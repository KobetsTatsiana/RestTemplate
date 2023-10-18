package org.example.servlet.dto.advertising.mapper;

import org.example.model.Advertising;
import org.example.model.SitePage;
import org.example.servlet.dto.advertising.AdvertisingIncomingDTO;
import org.example.servlet.dto.advertising.AdvertisingAllOutGoingDTO;
import org.example.servlet.dto.advertising.AdvertisingDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface AdvertisingDtoMapper {
    AdvertisingDtoMapper INSTANCE = Mappers.getMapper( AdvertisingDtoMapper.class );

    AdvertisingDto map(Advertising advertising);

    Advertising map(AdvertisingDto advertisingDto);

    default AdvertisingAllOutGoingDTO mapListToDto(List<Advertising> advertisingList) {
        AdvertisingAllOutGoingDTO advertisingAllOutGoingDTO = new AdvertisingAllOutGoingDTO();
        advertisingAllOutGoingDTO.setAdvertisingList( advertisingList );
        return advertisingAllOutGoingDTO;
    }

    default Advertising map(AdvertisingIncomingDTO advertisingIncomingDTO) {
        Advertising advertising = new Advertising();
        advertising.setInfoText( advertisingIncomingDTO.getInfoText());
        List<SitePage> sitePageList = new ArrayList<>();
        for (SitePage sitePage : advertisingIncomingDTO.getSitePageList()) {
            sitePageList.add( sitePage);
        }
        advertising.setSitePageList( sitePageList );
        return advertising;
    }
}