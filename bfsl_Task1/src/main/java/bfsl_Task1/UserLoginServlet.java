package bfsl_Task1;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession; 

public class UserLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		
		String userName=req.getParameter("userName");
		String password= req.getParameter("password");
	
		
		PrintWriter out= res.getWriter();
		UserLogin u= new UserLogin();
		Connection c=null;
	    Logging l=new Logging();	
		 try {
			 if(u.validate(userName, password)) {
				
				Class.forName("com.mysql.jdbc.Driver");
				 l.logger.debug("UserLoginServlet::JDBC driver class is loaded");
				c=DriverManager.getConnection("jdbc:mysql://dev-ws.bajajfinservsecurities.in:8444/SESSION_DATABASE", "platformwrite", "bfslwrite");
				l.logger.info("UserLoginServlet::JDBC connection is established");
				out.println("logged in successfully");
				HttpSession session=req.getSession();  
			    session.setAttribute("username",userName);  
			    session.setAttribute("password", password);
			    l.logger.debug("UserLoginServlet::HttpSession created"); 
			 }else {out.println("please enter the currect username and password");
				 }}
			 catch(Exception e) {			
					e.printStackTrace();
					l.logger.fatal("UserLoginServlet::unknown DB problem"+e.getMessage());
				}
				 finally{
					l.logger.debug("UserServlet::closing JDBC objects");
					 try {
						 if( c!= null)
						 c.close();
						 l.logger.debug("UserLoginServlet::connection object is closed");
					 }
					 catch(SQLException se) {
						 se.printStackTrace();			 
					 }}
		 				l.logger.debug("UserLoginServlet::end of the doGet method");
				 	}}

	
