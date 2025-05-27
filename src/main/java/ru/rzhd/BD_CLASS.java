package ru.rzhd;
import java.sql.Connection;
import java.sql.DriverManager;
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
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(System.getenv("DB_URL"));
        config.setUsername(System.getenv("DB_USER"));
        config.setPassword(System.getenv("DB_PASS"));
        config.setMaximumPoolSize(10);
        dataSource = new HikariDataSource(config);
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
            logger.error("Database error", e);
            throw e;
        }
    }

    // Similar improvements for other methods...
}