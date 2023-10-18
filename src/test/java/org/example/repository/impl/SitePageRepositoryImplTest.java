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

@Testcontainers
public class SitePageRepositoryImplTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13.1")
            .withDatabaseName("test-db")
            .withUsername("test")
            .withPassword("test")
            .withInitScript("mydb.sql");

    private SitePageRepositoryImpl repository;
    private AdvertisingRepositoryImpl advertisingRepository;

    @BeforeEach
    void setUp() {
        ConnectionManager testConnectionManager = new ConnectionManager() {
            @Override
            public Connection getConnection() throws SQLException {
                return postgreSQLContainer.createConnection("");
            }
        };
        advertisingRepository = new AdvertisingRepositoryImpl(testConnectionManager);
        repository = new SitePageRepositoryImpl(testConnectionManager, advertisingRepository);
    }

    @AfterEach
    void tearDown() throws SQLException {
        repository.clearAll();
    }

    @Test
    void testFindById() throws SQLException {
        SitePage entity = new SitePage();
        entity.setId(2L);
        entity.setNamePage("Test NamePage");

        Advertising advertising = new Advertising();
        advertising.setId(2L);
        advertising.setInfoText("Test InfoText");
        entity.getAdvertisingList().add(advertising);
        repository.save(entity);

        SitePage foundEntity = repository.findById(entity.getId()).orElse(null);
        Assertions.assertNotNull(foundEntity);
        Assertions.assertEquals(entity.getId(), foundEntity.getId());
        Assertions.assertEquals(entity.getNamePage(), foundEntity.getNamePage());
        Assertions.assertEquals(1, foundEntity.getAdvertisingList().size());
    }

    @Test
    void testFindAll() throws SQLException {
        SitePage entity = new SitePage();
        entity.setId(3L);
        entity.setNamePage("Test NamePage");
        Advertising advertising = new Advertising();
        advertising.setId(3L);
        advertising.setInfoText("Test InfoText");
        entity.getAdvertisingList().add(advertising);
        SitePage savedEntity = repository.save(entity).get();

        List<SitePage> sitePageList = repository.findAll();
        Assertions.assertEquals(1, sitePageList.size());
    }

    @Test
    void testDeleteById() throws SQLException {
        SitePage entity = new SitePage();
        entity.setId(3L);
        entity.setNamePage("Test NamePage");
        Advertising advertising = new Advertising();
        advertising.setId(3L);
        advertising.setInfoText("Test InfoText");
        entity.getAdvertisingList().add(advertising);
        SitePage savedEntity = repository.save(entity).get();

        boolean isDeleted = repository.deleteById(entity.getId());
        Assertions.assertTrue(isDeleted);

        SitePage deletedEntity = repository.findById(entity.getId()).orElse(null);
        Assertions.assertNull(deletedEntity);
    }

    @Test
    void testSaveAndGetID() throws SQLException {
        SitePage entity = new SitePage();
        entity.setId(3L);
        entity.setNamePage("Test NamePage");
        Advertising advertising = new Advertising();
        advertising.setId(3L);
        advertising.setInfoText("Test InfoText");
        entity.getAdvertisingList().add(advertising);
        SitePage savedEntity = repository.save(entity).get();
        Assertions.assertNotNull(savedEntity);
        Assertions.assertEquals(entity.getNamePage(), savedEntity.getNamePage());
        Assertions.assertEquals(1, savedEntity.getAdvertisingList().size());


        Long idSavedEntity = entity.getId();
        savedEntity = repository.findById(idSavedEntity).get();
        assert savedEntity != null;
        Assertions.assertEquals(entity.getNamePage(), savedEntity.getNamePage());
        Assertions.assertEquals(entity.getId(), savedEntity.getId());
    }

    @Test
    void testUpdate() throws SQLException {
        SitePage entity = new SitePage();
        entity.setId(3L);
        entity.setNamePage("Test NamePage");
        Advertising advertising = new Advertising();
        advertising.setId(3L);
        advertising.setInfoText("Test InfoText");
        entity.getAdvertisingList().add(advertising);
        SitePage savedEntity = repository.save(entity).get();
        Assertions.assertNotNull(savedEntity);
        Assertions.assertEquals(entity.getNamePage(), savedEntity.getNamePage());
        Assertions.assertEquals(1, savedEntity.getAdvertisingList().size());

        Long idSavedEntity = entity.getId();
        savedEntity = repository.findById(idSavedEntity).get();
        assert savedEntity != null;
        Assertions.assertEquals(entity.getNamePage(), savedEntity.getNamePage());
        Assertions.assertEquals(entity.getId(), savedEntity.getId());

        String updatedNamePage = "Updated NamePage";
        entity.setNamePage(updatedNamePage);
        savedEntity = repository.save(entity).get();
        Assertions.assertNotNull(savedEntity);
        Assertions.assertEquals(entity.getNamePage(), savedEntity.getNamePage());

        String updatedAdvertisingInfoText = "Updated InfoText";
        advertising.setInfoText(updatedAdvertisingInfoText);
        savedEntity = repository.save(entity).get();
        Assertions.assertNotNull(savedEntity);
        Assertions.assertEquals(1, savedEntity.getAdvertisingList().size());
        Advertising savedAdvertising = savedEntity.getAdvertisingList().get(0);
        Assertions.assertNotNull(savedAdvertising);
        Assertions.assertEquals(savedAdvertising.getInfoText(), advertising.getInfoText());
    }

    @Test
    void clearAll() throws SQLException {
        SitePage entity = new SitePage();
        entity.setId(3L);
        entity.setNamePage("Test NamePage");
        Advertising advertising = new Advertising();
        advertising.setId(3L);
        advertising.setInfoText("Test InfoText");
        entity.getAdvertisingList().add(advertising);
        SitePage savedEntity = repository.save(entity).get();

        List<SitePage> sitePageList = repository.findAll();
        Assertions.assertEquals(1, sitePageList.size());

        repository.clearAll();

        Assertions.assertEquals(0, repository.findAll().size());
    }
}