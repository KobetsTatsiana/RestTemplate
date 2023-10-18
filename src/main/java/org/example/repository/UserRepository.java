package org.example.repository;

import org.example.model.UserEntity;
import org.example.model.UserSite;

import java.sql.SQLException;
import java.util.List;

public interface UserRepository<T, K> extends Repository<UserEntity, Long> {

    List<UserSite> findSitesAll() throws SQLException;
}
