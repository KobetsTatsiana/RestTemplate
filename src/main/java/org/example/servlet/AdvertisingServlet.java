package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.db.ConnectionManager;
import org.example.db.HikariCPDataSource;
import org.example.model.Advertising;
import org.example.repository.Repository;
import org.example.repository.impl.AdvertisingRepositoryImpl;
import org.example.service.Service;
import org.example.service.impl.AdvertisingServiceImpl;
import org.example.servlet.dto.advertising.AdvertisingIncomingDTO;
import org.example.servlet.dto.advertising.AdvertisingAllOutGoingDTO;
import org.example.servlet.dto.advertising.mapper.AdvertisingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "AdvertisingServlet", value = "/advertisingServlet")
public class AdvertisingServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdvertisingServlet.class);
    private ObjectMapper mapper = new ObjectMapper();
    private final ConnectionManager connectionManager;
    private final Repository<Advertising, Long> advertisingRepository;
    private transient Service<Advertising, Long> service;

    public AdvertisingServlet() {
        this.connectionManager = new HikariCPDataSource();
        this.advertisingRepository = new AdvertisingRepositoryImpl(connectionManager);
        this.service = new AdvertisingServiceImpl(advertisingRepository);
    }

    public AdvertisingServlet(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        this.advertisingRepository = new AdvertisingRepositoryImpl(connectionManager);
        this.service = new AdvertisingServiceImpl(advertisingRepository);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Advertising> advertisingList = service.findAll();
            AdvertisingAllOutGoingDTO advertisingAllOutGoingDTO = AdvertisingMapper.INSTANCE.mapListToDto(advertisingList);
            String jsonString = mapper.writeValueAsString(advertisingAllOutGoingDTO);
            sendSuccessResponse(response, "GetAll Advertising: " + jsonString, HttpServletResponse.SC_OK);
        } catch (SQLException e) {
            handleException(response, e, "Error retrieving all AdvertisingList");
        } catch (Exception e) {
            handleException(response, e, "Error processing GET request");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            String json = readRequestBody(request).toString();
            AdvertisingIncomingDTO advertisingIncomingDTO = mapper.readValue(json, AdvertisingIncomingDTO.class);
            Advertising advertising = AdvertisingMapper.INSTANCE.map(advertisingIncomingDTO);
            service.save(advertising);
            sendSuccessResponse(response, "Added Advertising ID: " + advertising.getId(), HttpServletResponse.SC_CREATED);
        } catch (SQLException e) {
            handleException(response, e, "Error saving Advertising");
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

    protected void setService(Service<Advertising, Long> service) {
        this.service = service;
    }
}
