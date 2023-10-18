package org.example.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.db.ConnectionManager;
import org.example.model.Advertising;

import org.example.repository.Repository;
import org.example.repository.impl.AdvertisingRepositoryImpl;

import org.example.service.Service;
import org.example.service.impl.AdvertisingServiceImpl;

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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AdvertisingIdServletTest {

    private AdvertisingIdServlet servlet;
    private Service<Advertising, Long> mockedService;
    private Repository<Advertising, Long> mockedRepository;
    private ConnectionManager mockedConnectionManager;
    private Connection mockedConnection;

    @BeforeEach
    void setUp() throws SQLException {
        mockedService = Mockito.mock(AdvertisingServiceImpl.class);
        mockedRepository = Mockito.mock(AdvertisingRepositoryImpl.class);
        mockedConnectionManager = Mockito.mock(ConnectionManager.class);
        mockedConnection = Mockito.mock(Connection.class);

        when(mockedService.getRepository()).thenReturn(mockedRepository);
        when(mockedRepository.save(any())).thenReturn(Optional.empty());
        when(mockedConnectionManager.getConnection()).thenReturn(mockedConnection);

        servlet = new AdvertisingIdServlet(mockedConnectionManager, mockedRepository);
        servlet.setService(mockedService);
    }

    @Test
    void testDefaultConstructor() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
        when(request.getPathInfo()).thenReturn("/advertisingId/" + 1);

        servlet.doGet(request, response);

        Assertions.assertFalse(stringWriter.toString().isEmpty(), "Response body should not be empty");

        Mockito.verify(response).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    void testDoGet() throws Exception {
        AdvertisingServiceImpl mockService = mock(AdvertisingServiceImpl.class);

        Long testID = 1L;
        Advertising mockEntity = new Advertising();
        mockEntity.setId(testID);

        when(mockService.findById(testID)).thenReturn(Optional.of(mockEntity));

        Field serviceField = AdvertisingIdServlet.class.getDeclaredField("service");
        serviceField.setAccessible(true);
        serviceField.set(servlet, mockService);

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(mockResponse.getWriter()).thenReturn(writer);
        when(mockRequest.getPathInfo()).thenReturn("/advertisingId/" + testID);

        servlet.doGet(mockRequest, mockResponse);

        Assertions.assertTrue(stringWriter.toString().contains(testID.toString()));
    }

    @Test
    void testDoGetNotFound() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        String invalidID = "2";
        Mockito.when(request.getPathInfo()).thenReturn("/advertisingId/" + invalidID);

        when(mockedService.findById(any())).thenReturn(Optional.empty());

        servlet.doGet(request, response);

        Assertions.assertFalse(stringWriter.toString().isEmpty(), "Response body should not be empty");
        Mockito.verify(response).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    void testDoPut() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        String validID = "3";
        Mockito.when(request.getPathInfo()).thenReturn("/advertisingId/" + validID);

        String validJson = "{\n" +
                "    \"infoText\": \"infoText\",\n" +
                "    \"sitePageList\": []\n" +
                "}";
        BufferedReader bufferedReader = new BufferedReader(new StringReader(validJson));
        Mockito.when(request.getReader()).thenReturn(bufferedReader);

        servlet.doPut(request, response);

        Assertions.assertTrue(stringWriter.toString().contains("Advertising updated successfully"), "Response should confirm the entity was updated");
        Mockito.verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void testDoPutWithInvalidID() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        String invalidID = "invalid-id";
        Mockito.when(request.getPathInfo()).thenReturn("/advertisingId/" + invalidID);

        String validJson = "{\"namePage\": \"namePage\", \"advertisingList\": []}";
        BufferedReader bufferedReader = new BufferedReader(new StringReader(validJson));
        Mockito.when(request.getReader()).thenReturn(bufferedReader);

        servlet.doPut(request, response);

        Assertions.assertTrue(stringWriter.toString().contains("An internal server error occurred."), "Response should indicate invalid ID format");
        Mockito.verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    void testDoDelete() throws Exception {
        AdvertisingServiceImpl mockService = mock(AdvertisingServiceImpl.class);

        Long testID = 1L;
        Advertising mockEntity = new Advertising();
        mockEntity.setId(testID);

        when(mockService.findById(testID)).thenReturn(Optional.of(mockEntity));

        Field serviceField = AdvertisingIdServlet.class.getDeclaredField("service");
        serviceField.setAccessible(true);
        serviceField.set(servlet, mockService);

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(mockResponse.getWriter()).thenReturn(writer);
        when(mockRequest.getPathInfo()).thenReturn("/advertisingId/" + testID.toString());

        servlet.doDelete(mockRequest, mockResponse);

        Assertions.assertTrue(stringWriter.toString().contains("Deleted Advertising ID:"));
    }

    @Test
    void testDoDeleteNotFound() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        String invalidID = "4";
        Mockito.when(request.getPathInfo()).thenReturn("/advertisingId/" + invalidID);

        Long id = Long.valueOf(invalidID);

        when(mockedService.delete(id)).thenReturn(false);

        servlet.doDelete(request, response);

        Assertions.assertFalse(stringWriter.toString().isEmpty(), "Response body should not be empty");
        Mockito.verify(response).setStatus(HttpServletResponse.SC_OK);
    }
}
