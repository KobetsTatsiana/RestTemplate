package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AdvertisingTest {

    private Advertising advertising;

    @BeforeEach
    public void setUp() {
        advertising = new Advertising();
    }

    @Test
    void testDefaultIdNotNull() {
        assertNotNull(advertising.getId());
    }

    @Test
    void testSetAndGetId() {
        advertising.setId(1L);
        assertEquals(1, advertising.getId());
    }

    @Test
    void testSetAndGetAdvertisingInfoText() {
        advertising.setInfoText("InfoText");
        assertEquals("InfoText", advertising.getInfoText());
    }

    @Test
    void testSetAndGetSitePageList() {
        SitePage sitePage1 = new SitePage();
        SitePage sitePage2 = new SitePage();

        advertising.setSitePageList(Arrays.asList(sitePage1, sitePage2));

        assertEquals(2, advertising.getSitePageList().size());
        assertTrue(advertising.getSitePageList().contains(sitePage1));
        assertTrue(advertising.getSitePageList().contains(sitePage2));
    }

    @Test
    void testEquals() {
        Advertising advertising1 = new Advertising();
        Advertising advertising2 = new Advertising();
        Advertising advertising3 = new Advertising();

        advertising1.setInfoText("Test");
        advertising2.setInfoText("Test");
        advertising3.setInfoText("Test2");

        assertEquals(advertising1, advertising2);
        assertNotEquals(advertising1, advertising3);

        assertEquals(advertising1, advertising1);

        SitePage sitePage = new SitePage();
        assertNotEquals(advertising1, sitePage);
    }

    @Test
    void testHashCode() {
        Advertising advertising1 = new Advertising();
        Advertising advertising2 = new Advertising();

        advertising1.setInfoText("Test");
        advertising2.setInfoText("Test");

        assertEquals(advertising1.hashCode(), advertising2.hashCode());
    }

    @Test
    void testToString() {
        advertising.setInfoText("Test");
        assertTrue(advertising.toString().contains("Test"));
    }
}
