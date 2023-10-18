package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.model.UserEntity;
import org.example.model.UserSite;
import org.example.repository.UserRepository;
import org.example.repository.mapper.UserResultSetMapper;
import org.example.repository.mapper.UserSiteResultSetMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository<UserEntity, Long> {
    private final ConnectionManager connectionManager;

    public UserRepositoryImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public Optional<UserEntity> findById(Long l) throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name, surname, address  FROM UserEntity WHERE id=?")) {
            preparedStatement.setObject(1, l);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(createUserFromResultSet(resultSet));
                }
                return Optional.empty();
            }
        }
    }

    private UserEntity createUserFromResultSet(ResultSet resultSet) throws SQLException {
        UserEntity userEntity = UserResultSetMapper.INSTANCE.map(resultSet);
        userEntity.setUserSiteList(findSitesByUserId(userEntity.getId()));
        return userEntity;
    }

    private List<UserSite> findSitesByUserId(Long userId) throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT id, user_id, namesite FROM UserSite WHERE user_id = ?")) {
            statement.setObject(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<UserSite> userSiteList = new ArrayList<>();
                while (resultSet.next()) {
                    userSiteList.add(createSiteFromResultSet(resultSet));
                }
                return userSiteList;
            }
        }
    }

    private UserSite createSiteFromResultSet(ResultSet resultSet) throws SQLException {
        return UserSiteResultSetMapper.INSTANCE.map(resultSet);
    }

    @Override
    public List<UserEntity> findAll() throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM UserEntity");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            List<UserEntity> userEntityList = new ArrayList<>();
            while (resultSet.next()) {
                userEntityList.add(createUserFromResultSet(resultSet));
            }
            return userEntityList;
        }
    }

    @Override
    public boolean deleteById(Long l) throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM UserEntity WHERE id=?")) {
            preparedStatement.setObject(1, l);
            return preparedStatement.executeUpdate() > 0;
        }
    }

    @Override
    public Optional<UserEntity> save(UserEntity userEntity) throws SQLException {
        try (Connection connection = connectionManager.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement preparedStatementUser = connection.prepareStatement("INSERT INTO UserEntity (name, surname, address) VALUES (?, ?, ?) RETURNING id", Statement.RETURN_GENERATED_KEYS)) {

                preparedStatementUser.setString(1, userEntity.getName());
                preparedStatementUser.setString(2, userEntity.getSurname());
                preparedStatementUser.setString(3, userEntity.getAddress());
                preparedStatementUser.executeUpdate();

                try (ResultSet generatedKeys = preparedStatementUser.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        userEntity.setId(generatedKeys.getLong(1));
                    }
                }

                if (userEntity.getUserSiteList() != null && !userEntity.getUserSiteList().isEmpty()) {
                    try (PreparedStatement preparedStatementSite = connection.prepareStatement("INSERT INTO UserSite (user_id, namesite) VALUES (?, ?) " +
                            "  ON CONFLICT (id) DO UPDATE SET user_id = EXCLUDED.user_id, namesite = EXCLUDED.namesite")) {
                        for (UserSite userSite : userEntity.getUserSiteList()) {
                            preparedStatementSite.setObject(1, userEntity.getId());
                            preparedStatementSite.setString(2, userSite.getNameSite());
                            preparedStatementSite.addBatch();
                        }
                        preparedStatementSite.executeBatch();
                    }
                }

                connection.commit();

                return Optional.of(userEntity);
            }
        }
    }

    @Override
    public List<UserSite> findSitesAll() throws SQLException {
        List<UserSite> userSiteList = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM UserSite");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                UserSite userSite = UserSiteResultSetMapper.INSTANCE.map(resultSet);
                userSiteList.add(userSite);
            }
        }
        return userSiteList;
    }

    @Override
    public void clearAll() throws SQLException {
        try (Connection connection = connectionManager.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement deleteSites = connection.prepareStatement("DELETE FROM UserSite")) {
                deleteSites.executeUpdate();
            }
            try (PreparedStatement deleteUsers = connection.prepareStatement("DELETE FROM UserEntity")) {
                deleteUsers.executeUpdate();
            }
            connection.commit();
        }
    }
}