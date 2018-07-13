package utils;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarUtil {
	public static final int FIRST_DAY_OF_WEEK = Calendar.SUNDAY;
	public static final int END_DAY_OF_WEEK = Calendar.SATURDAY;
	public static int pyear; //当前使用的年
	public static int pmonth; //当前使用的月
	public static int maxYear; //最大的年
	public static int maxMoth; //最大的月
	public static int minYear; //最小的年
	public static int minMoth; //最小的月
	private final static long minute = 60 * 1000;// 1分钟
	private final static long hour = 60 * minute;// 1小时
	private final static long day = 24 * hour;// 1天
	private final static long month = 31 * day;// 月
	private final static long year = 12 * month;// 年

	public static Date add(Date target, int field, int amount)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(target);
		calendar.add(field, amount);
		return calendar.getTime();
	}
	
	public static String getThisSeasonFirstTime(int month) {
		int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
		int season = 1;
		if (month >= 1 && month <= 3) {
			season = 1;
		}
		if (month >= 4 && month <= 6) {
			season = 2;
		}
		if (month >= 7 && month <= 9) {
			season = 3;
		}
		if (month >= 10 && month <= 12) {
			season = 4;
		}
		int start_month = array[season - 1][0];
		int end_month = array[season - 1][2];

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);

		int start_days = 1;// years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);
		int end_days = getLastDayOfMonth(years_value, end_month);
		String seasonDate = years_value + "-" + start_month + "-" + start_days;
		return seasonDate;

	}

	public static String getThisSeasonFinallyTime(int month) {
		int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
		int season = 1;
		if (month >= 1 && month <= 3) {
			season = 1;
		}
		if (month >= 4 && month <= 6) {
			season = 2;
		}
		if (month >= 7 && month <= 9) {
			season = 3;
		}
		if (month >= 10 && month <= 12) {
			season = 4;
		}
		int start_month = array[season - 1][0];
		int end_month = array[season - 1][2];

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);

		int start_days = 1;// years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);
		int end_days = getLastDayOfMonth(years_value, end_month);
		String seasonDate = years_value + "-" + end_month + "-" + end_days;
		return seasonDate;

	}

	public static int getLastDayOfMonth(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
				|| month == 10 || month == 12) {
			return 31;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		}
		if (month == 2) {
			if (isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		}
		return 0;
	}

	public static boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}

	public static int getMonth() {
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}

	public static int getNowWeek(){
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(FIRST_DAY_OF_WEEK);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	public static int getNowMonth(){
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(FIRST_DAY_OF_WEEK);
		return cal.get(Calendar.MONTH);
	}

	public static int getMonthOfWeek(int year,int week){
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(FIRST_DAY_OF_WEEK);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.WEEK_OF_YEAR, week);
		cal.set(Calendar.DAY_OF_WEEK, END_DAY_OF_WEEK);

		return cal.get(Calendar.MONTH);
	}

	public static int getWeekNumOfYear(int year){
		return getWeekNumOfYear(year,31);
	}

	public static int getWeekNumOfYear(int year,int day_of_month){
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(FIRST_DAY_OF_WEEK);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, 11);//11表示的是12月
		cal.set(Calendar.DAY_OF_MONTH, day_of_month);

		if(cal.get(Calendar.WEEK_OF_YEAR) == 1){
			return getWeekNumOfYear(year,day_of_month-1);
		}
		else{
			return cal.get(Calendar.WEEK_OF_YEAR);
		}
	}

	public static void getCurrentTime() {
		Calendar c = Calendar.getInstance();
		pyear = c.get(Calendar.YEAR); // 获取当前年份
		pmonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
		getMaxDate(pyear, pmonth);
		getMinDate(pyear, pmonth);
	}

	//加一个月
	public static List<Integer> getAddOneMonth(int pyear, int pmonth) {
		List<Integer> list = new ArrayList<>();
		if (pmonth == 12) {
			pmonth = 1;
			pyear += 1;
		} else {
			pmonth += 1;
		}
		list.add(pyear);
		list.add(pmonth);
		return list;
	}

	//减一个月
	public static List<Integer> getMinusOneMonth(int pyear, int pmonth) {
		List<Integer> list = new ArrayList<>();
		if (pmonth == 1) {
			pmonth = 12;
			pyear -= 1;
		} else {
			pmonth -= 1;
		}
		list.add(pyear);
		list.add(pmonth);
		return list;
	}

	public static void getMaxDate(int pyear, int pmonth) {
//        for (int i = 0; i < 6; i++) {
//            if (pmonth == 12) {
//                pmonth = 1;
//                pyear += 1;
//            } else {
//                pmonth += 1;
//            }
//        }
		maxYear = pyear;
		maxMoth = pmonth;
	}

	public static void getMinDate(int pyear, int pmonth) {
		for (int i = 0; i < 24; i++) {
			if (pmonth == 1) {
				pmonth = 12;
				pyear -= 1;
			} else {
				pmonth -= 1;
			}
		}
		minYear = pyear;
		minMoth = pmonth;
	}

	public static Boolean ifMin(Context context, int pyear, int pmonth) {
		Boolean isflag = false;
		if (pyear == minYear && pmonth == minMoth) {
			//   ToastUtil.showToast(context, "两年之后的信息没有！！！");
		} else {
			isflag = true;
		}
		return isflag;
	}

	public static Boolean ifMax(Context context, int pyear, int pmonth) {
		Boolean isflag = false;
		if (pyear == maxYear && pmonth == maxMoth) {
			//     ToastUtil.showToast(context, "超过本月的信息没有！！！");
		} else {
			isflag = true;
		}
		return isflag;
	}


	/**
	 * 返回文字描述的日期
	 *
	 * @param date
	 * @return
	 */
	public static String getTimeFormatText(Date date) {
		if (date == null) {
			return null;
		}
		long diff = new Date().getTime() - date.getTime();
		long r = 0;
		if (diff > year) {
			r = (diff / year);
			return r + "年前";
		}
		if (diff > month) {
			r = (diff / month);
			return r + "个月前";
		}
		if (diff > day) {
			r = (diff / day);
			return r + "天前";
		}
		if (diff > hour) {
			r = (diff / hour);
			return r + "个小时前";
		}
		if (diff > minute) {
			r = (diff / minute);
			return r + "分钟前";
		}
		return "刚刚";
	}

	/**
	 * Function description : 得到本月第一天
	 * create at 2016/11/15 18:08
	 */
	public static Date getFirstdayOfThisMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

}
