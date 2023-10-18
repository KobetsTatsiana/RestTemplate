package org.example.repository.mapper;

import org.example.model.SitePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

    @ExtendWith(MockitoExtension.class)
    public class SitePageResultSetMapperTest {
        @Mock
        private ResultSet resultSet;

        private SitePageResultSetMapper sitePageResultSetMapper;

        @BeforeEach
        public void setup() {
            sitePageResultSetMapper = SitePageResultSetMapper.INSTANCE;
        }

        @Test
        public void shouldMapResultSetToSitePage() throws SQLException {
            when(resultSet.getLong("id")).thenReturn(1L);
            when(resultSet.getString("namepage")).thenReturn("testNamePage");

            SitePage sitePage = sitePageResultSetMapper.map(resultSet);

            assertEquals(1L, sitePage.getId());
            assertEquals("testNamePage", sitePage.getNamePage());

        }
    }

