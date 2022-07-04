package bfsl_Task1;
import jakarta.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class FetchNewsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		Statement st=null;
		ResultSet r=null;
		PrintWriter out=res.getWriter();
		PreparedStatement ps=null;
		
		Logging l=new Logging();
	
		String BASE_SYMBOL = req.getParameter("BASE_SYMBOL");
		System.out.println(BASE_SYMBOL);
		try {
		        HttpSession session = req.getSession(false);
		         out.println("session found with "+session.getId());
		         out.println("session found with "+session.getLastAccessedTime());
		         l.logger.debug("FetchNewsServlet::HttpSession session called");
		    
		     Class.forName("com.mysql.jdbc.Driver");
		     l.logger.debug("FetchNewsServlet::JDBC driver class is loaded");
			 Connection c1 = DriverManager.getConnection("jdbc:mysql://dev-ws.bajajfinservsecurities.in:8444/BFSL", "platformwrite", "bfslwrite");
			 l.logger.info("FetchNewsServlet::JDBC connection is established");
			 System.out.println("BFSL database connected");
			 st= c1.createStatement();		
			 ps= c1.prepareStatement("SELECT * FROM BFSL.NEWS WHERE BASE_SYMBOL=?");   
			 ps.setString(1,BASE_SYMBOL);			
			 r = ps.executeQuery();
					
			   while (r.next()) {
						JSONArray jsonr= new JSONArray();
						JSONObject jo= new JSONObject();
						jo.put("ID", r.getString("ID"));
						jo.put("TITLE", r.getString("TITLE"));
						jo.put("TEXT", r.getString("TEXT"));
						jo.put("CATEGORY", r.getString("CATEGORY"));
						jo.put("NEWS_AT", r.getString("NEWS_AT"));
						jo.put("URL", r.getString("URL"));
						jo.put("BASE_SYMBOL", r.getString("BASE_SYMBOL"));
						jo.put("UPDATED_AT", r.getString("UPDATED_AT"));			
						jsonr.add(jo);
						out.println(jsonr);							
					}
					System.out.println("done");
					}catch(Exception e) {
						e.printStackTrace();
						l.logger.fatal("FetchNewsServlet::unknown DB problem"+e.getMessage());
					}
		}}



