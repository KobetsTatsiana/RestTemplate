package org.example.repository.mapper;

import org.example.model.UserSite;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

@Mapper
public interface UserSiteResultSetMapper {
    UserSiteResultSetMapper INSTANCE = Mappers.getMapper(UserSiteResultSetMapper.class);

    default UserSite map(ResultSet resultSet) throws SQLException {
        UserSite userSite = new UserSite();
        userSite.setId(resultSet.getLong("id"));
        userSite.setNameSite(resultSet.getString("namesite"));
        return userSite;
    }
}
