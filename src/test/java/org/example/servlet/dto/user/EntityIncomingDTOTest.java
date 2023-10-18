package org.example.servlet.dto.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntityIncomingDTOTest {
    private EntityIncomingDTO entityIncomingDTO;

    @BeforeEach
    void setUp() {
        entityIncomingDTO = new EntityIncomingDTO();
    }

    @Test
    void getName() {
        String expectedName = "Test Name";
        entityIncomingDTO.setName(expectedName);
        assertEquals(expectedName, entityIncomingDTO.getName());
    }

    @Test
    void setName() {
        String setName = "Set Name";
        entityIncomingDTO.setName(setName);
        assertSame(setName, entityIncomingDTO.getName());
    }

    @Test
    void getSurname() {
        String expectedSurname = "Test Surname";
        entityIncomingDTO.setSurname(expectedSurname);
        assertEquals(expectedSurname, entityIncomingDTO.getSurname());
    }

    @Test
    void setSurname() {
        String setSurname = "Set Surname";
        entityIncomingDTO.setSurname(setSurname);
        assertSame(setSurname, entityIncomingDTO.getSurname());
    }
}
