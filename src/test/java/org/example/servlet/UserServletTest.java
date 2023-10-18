package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.db.ConnectionManager;

import org.example.model.UserEntity;

import org.example.repository.Repository;

import org.example.repository.UserRepository;
import org.example.service.Service;
import org.example.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.when;

class UserServletTest {

    private UserServlet servlet;
    private Service<UserEntity, Long> mockedService;
    private Repository<UserEntity, Long> mockedRepository;
    private ConnectionManager mockedConnectionManager;
    private Connection mockedConnection;

    @BeforeEach
    void setUp() throws SQLException {
        mockedService = Mockito.mock(UserService.class);
        mockedRepository = Mockito.mock(UserRepository.class);
        mockedConnectionManager = Mockito.mock(ConnectionManager.class);
        mockedConnection = Mockito.mock(Connection.class);

        when(mockedService.getRepository()).thenReturn(mockedRepository);
        when(mockedRepository.save(any())).thenReturn(Optional.empty());
        when(mockedRepository.findAll()).thenReturn(Collections.emptyList());
        when(mockedConnectionManager.getConnection()).thenReturn(mockedConnection);

        servlet = new UserServlet(mockedConnectionManager);
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

        String validJson = "{\"id\": 1, \"name\": \"Name\", \"surname\": \"Surname\", \"address\": \"Address\"}";
        BufferedReader bufferedReader = new BufferedReader(new StringReader(validJson));
        Mockito.when(request.getReader()).thenReturn(bufferedReader);

        servlet.doPost(request, response);

        Assertions.assertTrue(stringWriter.toString().contains("Added User ID:"), "Response should confirm the entity was added");
        Mockito.verify(response).setStatus(HttpServletResponse.SC_CREATED);
    }

    @Test
    void testDoPostSavesUserEntity() throws Exception {
        ObjectMapper realMapper = new ObjectMapper();
        Service<UserEntity, Long> mockedService = Mockito.mock(Service.class);

        servlet.setMapper(realMapper);
        servlet.setService(mockedService);

        String jsonString = "{\"id\":1,\"name\":\"a\",\"surname\":\"aa\",\"address\":\"aaa\",\"userSiteList\":[{\"id\":1,\"userId\":0,\"nameSite\":\"a\"}]}";


        UserEntity expectedEntity = realMapper.readValue(jsonString, UserEntity.class);

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
        BufferedReader bufferedReader = new BufferedReader(new StringReader(jsonString));
        Mockito.when(request.getReader()).thenReturn(bufferedReader);

        servlet.doPost(request, response);

        Mockito.verify(mockedService).save(expectedEntity);
        String expectedMessage = "Added User ID:";
        Assertions.assertTrue(stringWriter.toString().contains(expectedMessage));
    }

    @Test
    void testDoPostUpdateUserEntity() throws Exception {
        ObjectMapper realMapper = new ObjectMapper();
        Service<UserEntity, Long> mockedService = Mockito.mock(Service.class);

        servlet.setMapper(realMapper);
        servlet.setService(mockedService);

        String validJson = "{\"id\":1,\"name\":\"a\",\"surname\":\"aa\",\"address\":\"aaa\",\"userSiteList\":[{\"id\":1,\"userId\":0,\"nameSite\":\"a\"}]}";

        UserEntity expectedEntity = realMapper.readValue(validJson, UserEntity.class);

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
        BufferedReader bufferedReader = new BufferedReader(new StringReader(validJson));
        Mockito.when(request.getReader()).thenReturn(bufferedReader);

        servlet.doPost(request, response);

        Mockito.verify(mockedService).save(expectedEntity);
        String expectedMessage = "Added User ID:" + expectedEntity.getId();
        Assertions.assertTrue(stringWriter.toString().contains(expectedMessage));
    }

    @Test
    void testSendSuccessResponseViaDoGet() throws Exception {

        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = Mockito.mock(HttpServletResponse.class);
        PrintWriter mockWriter = Mockito.mock(PrintWriter.class);

        Service<UserEntity, Long> mockService = Mockito.mock(Service.class);
        Mockito.when(mockService.findAll()).thenReturn(Collections.emptyList());

        Mockito.when(mockResponse.getWriter()).thenReturn(mockWriter);

        servlet.setService(mockService);

        servlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockResponse).setContentType("application/json");
        Mockito.verify(mockResponse).setCharacterEncoding("UTF-8");
        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_OK);
        Mockito.verify(mockWriter).write(startsWith("GetAll User:"));
    }
}