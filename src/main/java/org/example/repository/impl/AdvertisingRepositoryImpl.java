package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.model.Advertising;
import org.example.model.SitePage;
import org.example.repository.Repository;
import org.example.repository.mapper.AdvertisingResultSetMapper;
import org.example.repository.mapper.SitePageResultSetMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AdvertisingRepositoryImpl implements Repository<Advertising, Long> {
    private final ConnectionManager connectionManager;

    public AdvertisingRepositoryImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public Optional<Advertising> findById(Long aLong) throws SQLException {
        Advertising advertising = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement tagStatement = connection.prepareStatement("SELECT * FROM advertising WHERE id = ?")) {
            tagStatement.setObject(1, aLong);
            ResultSet rs = tagStatement.executeQuery();
            if (rs.next()) {
                advertising = AdvertisingResultSetMapper.INSTANCE.map(rs);
            }
            if (advertising != null) {
                try (PreparedStatement pageStatement = connection.prepareStatement("SELECT b.* FROM sitepage b JOIN sitepage_advertising bt ON b.id = bt.sitepage_id WHERE bt.advertising_id = ?")) {
                    pageStatement.setObject(1, aLong);
                    ResultSet pageResultSet = pageStatement.executeQuery();
                    List<SitePage> sitePageList = new ArrayList<>();
                    while (pageResultSet.next()) {
                        SitePage sitePage = SitePageResultSetMapper.INSTANCE.map(pageResultSet);
                        sitePageList.add(sitePage);
                    }
                    advertising.setSitePageList(sitePageList);
                }
            }
        }
        return Optional.ofNullable(advertising);
    }

    @Override
    public boolean deleteById(Long id) throws SQLException {
        try (Connection connection = connectionManager.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement sitePageAdvertisingStatement = connection.prepareStatement
                    ("DELETE FROM SitePage_Advertising WHERE advertising_id = ?");
                 PreparedStatement advertisingStatement = connection.prepareStatement
                         (" DELETE FROM Advertising WHERE id = ?")) {
                sitePageAdvertisingStatement.setObject(1, id);
                sitePageAdvertisingStatement.executeUpdate();
                advertisingStatement.setObject(1, id);
                int affectedRows = advertisingStatement.executeUpdate();
                if (affectedRows == 0) {
                    connection.rollback();
                    return false;
                }
                connection.commit();
            }
        }
        return true;
    }

    @Override
    public List<Advertising> findAll() throws SQLException {
        List<Advertising> advertisingList = new ArrayList<>();
        Map<Long, Advertising> advertisingMap = new HashMap<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Advertising")) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Advertising advertising = AdvertisingResultSetMapper.INSTANCE.map(resultSet);
                advertisingList.add(advertising);
                advertisingMap.put(advertising.getId(), advertising);
            }

            try (PreparedStatement sitePageStatement = connection.prepareStatement
                    ("SELECT p.*, pa.advertising_id FROM SitePage p " +
                            "JOIN SitePage_Advertising pa ON p.id = pa.sitepage_id")) {
                ResultSet sitePageResultSet = sitePageStatement.executeQuery();

                while (sitePageResultSet.next()) {
                    Long advertisingId = (Long) sitePageResultSet.getObject("advertising_id");
                    Advertising relatedAdvertising = advertisingMap.get(advertisingId);
                    if (relatedAdvertising != null) {
                        SitePage sitePage = SitePageResultSetMapper.INSTANCE.map(sitePageResultSet);
                        if (relatedAdvertising.getSitePageList() == null) {
                            relatedAdvertising.setSitePageList(new ArrayList<>());
                        }
                        relatedAdvertising.getSitePageList().add(sitePage);
                    }
                }
            }
        }
        return advertisingList;
    }

    @Override
    public Optional<Advertising> save(Advertising advertising) throws SQLException {
        try (Connection connection = connectionManager.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement advertisingStatement = connection.prepareStatement("INSERT INTO Advertising (id, infotext) VALUES (?, ?) ON CONFLICT (id) DO UPDATE SET infotext = EXCLUDED.infotext")) {
                advertisingStatement.setObject(1, advertising.getId());
                advertisingStatement.setString(2, advertising.getInfoText());
                advertisingStatement.executeUpdate();
            }
            if (advertising.getSitePageList() != null && !advertising.getSitePageList().isEmpty()) {
                try (PreparedStatement pageStatement = connection.prepareStatement("INSERT INTO sitepage (id, namepage) VALUES (?, ?) ON CONFLICT (id) DO UPDATE SET namepage = EXCLUDED.namepage");
                     PreparedStatement pageadvertisingStatement = connection.prepareStatement("INSERT INTO sitepage_advertising (sitepage_id, advertising_id) VALUES (?, ?) ON CONFLICT DO NOTHING")) {
                    for (SitePage sitePage : advertising.getSitePageList()) {
                        pageStatement.setObject(1, sitePage.getId());
                        pageStatement.setString(2, sitePage.getNamePage());

                        pageStatement.addBatch();
                        pageadvertisingStatement.setObject(1, sitePage.getId());
                        pageadvertisingStatement.setObject(2, advertising.getId());
                        pageadvertisingStatement.addBatch();
                    }
                    pageStatement.executeBatch();
                    pageadvertisingStatement.executeBatch();
                }
            }
            connection.commit();
            return Optional.of(advertising);
        }
    }

    @Override
    public void clearAll() throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement
                     ("DELETE FROM Advertising")) {
            preparedStatement.executeUpdate();
        }
    }
}

