package com.myUtil;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {
	
	public static Calendar calendar =Calendar.getInstance();
	public static SimpleDateFormat sdf =new SimpleDateFormat("yyyyMMdd hh:mm:ss.SSS");
	
	public static boolean isWeekday(String sDate) throws ParseException{
		Date date= sdf.parse(sDate);
		calendar.clear();//单例一定要clear()
		calendar.setTime(date);
		int dayofWeek =calendar.get(calendar.DAY_OF_WEEK);
		return dayofWeek <= 5;
	}
	
	
	
	public static Date paraseFromSecond(int second, int millisecond){
		calendar.clear();//单例一定要clear()
		calendar.set(Calendar.YEAR,1970);
		calendar.add(Calendar.SECOND, second);
		calendar.add(Calendar.MILLISECOND, millisecond);
		return calendar.getTime();
	}
	
	public static Date paraseFromSecond(String sSecond, String sMillisecond){
		return paraseFromSecond(Integer.parseInt(sSecond), Integer.parseInt(sMillisecond));
		
	}
	
	
	
	public static String parseToString(Date date){
		return sdf.format(date);
	}
	
	
	public static int getDateInternal(Date d1,Date d2){
		return (int)((d2.getTime()-d1.getTime())/(1000*60*60*24));
	}
	
	
	public static int getDateInternal(String s1,String s2) throws ParseException{
		return getDateInternal(sdf.parse(s1),sdf.parse(s2));
	}
	
	

}
