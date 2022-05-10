package ru.rzhd;

import jakarta.servlet.http.HttpServlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ErrorServlet
 */

@WebServlet(name="ErrorServlet",urlPatterns={"/ErrorServlet"}) 
public class ErrorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ErrorServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String RoadCode=request.getParameter("road_code");
		String RegionCode=request.getParameter("region_code");
		String sel=request.getParameter("sel");
		String km=request.getParameter("kilometr");
		String station_start=request.getParameter("station_start");
		String station_finish=request.getParameter("station_finish");
		String nb_type=request.getParameter("nb_type");
		
		System.out.println();
		System.out.println("Ola");
		System.out.println();
		System.out.println("RoadCode: "+RoadCode);
		System.out.println();
		System.out.println("RegionCode: "+RegionCode);
		System.out.println();
		System.out.println("Sel: "+sel);
		System.out.println();
		System.out.println("Kilometr: "+km);
		System.out.println();
		System.out.println("StationStart: "+station_start);
		System.out.println();
		System.out.println("StationFinish: "+station_finish);
		System.out.println();
		System.out.println("nb_type: "+nb_type);
		
		
		
		
		BD_CLASS element=new BD_CLASS("user", "user", "jdbc:postgresql://localhost:5432/RZHD");
		
		element.SendError(RoadCode,RegionCode,sel,km,station_start,station_finish,nb_type);
		getServletContext().getRequestDispatcher("/success.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
