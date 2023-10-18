package org.example.service;


import org.example.model.SitePage;
import org.example.repository.Repository;
import org.example.service.impl.SitePageServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
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

class PageServiceImplTest {

    @Mock
    private Repository<SitePage, Long> repository;

    @InjectMocks
    private SitePageServiceImpl sitePageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveSitePageSuccessfully() throws SQLException {
        SitePage sitePage = new SitePage();
        when(repository.save(sitePage)).thenReturn(Optional.of(sitePage));
        Optional<SitePage> savedEntity = sitePageService.save(sitePage);
        assertTrue(savedEntity.isPresent());
        assertEquals(sitePage, savedEntity.get());
    }

    @Test
    void testSaveSitePageWithSQLException() throws SQLException {
        SitePage sitePage = new SitePage();
        doThrow(new SQLException("Simulated SQL Exception")).when(repository).save(sitePage);
        assertThrows(SQLException.class, () -> sitePageService.save(sitePage));
    }

    @Test
    void testFindById() throws SQLException {
        Long id = 1L;
        SitePage sitePage = new SitePage();
        sitePage.setId(id);
        when(repository.findById(id)).thenReturn(Optional.of(sitePage));
        Optional<SitePage> foundEntity = sitePageService.findById(id);
        assertTrue(foundEntity.isPresent());
        assertEquals(sitePage, foundEntity.get());
    }

    @Test
    void testFindAllSuccessfully() throws SQLException {
        SitePage sitePage = new SitePage();
        when(repository.findAll()).thenReturn(Collections.singletonList(sitePage));
        List<SitePage> allEntities = sitePageService.findAll();
        assertFalse(allEntities.isEmpty());
        assertEquals(1, allEntities.size());
        assertEquals(sitePage, allEntities.get(0));
    }

    @Test
    void testFindAllWithSQLException() throws SQLException {
        when(repository.findAll()).thenThrow(new SQLException("Simulated SQL Exception"));
        assertThrows(SQLException.class, () -> sitePageService.findAll());
    }

    @Test
    void testDeleteSitePageSuccessfully() throws SQLException {
        Long id = 1L;
        when(repository.deleteById(id)).thenReturn(true);
        boolean deleted = sitePageService.delete(id);
        assertTrue(deleted);
    }

    @Test
    void testDeleteSitePageNotFound() throws SQLException {
        Long id = 1L;
        when(repository.deleteById(id)).thenReturn(false);
        boolean deleted = sitePageService.delete(id);
        assertFalse(deleted);
    }

    @Test
    void testDeleteSitePageWithException() throws SQLException {
        Long id = 1L;
        doThrow(new RuntimeException("Simulated Exception")).when(repository).deleteById(id);
        assertThrows(RuntimeException.class, () -> sitePageService.delete(id));
    }

    @Test
    void testGetRepository() {
        Repository<SitePage, Long> serviceRepository = sitePageService.getRepository();
        assertNotNull(serviceRepository);
        assertEquals(repository, serviceRepository);
    }
}
