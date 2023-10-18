package org.example.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserSiteTest {
    @Test
    void testUserSiteCreation() {
        UserSite userSite = new UserSite();
        assertEquals(0, userSite.getId());
        assertNull(userSite.getNameSite());

        String nameSite = "Site1";
        userSite = new UserSite(1, nameSite);
        assertEquals(1, userSite.getId());
        assertEquals(nameSite, userSite.getNameSite());

        userSite = new UserSite(nameSite);
        assertEquals(0, userSite.getId());
        assertEquals(0, userSite.getUserId());
        assertEquals(nameSite, userSite.getNameSite());

        userSite = new UserSite(2, nameSite);
        assertEquals(2, userSite.getId());
        assertEquals(nameSite, userSite.getNameSite());
    }

    @Test
    void testSettersAndGetters() {
        UserSite userSite = new UserSite();
        String nameSite = "Sample nameSite";

        userSite.setId( 1 );
        userSite.setUserId( 1 );
        userSite.setNameSite( nameSite );

        assertEquals( 1, userSite.getId() );
        assertEquals( 1, userSite.getUserId() );
        assertEquals( nameSite, userSite.getNameSite() );
    }

    @Test
    void testEqualsAndHashCode() {
        String nameSite = "Site1";
        UserSite userSite1 = new UserSite( nameSite );
        UserSite userSite2 = new UserSite( nameSite );
        UserSite userSite3 = new UserSite( "Site2" );

        assertEquals( userSite1, userSite1 );

        assertEquals( userSite1, userSite2 );
        assertNotEquals( userSite1, userSite3 );
        assertNotEquals( userSite2, userSite3 );

        UserEntity userEntity = new UserEntity();
        assertNotEquals( userSite2, userEntity );

        assertEquals( userSite1.hashCode(), userSite2.hashCode() );
        assertNotEquals( userSite1.hashCode(), userSite3.hashCode() );
    }

    @Test
    void testToString() {
        String nameSite = "Sample nameSite";
        UserSite userSite = new UserSite( 1, nameSite );
        userSite.setUserId( 1 );

        String expectedString = "UserSite{" +
                "id=" + 1 +
                ", userId=" + 1 +
                ", nameSite='" + nameSite + '\'' +
                '}';
        assertEquals( expectedString, userSite.toString() );
    }
}
