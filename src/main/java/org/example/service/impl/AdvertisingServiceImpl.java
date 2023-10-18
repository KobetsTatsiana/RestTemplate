package org.example.service.impl;

import org.example.model.Advertising;
import org.example.repository.Repository;
import org.example.service.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AdvertisingServiceImpl implements Service<Advertising, Long> {
    private final Repository<Advertising, Long> repository;

    public AdvertisingServiceImpl(Repository<Advertising, Long> repository) {
        this.repository = repository;
    }

    @Override
    public Repository<Advertising, Long> getRepository() {
        return this.repository;
    }

    @Override
    public Optional<Advertising> save(Advertising advertising) throws SQLException {
        return repository.save(advertising);
    }

    @Override
    public Optional<Advertising> findById(Long aLong) throws SQLException {
        return repository.findById(aLong);
    }

    @Override
    public List<Advertising> findAll() throws SQLException {
        return repository.findAll();
    }

    @Override
    public boolean delete(Long aLong) throws SQLException {
        return repository.deleteById(aLong);
    }
}
