package org.example.servlet.dto.user.mapper;

import org.example.model.UserEntity;
import org.example.servlet.dto.user.EntityAllOutGoingDTO;
import org.example.servlet.dto.user.EntityIncomingDTO;
import org.example.servlet.dto.user.EntityOutGoingAndUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserDtoMapper {

    UserDtoMapper INSTANCE = Mappers.getMapper(UserDtoMapper.class);

    UserEntity map(EntityIncomingDTO entityIncomingDTO);

    EntityOutGoingAndUpdateDTO map(UserEntity userEntity);

    UserEntity map(EntityOutGoingAndUpdateDTO entityOutGoingAndUpdateDTO);

    default EntityAllOutGoingDTO mapListToDto(List<UserEntity> userEntityList) {
        EntityAllOutGoingDTO entityAllOutGoingDTO = new EntityAllOutGoingDTO();
        entityAllOutGoingDTO.setUserEntityList(userEntityList);
        return entityAllOutGoingDTO;
    }
}