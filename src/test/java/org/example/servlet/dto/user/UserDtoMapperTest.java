package org.example.servlet.dto.user;

import org.example.model.UserEntity;
import org.example.servlet.dto.user.mapper.UserDtoMapper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDtoMapperTest {

    private final UserDtoMapper mapper = UserDtoMapper.INSTANCE;

    @Test
    void map_incoming() {
        EntityIncomingDTO dto = new EntityIncomingDTO();
        dto.setName("Test");
        dto.setSurname("User");

        UserEntity entity = mapper.map(dto);

        assertEquals("Test", entity.getName());
        assertEquals("User", entity.getSurname());
    }

    @Test
    void map_outgoing() {
        UserEntity entity = new UserEntity();
        entity.setId(1L);
        entity.setName("Test");
        entity.setSurname("User");

        EntityOutGoingAndUpdateDTO dto = mapper.map(entity);

        assertEquals(1L, dto.getId());
        assertEquals("Test", dto.getName());
        assertEquals("User", dto.getSurname());
    }

    @Test
    void map_listToDto() {
        UserEntity entity1 = new UserEntity();
        entity1.setName("User1");
        UserEntity entity2 = new UserEntity();
        entity2.setName("User2");

        List<UserEntity> list = Arrays.asList(entity1, entity2);

        EntityAllOutGoingDTO dto = mapper.mapListToDto(list);

        assertEquals(2, dto.getUserEntityList().size());
        assertEquals("User1", dto.getUserEntityList().get(0).getName());
        assertEquals("User2", dto.getUserEntityList().get(1).getName());
    }
}
