package org.example.repository.mapper;

import org.example.model.Advertising;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AdvertisingResultSetMapperTest {
/*
    @Test
    void testMap() throws SQLException {

        ResultSet mockResultSet = Mockito.mock( ResultSet.class );
        long exceptedId = 1;
        String expectedInfoText = "Expected infoText";

        when( mockResultSet.getObject( "id" ) ).thenReturn( exceptedId );
        when( mockResultSet.getString( "infoText" ) ).thenReturn( expectedInfoText );

        Advertising advertising = AdvertisingResultSetMapper.INSTANCE.map( mockResultSet );

        assertEquals( exceptedId, advertising.getId() );
        assertEquals( expectedInfoText, advertising.getInfoText() );
    }*/
@Test
public void testMap() throws SQLException {
    // Подготовка мок объектов
    ResultSet resultSetMock = mock(ResultSet.class);
    when(resultSetMock.getLong("id")).thenReturn(12345L);
    when(resultSetMock.getString("infoText")).thenReturn("Test info text");
    // Оставляем wasNull() всегда возврашать false для простоты теста
    when(resultSetMock.wasNull()).thenReturn(false);

    // Тестируемый блок
    AdvertisingResultSetMapper mapper = AdvertisingResultSetMapper.INSTANCE;
    Advertising advertising = mapper.map(resultSetMock);

    // Проверки
    assertEquals(12345L, advertising.getId());
    assertEquals("Test info text", advertising.getInfoText());
}

}
