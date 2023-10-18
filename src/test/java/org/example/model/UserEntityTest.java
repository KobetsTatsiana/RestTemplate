package org.example.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest extends Throwable {

    @Test
    void testEqualsAndHashCode() {
        UserEntity userEntity1 = new UserEntity();
        UserEntity userEntity2 = new UserEntity();

        userEntity1.setName("UserName");
        userEntity1.setSurname("UserSurname");
        userEntity1.setAddress("UserAddress");
        userEntity1.setUserSiteList(Arrays.asList(new UserSite()));

        userEntity2.setName("UserName");
        userEntity2.setSurname("UserSurname");
        userEntity2.setAddress("UserAddress");
        userEntity2.setUserSiteList(Arrays.asList(new UserSite()));

        assertTrue(userEntity1.equals(userEntity2) && userEntity2.equals(userEntity1));
        assertTrue(userEntity1.getName().equals(userEntity2.getName()) && userEntity1.getSurname().equals(userEntity2.getSurname()) && userEntity1.getAddress().equals(userEntity2.getAddress()) &&  userEntity2.getUserSiteList().equals(userEntity1.getUserSiteList()));
        assertEquals(userEntity1.hashCode(), userEntity2.hashCode());

        UserSite userSite = new UserSite();
        assertNotEquals(userEntity1, userSite);

        assertEquals(userEntity1, userEntity1);
    }

    @Test
    void testGettersAndSetters() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setName("Test Name");
        userEntity.setSurname("Test Surname");
        userEntity.setAddress("Test Address");
        userEntity.setUserSiteList(Arrays.asList(new UserSite()));

        assertEquals(1, userEntity.getId());
        assertEquals("Test Name", userEntity.getName());
        assertEquals("Test Surname", userEntity.getSurname());
        assertEquals("Test Address", userEntity.getAddress());
        assertEquals(1, userEntity.getUserSiteList().size());
    }

    @Test
    void equalsWithSameValues() {
        UserEntity userEntity1 = new UserEntity();
        userEntity1.setName("Ivan");
        userEntity1.setSurname("Ivanov");
        userEntity1.setAddress("Ivanovo");
        userEntity1.setUserSiteList(Arrays.asList(new UserSite(), new UserSite()));

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setName("Ivan");
        userEntity2.setSurname("Ivanov");
        userEntity2.setAddress("Ivanovo");
        userEntity2.setUserSiteList(Arrays.asList(new UserSite(), new UserSite()));


        assertTrue(userEntity1.equals(userEntity2) && userEntity2.equals(userEntity1));
    }

    @Test
    void equalsWithDifferentUserNames() {
        UserEntity userEntity1 = new UserEntity();
        userEntity1.setName("Ivan");
        userEntity1.setSurname("Ivanov");
        userEntity1.setAddress("Ivanovo");
        userEntity1.setUserSiteList(Arrays.asList(new UserSite(), new UserSite()));

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setName("Semen");
        userEntity2.setSurname("Semenov");
        userEntity2.setAddress("Semenovo");
        userEntity2.setUserSiteList(Arrays.asList(new UserSite(), new UserSite()));

        assertFalse(userEntity1.equals(userEntity2) || userEntity2.equals(userEntity1));
    }

    @Test
    void equalsWithDifferentSiteLists() {
        UserEntity userEntity1 = new UserEntity();
        userEntity1.setName("Ivan");
        userEntity1.setSurname("Ivanov");
        userEntity1.setAddress("Ivanovo");
        userEntity1.setUserSiteList(Arrays.asList(new UserSite()));

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setName("Ivan");
        userEntity2.setSurname("Ivanov");
        userEntity2.setAddress("Ivanovo");
        userEntity2.setUserSiteList(Arrays.asList(new UserSite(), new UserSite()));

        assertFalse(userEntity1.equals(userEntity2) || userEntity2.equals(userEntity1));
    }

    @Test
    void notEqualsWithNullOrDifferentType() {
        UserEntity userEntity = new UserEntity();
        userEntity.setName("Ivan");
        userEntity.setSurname("Ivanov");
        userEntity.setAddress("Ivanovo");
        userEntity.setUserSiteList(Arrays.asList(new UserSite()));

        assertNotEquals(null, userEntity);
        assertNotEquals("a string", userEntity);
    }

    @Test
    void testToString() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setName("Test Name");
        userEntity.setSurname("Test Surname");
        userEntity.setAddress("Test Address");
        userEntity.setUserSiteList(Arrays.asList(new UserSite(), new UserSite()));


        String expectedString = "UserEntity{" +
                "id=" + 1 +
                ", name='Test Name'" +
                ", surname='Test Surname'" +
                ", address='Test Address'" +
                ", userSiteList=" + userEntity.getUserSiteList() +
                '}';

        assertEquals(expectedString, userEntity.toString());
    }
}

