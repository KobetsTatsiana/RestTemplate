package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.when;

class PageServletTest {

    private PageServlet servlet;
    private Service<SitePage, Long> mockedPageService;
    private Repository<SitePage, Long> mockedPageRepository;
    private Repository<Advertising, Long> mockedAdvertisingRepository;
    private ConnectionManager mockedConnectionManager;
    private Connection mockedConnection;

    @BeforeEach
    void setUp() throws SQLException {
        mockedPageService = Mockito.mock( SitePageServiceImpl.class );
        mockedPageRepository = Mockito.mock( SitePageRepositoryImpl.class );
        mockedAdvertisingRepository = Mockito.mock( AdvertisingRepositoryImpl.class );
        mockedConnectionManager = Mockito.mock( ConnectionManager.class );
        mockedConnection = Mockito.mock( Connection.class );

        when( mockedPageService.getRepository() ).thenReturn( mockedPageRepository );
        when( mockedPageRepository.save( any() ) ).thenReturn( Optional.empty() );
        when( mockedPageRepository.findAll() ).thenReturn( Collections.emptyList() );
        when( mockedConnectionManager.getConnection() ).thenReturn( mockedConnection );

        servlet = new PageServlet( mockedConnectionManager, mockedAdvertisingRepository );
        servlet.setService( mockedPageService );
    }

    @Test
    void testDefaultConstructor() throws Exception {
        HttpServletRequest request = Mockito.mock( HttpServletRequest.class );
        HttpServletResponse response = Mockito.mock( HttpServletResponse.class );
        StringWriter stringWriter = new StringWriter();
        Mockito.when( response.getWriter() ).thenReturn( new PrintWriter( stringWriter ) );

        servlet.doGet( request, response );

        Assertions.assertFalse( stringWriter.toString().isEmpty(), "Response body should not be empty" );

        Mockito.verify( response ).setStatus( HttpServletResponse.SC_OK );
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

    @Test
    void testDoPost() throws Exception {
        HttpServletRequest request = Mockito.mock( HttpServletRequest.class );
        HttpServletResponse response = Mockito.mock( HttpServletResponse.class );
        StringWriter stringWriter = new StringWriter();
        Mockito.when( response.getWriter() ).thenReturn( new PrintWriter( stringWriter ) );

        String validJson = "{\n" +
                "            \"namePage\": \"namePage2\",\n" +
                "            \"advertisingList\": [\n" +
                "                {\n" +
                "                    \"id\": \"2\",\n" + //
                "                    \"infoText\": \"infoText\",\n" +
                "                    \"sitePageList\": null\n" +
                "                },\n" +
                "                {\n" +
                "                    \"id\": \"3\",\n" +
                "                    \"infoText\": \"infoText2\",\n" +
                "                    \"sitePageList\": null\n" +
                "                }\n" +
                "            ]\n" +
                "        }";
        BufferedReader bufferedReader = new BufferedReader( new StringReader( validJson ) );
        Mockito.when( request.getReader() ).thenReturn( bufferedReader );

        servlet.doPost( request, response );

        Assertions.assertTrue( stringWriter.toString().contains( "Added SitePage ID:" ), "Response should confirm the entity was added" );
        Mockito.verify( response ).setStatus( HttpServletResponse.SC_CREATED );
    }

    @Test
    void testDoPostSavesSitePage() throws Exception {
        ObjectMapper realMapper = new ObjectMapper();
        Service<SitePage, Long> mockedService = Mockito.mock( Service.class );

        servlet.setMapper( realMapper );
        servlet.setService( mockedService );

        String jsonString ="{\n" +
                "    \"namePage\": \"namePage\",\n" +
                "    \"advertisingList\": [\n" +
                "        {\n" +
                "            \"id\": 1,\n" +
                "            \"infoText\": \"infoText1\",\n" +
                "            \"sitePageList\": []\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 2,\n" +
                "            \"infoText\": \"infoText2\",\n" +
                "            \"sitePageList\": []\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 3,\n" +
                "            \"infoText\": \"infoText3\",\n" +
                "            \"sitePageList\": []\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        SitePage expectedEntity = realMapper.readValue( jsonString, SitePage.class );

        HttpServletRequest request = Mockito.mock( HttpServletRequest.class );
        HttpServletResponse response = Mockito.mock( HttpServletResponse.class );
        StringWriter stringWriter = new StringWriter();
        Mockito.when( response.getWriter() ).thenReturn( new PrintWriter( stringWriter ) );
        BufferedReader bufferedReader = new BufferedReader( new StringReader( jsonString ) );
        Mockito.when( request.getReader() ).thenReturn( bufferedReader );

        servlet.doPost( request, response );

        Mockito.verify( mockedService ).save( expectedEntity );
        String expectedMessage = "Added SitePage ID:";
        Assertions.assertTrue( stringWriter.toString().contains( expectedMessage ) );
    }

    @Test
    void testDoPostUpdateSitePage() throws Exception {
        ObjectMapper realMapper = new ObjectMapper();
        Service<SitePage, Long> mockedService = Mockito.mock( Service.class );

        servlet.setMapper( realMapper );
        servlet.setService( mockedService );

        String validJson = "{\n" +
                "    \"namePage\": \"namePage\",\n" +
                "    \"advertisingList\": [\n" +
                "        {\n" +
                "            \"id\": 1,\n" +
                "            \"infoText\": \"infoText1\",\n" +
                "            \"sitePageList\": []\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 2,\n" +
                "            \"infoText\": \"infoText2\",\n" +
                "            \"sitePageList\": []\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 3,\n" +
                "            \"infoText\": \"infoText3\",\n" +
                "            \"sitePageList\": []\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        SitePage expectedEntity = realMapper.readValue( validJson, SitePage.class );

        HttpServletRequest request = Mockito.mock( HttpServletRequest.class );
        HttpServletResponse response = Mockito.mock( HttpServletResponse.class );
        StringWriter stringWriter = new StringWriter();
        Mockito.when( response.getWriter() ).thenReturn( new PrintWriter( stringWriter ) );
        BufferedReader bufferedReader = new BufferedReader( new StringReader( validJson ) );
        Mockito.when( request.getReader() ).thenReturn( bufferedReader );

        servlet.doPost( request, response );

        Mockito.verify( mockedService ).save( expectedEntity );
        String expectedMessage = "Added SitePage ID:" ;
        Assertions.assertTrue( stringWriter.toString().contains( expectedMessage ) );
    }

    @Test
    void testSendSuccessResponseViaDoGet() throws Exception {
        HttpServletRequest mockRequest = Mockito.mock( HttpServletRequest.class );
        HttpServletResponse mockResponse = Mockito.mock( HttpServletResponse.class );
        PrintWriter mockWriter = Mockito.mock( PrintWriter.class );

        Service<SitePage, Long> mockService = Mockito.mock( Service.class );
        Mockito.when( mockService.findAll() ).thenReturn( Collections.emptyList() );

        Mockito.when( mockResponse.getWriter() ).thenReturn( mockWriter );

        servlet.setService( mockService );

        servlet.doGet( mockRequest, mockResponse );

        Mockito.verify( mockResponse ).setContentType( "application/json" );
        Mockito.verify( mockResponse ).setCharacterEncoding( "UTF-8" );
        Mockito.verify( mockResponse ).setStatus( HttpServletResponse.SC_OK );
        Mockito.verify( mockWriter ).write( startsWith( "GetAll SitePage:" ) );
    }
}
