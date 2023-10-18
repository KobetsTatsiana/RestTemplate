package org.example.servlet.dto.user;

import org.example.model.UserEntity;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EntityAllOutGoingDTOTest {

    @Test
    public void testUserEntity() {
        EntityAllOutGoingDTO dto = new EntityAllOutGoingDTO();
        UserEntity user = new UserEntity();
        user.setId(1);
        List<UserEntity> users = Arrays.asList(user);

        dto.setUserEntityList(users);
        assertEquals(users, dto.getUserEntityList());
    }
}
