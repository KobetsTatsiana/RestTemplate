package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AdvertisingServletTest {

    private AdvertisingServlet servlet;
    private Service<Advertising, Long> mockedService;
    private Repository<Advertising, Long> mockedRepository;
    private ConnectionManager mockedConnectionManager;
    private Connection mockedConnection;

    @BeforeEach
    void setUp() throws SQLException {
        mockedService = Mockito.mock(AdvertisingServiceImpl.class);
        mockedRepository = Mockito.mock( AdvertisingRepositoryImpl.class);
        mockedConnectionManager = Mockito.mock(ConnectionManager.class);
        mockedConnection = Mockito.mock(Connection.class);

        when(mockedService.getRepository()).thenReturn(mockedRepository);
        when(mockedRepository.save(any())).thenReturn( Optional.empty());
        when(mockedRepository.findAll()).thenReturn(Collections.emptyList());
        when(mockedConnectionManager.getConnection()).thenReturn(mockedConnection);

        servlet = new AdvertisingServlet(mockedConnectionManager);
        servlet.setService(mockedService);
    }

    @Test
    void testDefaultConstructor() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        Assertions.assertFalse(stringWriter.toString().isEmpty(), "Response body should not be empty");

        Mockito.verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void testDoGet() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        Assertions.assertFalse(stringWriter.toString().isEmpty(), "Response body should not be empty");
        Mockito.verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void testDoPost() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        String validJson = "{    \n" +
                "    \"infoText\": \"infoText\",\n" +
                "    \"sitePageList\": [\n" +
                "        {\n" +
                "            \"id\": 1,\n" +
                "            \"namePage\": \"namePage1\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 2,\n" +
                "            \"namePage\": \"namePage2\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        BufferedReader bufferedReader = new BufferedReader(new StringReader(validJson));
        Mockito.when(request.getReader()).thenReturn(bufferedReader);

        servlet.doPost(request, response);

        Assertions.assertTrue(stringWriter.toString().contains("Added Advertising ID:"), "Response should confirm the entity was added");
        Mockito.verify(response).setStatus(HttpServletResponse.SC_CREATED);
    }

    @Test
    void testDoPostSavesAdvertising() throws Exception {
        ObjectMapper realMapper = new ObjectMapper();
        Service<Advertising, Long> mockedService = Mockito.mock(Service.class);

        servlet.setMapper(realMapper);
        servlet.setService(mockedService);

        String jsonString = "{    \n" +
                "    \"infoText\": \"infoText\",\n" +
                "    \"sitePageList\": [\n" +
                "        {\n" +
                "            \"id\": 1,\n" +
                "            \"namePage\": \"namePage1\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 2,\n" +
                "            \"namePage\": \"namePage2\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        Advertising expectedEntity = realMapper.readValue(jsonString, Advertising.class);

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
        BufferedReader bufferedReader = new BufferedReader(new StringReader(jsonString));
        Mockito.when(request.getReader()).thenReturn(bufferedReader);

        servlet.doPost(request, response);

        Mockito.verify(mockedService).save(expectedEntity);
        String expectedMessage = "Added Advertising ID:";
        Assertions.assertTrue(stringWriter.toString().contains(expectedMessage));
    }
}
