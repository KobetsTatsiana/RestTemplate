package org.example.repository.mapper;

import org.example.model.Advertising;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

@Mapper
public interface AdvertisingResultSetMapper {

    AdvertisingResultSetMapper INSTANCE = Mappers.getMapper(AdvertisingResultSetMapper.class);

    default Advertising map(ResultSet resultSet) throws SQLException {

        Advertising advertising = new Advertising();
        Long id = resultSet.getLong("id");
        if (resultSet.wasNull()) {
            advertising.setId(null);
        } else {
            advertising.setId(id);
        }
        advertising.setInfoText(resultSet.getString("infoText"));
        return advertising;
    }
}