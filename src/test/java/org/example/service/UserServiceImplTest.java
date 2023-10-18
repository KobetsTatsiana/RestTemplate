package org.example.service;

import org.example.model.UserEntity;
import org.example.model.UserSite;
import org.example.repository.UserRepository;
import org.example.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @Mock
    private UserRepository<UserEntity, Long> repository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUserSuccessfully() throws SQLException {
        UserEntity userEntity = new UserEntity();

        when(repository.save(userEntity)).thenReturn(Optional.of(userEntity));

        Optional<UserEntity> savedEntity = userService.save(userEntity);

        assertTrue(savedEntity.isPresent());
        assertEquals(userEntity, savedEntity.get());
    }

    @Test
    void testFindById() throws SQLException {
        Long id = 1L;
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(userEntity));

        Optional<UserEntity> foundEntity = userService.findById(id);

        assertTrue(foundEntity.isPresent());
        assertEquals(userEntity, foundEntity.get());
    }

    @Test
    void testFindAllSuccessfully() throws SQLException {
        UserEntity userEntity = new UserEntity();

        when(repository.findAll()).thenReturn(Collections.singletonList(userEntity));

        List<UserEntity> allEntities = userService.findAll();

        assertFalse(allEntities.isEmpty());
        assertEquals(1, allEntities.size());
        assertEquals(userEntity, allEntities.get(0));
    }

    @Test
    void testDeleteUserSuccessfully() throws SQLException {
        Long id = 1L;

        when(repository.deleteById(id)).thenReturn(true);

        boolean deleted = userService.delete(id);

        assertTrue(deleted);
    }

    @Test
    void testDeleteUserNotFound() throws SQLException {
        Long id = 1L;

        when(repository.deleteById(id)).thenReturn(false);

        boolean deleted = userService.delete(id);

        assertFalse(deleted);
    }

    @Test
    void testGetRepository() {
        UserRepository<UserEntity, Long> serviceRepository = userService.getRepository();

        assertNotNull(serviceRepository);
        assertEquals(repository, serviceRepository);
    }

    @Test
    void testFindSitesAll() throws SQLException {
        List<UserSite> userSiteList = Collections.singletonList(new UserSite());

        when(repository.findSitesAll()).thenReturn(userSiteList);

        List<UserSite> foundSites = userService.findSitesAll();

        assertNotNull(foundSites);
        assertEquals(userSiteList, foundSites);
    }

    @Test
    void testSaveUserWithSQLException() throws SQLException {
        UserEntity userEntity = new UserEntity();

        doThrow(new SQLException("Simulated SQL Exception")).when(repository).save(userEntity);

        assertThrows(SQLException.class, () -> userService.save(userEntity));
    }

    @Test
    void testFindAllWithSQLException() throws SQLException {
        when(repository.findAll()).thenThrow(new SQLException("Simulated SQL Exception"));

        assertThrows(SQLException.class, () -> userService.findAll());
    }

    @Test
    void testDeleteUserWithException() throws SQLException {
        Long id = 1L;

        when(repository.deleteById(id)).thenThrow(new RuntimeException("Simulated Exception"));

        assertThrows(RuntimeException.class, () -> userService.delete(id));
    }
}
