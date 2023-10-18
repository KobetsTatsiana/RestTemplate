package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import org.example.db.ConnectionManager;
import org.example.db.HikariCPDataSource;
import org.example.model.Advertising;
import org.example.model.SitePage;
import org.example.repository.Repository;
import org.example.repository.impl.AdvertisingRepositoryImpl;
import org.example.repository.impl.SitePageRepositoryImpl;
import org.example.service.Service;
import org.example.service.impl.SitePageServiceImpl;

import org.example.servlet.dto.page.PageAllOutGoingDTO;
import org.example.servlet.dto.page.PageIncomingDTO;
import org.example.servlet.dto.page.mapper.PageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@WebServlet(name = "PageServlet", value = "/pageServlet")
public class PageServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(PageServlet.class);
    private ObjectMapper mapper = new ObjectMapper();
    private final ConnectionManager connectionManager;
    private final Repository<SitePage, Long> pageRepository;
    private final Repository<Advertising, Long> advertisingRepository;
    private transient Service<SitePage, Long> service;

    public PageServlet() {
        this.connectionManager = new HikariCPDataSource();
        this.advertisingRepository = new AdvertisingRepositoryImpl(connectionManager);
        this.pageRepository = new SitePageRepositoryImpl(connectionManager, advertisingRepository);
        this.service = new SitePageServiceImpl(pageRepository);
    }


    public PageServlet(ConnectionManager connectionManager, Repository<Advertising, Long> advertisingRepository) {
        this.connectionManager = connectionManager;
        this.pageRepository = new SitePageRepositoryImpl(connectionManager, advertisingRepository);
        this.advertisingRepository = advertisingRepository;
        this.service = new SitePageServiceImpl(pageRepository);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<SitePage> sitePageList = service.findAll();
            PageAllOutGoingDTO pageAllOutGoingDTO = PageMapper.INSTANCE.mapListToDto(sitePageList);
            String jsonString = mapper.writeValueAsString(pageAllOutGoingDTO);
            sendSuccessResponse(response, "GetAll SitePage: " + jsonString, HttpServletResponse.SC_OK);
        } catch (SQLException e) {
            handleException(response, e, "Error retrieving all sitePageList");
        } catch (Exception e) {
            handleException(response, e, "Error processing GET request");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            String json = readRequestBody(request).toString();
            PageIncomingDTO pageIncomingDTO = mapper.readValue(json, PageIncomingDTO.class);
            SitePage sitePage = PageMapper.INSTANCE.map(pageIncomingDTO);
            service.save(sitePage);
            sendSuccessResponse(response, "Added SitePage ID: " + sitePage.getId(), HttpServletResponse.SC_CREATED);
        } catch (SQLException e) {
            handleException(response, e, "Error saving SitePage");
        } catch (Exception e) {
            handleException(response, e, "Error processing POST request");
        }
    }

    private StringBuilder readRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb;
    }

    private void sendSuccessResponse(HttpServletResponse response, String message, int statusCode) throws IOException {
        writeResponse(response, message, statusCode, "application/json");
    }

    void handleException(HttpServletResponse response, Exception e, String logMessage) {
        LOGGER.error(logMessage, e);
        try {
            writeResponse(response, "An internal server error occurred.", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "text/plain");
        } catch (IOException ioException) {
            LOGGER.error("Failed to send error response.", ioException);
        }
    }

    private void writeResponse(HttpServletResponse response, String message, int statusCode, String contentType) throws IOException {
        response.setContentType(contentType);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(statusCode);
        response.getWriter().write(message);
    }

    protected void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    protected void setService(Service<SitePage, Long> service) {
        this.service = service;
    }
}
