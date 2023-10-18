package org.example.repository.mapper;

import org.example.model.UserSite;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserSiteResultSetMapperTest {
    @Mock
    private ResultSet resultSet;

    private UserSiteResultSetMapper userSiteResultSetMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userSiteResultSetMapper = UserSiteResultSetMapper.INSTANCE;
    }

    @Test
    public void shouldMapResultSetToUserSite() throws SQLException {

        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("namesite")).thenReturn("testSite.com");

        UserSite userSite = userSiteResultSetMapper.map(resultSet);

        assertEquals(1L, userSite.getId());
        assertEquals("testSite.com", userSite.getNameSite());
    }
}
