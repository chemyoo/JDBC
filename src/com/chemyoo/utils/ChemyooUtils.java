package com.chemyoo.utils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * Personal utils
 * @author Chemyoo
 */
public class ChemyooUtils {
	/**
	 * MILLS OF A DAY
	 */
	/**类级锁对象*/
	private static Object objLock = new Object();
	
	private static String MAC_ADDR_STR = LocalMac.Mac.getInstanse().replace("-", "");
	
	private static final long MILLS_OF_DAY = 1000*60*60*24;
	
	private ChemyooUtils(){};
	/**
	 * use to judge the collection is empty or not
	 * @param collection
	 * @return true or false
	 */
	public static <T> boolean isEmpty(Collection<T> collection)
	{
		return (collection == null || collection.size() == 0);
	}
	/**
	 * use to judge the collection is empty or not ,the static funtion isEmpty's opposite
	 * @param collection
	 * @return true or false
	 */
	public static <T> boolean isNotEmpty(Collection<T> collection)
	{
		return !isEmpty(collection);
	}
	
	public static <K,V> boolean isEmpty(Map<K,V> map)
	{
		return (map== null || map.isEmpty());
	}
	
	public static <K,V> boolean isNotEmpty(Map<K,V> map)
	{
		return isEmpty(map);
	}
	
	/**
	 * judge a class object is empty or not
	 * @param type is Java Type
	 * @return
	 */
	public static <T> boolean isNotEmpty(T type)
	{
		if(type == null) return false;
		Class<? extends Object> c = type.getClass();
		if(!c.isInterface())
		{
			Field [] fields = c.getDeclaredFields();
			for(Field f :fields)
			{
				f.setAccessible(true);
		        try 
		        {
					Object obj = f.get(type);
					//System.err.println(obj);
					if(obj!=null)
					{
						if(obj instanceof String && "".equals( ((String)obj).trim() ))
						{
							continue;
						}
						return true;
					}
				} 
		        catch (IllegalArgumentException e) 
				{
					e.printStackTrace();
				} 
		        catch (IllegalAccessException e) 
				{
					e.printStackTrace();
				}
		        f.setAccessible(false);
			}
		}
		return false;
	}
	/**
	 * judge a class object is empty or not
	 * @param type is Java Type
	 * @return
	 */
	public static <T> boolean isEmpty(T type)
	{
		return !isNotEmpty(type);
	}
	/**
	 * 将日期转换成指定格式的字符串
	 * @param format 时间表现形式，例如："yyyy-MM-dd"，"yyyy-MM-dd HH:mm:ss"等
	 * @param date 待格式化的日期
	 * @return 返回格式化后的日期字符串
	 */
	public static String formatDate(String format, Date date)
	{
		String formatStr = "";
		if (date != null)
		{
			if (null == format)
	        {
				format = "EEE MMM d HH:mm:ss z yyyy";//美国时间格式
	        }
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
			formatStr = simpleDateFormat.format(date);
		}
		return formatStr;
	}
	
