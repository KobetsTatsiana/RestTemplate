package org.example.servlet;

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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class SiteServletTest {

    private UserServlet servlet;
    private Service<UserEntity, Long> mockedService;
    private Repository<UserEntity, Long> mockedRepository;
    private ConnectionManager mockedConnectionManager;
    private Connection mockedConnection;

    @BeforeEach
    void setUp() throws SQLException {
        mockedService = Mockito.mock( UserService.class );
        mockedRepository = Mockito.mock( UserRepository.class );
        mockedConnectionManager = Mockito.mock( ConnectionManager.class );
        mockedConnection = Mockito.mock( Connection.class );

        when( mockedService.getRepository() ).thenReturn( mockedRepository );
        when( mockedRepository.save( any() ) ).thenReturn( Optional.empty() );
        when( mockedRepository.findAll() ).thenReturn( Collections.emptyList() );
        when( mockedConnectionManager.getConnection() ).thenReturn( mockedConnection );

        servlet = new UserServlet( mockedConnectionManager );
        servlet.setService( mockedService );
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
        HttpServletRequest request = Mockito.mock( HttpServletRequest.class );
        HttpServletResponse response = Mockito.mock( HttpServletResponse.class );
        StringWriter stringWriter = new StringWriter();
        Mockito.when( response.getWriter() ).thenReturn( new PrintWriter( stringWriter ) );

        servlet.doGet( request, response );

        Assertions.assertFalse( stringWriter.toString().isEmpty(), "Response body should not be empty" );
        Mockito.verify( response ).setStatus( HttpServletResponse.SC_OK );
    }
}