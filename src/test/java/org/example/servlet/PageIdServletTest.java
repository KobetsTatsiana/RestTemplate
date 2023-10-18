package org.example.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.db.ConnectionManager;
import org.example.model.Advertising;

import org.example.model.SitePage;

import org.example.repository.Repository;
import org.example.repository.impl.AdvertisingRepositoryImpl;

import org.example.repository.impl.SitePageRepositoryImpl;

import org.example.service.Service;

import org.example.service.impl.SitePageServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PageIdServletTest {

    private PageIdServlet servlet;
    private Service<SitePage, Long> mockedPageService;
    private Repository<SitePage, Long> mockedPageRepository;
    private Repository<Advertising, Long> mockedAdvertisingRepository;
    private ConnectionManager mockedConnectionManager;
    private Connection mockedConnection;

    @BeforeEach
    void setUp() throws SQLException {
        mockedPageService = Mockito.mock(SitePageServiceImpl.class);
        mockedPageRepository = Mockito.mock(SitePageRepositoryImpl.class);
        mockedAdvertisingRepository = Mockito.mock(AdvertisingRepositoryImpl.class);
        mockedConnectionManager = Mockito.mock(ConnectionManager.class);
        mockedConnection = Mockito.mock(Connection.class);

        when(mockedPageService.getRepository()).thenReturn(mockedPageRepository);
        when(mockedPageRepository.save(any())).thenReturn(Optional.empty());
        when(mockedPageRepository.findAll()).thenReturn(Collections.emptyList());
        when(mockedConnectionManager.getConnection()).thenReturn(mockedConnection);

        servlet = new PageIdServlet(mockedConnectionManager, mockedAdvertisingRepository);
        servlet.setService(mockedPageService);
    }

    @Test
    void testDefaultConstructor() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
        when(request.getPathInfo()).thenReturn("/pageId/" + 1);

        servlet.doGet(request, response);

        Assertions.assertFalse(stringWriter.toString().isEmpty(), "Response body should not be empty");

        Mockito.verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void testDoGet() throws Exception {
        SitePageServiceImpl mockService = mock(SitePageServiceImpl.class);

        Long testID = 1L;
        SitePage mockEntity = new SitePage();
        mockEntity.setId(testID);

        when(mockService.findById(testID)).thenReturn(Optional.of(mockEntity));

        Field serviceField = PageIdServlet.class.getDeclaredField("service");
        serviceField.setAccessible(true);
        serviceField.set(servlet, mockService);

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(mockResponse.getWriter()).thenReturn(writer);
        when(mockRequest.getPathInfo()).thenReturn("/pageId/" + testID);

        servlet.doGet(mockRequest, mockResponse);

        Assertions.assertTrue(stringWriter.toString().contains(testID.toString()));
    }

    @Test
    void testDoGetWithValidID() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        String validID = "2";
        Mockito.when(request.getPathInfo()).thenReturn("/" + validID);

        servlet.doGet(request, response);

        Assertions.assertFalse(stringWriter.toString().isEmpty(), "Response body should not be empty");
        Mockito.verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void testDoPut() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        String validID = "3";
        Mockito.when(request.getPathInfo()).thenReturn("/pageId/" + validID);

        String validJson = "{\"namePage\": \"namePage3\", \"advertisingList\": []}";
        BufferedReader bufferedReader = new BufferedReader(new StringReader(validJson));
        Mockito.when(request.getReader()).thenReturn(bufferedReader);

        servlet.doPut(request, response);

        Assertions.assertTrue(stringWriter.toString().contains("SitePage updated successfully"), "Response should confirm the entity was updated");
        Mockito.verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void testDoPutWithInvalidID() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        String invalidID = "invalid-id";
        Mockito.when(request.getPathInfo()).thenReturn("/pageId/" + invalidID);

        String validJson = "{\"namePage\": \"namePage\", \"advertisingList\": []}";
        BufferedReader bufferedReader = new BufferedReader(new StringReader(validJson));
        Mockito.when(request.getReader()).thenReturn(bufferedReader);

        servlet.doPut(request, response);

        Assertions.assertTrue(stringWriter.toString().contains("An internal server error occurred."), "Response should indicate invalid ID format");
        Mockito.verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    void testDoDelete() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        Mockito.when(response.getWriter()).thenReturn(printWriter);

        String validID = "4";
        Mockito.when(request.getPathInfo()).thenReturn("/pageId/" + validID);

        Long id = Long.valueOf(validID);
        when(mockedPageService.delete(id)).thenReturn(true);

        servlet.doDelete(request, response);

        Assertions.assertFalse(stringWriter.toString().isEmpty(), "Response body should not be empty");
        Mockito.verify(response).setStatus(HttpServletResponse.SC_OK);
        Assertions.assertTrue(stringWriter.toString().contains("Deleted SitePage ID:"));
    }
}
