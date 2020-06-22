package com.ssg.rough;

import java.util.Date;

public class TestTimeStamp {
	public static void main(String [] args) {
	Date d = new Date();
	String screenShotName = d.toString().replace(":","").replace(" ","_") + ".jpg";
	System.out.println(screenShotName);
	
}}
