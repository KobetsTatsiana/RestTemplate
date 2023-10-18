package org.example.service.impl;

import org.example.model.SitePage;
import org.example.repository.Repository;
import org.example.service.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SitePageServiceImpl implements Service<SitePage, Long> {
    private final Repository<SitePage, Long> repository;

    public SitePageServiceImpl(Repository<SitePage, Long> repository) {
        this.repository = repository;
    }

    @Override
    public Repository<SitePage, Long> getRepository() {
        return this.repository;
    }

    @Override
    public Optional<SitePage> save(SitePage sitePage) throws SQLException {
        repository.save(sitePage);
        return Optional.of(sitePage);
    }

    @Override
    public Optional<SitePage> findById(Long aLong) throws SQLException {
        return repository.findById(aLong);
    }

    @Override
    public List<SitePage> findAll() throws SQLException {
        return repository.findAll();
    }

    @Override
    public boolean delete(Long aLong) throws SQLException {
        return repository.deleteById(aLong);
    }
}

