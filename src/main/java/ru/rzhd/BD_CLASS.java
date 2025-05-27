package ru.rzhd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class BD_CLASS {
    private static final Logger logger = LoggerFactory.getLogger(BD_CLASS.class);
    private static HikariDataSource dataSource;
    
    static {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(System.getenv("DB_URL"));
            config.setUsername(System.getenv("DB_USER"));
            config.setPassword(System.getenv("DB_PASS"));
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(5);
            config.setIdleTimeout(300000);
            config.setConnectionTimeout(20000);
            config.setAutoCommit(true);
            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
            logger.error("Failed to initialize connection pool", e);
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    public void setInfo(List<EKASUI> datas) throws SQLException {
        String query = "SELECT * FROM EKASUI";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                datas.add(new EKASUI(
                    rs.getInt("RoadCode"),
                    rs.getInt("RegionCode"), 
                    rs.getString("AreaCode"),
                    rs.getString("StationStartName"),
                    rs.getString("StationFinishName"),
                    rs.getInt("ObjectCode"),
                    rs.getBoolean("ResultState")
                ));
            }
        } catch (SQLException e) {
            logger.error("Failed to fetch EKASUI data", e);
            throw e;
        }
    }

    public void fillData(String areaCode) throws SQLException {
        String query = "UPDATE EKASUI SET ResultState = ? WHERE AreaCode = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setBoolean(1, true);
            stmt.setString(2, areaCode);
            stmt.executeUpdate();
            
            logger.info("Updated ResultState for AreaCode: {}", areaCode);
        } catch (SQLException e) {
            logger.error("Failed to update ResultState for AreaCode: {}", areaCode, e);
            throw e;
        }
    }

    public void SendError(String roadCode, String regionCode, String areaCode, 
                         String km, String stationStart, String stationFinish, 
                         String nbType) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try {
                // Insert into MobileWorkspace
                String insertQuery = "INSERT INTO MobileWorkspace(RoadCode,RegionCode,AreaCode,Kilometr," +
                                   "StationStartName,StationFinishName,nbtype) VALUES(?,?,?,?,?,?,?)";
                try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
                    stmt.setInt(1, Integer.parseInt(roadCode));
                    stmt.setInt(2, Integer.parseInt(regionCode));
                    stmt.setString(3, areaCode);
                    stmt.setInt(4, Integer.parseInt(km));
                    stmt.setString(5, stationStart);
                    stmt.setString(6, stationFinish);
                    stmt.setInt(7, Integer.parseInt(nbType));
                    stmt.executeUpdate();
                }

                // Delete from EKASUI
                String deleteQuery = "DELETE FROM EKASUI WHERE AreaCode = ?";
                try (PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {
                    stmt.setString(1, areaCode);
                    stmt.executeUpdate();
                }

                conn.commit();
                logger.info("Successfully processed error report for AreaCode: {}", areaCode);
            } catch (Exception e) {
                conn.rollback();
                logger.error("Failed to process error report for AreaCode: {}", areaCode, e);
                throw e;
            }
        }
    }

    public static void shutdown() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}