package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.model.UserEntity;
import org.example.model.UserSite;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


@Testcontainers
class UserRepositoryImplTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>( "postgres:13.1" )
            .withDatabaseName( "test-db" )
            .withUsername( "test" )
            .withPassword( "test" )
            .withInitScript( "mydb.sql" );

    private UserRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        ConnectionManager testConnectionManager = new ConnectionManager() {
            @Override
            public Connection getConnection() throws SQLException {
                return postgreSQLContainer.createConnection( "" );
            }
        };
        repository = new UserRepositoryImpl( testConnectionManager );
    }

    @AfterEach
    void tearDown() throws SQLException {
        repository.clearAll();
    }

    @Test
    void testFindById() throws SQLException {
        UserEntity entity = new UserEntity(  );
        entity.setName( "Name" );
        entity.setSurname("Surname");
        entity.setAddress("Address");
        UserSite userSite = new UserSite();
        userSite.setNameSite( "NameSite" );
        entity.getUserSiteList().add( userSite );
        repository.save( entity );

        UserEntity foundEntity = repository.findById( entity.getId() ).orElse( null );
        Assertions.assertNotNull( foundEntity );
        Assertions.assertEquals( entity.getId(), foundEntity.getId() );
        Assertions.assertEquals( entity.getName(), foundEntity.getName() );
        Assertions.assertEquals( entity.getSurname(), foundEntity.getSurname() );
        Assertions.assertEquals( entity.getAddress(), foundEntity.getAddress() );
        Assertions.assertEquals( 1, foundEntity.getUserSiteList().size() );
    }

    @Test
    void testFindAll() throws SQLException {
        UserEntity user1 = new UserEntity();
        user1.setId(1);
        repository.save(user1);

        UserEntity user2 = new UserEntity();
        user2.setId(2);
        repository.save(user2);

        List<UserEntity> entities = repository.findAll();
        Assertions.assertEquals(2, entities.size());
    }

    @Test
    void testDeleteById() throws SQLException {
        UserEntity entity = new UserEntity(  );
        entity.setName( "Name" );
        entity.setSurname( "Surname" );
        entity.setAddress( "Address" );
        UserSite userSite = new UserSite();
        userSite.setNameSite( "Name Site" );
        entity.getUserSiteList().add( userSite );
        repository.save( entity );

        boolean isDeleted = repository.deleteById( entity.getId() );
        Assertions.assertTrue( isDeleted );

        UserEntity deletedEntity = repository.findById( entity.getId() ).orElse( null );
        Assertions.assertNull( deletedEntity );
    }

    @Test
    void testSave() throws SQLException {
        UserEntity entity = new UserEntity(  );
        entity.setName( "Name" );
        entity.setSurname( "Surname" );
        entity.setAddress( "Address" );
        UserSite userSite = new UserSite();
        userSite.setNameSite( "Name Site" );
        entity.getUserSiteList().add( userSite );
        UserEntity savedEntity = repository.save( entity ).orElse( null );
        Assertions.assertNotNull( savedEntity );
        Assertions.assertEquals( entity.getName(), savedEntity.getName() );
        Assertions.assertEquals( entity.getSurname(), savedEntity.getSurname() );
        Assertions.assertEquals( entity.getAddress(), savedEntity.getAddress() );
        Assertions.assertEquals( 1, savedEntity.getUserSiteList().size() );

        long idSavedEntity = entity.getId();
        savedEntity = repository.findById( idSavedEntity ).orElse( null );
        assert savedEntity != null;
        Assertions.assertEquals( entity.getName(), savedEntity.getName() );
        Assertions.assertEquals( entity.getSurname(), savedEntity.getSurname() );
        Assertions.assertEquals( entity.getAddress(), savedEntity.getAddress() );
        Assertions.assertEquals( entity.getId(), savedEntity.getId() );
    }

    @Test
    void testUpdate() throws SQLException {
        UserEntity entity = new UserEntity(  );
        entity.setName( "Name" );
        entity.setSurname( "Surname" );
        entity.setAddress( "Address" );
        UserSite userSite = new UserSite();
        userSite.setNameSite( "Name Site" );
        entity.getUserSiteList().add( userSite );
        UserEntity savedEntity = repository.save( entity ).orElse( null );
        Assertions.assertNotNull( savedEntity );
        Assertions.assertEquals( entity.getName(), savedEntity.getName() );
        Assertions.assertEquals( entity.getSurname(), savedEntity.getSurname() );
        Assertions.assertEquals( entity.getAddress(), savedEntity.getAddress() );
        Assertions.assertEquals( 1, savedEntity.getUserSiteList().size() );

        long idSavedEntity = entity.getId();
        savedEntity = repository.findById( idSavedEntity ).orElse( null );
        assert savedEntity != null;
        Assertions.assertEquals( entity.getName(), savedEntity.getName() );
        Assertions.assertEquals( entity.getSurname(), savedEntity.getSurname() );
        Assertions.assertEquals( entity.getAddress(), savedEntity.getAddress() );
        Assertions.assertEquals( entity.getId(), savedEntity.getId() );

        String updatedName = "Updated Name";
        String updatedSurname = "Updated Surname";
        String updatedAddress = "Updated Address";
        entity.setName( updatedName );
        entity.setSurname( updatedSurname );
        entity.setAddress( updatedAddress );
        savedEntity = repository.save( entity ).orElse( null );
        Assertions.assertNotNull( savedEntity );
        Assertions.assertEquals( entity.getName(), savedEntity.getName() );
        Assertions.assertEquals( entity.getSurname(), savedEntity.getSurname() );
        Assertions.assertEquals( entity.getAddress(), savedEntity.getAddress() );
        Assertions.assertEquals( 1, savedEntity.getUserSiteList().size() );


        String updatedNameSite = "Updated NameSite";
        userSite.setNameSite( updatedNameSite );

        savedEntity = repository.save( entity ).orElse( null );
        Assertions.assertNotNull( savedEntity );
        Assertions.assertEquals( 1, savedEntity.getUserSiteList().size() );
        UserSite savedUserSite = savedEntity.getUserSiteList().get( 0 );
        Assertions.assertNotNull( savedUserSite );
        Assertions.assertEquals( savedUserSite.getNameSite(), userSite.getNameSite() );
    }
    @Test
    void findSitesAll() throws SQLException {
        UserEntity entity = new UserEntity(  );
        entity.setName( "Test Name" );
        entity.setSurname( "Test Surname" );
        entity.setAddress( "Test Address" );
        UserSite userSite = new UserSite();
        userSite.setNameSite( "NameSite" );
        entity.getUserSiteList().add( userSite );
        UserSite newUserSite = new UserSite();
        newUserSite.setNameSite( "New NameSite" );
        entity.getUserSiteList().add( newUserSite );
        repository.save( entity );

        List<UserSite> userSiteList = repository.findSitesAll();

        Assertions.assertEquals( 2,userSiteList.size() );

    }

    @Test
    void clearAll() throws SQLException {
        UserEntity user1 = new UserEntity();
        user1.setId(1);
        repository.save(user1);

        UserEntity user2 = new UserEntity();
        user2.setId(2);
        repository.save(user2);

        List<UserEntity> entities = repository.findAll();
        Assertions.assertEquals( 2, entities.size() );

        repository.clearAll();

        Assertions.assertEquals(0, repository.findAll().size() );
    }
}