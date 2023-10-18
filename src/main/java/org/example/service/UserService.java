package org.example.service;

import org.example.model.UserEntity;
import org.example.model.UserSite;

import java.sql.SQLException;
import java.util.List;

public interface UserService extends Service<UserEntity, Long>  {

    List<UserSite> findSitesAll() throws SQLException;
}
