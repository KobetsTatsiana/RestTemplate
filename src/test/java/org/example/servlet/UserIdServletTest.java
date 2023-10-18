package org.example.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.db.ConnectionManager;

import org.example.model.UserEntity;
import org.example.repository.Repository;
import org.example.repository.UserRepository;
import org.example.service.Service;
import org.example.service.UserService;
import org.example.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.*;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class UserIdServletTest {

    private UserIdServlet servlet = new UserIdServlet();

    @BeforeEach
    void setUp() throws SQLException {
        Service<UserEntity, Long> mockedService = Mockito.mock(UserService.class);
        Repository<UserEntity, Long> mockedRepository = Mockito.mock(UserRepository.class);
        ConnectionManager mockedConnectionManager = Mockito.mock(ConnectionManager.class);
        Connection mockedConnection = Mockito.mock(Connection.class);

        when(mockedService.getRepository()).thenReturn(mockedRepository);
        when(mockedRepository.save(any())).thenReturn(Optional.empty());
        when(mockedRepository.findAll()).thenReturn(Collections.emptyList());
        when(mockedConnectionManager.getConnection()).thenReturn(mockedConnection);

        servlet = new UserIdServlet(mockedConnectionManager);
        servlet.setService(mockedService);
    }

    @Test
    void testDefaultConstructor() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
        when(request.getPathInfo()).thenReturn("/userServlet/" + 1);

        servlet.doGet(request, response);

        Assertions.assertFalse(stringWriter.toString().isEmpty(), "Response body should not be empty");
        Mockito.verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void testDoGet() throws Exception {
        UserServiceImpl mockService = mock(UserServiceImpl.class);

        Long id = 1L;
        UserEntity mockEntity = new UserEntity();
        mockEntity.setId(id);

        when(mockService.findById(id)).thenReturn(Optional.of(mockEntity));

        Field serviceField = UserIdServlet.class.getDeclaredField("service");
        serviceField.setAccessible(true);
        serviceField.set(servlet, mockService);

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(mockResponse.getWriter()).thenReturn(writer);
        when(mockRequest.getPathInfo()).thenReturn("/userServlet/" + 1);

        servlet.doGet(mockRequest, mockResponse);

        Assertions.assertTrue(stringWriter.toString().contains(id.toString()));
    }

    @Test
    void testDoPut() throws Exception {
       UserServiceImpl mockService = mock(UserServiceImpl.class);

        Long testID = 1L;
        UserEntity mockEntity = new UserEntity();
        mockEntity.setId(testID);

        when(mockService.findById(testID)).thenReturn(Optional.of(mockEntity));

        Field serviceField = UserIdServlet.class.getDeclaredField("service");
        serviceField.setAccessible(true);
        serviceField.set(servlet, mockService);

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(mockResponse.getWriter()).thenReturn(writer);
        when(mockRequest.getPathInfo()).thenReturn("/userService/" + testID);

        testPutMethod(testID, mockRequest, mockResponse, stringWriter);
        testUpdateMethod(testID, mockRequest, mockResponse, stringWriter);
    }

    private void testUpdateMethod(Long testID, HttpServletRequest mockRequest, HttpServletResponse mockResponse, StringWriter stringWriter) throws IOException {
        String inputJson = "{    \n" +
                "    \"name\": \"name\",\n" +
                "    \"surname\": \"surname\",\n" +
                "    \"address\": \"address\",\n" +
                "    \"userSiteList\": [\n" +
                "        {\n" +
                "            \"id\": 2,\n" +
                "            \"userId\": 2,\n" +
                "            \"nameSite\": \"New Site\"\n" +
                "        }        \n" +
                "    ]\n" +
                "}";
        when(mockRequest.getReader()).thenReturn(new BufferedReader(new StringReader(inputJson)));

        servlet.doPut(mockRequest, mockResponse);

        Assertions.assertTrue(stringWriter.toString().contains("User updated successfully"));
    }

    private void testPutMethod(Long testID, HttpServletRequest mockRequest, HttpServletResponse mockResponse, StringWriter stringWriter) throws IOException {
        String inputJson = "{\n" +
                "    \"name\": \"Update name\",\n" +
                "    \"surname\": \"Update surname\",\n" +
                "    \"address\": \"Update address\",\n" +
                "    \"userSiteList\": [\n" +
                "        {\n" +
                "            \"id\": 2,\n" +
                "            \"userId\": 2,\n" +
                "            \"nameSite\": \"Create nameSite\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        when(mockRequest.getReader()).thenReturn(new BufferedReader(new StringReader(inputJson)));

        servlet.doPut(mockRequest, mockResponse);

        Assertions.assertTrue(stringWriter.toString().contains("User updated successfully"));
    }

    @Test
    void testDoDelete() throws Exception {
        UserServiceImpl mockService = mock(UserServiceImpl.class);

        Long testID = 1L;
        UserEntity mockEntity = new UserEntity();
        mockEntity.setId(testID);

        when(mockService.findById(testID)).thenReturn(Optional.of(mockEntity));

        Field serviceField = UserIdServlet.class.getDeclaredField("service");
        serviceField.setAccessible(true);
        serviceField.set(servlet, mockService);

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(mockResponse.getWriter()).thenReturn(writer);
        when(mockRequest.getPathInfo()).thenReturn("/userService/" + testID.toString());

        servlet.doDelete(mockRequest, mockResponse);

        Assertions.assertTrue(stringWriter.toString().contains("Deleted User ID:"));
    }

    @Test
    void testHandleExceptionViaDoGet() throws Exception {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        PrintWriter mockWriter = mock(PrintWriter.class);

        when(mockRequest.getPathInfo()).thenReturn("/userIdServlet/invalidID");
        when(mockResponse.getWriter()).thenReturn(mockWriter);

        servlet.doGet(mockRequest, mockResponse);

        verify(mockResponse).setContentType("application/json");
        verify(mockResponse).setCharacterEncoding("UTF-8");
        verify(mockResponse).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        verify(mockWriter).write("An internal server error occurred.");
    }

}