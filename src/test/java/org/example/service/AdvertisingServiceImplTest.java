package org.example.service;

import org.example.model.Advertising;
import org.example.repository.Repository;
import org.example.repository.impl.AdvertisingRepositoryImpl;
import org.example.service.impl.AdvertisingServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class AdvertisingServiceImplTest {

    @Mock
    private AdvertisingRepositoryImpl repository;

    @InjectMocks
    private AdvertisingServiceImpl advertisingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveTagEntitySuccessfully() throws SQLException {
        Advertising advertising = new Advertising();

        when(repository.save(advertising)).thenReturn(Optional.of(advertising));

        Optional<Advertising> savedEntity = advertisingService.save(advertising);

        assertTrue(savedEntity.isPresent());
        assertEquals(advertising, savedEntity.get());
    }

    @Test
    void testSaveAdvertisingWithSQLException() throws SQLException {
        Advertising advertising = new Advertising();

        doThrow(new SQLException("Simulated SQL Exception")).when(repository).save(advertising);

        assertThrows(SQLException.class, () -> advertisingService.save(advertising));
    }

    @Test
    void testFindById() throws SQLException {
        Long id = 1L;
        Advertising advertising = new Advertising();
        advertising.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(advertising));

        Optional<Advertising> foundEntity = advertisingService.findById(id);

        assertTrue(foundEntity.isPresent());
        assertEquals(advertising, foundEntity.get());
    }

    @Test
    void testFindAllSuccessfully() throws SQLException {
        Advertising advertising = new Advertising();

        when(repository.findAll()).thenReturn(Collections.singletonList(advertising));

        List<Advertising> allEntities = advertisingService.findAll();

        assertFalse(allEntities.isEmpty());
        assertEquals(1, allEntities.size());
        assertEquals(advertising, allEntities.get(0));
    }

    @Test
    void testFindAllWithSQLException() throws SQLException {
        when(repository.findAll()).thenThrow(new SQLException("Simulated SQL Exception"));

        assertThrows(SQLException.class, () -> advertisingService.findAll());
    }

    @Test
    void testDeleteAdvertisingEntitySuccessfully() throws SQLException {
        Long id = 1L;

        when(repository.deleteById(id)).thenReturn(true);

        boolean deleted = advertisingService.delete(id);

        assertTrue(deleted);
    }

    @Test
    void testDeleteAdvertisingNotFound() throws SQLException {
        Long id = 1L;

        when(repository.deleteById(id)).thenReturn(false);

        boolean deleted = advertisingService.delete(id);

        assertFalse(deleted);
    }

    @Test
    void testDeleteAdvertisingWithException() throws SQLException {
        Long id = 1L;

        when(repository.deleteById(id)).thenThrow(new RuntimeException("Simulated Exception"));

        assertThrows(RuntimeException.class, () -> advertisingService.delete(id));
    }

    @Test
    void testGetRepository() {
        Repository<Advertising, Long> serviceRepository = advertisingService.getRepository();

        assertNotNull(serviceRepository);
        assertEquals(repository, serviceRepository);
    }
}
