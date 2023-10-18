package org.example.repository.mapper;

import org.example.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

@Mapper
public interface UserResultSetMapper {
    UserResultSetMapper INSTANCE = Mappers.getMapper(UserResultSetMapper.class);

    default UserEntity map(ResultSet resultSet) throws SQLException {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(resultSet.getLong("id"));
        userEntity.setName(resultSet.getString("name"));
        userEntity.setSurname(resultSet.getString("surname"));
        userEntity.setAddress(resultSet.getString("address"));
        return userEntity;
    }
}
