package bfsl_Task1;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

public class Logging {
		static Logger logger = Logger.getLogger(Logging.class.getName());
		static {
		try {
		SimpleLayout layout = new SimpleLayout();
		ConsoleAppender appender =new ConsoleAppender(layout);
		logger.addAppender(appender);
		logger.setLevel(Level.DEBUG);
		logger.info("UsersServlet::log4jsetup is ready");}
		
		 catch(Exception e) {			
			e.printStackTrace();
			logger.fatal("UsersServlet::problem while setting up log4j");
		}}}
