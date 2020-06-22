package com.ssg.base;
import org.apache.log4j.Logger;
public class myPractice_Logs {
	static Logger log = Logger.getLogger(myPractice_Logs.class);	
	public static void main(String[] args)  {			
	log.debug("This is debug log");
	log.info("This is info log.");
	log.error("This is error log.");	
	}
}
