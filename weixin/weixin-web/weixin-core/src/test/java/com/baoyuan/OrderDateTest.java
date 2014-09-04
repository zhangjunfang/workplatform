package com.baoyuan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class OrderDateTest {

	@Test
	public void orderDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		Date start = null;
		Date end = null;
		try {
			start = sdf.parse("2014-03-18");
			end = sdf.parse("2014-03-18");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date today = new Date();
		List<Date> orderDateList = new ArrayList<Date>();
		Calendar cd = Calendar.getInstance();   
		if((today.getTime()>=start.getTime() && today.getTime()<=end.getTime()) || today.getTime()<start.getTime()){
			if(today.getTime()<start.getTime()){
				today=start;
			}
			while(today.getTime()<end.getTime()){
				orderDateList.add(today);
				cd.setTime(today);
				cd.add(Calendar.DATE,1);
				today = cd.getTime();
				if(sdf.format(today).equals(sdf.format(end))){
					orderDateList.add(end);
				}
			}
		}
		
		for(Date date:orderDateList){
			System.out.println(sdf.format(date));
		}
	}
}
