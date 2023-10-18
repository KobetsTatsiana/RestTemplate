package org.example.repository.mapper;

import org.example.model.SitePage;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

@Mapper
public interface SitePageResultSetMapper {
    SitePageResultSetMapper INSTANCE = Mappers.getMapper(SitePageResultSetMapper.class);

    default SitePage map(ResultSet resultSet) throws SQLException {
        SitePage sitePage = new SitePage();
        sitePage.setId(resultSet.getLong("id"));
        sitePage.setNamePage(resultSet.getString("namepage"));

        return sitePage;
    }
}