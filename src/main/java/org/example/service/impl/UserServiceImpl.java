package org.example.service.impl;

import org.example.model.UserEntity;
import org.example.model.UserSite;
import org.example.repository.Repository;
import org.example.repository.UserRepository;
import org.example.service.Service;
import org.example.service.UserService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserRepository<UserEntity, Long> repository;

    public UserServiceImpl(UserRepository<UserEntity, Long> repository) {
        this.repository = repository;
    }

    @Override
    public Optional<UserEntity> save(UserEntity userEntity) throws SQLException {
        repository.save(userEntity);
        return Optional.of(userEntity);
    }

    @Override
    public Optional<UserEntity> findById(Long id) throws SQLException {
        return repository.findById(id);
    }

    @Override
    public List<UserEntity> findAll() throws SQLException {
        return repository.findAll();
    }

    @Override
    public boolean delete(Long aLong) throws SQLException {
        return repository.deleteById(aLong);
    }
    @Override
    public UserRepository<UserEntity, Long> getRepository() {
        return this.repository;
    }

    @Override
    public List<UserSite> findSitesAll() throws SQLException {
        return repository.findSitesAll();
    }
}


