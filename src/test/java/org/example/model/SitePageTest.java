package org.example.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SitePageTest {

    @Test
    void testDefaultConstructor() {
        SitePage sitePage = new SitePage();
        assertNotNull(sitePage.getAdvertisingList());
    }

    @Test
    void testGettersAndSetters() {
        SitePage sitePage = new SitePage();
        sitePage.setId(1L);
        sitePage.setNamePage("Test NamePage");
        sitePage.setAdvertisingList(Arrays.asList(new Advertising()));

        assertEquals(1L, sitePage.getId());
        assertEquals("Test NamePage", sitePage.getNamePage());
        assertEquals(1, sitePage.getAdvertisingList().size());
    }

    @Test
    void testEquals() {
        SitePage sitePage1 = new SitePage();
        sitePage1.setNamePage("Test NamePage");
        sitePage1.setAdvertisingList(Arrays.asList(new Advertising()));

        SitePage sitePage2 = new SitePage();
        sitePage2.setNamePage("Test NamePage");
        sitePage2.setAdvertisingList(Arrays.asList(new Advertising()));

        assertEquals(sitePage1, sitePage1);
        assertEquals(sitePage1, sitePage2);

        sitePage2.setNamePage("Different namePage");
        assertNotEquals(sitePage1, sitePage2);

        Advertising advertising = new Advertising();
        assertNotEquals(sitePage1, advertising);
    }

    @Test
    void testHashCode() {
        SitePage sitePage1 = new SitePage();
        sitePage1.setNamePage("Test NamePage");
        sitePage1.setAdvertisingList(Arrays.asList(new Advertising()));

        SitePage sitePage2 = new SitePage();
        sitePage2.setNamePage("Test NamePage");
        sitePage2.setAdvertisingList(Arrays.asList(new Advertising()));

        assertEquals(sitePage1.hashCode(), sitePage2.hashCode());
    }

    @Test
    void testToString() {
        SitePage sitePage = new SitePage();
        sitePage.setId(1L);
        sitePage.setNamePage("name");
        sitePage.setAdvertisingList(new ArrayList<>());
        String expectedString = "SitePage{id=" + sitePage.getId() + ", namePage='" + sitePage.getNamePage() + "', advertisingList=" + sitePage.getAdvertisingList() + "}";
        assertEquals(expectedString, sitePage.toString());
    }
}