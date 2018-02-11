package com.chemyoo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    public static Date getNow()
    {
        return Calendar.getInstance().getTime();
    }
    
    public static Date convertStringToDate(String strDate, String pattern){
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			return format.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
    
    public static String convertDateToString(Date date, String pattern){
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}
    
}
