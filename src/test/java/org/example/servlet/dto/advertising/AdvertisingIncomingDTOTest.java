package org.example.servlet.dto.advertising;

import org.example.model.SitePage;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdvertisingIncomingDTOTest {

    @Test
    public void testGettersAndSetters() {
        // Создаем экземпляр DTO
        AdvertisingIncomingDTO dto = new AdvertisingIncomingDTO();

        // Набор тестовых данных
        String infoTextTest = "Test info";
        List<SitePage> sitePageListTest = new ArrayList<>(); // добавьте SitePage объекты при необходимости

        // Присваиваем данные через сеттеры
        dto.setInfoText(infoTextTest);
        dto.setSitePageList(sitePageListTest);

        // Проверяем, что геттеры возвращают правильные значения
        assertEquals(infoTextTest, dto.getInfoText());
        assertEquals(sitePageListTest, dto.getSitePageList());
    }
}
