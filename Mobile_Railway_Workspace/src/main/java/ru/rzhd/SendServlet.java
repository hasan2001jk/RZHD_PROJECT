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
 * Servlet implementation class SendServlet
 */

@WebServlet(name="SendServlet",urlPatterns={"/SendServlet"}) 
public class SendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		List<EKASUI> datas=new ArrayList<EKASUI>();
		
		BD_CLASS element=new BD_CLASS("user", "user", "jdbc:postgresql://localhost:5432/RZHD");
		
		try {
			element.setInfo(datas);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.setAttribute("list", datas);
		getServletContext().getRequestDispatcher("/send.jsp").forward(request, response);	
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String var=request.getParameter("sel");
		String check=request.getParameter("result");
		System.out.println(var);
		System.out.println();
		System.out.println(check);
		
		
		if(check !=null) {
			BD_CLASS element=new BD_CLASS("user", "user", "jdbc:postgresql://localhost:5432/RZHD");
			System.out.println("We're here not yet");
			element.fillData(var);
			System.out.println("We're here");
			getServletContext().getRequestDispatcher("/success.jsp").forward(request, response);	
		}else{
			List<EKASUI> datas=new ArrayList<EKASUI>();
			
			BD_CLASS element=new BD_CLASS("user", "user", "jdbc:postgresql://localhost:5432/RZHD");
			
			try {
				element.setInfo(datas);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			request.setAttribute("dan", datas);
			getServletContext().getRequestDispatcher("/send_error.jsp").forward(request, response);
		}
		
	}

}
