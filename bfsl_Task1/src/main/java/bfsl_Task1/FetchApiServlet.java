package bfsl_Task1;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class FetchApiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Logging l=new Logging();
		try {
			PrintWriter out= response.getWriter();
			 HttpSession session = request.getSession(false);
	         out.println("session found with "+session.getId());
	         out.println("session found with "+session.getLastAccessedTime());
	         l.logger.debug("FetchApiServlet::HttpSession session called");
	         
			URL url = new URL("http://bajajfinsecapis.cmots.com/api/CompanyBackground/227");
			 l.logger.info("FetchApiServlet::URL created");
			HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
			 l.logger.info("FetchApiServlet::Api connection is established");
			
			InputStream ip = httpUrlConnection.getInputStream();
			BufferedReader br1 = new BufferedReader(new InputStreamReader(ip));
			StringBuilder response1 = new StringBuilder();
			String responseSingle = null;
			int responseCode = httpUrlConnection.getResponseCode();
			if(responseCode !=200) {
				
			}else {
				while((responseSingle = br1.readLine())!=null) {
					response1.append(responseSingle);
				}
				String str = response1.toString();
				JSONParser parser = new JSONParser();
				JSONObject json = (JSONObject) parser.parse(str);  
				out.println(json);
			}
			 l.logger.info("FetchApiServlet::Api access successful");
		}catch (Exception e) {
			e.printStackTrace();
			l.logger.fatal("FetchApiServlet::unknown url problem"+e.getMessage());
		}}}
