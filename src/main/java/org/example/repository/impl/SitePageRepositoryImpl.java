package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.model.Advertising;
import org.example.model.SitePage;
import org.example.repository.Repository;
import org.example.repository.mapper.SitePageResultSetMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class SitePageRepositoryImpl implements Repository<SitePage, Long> {
    private final ConnectionManager connectionManager;

    public SitePageRepositoryImpl(ConnectionManager connectionManager, Repository<Advertising, Long> advertisingRepository) {
        this.connectionManager = connectionManager;
        this.advertisingRepository = advertisingRepository;
    }

    private final Repository<Advertising, Long> advertisingRepository;

    @Override
    public Optional<SitePage> findById(Long l) throws SQLException {
        SitePage sitePage = null;

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement
                     ("SELECT * FROM SitePage WHERE id = ?")) {
            preparedStatement.setObject(1, l);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    sitePage = SitePageResultSetMapper.INSTANCE.map(rs);
                }
            }
            if (sitePage != null) {
                List<Advertising> advertisingList = new ArrayList<>();
                try (PreparedStatement advertisingStatement = connection.prepareStatement
                        ("SELECT p.id AS sitepage_id, p.namepage, a.id AS advertising_id, a.infotext FROM SitePage p " +
                                "LEFT JOIN SitePage_Advertising pa ON p.id = pa.sitepage_id " +
                                "LEFT JOIN Advertising a ON pa.advertising_id = a.id " +
                                "WHERE p.id = ? " +
                                "ORDER BY p.id")) {

                    advertisingStatement.setObject(1, l);
                    try (ResultSet advertisingResultSet = advertisingStatement.executeQuery()) {
                        while (advertisingResultSet.next()) {
                            Advertising advertising = new Advertising();
                            advertising.setId(Long.parseLong(advertisingResultSet.getString("advertising_id")));
                            advertising.setInfoText(advertisingResultSet.getString("infotext"));
                            advertisingList.add(advertising);
                        }
                    }
                }
                sitePage.setAdvertisingList(advertisingList);
            }
        }
        return Optional.ofNullable(sitePage);
    }

    @Override
    public boolean deleteById(Long l) throws SQLException {
        try (Connection connection = connectionManager.getConnection()) {
            connection.setAutoCommit(false);
            try (
                    PreparedStatement sitePageAdvertisingStatement = connection.prepareStatement
                            ("DELETE FROM SitePage_Advertising WHERE sitepage_id = ?");
                    PreparedStatement sitePageStatement = connection.prepareStatement
                            ("DELETE FROM SitePage WHERE id = ?")) {

                sitePageAdvertisingStatement.setObject(1, l);
                sitePageAdvertisingStatement.executeUpdate();

                sitePageStatement.setObject(1, l);
                int affectedRows = sitePageStatement.executeUpdate();
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
    public List<SitePage> findAll() throws SQLException {
        List<SitePage> sitePageList = new ArrayList<>();
        HashMap<Long, SitePage> map = new HashMap<>();

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement
                     ("SELECT p.id AS sitepage_id, p.namepage, a.id AS advertising_id, a.infotext " +
                             "FROM SitePage p " +
                             "LEFT JOIN SitePage_Advertising pa ON p.id = pa.sitepage_id " +
                             "LEFT JOIN Advertising a ON pa.advertising_id = advertising_id " +
                             "ORDER BY p.id")) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Long sitePageId = Long.parseLong(rs.getString("sitepage_id"));
                    SitePage sitePage = map.getOrDefault(sitePageId, new SitePage());
                    sitePage.setId(sitePageId);
                    sitePage.setNamePage(rs.getString("namepage"));

                    if (rs.getString("advertising_id") != null) {
                        Advertising advertising = new Advertising();
                        advertising.setId(Long.parseLong(rs.getString("advertising_id")));
                        advertising.setInfoText(rs.getString("infotext"));
                        if (sitePage.getAdvertisingList() == null) {
                            sitePage.setAdvertisingList(new ArrayList<>());
                        }
                        sitePage.getAdvertisingList().add(advertising);
                    }
                    map.put(sitePageId, sitePage);
                }
            }
            sitePageList = new ArrayList<>(map.values());
        }
        return sitePageList;
    }

    @Override
    public Optional<SitePage> save(SitePage sitePage) throws SQLException {
        try (Connection connection = connectionManager.getConnection()) {
            connection.setAutoCommit(false);

            List<Advertising> advertisingList = sitePage.getAdvertisingList();
            if (advertisingList != null) {
                for (Advertising advertising : advertisingList) {
                    advertisingRepository.save(advertising);
                }
            }

            String insertWithID = "INSERT INTO SitePage (id, namepage) VALUES (?, ?) ON CONFLICT (id) DO UPDATE SET namepage = EXCLUDED.namepage";
            String insertWithoutID = "INSERT INTO SitePage (namepage) VALUES (?)";

            if (sitePage.getId() != null) {
                try (PreparedStatement sitePageStatement = connection.prepareStatement(insertWithID)) {
                    sitePageStatement.setObject(1, sitePage.getId());
                    sitePageStatement.setString(2, sitePage.getNamePage());
                    sitePageStatement.executeUpdate();
                }
            } else {
                try (PreparedStatement sitePageStatement = connection.prepareStatement(insertWithoutID)) {
                    sitePageStatement.setString(1, sitePage.getNamePage());
                    sitePageStatement.executeUpdate();
                }
            }

            if (advertisingList != null) {
                for (Advertising advertising : advertisingList) {
                    try (PreparedStatement sitePageAdvertisingStatement = connection.prepareStatement
                            ("INSERT INTO SitePage_Advertising (sitepage_id, advertising_id) VALUES (?, ?)" +
                                    " ON CONFLICT DO NOTHING")) {
                        sitePageAdvertisingStatement.setObject(1, sitePage.getId());
                        sitePageAdvertisingStatement.setObject(2, advertising.getId());
                        sitePageAdvertisingStatement.executeUpdate();
                    }
                }
            }
            connection.commit();
            return Optional.of(sitePage);
        }
    }

    @Override
    public void clearAll() throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM SitePage")) {
            preparedStatement.executeUpdate();
        }
    }
}
