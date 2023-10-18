package org.example.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet(name = "AdvertisingIdServlet", value = "/advertisingId/*")
public class AdvertisingIdServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdvertisingIdServlet.class);
    private final ObjectMapper mapper = new ObjectMapper();
    private final ConnectionManager connectionManager;
    private final Repository<Advertising, Long> advertisingRepository;
    private Service<Advertising, Long> service;

    public AdvertisingIdServlet() {
        this.connectionManager = new HikariCPDataSource();
        this.advertisingRepository = new AdvertisingRepositoryImpl(connectionManager);
        this.service = new AdvertisingServiceImpl(advertisingRepository);
    }

    public AdvertisingIdServlet(ConnectionManager connectionManager, Repository<Advertising, Long> advertisingRepository) {
        this.connectionManager = connectionManager;
        this.advertisingRepository = advertisingRepository;
        this.service = new AdvertisingServiceImpl(advertisingRepository);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String id = extractIdFromRequest(request);
            if (id == null) {
                sendBadRequest(response, "Invalid ID format");
                return;
            }
            processGetRequest(id, response);
        } catch (Exception e) {
            handleException(response, e, "Failed to process GET request");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String id = extractIdFromRequest(request);
            if (id == null) {
                sendBadRequest(response, "Invalid ID format");
                return;
            }
            processPutRequest(id, request, response);
        } catch (Exception e) {
            handleException(response, e, "Failed to process PUT request");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String pathInfo = request.getPathInfo();
            if (pathInfo != null && !pathInfo.isEmpty()) {
                String[] pathParts = pathInfo.split("/");
                if (pathParts.length > 1) {
                    Long entityID = Long.valueOf(pathParts[pathParts.length - 1]);
                    service.delete(entityID);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write("Deleted Advertising ID:" + entityID);
                    response.setStatus(HttpServletResponse.SC_OK);
                }
            }
        } catch (Exception e) {
            handleException(response, e, "Failed to process DELETE request");
        }
    }

    private String extractIdFromRequest(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.isEmpty()) {
            return null;
        }
        String[] pathParts = pathInfo.split("/");
        return pathParts.length > 1 ? String.valueOf(Long.valueOf(pathParts[pathParts.length - 1])) : null;
    }

    private void processGetRequest(String ID, HttpServletResponse response) throws IOException, SQLException {
        setResponseDefaults(response);

        Long id;
        try {
            id = Long.valueOf(ID);
        } catch (IllegalArgumentException e) {
            sendBadRequest(response, "Invalid ID format");
            return;
        }

        Optional<Advertising> advertisingOpt = service.findById(id);
        if (!advertisingOpt.isPresent()) {
            sendNotFound(response);
            return;
        }
        Advertising advertising = advertisingOpt.get();
        String jsonString = mapper.writeValueAsString(advertising);
        response.getWriter().write(jsonString);
    }

    private void processPutRequest(String ID, HttpServletRequest request, HttpServletResponse response) throws IOException {
        setResponseDefaults(response);

        Long id;
        try {
            id = Long.valueOf(ID);
        } catch (IllegalArgumentException e) {
            sendBadRequest(response, "Invalid ID format");
            return;
        }

        StringBuilder sb = getStringFromRequest(request);
        Advertising advertising;
        try {
            advertising = mapper.readValue(sb.toString(), Advertising.class);
        } catch (JsonProcessingException e) {
            sendBadRequest(response, "Invalid JSON format");
            return;
        }

        advertising.setId(id);

        try {
            service.save(advertising);
            response.getWriter().write("Advertising updated successfully");
        } catch (SQLException e) {
            LOGGER.error("Failed to save Advertising", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void sendBadRequest(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write(message);
    }

    private void sendNotFound(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        response.getWriter().write("Advertising not found");
    }

    private void setResponseDefaults(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    private StringBuilder getStringFromRequest(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb;
    }

    private void handleException(HttpServletResponse response, Exception e, String logMessage) throws IOException {
        LOGGER.error(logMessage, e);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("An internal server error occurred.");
    }

    protected void setService(Service<Advertising, Long> service) {
        this.service = service;
    }
}
