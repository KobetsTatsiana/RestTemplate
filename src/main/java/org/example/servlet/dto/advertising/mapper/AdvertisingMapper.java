package org.example.servlet.dto.advertising.mapper;

import org.example.model.Advertising;
import org.example.model.SitePage;
import org.example.servlet.dto.advertising.AdvertisingIncomingDTO;
import org.example.servlet.dto.advertising.AdvertisingAllOutGoingDTO;
import org.example.servlet.dto.advertising.AdvertisingOutGoingDTO;
import org.example.servlet.dto.advertising.AdvertisingUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface AdvertisingMapper {

    AdvertisingMapper INSTANCE = Mappers.getMapper(AdvertisingMapper.class);

    AdvertisingOutGoingDTO map(Advertising advertising);

    Advertising map(AdvertisingUpdateDTO advertisingUpdateDTO);

    default AdvertisingAllOutGoingDTO mapListToDto(List<Advertising> advertisingList) {
        AdvertisingAllOutGoingDTO advertisingAllOutGoingDTO = new AdvertisingAllOutGoingDTO();
        advertisingAllOutGoingDTO.setAdvertisingList(advertisingList);
        return advertisingAllOutGoingDTO;
    }

    default Advertising map(AdvertisingIncomingDTO advertisingIncomingDTO) {
        Advertising advertising = new Advertising();
        advertising.setInfoText(advertisingIncomingDTO.getInfoText());
        List<SitePage> sitePageList = new ArrayList<>();
        for (SitePage sitePage : advertisingIncomingDTO.getSitePageList()) {
            sitePageList.add(sitePage);
        }
        advertising.setSitePageList(sitePageList);
        return advertising;
    }
}
