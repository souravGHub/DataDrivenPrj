package com.ssg.base;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
public class myPractice_Reflection {
	public static void main(String[] args)  {			
		
		myPractice_Reflection rc = new myPractice_Reflection();
		System.out.println(rc.getClass());		
		myPractice_RefTest trc = new myPractice_RefTest();
		Class cl = trc.getClass();		
		System.out.println(cl.getSimpleName());
		Method[] methods = cl.getDeclaredMethods();
		for(Method methName:methods ) {
		System.out.println("*Method: "+ methName.getName()+
		" Return Type:" +methName.getReturnType());
		Parameter[] params = methName.getParameters();
		for(Parameter paramName:params ) {
			System.out.println("**Parameter: "+ paramName + ", ");
		}
		}
		
	}
}
