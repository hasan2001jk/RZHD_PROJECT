package ru.rzhd;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParserClass {
	private String URL;
	private String User;
	private String Pass;
	
	// Setters are here ↓
	public void setURL(String uRL) {
		URL = uRL;
	}
	public void setUser(String user) {
		User = user;
	}
	public void setPass(String pass) {
		Pass = pass;
	}
	
	public ParserClass(String URL,String User,String Pass){
		this.URL=URL;
		this.User=User;
		this.Pass=Pass;
		
	}
	
	public void ParsePage(List<Integer>NumberStat,List<String> Areas,List<String> AreasNums,List<String> AreaLinks,List<Integer> StationNums,List<String> StationName ) throws IOException {
			String url =this.URL;
			Document doc = Jsoup.connect(url)
	            .userAgent("Chrome/4.0.249.0 Safari/532.5")
	            .referrer("http://www.google.com")
	            .get();
			
			Element body=doc.body();
			Elements tablesch=body.select("table");
			Elements tbody =tablesch.select("tbody");
			Elements tr =tbody.select("tr");
			Elements td =tr.select("td");
			System.out.println("OLA");
			System.out.println(td.get(0));
			/*
			for(Element i : td) {
				System.out.println(i);
			}
			 */
			String urlies ="https://cssrzd.ru/tr4/book1/kbsh.php";
			Document docies = Jsoup.connect(urlies)
	            .userAgent("Chrome/4.0.249.0 Safari/532.5")
	            .referrer("http://www.google.com")
	            .get();
			/*
			List<String> Areas =new ArrayList<String>();
			List<String> AreasNums =new ArrayList<String>();
			List<String> AreaLinks =new ArrayList<String>();
			List<Integer> StationNums=new ArrayList<Integer>();
			List<String> StationName = new ArrayList<String>();
			List<Integer> NumberStat = new ArrayList<Integer>();
			*/
			Element bodies=docies.body();
			Elements tableschies=bodies.select("table");
			Elements tbodyies =tableschies.last().select("tbody");
			Elements tries =tbodyies.select("tr");
			Elements tdies =tries.select("td").next();
			
			
			Elements p=tdies.select("p");
	
			for(int i=0;i<p.size();i++) {
				if(p.get(i).select("a").hasAttr("href")) {
					AreaLinks.add("https://cssrzd.ru/tr4/book1/"+p.get(i).select("a").attr("href"));
				}
			}
			
			Elements a=p.select(".district_name");
		
			//We're adding the first necessary row in our array ↓ 
			for(int i=0;i<tdies.size()-2;) {
				Element nes=tdies.get(i);
				AreasNums.add(nes.text());
				i=i+3;
			}
			
			
			//We're adding the second necessary row in our array ↓
			for(Element f:a) {
				Areas.add(f.text());
			}
			
			/*	
			//Display the first row and size() ↓
			System.out.println(AreasNums.size());
			System.out.println();
			*/
			for(String i : AreasNums) {
				System.out.println(i);
			}
			/*
			//Display the second row and size() ↓
			System.out.println();
			System.out.println();
			for(String i : Areas) {
				System.out.println(i);
			}	
			
			//Display links of area's stations ↓
			for(String g : AreaLinks) {
				System.out.println(g);
			}
			*/
			
			int iter=1;
			//here we're getting area's stations ↓ §♀♪►‼◘◄
			for(int k=0;k<AreaLinks.size();k++) {
				Document document = Jsoup.connect(AreaLinks.get(k))
			            .userAgent("Chrome/4.0.249.0 Safari/532.5")
			            .referrer("http://www.google.com")
			            .get();
					
				Element body_s=document.body();
				Elements tablesch_s=body_s.select("table");
				Elements tbody_s =tablesch_s.last().select("tbody");
				Elements tr_s =tbody_s.select("tr");
				Elements td_s =tr_s.select("td");
				Elements st_n =td_s.select(".station");
				Elements num_st = td_s.not(".station");
				//Here are station nums ↓
				for(int jk=0;jk<num_st.size()-1;jk++) {
					//System.out.println(num_st.get(jk).firstElementSibling().text());
					StationNums.add(Integer.parseInt(num_st.get(jk).firstElementSibling().text()));
					jk++;
				}
				System.out.println();
				//Here are station names ↓
				
				for(Element i:st_n) {
					//System.out.println(i.text());
					StationName.add(i.text());
					NumberStat.add(iter);
				}
				
				//Checking 
				/*for(String i : StationName) {
					System.out.println(i);
					
				}*/
				
				iter++;
			}
			
		
	}
	


	public void fill_db(List<Integer>NumberStat,List<String> Areas,List<String> AreasNums,List<String> AreaLinks,List<Integer> StationNums,List<String> StationName ) throws SQLException {
		String DB_URL = "jdbc:postgresql://localhost:5432/RZHD";
		String User =this.User;
		String Pass =this.Pass;
		System.out.println("Testing connection to Postgresql JDBC");
		Connection connection = null;
		
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Postgresql not found");
			e.printStackTrace();
			return;
		}
		
		try {
			connection = DriverManager.getConnection(DB_URL, User, Pass);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			try {
				
				
				PreparedStatement statement=connection.prepareStatement("INSERT INTO Area(AreaId,AreaCode,AreaName) VALUES((?),(?),(?))");
				for(int i=0;i<AreasNums.size();i++) {
					statement.setInt(1, i+1);
					statement.setString(2, AreasNums.get(i));
					statement.setString(3, Areas.get(i));
					statement.executeUpdate();
				}
		
				statement.close();
				
				PreparedStatement smt=connection.prepareStatement("INSERT INTO AreaStation(AreaId,AreaStationCode,StationName) VALUES((?),(?),(?))");
				for(int i=0;i<NumberStat.size();i++) {
					smt.setInt(1, NumberStat.get(i));
					smt.setInt(2, StationNums.get(i));
					smt.setString(3, StationName.get(i));
					smt.executeUpdate();
				}
			
				smt.close();
				connection.close();
			}catch(SQLException se) {
				se.printStackTrace();
			}
			
			
	}
	
	public boolean check_db() throws SQLException {
		String DB_URL = "jdbc:postgresql://localhost:5432/RZHD";
		String User =this.User;
		String Pass =this.Pass;
		System.out.println("Testing connection to Postgresql JDBC");
		Connection connection = null;
		
		String[] table_name=new String[4];
		boolean db_is_empty=false;
		int count_db=0;
		
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Postgresql not found");
			e.printStackTrace();
		}
		
		try {
			connection = DriverManager.getConnection(DB_URL, User, Pass);
			PreparedStatement smt = connection.prepareStatement("SELECT table_name\r\n"
					+ "  FROM information_schema.tables\r\n"
					+ " WHERE table_schema='public' AND table_type='BASE TABLE'");
			ResultSet rs=smt.executeQuery();
			int iter=0;
			while(rs.next()) {
				
				String tab_name=rs.getString(1);
				System.out.println(tab_name);
				table_name[iter]=tab_name;
				iter++;
			}
			System.out.println();
			rs.close();
			smt.close();
			
			for(int i=0;i<table_name.length;i++) {
				PreparedStatement state=connection.prepareStatement("SELECT * FROM "+table_name[i]);
				ResultSet rst=state.executeQuery();
				if(rst.next()) {
					
				}else {
					db_is_empty=true;
					count_db++;
					
				}
				
			}
			
			connection.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(count_db);
		if(count_db==4) {
			return true;
		}else {
			return false;
		}
		
		
	}
	
}

