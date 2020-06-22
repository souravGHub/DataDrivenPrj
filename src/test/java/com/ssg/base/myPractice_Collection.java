package com.ssg.base;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class myPractice_Collection {
	public static void main(String[] args)  {			
		
		//1. wrapping unwraapping
		short var1 = 10;
		Short obj = new Short(var1);//autoboxing
		System.out.print("objShort="+obj);
		int i = obj;//unboxing
		System.out.println(";i_int="+i);
		
		//*****************List : ArrayList
		List AL = new ArrayList();
		System.out.println("AL content="+AL + " AL.size()=" +AL.size() );
		
		AL.add(10);AL.add(false);AL.add('c');AL.add("null");AL.add("Sourav");
		System.out.println("AL content="+AL);
		for(Object obj1 :AL) {
			System.out.print(obj1);
		}		
		AL.remove(2);
		System.out.println("\nIterator");
		//6********Get content of AL through iterator*********
		Iterator itr = AL.iterator();
		while(itr.hasNext()) {
			System.out.print(itr.next()+ ",");
		}		
		//Above approach has type pasting issues . So we will use mostly the
		//below and separate lists for separate Data types - GENERICS
		List<Integer> ALint = new ArrayList<Integer>();
		ALint.add(null);ALint.add(null);ALint.add(22);
		int a =ALint.get(2);System.out.println(a);
		//Set and HashSet 
		System.out.print("******************Set HashSet****************");
		Set<String> hset= new HashSet<String>();
		hset.add("Sourav");hset.add("Sinha");hset.add("Chak");hset.add("chat");
		hset.add("Sinha");
		for(String str :hset) {
			System.out.print(str);System.out.print(",");
		}	
		Iterator<String> itrS = hset.iterator();		
		while(itrS.hasNext()) {
			String objofSet = itrS.next();
			if(objofSet.equals("Sinha"))
			System.out.println("Found Sinha" + objofSet);
		}
		
		
		System.out.print("********* Map: HashMap************* ");
		Map<String,String> hMap = new HashMap<String,String>();
		
		hMap.put("fName", "Apple");
		hMap.put("lName", "Banana");
		hMap.put("rollNum", "123456");
		hMap.put("dept", "ETC");
		System.out.println(hMap + " Size:" + hMap.size() );
		
		Set<String> keysOfHMap = hMap.keySet();
		for(String key:keysOfHMap) {
			System.out.print(key + "="+  hMap.get(key)+ ", ");
			
			
		}
		
	}
}
