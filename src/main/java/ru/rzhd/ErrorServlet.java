package ru.rzhd;

import jakarta.servlet.http.HttpServlet;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name="ErrorServlet", urlPatterns={"/ErrorServlet"}) 
public class ErrorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(ErrorServlet.class);
    private final BD_CLASS dbHandler = new BD_CLASS();
       
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            String roadCode = request.getParameter("road_code");
            String regionCode = request.getParameter("region_code");
            String areaCode = request.getParameter("sel");
            String km = request.getParameter("kilometr");
            String stationStart = request.getParameter("station_start");
            String stationFinish = request.getParameter("station_finish");
            String nbType = request.getParameter("nb_type");

            // Validate input parameters
            if (roadCode == null || regionCode == null || areaCode == null || 
                km == null || stationStart == null || stationFinish == null || nbType == null) {
                logger.error("Missing required parameters");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required parameters");
                return;
            }

            dbHandler.SendError(roadCode, regionCode, areaCode, km, stationStart, stationFinish, nbType);
            logger.info("Successfully processed error report for area: {}", areaCode);
            
            getServletContext().getRequestDispatcher("/success.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error("Error processing request", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}