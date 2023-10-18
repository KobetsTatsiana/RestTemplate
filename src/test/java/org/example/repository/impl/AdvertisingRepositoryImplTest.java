package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.model.Advertising;
import org.example.model.SitePage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Testcontainers
class AdvertisingRepositoryImplTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13.1")
            .withDatabaseName("test-db")
            .withUsername("test")
            .withPassword("test")
            .withInitScript("mydb.sql");

    private AdvertisingRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        ConnectionManager testConnectionManager = new ConnectionManager() {
            @Override
            public Connection getConnection() throws SQLException {
                return postgreSQLContainer.createConnection("");
            }
        };
        repository = new AdvertisingRepositoryImpl(testConnectionManager);
    }

    @AfterEach
    void tearDown() throws SQLException {
        repository.clearAll();
    }

    @Test
    void testFindById() throws SQLException {
        Advertising entity = new Advertising();
        entity.setId(2L);
        entity.setInfoText("Test InfoText");
        SitePage sitePage = new SitePage();
        sitePage.setId(2L);
        sitePage.setNamePage("Test NamePage");
        entity.getSitePageList().add(sitePage);
        repository.save(entity);

        Advertising foundEntity = repository.findById(entity.getId()).orElse(null);
        Assertions.assertNotNull(foundEntity);
        assertEquals(entity.getId(), foundEntity.getId());
        assertEquals(entity.getInfoText(), foundEntity.getInfoText());
        assertEquals(1, foundEntity.getSitePageList().size());
    }

    @Test
    void testFindAll() throws SQLException {
        Advertising advertising1 = new Advertising();
        advertising1.setId(1L);
        repository.save(advertising1);
        repository.save(new Advertising());
        Advertising advertising2 = new Advertising();
        advertising2.setId(1L);
        repository.save(advertising2);

        List<Advertising> entities = repository.findAll();
        assertEquals(2, entities.size());
    }


    @Test
    void testDeleteById() throws SQLException {
        Advertising entity = new Advertising();
        entity.setInfoText("Test InfoText");
        SitePage sitePage = new SitePage();
        sitePage.setId(1L);
        sitePage.setNamePage("Test NamePage");

        entity.getSitePageList().add(sitePage);
        repository.save(entity);

        boolean isDeleted = repository.deleteById(entity.getId());
        Assertions.assertTrue(isDeleted);

        Advertising deletedEntity = repository.findById(entity.getId()).orElse(null);
        assertNull(deletedEntity);
    }


    @Test
    void testSave() throws SQLException {
        Advertising entity = new Advertising();
        entity.setInfoText("Test InfoText");
        SitePage sitePage = new SitePage();
        sitePage.setId(1L);
        sitePage.setNamePage("Test NamePage");
        entity.getSitePageList().add(sitePage);
        Advertising savedEntity = repository.save(entity).orElse(null);
        Assertions.assertNotNull(savedEntity);
        assertEquals(entity.getInfoText(), savedEntity.getInfoText());
        assertEquals(1, savedEntity.getSitePageList().size());

        long idSavedEntity = entity.getId();
        savedEntity = repository.findById(idSavedEntity).get();
        assert savedEntity != null;
        assertEquals(entity.getInfoText(), savedEntity.getInfoText());
        assertEquals(entity.getId(), savedEntity.getId());
    }

    @Test
    void clearAll() throws SQLException {
        Advertising advertising1 = new Advertising();
        advertising1.setId(1L);
        repository.save( advertising1 );

        Advertising advertising2 = new Advertising();
        advertising2.setId(2L);
        repository.save( advertising2 );


        List<Advertising> entities = repository.findAll();
        assertEquals( 2, entities.size() );

        repository.clearAll();

        assertEquals( 0, repository.findAll().size() );
    }}
