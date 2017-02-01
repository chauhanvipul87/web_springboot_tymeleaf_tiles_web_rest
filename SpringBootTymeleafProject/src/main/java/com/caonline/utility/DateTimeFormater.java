package com.caonline.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import com.caonline.common.GlobalVariables;

public class DateTimeFormater {
	
	static Logger log = Logger.getLogger(DateTimeFormater.class);
	
	public static java.sql.Timestamp getSqlSysTimestamp() {
		return new java.sql.Timestamp(System.currentTimeMillis());
	}
	
	
	public static String format_datetime(){
		Calendar c = Calendar.getInstance();
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = f.format(c.getTime());
		System.out.println(date);
		return date;
	}
	
	public static String getCurrentDate(){
		Calendar c = Calendar.getInstance();
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String date = f.format(c.getTime());
		System.out.println(date);
		return date;
	}
	
	public static String getCurrentDateDir(){
		Calendar c = Calendar.getInstance();
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String date = f.format(c.getTime());
		System.out.println(date);
		return date + GlobalVariables.FORWARD_SLASH;
	}
	
	public static String convertDateFormat(String date){
		 log.info("Original Date "+date);
		 String dates[] = date.split("/");
		 String convertdate = dates[2]+"/"+dates[0]+"/"+dates[1];
		 log.info("Converted Date"+convertdate);
		 return convertdate;
	}
	
	 private static boolean isWorkingDay(Calendar cal) {
	        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
	        if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY)
	            return true;
	        return true;
	    }

	public static String getThirtyDaysDateFromToday() {
		SimpleDateFormat s = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = new GregorianCalendar();
		// cal now contains current date
		//System.out.println(cal.getTime());
		// add the working days
		int workingDaysToAdd = 29;
		for (int i = 0; i < workingDaysToAdd; i++)
			do {
				cal.add(Calendar.DAY_OF_MONTH, 1);
			} while (!isWorkingDay(cal));
		//System.out.println(s.format(cal.getTime()));
		return s.format(cal.getTime());
	}
 
	/*public static void main(String[] args) {
		System.out.println(getThirtyDaysDateFromToday());
	}*/
	
}
