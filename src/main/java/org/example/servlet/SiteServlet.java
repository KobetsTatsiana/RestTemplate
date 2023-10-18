package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import org.example.db.ConnectionManager;
import org.example.db.HikariCPDataSource;
import org.example.model.UserEntity;
import org.example.repository.UserRepository;
import org.example.repository.impl.UserRepositoryImpl;

import org.example.service.UserService;
import org.example.service.impl.UserServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@WebServlet(name = "SiteServlet", value = "/siteServlet")
public class SiteServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(SiteServlet.class);
    private ObjectMapper mapper = new ObjectMapper();
    private final ConnectionManager connectionManager;
    private final UserRepository<UserEntity, Long> repository;
    private transient UserService service;

    public SiteServlet() {
        this.connectionManager = new HikariCPDataSource();
        this.repository = new UserRepositoryImpl(this.connectionManager);
        this.service = new UserServiceImpl(repository);
    }

    public SiteServlet(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        this.repository = new UserRepositoryImpl(this.connectionManager);
        this.service = new UserServiceImpl(repository);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<UserEntity> userEntityList = service.findAll();
            String jsonString = mapper.writeValueAsString(userEntityList);
            sendSuccessResponse(response, "GetAll UserEntity:" + jsonString );
        } catch (SQLException e) {
            handleException(response, e, "Failed to fetch all userEntityList");
        } catch (Exception e) {
            handleException(response, e, "Failed to process GET request");
        }
    }


    private void sendSuccessResponse(HttpServletResponse response, String message) throws IOException {
        writeResponse(response, message, HttpServletResponse.SC_OK, "application/json");
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

}