	/** 
	 * 计算两个日期间隔的天数 
	 * @param first 第一个日期
	 * @param second 第二个日期
	 */
	public static long getDiffDays(Date first,Date second)
	{
		long mm=first.getTime()-second.getTime();
		long days=mm/MILLS_OF_DAY;
		return days;
	}
	/** 
	 * 计算两个日期间隔的毫秒数
	 * @param first 第一个日期
	 * @param second 第二个日期
	 */
	public static long getDiffMills(Date first,Date second)
	{
		long mm=first.getTime()-second.getTime();
		return mm;
	}
//	/**
//	 * 关闭计算机
//	 * @param second 延迟的秒数
//	 */
//	public static void powerOff(long second)
//	{
//		 Runtime rt=Runtime.getRuntime();
//		  try
//		  {
//			  if(second < 0)
//					second = 5;
//			   rt.exec("shutdown.exe -s -t "+second);
//			   /* 单位为秒。
//				如果是想定时关机，可用这句：rt.exec("at 19:00 shutdown.exe -s");*/
//		  }
//		  catch(Exception e)
//		  {
//		   e.printStackTrace();
//		  }
//	}
//	/**
//	 * 定时关闭计算机
//	 * @param clock 关闭计算机的时间
//	 * @throws ParseException 
//	 */
//	public static void powerOff(String clock) throws ParseException
//	{
//		 Runtime rt=Runtime.getRuntime();
//		 Pattern pattern = Pattern.compile("^[0-2]??[0-9][:]{1}[0-5][0-9]$");
//		 Matcher matcher = pattern.matcher(clock);
//		 // 字符串是否与正则表达式相匹配
//		 if(matcher.matches())
//		 {
//			  try
//			  {
//				  if(clock == null)
//					  clock = "00:00";			  
//				  rt.exec("at "+ clock +" shutdown.exe -s");
//			  }
//			  catch(Exception e)
//			  {
//				  e.printStackTrace();
//			  }
//		 }
//		 else
//			throw new ParseException("时间格式不正确，格式为00:00或0:00,冒号为英文字符",0);
//	}
	/**
//	 * 重新启动计算机
//	 * @param clock 关闭计算机的时间
//	 * @throws ParseException 
//	 */
//	public static void restart() 
//	{
//		Runtime rt=Runtime.getRuntime();
//		  try
//		  {
//			   rt.exec("shutdown.exe -r");
//		  }
//		  catch(Exception e)
//		  {
//			  	e.printStackTrace();
//		  }
//	}
	/**
	 * <p>Adds the specified (signed) amount of time to the given calendar field, based on the 
	 * calendar's rules. </p>
	 *
	 * <p>Add rule 1. The value of field after the call minus the value of field before the call is amount, 
	 * modulo any overflow that has occurred in field. Overflow occurs when a field value exceeds its 
	 * range and, as a result, the next larger field is incremented or decremented and the field value 
	 * is adjusted back into its range.</p>
	 *
	 * <p>Add rule 2. If a smaller field is expected to be invariant, but it is impossible for it to be equal 
	 * to its prior value because of changes in its minimum or maximum after field is changed, then its 
	 * value is adjusted to be as close as possible to its expected value. A smaller field represents a 
	 * smaller unit of time. HOUR is a smaller field than DAY_OF_MONTH. 
	 * No adjustment is made to smaller fields that are not expected to be invariant. 
	 * The calendar system determines what fields are expected to be invariant.</p>
	 *
	 * @throws <p>IllegalArgumentException - if field is ZONE_OFFSET, DST_OFFSET, or unknown, 
	 * or if any  calendar fields have out-of-range values in non-lenient mode.</p>
	 * @param datetime  the date or time to be added.
	 * @param datepart  the calendar datepart.
	 * @param value the amount of date or time to be added to the field.
	 * @return Date the Date after operation
	 */
	public static Date dateAdd(Date datetime,int datepart,int value)
	{
		GregorianCalendar grego = new GregorianCalendar();
		grego.setTime(datetime);
		grego.add(datepart, value);
		return grego.getTime();
	}
	/**
	 * clear lists 
	 * @param list
	 */
	public static void clearList(List<?>... list)
	{
		if(list!=null)
		{
			for(int i=0,size=list.length;i<size;i++)
			{
				if(isNotEmpty(list[i]))
				{
					list[i].clear();
					list[i] = null;//使对象变为游离态
				}
				else if (list[i]!=null)
				{
					list[i] = null;//使对象变为游离态
				}
			}
		}
	}
	/**
	 * clear maps
	 * @param map
	 */
	public static void clearMap(Map<?,?>... map)
	{
		if(map!=null)
		{
			for(int i=0,size=map.length;i<size;i++)
			{
				if(isNotEmpty(map[i]))
				{
					map[i].clear();
					map[i] = null;//使对象变为游离态
				}
				else if(map[i]!=null)
				{
					map[i] = null;//使对象变为游离态
				}
			}
		}
	}
	
	
	public static  String toString(Object obj) throws ClassNotFoundException
	{
		if(obj==null)
			return "";
//		Class c =  Class.forName(obj.getClass().getName());
//		return c.cast(obj).toString();
		return obj.toString();
	}
	
	public static Object toObject(Object o1,Object o2) throws ClassNotFoundException
	{
		if(o1==null)
			return o2;
		Class c =  Class.forName(o1.getClass().getName());
		return c.cast(o2);
	}
	/**
	 * @since 2017.08.29 
	 * <p>生成不重复的主键</p>
	 * @param flag 是否生成带Mac地址的主键
	 */
	public static String keyGenerator(boolean flag)
	{
		StringBuffer key = new StringBuffer();
		synchronized (objLock) 
		{
			if(flag)
			{
				key.append(MAC_ADDR_STR);
			}
			GregorianCalendar grego = new GregorianCalendar();
			key.append(formatDate("yyyyMMddHHmmssSSS",grego.getTime()));
			try {
				TimeUnit.MILLISECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			key.append(getUppercaseLetter());
		}
		return key.toString();
	}

	/**
	 * @return a uppercase character
	 */
	private static char getUppercaseLetter()
	{
		return (char)((int)Math.floor(Math.random()*26)+65);
	}
	/**
	 * @return a lowercase character
	 */
	private static char geLowercaseLetter()
	{
		return (char)((int)Math.floor(Math.random()*26)+97);
	}
	
	public static String getLineSeparator() {
		return System.getProperty("line.separator");
	}

	public static String getPathSeparator() {
		return System.getProperty("path.separator");
	}

	public static String getFileSeparator() {
		return System.getProperty("file.separator");
	}
}
