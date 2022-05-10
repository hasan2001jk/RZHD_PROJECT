package ru.rzhd;


import jakarta.servlet.http.HttpServlet;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class nobeServlet
 * 
 */



@WebServlet(name="nobeServlet",urlPatterns={"/nobeServlet"}) 
public class nobeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public nobeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		List<String> Areas =new ArrayList<String>();
		List<String> AreasNums =new ArrayList<String>();
		List<String> AreaLinks =new ArrayList<String>();
		List<Integer> StationNums=new ArrayList<Integer>();
		List<String> StationName = new ArrayList<String>();
		List<Integer> NumberStat = new ArrayList<Integer>();
		List<EKASUI> datas=new ArrayList<EKASUI>();
		
		response.getWriter().append("Served at: ").append(request.getContextPath());

	
		ParserClass el=new ParserClass("https://cssrzd.ru/tr4/book1/kbsh.php","user","user");
	
		
		try {
			if(el.check_db()==true) {
				try {
					
					el.ParsePage(NumberStat,Areas,AreasNums,AreaLinks,StationNums,StationName);
					System.out.println(AreasNums.size());
					System.out.println(Areas.size());
					System.out.println(NumberStat.size());
					el.fill_db(NumberStat, Areas, AreasNums, AreaLinks, StationNums, StationName);
				
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
			
		try {
			el.check_db();
			System.out.println();
			System.out.println(el.check_db());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BD_CLASS element=new BD_CLASS("user", "user", "jdbc:postgresql://localhost:5432/RZHD");
		try {
			element.setInfo(datas);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			request.setAttribute("temp", datas);

			getServletContext().getRequestDispatcher("/work.jsp").forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
