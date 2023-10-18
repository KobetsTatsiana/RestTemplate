package org.example.repository.mapper;

import org.example.model.UserEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class UserResultSetMapperTest {

    @Test
    void testMap() throws SQLException {
        ResultSet mockResultSet = Mockito.mock( ResultSet.class );
        long expectedId = 1;
        String expectedName = "Ivan";
        String expectedSurname = "Ivanov";
        String expectedAddress = "Ivanovo";

        when(mockResultSet.getLong("id")).thenReturn(expectedId);
        when(mockResultSet.getString("name")).thenReturn(expectedName);
        when(mockResultSet.getString("surname")).thenReturn(expectedSurname);
        when(mockResultSet.getString("address")).thenReturn(expectedAddress);
        UserResultSetMapper mapper = Mappers.getMapper( UserResultSetMapper.class );


        UserEntity userEntity = mapper.map( mockResultSet );


        assertEquals( expectedId, userEntity.getId() );
        assertEquals( expectedName, userEntity.getName() );
        assertEquals( expectedSurname, userEntity.getSurname() );
        assertEquals( expectedAddress, userEntity.getAddress() );
    }
}
