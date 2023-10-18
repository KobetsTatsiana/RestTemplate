package org.example.repository.mapper;

import org.example.model.Advertising;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AdvertisingResultSetMapperTest {
    @Test
    public void testMap() throws SQLException {
        ResultSet resultSetMock = mock(ResultSet.class);
        when(resultSetMock.getLong("id")).thenReturn(12345L);
        when(resultSetMock.getString("infoText")).thenReturn("Test info text");

        when(resultSetMock.wasNull()).thenReturn(false);

        AdvertisingResultSetMapper mapper = AdvertisingResultSetMapper.INSTANCE;
        Advertising advertising = mapper.map(resultSetMock);

        assertEquals(12345L, advertising.getId());
        assertEquals("Test info text", advertising.getInfoText());
    }
}
