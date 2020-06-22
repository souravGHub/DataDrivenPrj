package com.ssg.rough;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.ssg.utilities.MonitoringMail;
import com.ssg.utilities.TestConfig;

public class TestHostAddress {
	
	public static void main(String[] args) throws UnknownHostException, AddressException, MessagingException {
		
		MonitoringMail mail = new MonitoringMail();
		String msgBody = "http://" + InetAddress.getLocalHost().getHostAddress()+":8080/job/DataDrivenLivePrj/EXTENT_5fReport/";
		//System.out.println(msgBody);
		
		mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject, msgBody);
	}
	

}
