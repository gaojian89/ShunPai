package cn.e23.shunpai.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.TimeZone;

import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.format.Time;

public class TimeUtils {
	public static final String DATE_FOR_DISPLAY = "date_for_display";
	public static final String WEEK = "week";
	public static final String DATE_TO_NET = "date_no_net";
	private static final long ONE_SECOND = 1000L;
	private static final long ONE_MINUTE = 60 * ONE_SECOND;
	private static final long ONE_HOUR = 60 * ONE_MINUTE;
	private static final long ONE_DAY = 24 * ONE_HOUR;
	private static final long ONE_WEEK = 7 * ONE_DAY;
	private static final String ONE_SECOND_AGO = "秒前";
	private static final String ONE_MINUTE_AGO = "分钟前";
	private static final String ONE_HOUR_AGO = "小时前";
	private static final String ONE_DAY_AGO = "天前";
	private static final String ONE_MONTH_AGO = "月前";
	private static final String ONE_YEAR_AGO = "年前";

	// static SimpleDateFormat format = new
	// SimpleDateFormat("yyyy-MM-dd HH:m:s");

	public static String getTimeDisplay(long second) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());

		Calendar createDate = Calendar.getInstance();
		createDate.setTimeInMillis(second * 1000);

		Calendar yesterday = Calendar.getInstance();
		yesterday.setTimeInMillis(System.currentTimeMillis() - 24 * 60 * 60 * 1000);

		// Date now = new Date(System.currentTimeMillis());
		// Date createDate = new Date(second * 1000l);
		// Date yesterday = new Date(System.currentTimeMillis()-24*60*60*1000);

		int nowMin = (int) (calendar.getTimeInMillis() / 60000);
		int createMin = (int) (createDate.getTimeInMillis() / 60000);
		int intervalMin = nowMin - createMin;
		if (intervalMin < 1) {
			return "刚刚";
		}
		if (intervalMin < 60) {
			return intervalMin + "分钟前";
		}
		int nowDay = calendar.get(Calendar.DATE);
		int createDay = createDate.get(Calendar.DATE);
		int yesterdayDay = yesterday.get(Calendar.DATE);

		int nowMonth = calendar.get(Calendar.MONTH);
		int createMonth = createDate.get(Calendar.MONTH);
		int yesterdayMonth = yesterday.get(Calendar.MONTH);

		int nowYear = calendar.get(Calendar.YEAR);
		int createYear = createDate.get(Calendar.YEAR);
		int yesterdayYear = yesterday.get(Calendar.YEAR);

		if (nowDay == createDay && nowMonth == createMonth && nowYear == createYear) {
			SimpleDateFormat df = new SimpleDateFormat("HH:mm");
			return df.format(createDate.getTimeInMillis());
		}
		if (yesterdayDay == createDay && yesterdayMonth == createMonth && yesterdayYear == createYear) {
			SimpleDateFormat df = new SimpleDateFormat("昨天HH:mm");
			return df.format(createDate.getTimeInMillis());
		}
		SimpleDateFormat df = new SimpleDateFormat("MM月dd日HH:mm");
		return df.format(createDate.getTimeInMillis());
	}

	public static String getTweetTimeDisplay(long second) {
		Date createDate = new Date(second * 1000l);
		// Date createDate = new Date(second);
		SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");
		return df.format(createDate);
	}

	public static String getFormatTime(long second) {
		Date createDate = new Date(second * 1000l);
		// Date createDate = new Date(second);
		SimpleDateFormat df = new SimpleDateFormat("MM月dd日 HH:mm");
		return df.format(createDate);
	}

	public static String getFormatNumTime(long second) {
		if (second == 0) {
			return "";
		}
		Date createDate = new Date(second*1000l);
		// Date createDate = new Date(second);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		return df.format(createDate);
	}

	public static void sleep(int second) {
		try {
			Thread.sleep(second * 1000);
		} catch (Exception e) {
		}
	}

	public static long getMilliSeconds(String time) {
		if (TextUtils.isEmpty(time)) {
			time = "00:00";
		}
		String[] hourAndMinute = time.split(":");
		int hourOfDay = Integer.parseInt(hourAndMinute[0]);
		int minute = Integer.parseInt(hourAndMinute[1]);

		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());

		c.set(Calendar.HOUR_OF_DAY, hourOfDay);
		c.set(Calendar.MINUTE, minute);
		c.set(Calendar.SECOND, 0);

		return c.getTimeInMillis();
	}

	/**
	 * 获取当前系统时间 12:21
	 * 
	 * @return 当前系统时间 格式为：12:21
	 */
	public static String getCurrentSystemTime() {
		Time time = new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
		time.setToNow(); // 取得系统时间。
		int hour = time.hour; // 0-23
		int minute = time.minute;
		return format(hour) + ":" + format(minute);
	}

	/* 日期时间显示两位数的方法 */
	private static String format(int x) {
		String s = "" + x;
		if (s.length() == 1)
			s = "0" + s;
		return s;
	}

	public static String toTime(int time) {

		time /= 1000;
		int minute = time / 60;
		int hour = minute / 60;
		int second = time % 60;
		minute %= 60;
		return String.format("%02d:%02d", minute, second);
	}

	/**
	 * 获取当前及往前N天的日期
	 * 
	 * @param n
	 * @return
	 */
	public static ArrayList<Hashtable<String, String>> getDatesList(String date, int n) {
		ArrayList<Hashtable<String, String>> list = new ArrayList<Hashtable<String, String>>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < n; i++) {
			Hashtable<String, String> table = new Hashtable<String, String>();
			Calendar cal = Calendar.getInstance();
			try {
				cal.setTime(format.parse(date));
			} catch (Exception e) {
				e.printStackTrace();
			}

			cal.add(Calendar.DATE, -i);
			String dateForDisplay = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
			String dateToNet = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
			table.put(DATE_FOR_DISPLAY, dateForDisplay);
			table.put(DATE_TO_NET, dateToNet);
			table.put(WEEK, getWeek(dateForDisplay));
			list.add(table);
		}
		return list;
	}

	/**
	 * 获取该日期是星期几
	 * 
	 * @param date
	 * @return
	 */
	public static String getWeek(String date) {
		String week = "星期";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(format.parse(date));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			week += "日";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 2) {
			week += "一";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 3) {
			week += "二";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 4) {
			week += "三";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 5) {
			week += "四";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 6) {
			week += "五";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 7) {
			week += "六";
		}
		return week;
	}

	public static String getAskFormatString(String tdate) {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String str = "";
		try {
			Date date = format1.parse(tdate);
			SimpleDateFormat format2 = new SimpleDateFormat("yyyy.MM.dd");
			str = format2.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return str;
	}

	public static String format(long second) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
		// Date createDate = new Date(second * 1000l);
		Date createDate = new Date(second);
		String time = sf.format(createDate);
		Date date = null;
		try {
			date = sf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (date == null) {
			return time;
		}

		long delta = new Date().getTime() - date.getTime();
		if (delta < 1L * ONE_MINUTE) {
			long seconds = toSeconds(delta);
			return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
		}
		if (delta < 45L * ONE_MINUTE) {
			long minutes = toMinutes(delta);
			return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
		}
		if (delta < 24L * ONE_HOUR) {
			long hours = toHours(delta);
			return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
		}
		if (delta < 48L * ONE_HOUR) {
			return "昨天";
		}
		if (delta < 30L * ONE_DAY) {
			long days = toDays(delta);
			return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
		}
		if (delta < 12L * 4L * ONE_WEEK) {
			long months = toMonths(delta);
			return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
		} else {
			long years = toYears(delta);
			return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
		}
	}

	private static long toSeconds(long date) {
		return date / 1000L;
	}

	private static long toMinutes(long date) {
		return toSeconds(date) / 60L;
	}

	private static long toHours(long date) {
		return toMinutes(date) / 60L;
	}

	private static long toDays(long date) {
		return toHours(date) / 24L;
	}

	private static long toMonths(long date) {
		return toDays(date) / 30L;
	}

	private static long toYears(long date) {
		return toMonths(date) / 365L;
	}

	public static boolean isToday(Date date) {
		return DateUtils.isToday(date.getTime());
	}

	public static Date getToday() {
		return new Date();
	}

	public static boolean isYesyday(Date date) {
		int distance = 0;
		Date dateToday = new Date();

		if (dateToday == null || date == null) {
			return false;
		}
		distance = (int) ((dateToday.getTime() - date.getTime()) / 1000 / 60 / 60 / 24);
		if (distance == 1) {
			return true;
		}
		return false;
	}

	/**
	 * 取两日期之间的天数间隔
	 * 
	 * @param strDate1
	 *            格式:yyyymmdd
	 * @param strDate2
	 *            格式:yyyymmdd
	 * @return
	 */
	public static int getDistance(String strDate1, String strDate2) {
		int distance = 0;
		Date date1 = getDate(strDate1, "yyyyMMdd");
		Date date2 = getDate(strDate2, "yyyyMMdd");
		if (date1 == null || date2 == null) {
			return distance;
		}
		distance = (int) ((date2.getTime() - date1.getTime()) / 1000 / 60 / 60 / 24);
		return distance;
	}

	/**
	 * 取两日期之间的天数间隔
	 * 
	 * @param date1
	 *            格式:yyyymmdd
	 * @param date2
	 *            格式:yyyymmdd
	 * @return
	 */
	public static int getDistance(Date date1, Date date2) {
		int distance = 0;
		if (date1 == null || date2 == null) {
			return distance;
		}
		distance = (int) ((date2.getTime() - date1.getTime()) / 1000 / 60 / 60 / 24);
		return distance;
	}

	/**
	 * 将一个字符串的日期描述转换为java.util.Date对象
	 * 
	 * @param strDate
	 *            字符串的日期描述
	 * @return 字符串转换的日期对象java.util.Date
	 */
	public static Date getDate(String strDate) {
		if (strDate == null || strDate.trim().equals("")) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date;
		try {
			date = formatter.parse(strDate.toString());
			return date;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将一个字符串的日期描述转换为java.util.Date对象
	 * 
	 * @param strDate
	 *            字符串的日期描述
	 * @param format
	 *            字符串的日期格式，比如:“yyyy-MM-dd HH:mm”
	 * @return 字符串转换的日期对象java.util.Date
	 */
	public static Date getDate(String strDate, String format) {
		if (strDate == null || strDate.trim().equals("")) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		formatter.setTimeZone(timeZone);
		Date date;
		try {
			date = formatter.parse(strDate);
		} catch (Exception e) {
			date = null;
		}
		return date;
	}

	public static boolean isWeekend(Date date) {
		if (date == null) {
			return false;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			return true;
		}
		return false;
	}

	public static String getWeekName(Date date) {
		if (date == null) {
			return "";
		}
		if (isToday(date)) {
			return "今天";
		}
		if (isYesyday(date)) {
			return "昨天";
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		switch (c.get(Calendar.DAY_OF_WEEK)) {
		case Calendar.MONDAY:
			return "周一";
		case Calendar.TUESDAY:
			return "周二";
		case Calendar.WEDNESDAY:
			return "周三";
		case Calendar.THURSDAY:
			return "周四";
		case Calendar.FRIDAY:
			return "周五";
		case Calendar.SATURDAY:
			return "周六";
		case Calendar.SUNDAY:
			return "周日";
		default:
			return "";
		}
	}

	/**
	 * 将一个日期集合中的每个元素按照“是否在同一个年月”的规则进行分组
	 * 
	 * @param dates
	 * @return
	 */
	public static ArrayList getDateList(ArrayList<String> dates) {
		ArrayList groupList = new ArrayList();
		ArrayList<String> tmpList = new ArrayList();
		for (int i = 0; i < dates.size(); i++) {
			if (i == 0) {
				tmpList.add(dates.get(i));
			} else if (getYearAndMonth(dates.get(i - 1)).equals(getYearAndMonth(dates.get(i)))) {
				tmpList.add(dates.get(i));
			} else {
				groupList.add(tmpList);
				tmpList = new ArrayList();
				tmpList.add(dates.get(i));
			}
		}
		if (!tmpList.isEmpty()) {
			groupList.add(tmpList);
		}
		return groupList;
	}

	private static String getYearAndMonth(String date) {
		return date.substring(0, 7);
	}

	public static String getDay(Date date) {
		if (date == null) {
			return "";
		}
		return date.getDate() + "";
	}

	public static String getUITime(String src) {
		try {
			String dst = "";
			if (src.contains("′")) {
				String minutes = src.split("′")[0];
				String second = src.split("′")[1];
				second = second.substring(0, second.length() - 1);
				int int_minutes = Integer.parseInt(minutes);
				int int_second = Integer.parseInt(second);
				int totalSecond = int_minutes * 60 + int_second;
				dst = totalSecond + "′";
				return dst;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return "";
		}
		return "";
	}

	//
	public static String getUITime2(String src) {
		// try {
		String dst = "";
		String dst_mintues = "";
		String dst_seconds = "";
		if (src.contains("'")) {
			String minutes = src.split("'")[0];
			String second = src.split("'")[1];
			second = second.substring(0, second.length() - 1);
			int int_minutes = Integer.parseInt(minutes);
			int int_second = Integer.parseInt(second);

			if (int_minutes >= 10) {
				dst_mintues = int_minutes + "";
			} else {
				dst_mintues = "0" + int_minutes;
			}
			if (int_second >= 10) {
				dst_seconds = int_second + "";
			} else {
				dst_seconds = "0" + int_second;
			}
			dst = dst_mintues + ":" + dst_seconds;
			return dst;
		}
		// // } catch (Exception e) {
		// TODO: handle exception
		// return "";
		// }
		return dst;
	}

	public static String getFormatTime(String time) {
		Date date = getDate(time, "yyyy-MM-dd HH:mm");
		if (date == null) {
			return "";
		}
		SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
		return format.format(date);
	}

	private static final TimeZone timeZone = TimeZone.getTimeZone("GMT+08:00");
	//字符串时间转换成时间戳
	public static long getTime(String user_time) { 
        String re_time = null; 
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        Date d; 
        long l = 0;
        try { 
            d = sdf.parse(user_time); 
            l= d.getTime(); 
//            String str = String.valueOf(l); 
//            re_time = str.substring(0, 10); 
        } catch (ParseException e) { 
            // TODO Auto-generated catch block 
            e.printStackTrace(); 
        } 
        return l; 
    }
}
