package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.db.ConnectionManager;
import org.example.db.HikariCPDataSource;
import org.example.model.UserEntity;
import org.example.repository.Repository;
import org.example.repository.UserRepository;
import org.example.repository.impl.UserRepositoryImpl;
import org.example.service.Service;
import org.example.service.impl.UserServiceImpl;
import org.example.servlet.dto.user.EntityAllOutGoingDTO;
import org.example.servlet.dto.user.mapper.UserDtoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@WebServlet(name = "UserServlet", value = "/userServlet")
public class UserServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserIdServlet.class);
    private ObjectMapper mapper = new ObjectMapper();
    private final ConnectionManager connectionManager;
    private final UserRepository<UserEntity, Long> repository;

    private transient Service<UserEntity, Long> service;

    public UserServlet() {
        this.connectionManager = new HikariCPDataSource();
        this.repository = new UserRepositoryImpl(this.connectionManager);
        this.service = new UserServiceImpl(repository);
    }

    public UserServlet(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        this.repository = new UserRepositoryImpl(this.connectionManager);
        this.service = new UserServiceImpl(repository);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<UserEntity> userEntityList = service.findAll();
            String jsonString = mapper.writeValueAsString(userEntityList);
            sendSuccessResponse(response, "GetAll User:" + jsonString, HttpServletResponse.SC_OK);
        } catch (SQLException e) {
            handleException(response, e, "Failed to fetch all userEntityList");
        } catch (Exception e) {
            handleException(response, e, "Failed to process GET request");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            String json = stringJson(request).toString();
            UserEntity userEntity = mapper.readValue(json, UserEntity.class);
            service.save(userEntity);
            sendSuccessResponse(response, "Added User ID:" +
                    userEntity.getId(), HttpServletResponse.SC_CREATED);
        } catch (SQLException e) {
            handleException(response, e, "Failed to save the UserEntity");
        } catch (Exception e) {
            handleException(response, e, "Failed to process POST request");
        }
    }

    private StringBuilder stringJson(HttpServletRequest request) throws IOException {
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
            writeResponse(response, "An internal server error occurred.",
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "text/plain");
        } catch (IOException ioException) {
            LOGGER.error("Failed to send error response.", ioException);
        }
    }

    private void writeResponse(HttpServletResponse response, String message, int statusCode,
                               String contentType) throws IOException {
        response.setContentType(contentType);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(statusCode);
        response.getWriter().write(message);
    }

    protected void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    protected void setService(Service<UserEntity, Long> service) {
        this.service = service;
    }
}
