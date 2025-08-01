package ru.rzhd;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
// Import the logger classes
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BD_CLASS {
	private String User;
	private String Pass;
	private String DB_URL;

	private static final Logger logger = LoggerFactory.getLogger(BD_CLASS.class);
	
	public BD_CLASS(String User,String Pass,String DB_URL){
		this.Pass=Pass;
		this.User=User;
		this.DB_URL=DB_URL;
	}
	
	//Our setters ↓
	public void setUser(String user) {
		User = user;
	}


	public void setPass(String pass) {
		Pass = pass;
	}

	//We're setting data to EKASUI class ↓
	public void setInfo(List<EKASUI> datas) throws SQLException {
		logger.info("Testing connection to Postgresql JDBC");
		Connection connection = null;
		
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e) {
				logger.error("Postgresql not found");
				return;
			}
		
				try {
					connection = DriverManager.getConnection(this.DB_URL, this.User, this.Pass);
					PreparedStatement statement=connection.prepareStatement("SELECT * FROM EKASUI");
					ResultSet rs=statement.executeQuery();
					while(rs.next()) {
						int roadCode=rs.getInt("RoadCode");
						int regionCode=rs.getInt("RegionCode");
						String areaCode=rs.getString("AreaCode");
						String stationStartName=rs.getString("StationStartName");
						String stationFinishName=rs.getString("StationFinishName");
						int objectCode=rs.getInt("ObjectCode");
						boolean resultState=rs.getBoolean("ResultState");
						
						datas.add(new EKASUI(roadCode,regionCode,areaCode,stationStartName,stationFinishName,objectCode,resultState));
					}
					
					statement.close();
					rs.close();
					connection.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.err.println("Database error occurred.");
				}
		
		
	}
	
	public void fillData(String areaCode) {
		logger.info("Testing connection to Postgresql JDBC");
		Connection connection = null;
		
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e) {
				logger.error("Postgresql not found", e);
				return;
			}
			
			
			try {
				connection = DriverManager.getConnection(this.DB_URL, this.User, this.Pass);
		
				PreparedStatement statement=connection.prepareStatement("UPDATE EKASUI SET ResultState=(?) WHERE AreaCode=(?)");
				statement.setBoolean(1, true);
				statement.setString(2, areaCode);
				statement.executeUpdate();
				
				statement.close();
				connection.close();
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				logger.error("Failed to close database connection.", e1);
			}
	}
	
	
	public void SendError(String RoadCode,String RegionCode,String sel,String km,String station_start,String station_finish,String nb_type) {
		logger.info("Testing connection to Postgresql JDBC");
		Connection connection = null;
		
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e) {
				logger.error("Postgresql not found",e);
				return;
			}
			
			
			try {
				connection = DriverManager.getConnection(this.DB_URL, this.User, this.Pass);
				PreparedStatement statement=connection.prepareStatement("INSERT INTO MobileWorkspace(RoadCode,RegionCode,AreaCode,Kilometr,StationStartName,StationFinishName,nbtype) VALUES((?),(?),(?),(?),(?),(?),(?))");
				statement.setInt(1, Integer.parseInt(RoadCode));
				statement.setInt(2, Integer.parseInt(RegionCode));
				statement.setString(3, sel);
				statement.setInt(4, Integer.parseInt(km));
				statement.setString(5, station_start);
				statement.setString(6, station_finish);
				statement.setInt(7, Integer.parseInt(nb_type));
				statement.executeUpdate();
				
				
				PreparedStatement smt=connection.prepareStatement("DELETE FROM EKASUI WHERE AreaCode=(?)");
				smt.setString(1, sel);
				smt.execute();
				
				smt.close();
				statement.close();
				connection.close();
			}catch (SQLException e1) {
				// TODO Auto-generated catch block
				 logger.error("Failed to close database connection.", e1);
			}
		
		
		
		
	}

	
	
	
	//end
}
