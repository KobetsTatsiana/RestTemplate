package org.example.servlet.dto.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityOutGoingAndUpdateDTOTest {

    private EntityOutGoingAndUpdateDTO entityOutGoingAndUpdateDTO;

    @BeforeEach
    public void setup() {
        entityOutGoingAndUpdateDTO = new EntityOutGoingAndUpdateDTO();
    }

    @Test
    public void testId() {
        long expectedId = 123L;
        entityOutGoingAndUpdateDTO.setId(expectedId);
        assertEquals(expectedId, entityOutGoingAndUpdateDTO.getId());
    }

    @Test
    public void testName() {
        String expectedName = "John";
        entityOutGoingAndUpdateDTO.setName(expectedName);
        assertEquals(expectedName, entityOutGoingAndUpdateDTO.getName());
    }

    @Test
    public void testSurname() {
        String expectedSurname = "Doe";
        entityOutGoingAndUpdateDTO.setSurname(expectedSurname);
        assertEquals(expectedSurname, entityOutGoingAndUpdateDTO.getSurname());
    }
}
