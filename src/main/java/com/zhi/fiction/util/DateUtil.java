package com.zhi.fiction.util;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.commons.lang3.time.DateUtils;

public class DateUtil {

    public static final String    DEFAULT_DATE_FORMAT           = "yyyy-MM-dd HH:mm:ss";
    
    public static final String    DEFAULT_DATE_FORMAT2           = "yyyy年MM月dd日";

    public static final String    DATE_FORMAT_YYYYMM            = "yyyy-MM";

    public static final String    DATE_FORMAT_YYYYMMDD          = "yyyy-MM-dd";
    
    public static final String    DATE_FORMAT_YYYYMMDD2          = "yyyy/MM/dd";
    
    public static final String    DATE_FORMAT_MMDDHHMM          = "MM/dd HH:mm";
    public static final String    DATE_FORMAT_MMDDHHMM2          = "MM-dd HH:mm";
    
    public static final String    DATE_FORMAT_HHMM          = "HH:mm";
    
    public static final String    DATE_FORMAT_MMDD          = "MM/dd";

    public static final String    DATE_FORMAT_YYYYMMDDHHMMSSSSS = "yyyy-MM-dd HH:mm:ss.SSS";
    
    public static final String    DATE_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    private static final String[] MONTH_STRING                  = { "January", "February", "March", "April", "May",
            "June", "July", "August", "September", "October", "November", "December" };

    public static String toGMTString(Date source) {
        if (null == source) {
            return "";
        }
        // 19 Aug 2010 12:48:49 GMT
        StringBuilder sb = new StringBuilder();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        DateFormat df = new SimpleDateFormat("dd {0} yyyy HH:mm:ss");
        df.setCalendar(cal);
        sb.append(df.format(source)).append(" GMT");
        int index = getMonth(source) - 1;
        String m = MONTH_STRING[index].substring(0, 3);
        return MessageFormat.format(sb.toString(), m);
    }

    public static String toYMDString(Date date) {
        if (date != null) {
            return new SimpleDateFormat(DATE_FORMAT_YYYYMMDD).format(date);
        }
        else {
            return null;
        }
    }

    public static String toYMDHSString(Date date) {
        if (date != null) {
            return new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(date);
        }
        else {
            return null;
        }
    }

    public static Date parseDateYMDHS(String dateStr) {
        return parse(dateStr, DEFAULT_DATE_FORMAT);
    }

    public static Date parseDate(String dateStr) {
        return parse(dateStr, DATE_FORMAT_YYYYMMDD);
    }

    private static Date parse(String yearMonth, String dateFormat) {
        Date date;
        try {
            date = new SimpleDateFormat(dateFormat).parse(yearMonth);
        }
        catch (Exception pe) {
            return null;
        }
        return date;
    }

    public static Date getMonthDate(Date date) {
        return getMonthDate(date, -2);
    }

    public static Date getMonthDate(Date date, int num) {
        Calendar c = toCalendar(date);
        if (c != null) {
            int m = (num <= 0 ? c.get(Calendar.DAY_OF_MONTH) - 1 : num);
            c.add(Calendar.MONTH, -m);
            return c.getTime();
        }
        else {
            return null;
        }
    }

    public static int getMonth(Date date) {
        Calendar c = toCalendar(date);
        if (c != null) {
            return c.get(Calendar.MONTH) + 1;
        }
        else {
            return -1;
        }
    }

    public static Calendar toCalendar(Date date) {
        Calendar c = null;
        if (date != null) {
            c = Calendar.getInstance();
            c.setTime(date);
        }
        return c;
    }

    @SuppressWarnings("deprecation")
    public static int getToday() {
        int todayWeek = Calendar.getInstance().getTime().getDay();
        if (todayWeek == 0) {
            return 7;
        }
        return todayWeek;
    }

    public static Date getDate() {
        Calendar canlendar = Calendar.getInstance();
        return canlendar.getTime();
    }

    public static Date getDate(int iYear, int iMonth, int iDate, int iHour, int iMinute, int iSecond) {
        int month = iMonth - 1;
        Calendar canlendar = Calendar.getInstance();
        canlendar.clear();
        canlendar.set(iYear, month, iDate, iHour, iMinute, iSecond);
        return canlendar.getTime();
    }

    public static Date getDate(int iYear, int iMonth, int iDate, int iHour, int iMinute) {
        return DateUtil.getDate(iYear, iMonth, iDate, iHour, iMinute, 0);
    }

    public static Date getDate(int iYear, int iMonth, int iDate, int iHour) {
        return DateUtil.getDate(iYear, iMonth, iDate, iHour, 0, 0);
    }

    public static Date getDate(int iYear, int iMonth, int iDate) {
        return DateUtil.getDate(iYear, iMonth, iDate, 0, 0, 0);
    }

    public static Date getDate(int iYear, int iMonth) {
        return DateUtil.getDate(iYear, iMonth, 1, 0, 0, 0);
    }

    public static Date getDate(int iYear) {
        return DateUtil.getDate(iYear, 1, 1, 0, 0, 0);
    }

    public static Date getDate(String sYear) {
        int iYear = DateUtil.getRightNumber(sYear);
        return DateUtil.getDate(iYear);
    }

    public static Date getDate(String sYear, String sMonth) {
        int iYear = DateUtil.getRightNumber(sYear);
        int iMonth = DateUtil.getRightNumber(sMonth);
        return DateUtil.getDate(iYear, iMonth);
    }

    public static Date getDate(String sYear, String sMonth, String sDate) {
        int iYear = DateUtil.getRightNumber(sYear);
        int iMonth = DateUtil.getRightNumber(sMonth);
        int iDate = DateUtil.getRightNumber(sDate);
        return DateUtil.getDate(iYear, iMonth, iDate);
    }

    public static Date getDate(String sYear, String sMonth, String sDate, String sHour) {
        int iYear = DateUtil.getRightNumber(sYear);
        int iMonth = DateUtil.getRightNumber(sMonth);
        int iDate = DateUtil.getRightNumber(sDate);
        int iHour = DateUtil.getRightNumber(sHour);
        return DateUtil.getDate(iYear, iMonth, iDate, iHour);
    }

    public static Date getDate(String sYear, String sMonth, String sDate, String sHour, String sMinute) {
        int iYear = DateUtil.getRightNumber(sYear);
        int iMonth = DateUtil.getRightNumber(sMonth);
        int iDate = DateUtil.getRightNumber(sDate);
        int iHour = DateUtil.getRightNumber(sHour);
        int iMinute = DateUtil.getRightNumber(sMinute);
        return DateUtil.getDate(iYear, iMonth, iDate, iHour, iMinute);
    }

    public static Date getDate(String sYear, String sMonth, String sDate, String sHour, String sMinute, String sSecond) {
        int iYear = DateUtil.getRightNumber(sYear);
        int iMonth = DateUtil.getRightNumber(sMonth);
        int iDate = DateUtil.getRightNumber(sDate);
        int iHour = DateUtil.getRightNumber(sHour);
        int iMinute = DateUtil.getRightNumber(sMinute);
        int iSecond = DateUtil.getRightNumber(sSecond);
        return DateUtil.getDate(iYear, iMonth, iDate, iHour, iMinute, iSecond);
    }

    public static Date parseDate(String day, String hour, String minute) {
        Date date = parseDate(day, "yyyy-MM-dd");
        date = addHo(date, hour);
        date = addMi(date, minute);
        return date;
    }

    private static int getRightNumber(String sNumber) {
        try {
            return Integer.parseInt(sNumber);
        }
        catch (NumberFormatException e) {
            return 0;
        }
    }

    public static boolean isMax(Date date0, Date date1) {
        return date0.after(date1);
    }

    public static Date Max(Date date0, Date date1) {
        if (date0 != null && date1 != null) {
            if (date0.getTime() > date1.getTime()) {
                return date0;
            }
            return date1;
        }
        else if (date0 != null && date1 == null) {
            return date0;
        }
        else if (date0 == null && date1 != null) {
            return date1;
        }
        else {
            return null;
        }
    }

    public static Date Min(Date date0, Date date1) {
        if (date0 != null && date1 != null) {
            if (date0.getTime() < date1.getTime()) {
                return date0;
            }
            return date1;
        }
        else {
            return null;
        }
    }

    public static long getMillisecondDif(Date date0, Date date1) {
        if (date0 == null || date1 == null) {
            return 0;
        }
        return date0.getTime() - date1.getTime();
    }

    public static long getSecondDif(Date date0, Date date1) {
        return DateUtil.getMillisecondDif(date0, date1) / 1000;
    }

    public static long getMinuteDif(Date date0, Date date1) {
        return DateUtil.getSecondDif(date0, date1) / 60;
    }

    public static int getHourDif(Date date0, Date date1) {
        return (int) DateUtil.getMinuteDif(date0, date1) / 60;
    }

    public static int getDayDif(Date date0, Date date1) {
        return (int) DateUtil.getHourDif(date0, date1) / 24;
    }

    public static int getMonthDif(Date date0, Date date1) {
        int elapsed = 0;
        GregorianCalendar gc0 = (GregorianCalendar) GregorianCalendar.getInstance();
        GregorianCalendar gc1 = (GregorianCalendar) GregorianCalendar.getInstance();

        if (date1.getTime() > date0.getTime()) {
            gc0.setTime(date0);
            gc1.setTime(date1);
        }
        else {
            gc1.setTime(date0);
            gc0.setTime(date1);
        }
        gc1.clear(Calendar.MILLISECOND);
        gc1.clear(Calendar.SECOND);
        gc1.clear(Calendar.MINUTE);
        gc1.clear(Calendar.HOUR_OF_DAY);
        gc1.clear(Calendar.DATE);

        gc0.clear(Calendar.MILLISECOND);
        gc0.clear(Calendar.SECOND);
        gc0.clear(Calendar.MINUTE);
        gc0.clear(Calendar.HOUR_OF_DAY);
        gc0.clear(Calendar.DATE);

        while (gc0.before(gc1)) {
            gc0.add(Calendar.MONTH, 1);
            elapsed++;
        }
        return elapsed;
    }

    private static Date addDate(Date date, int iArg0, int iDate) {
        Calendar canlendar = Calendar.getInstance();
        canlendar.setTime(date);
        canlendar.add(iArg0, iDate);
        return canlendar.getTime();
    }

    public static Date addSecond(Date date, int iSecond) {
        return addDate(date, Calendar.SECOND, iSecond);
    }

    public static Date addMiles(Date date, int miles) {
        return addDate(date, Calendar.MILLISECOND, miles);
    }

    public static Date addMinute(Date date, int iMinute) {
        return addDate(date, Calendar.MINUTE, iMinute);
    }

    public static Date addHour(Date date, int iHour) {
        return addDate(date, Calendar.HOUR, iHour);
    }

    public static Date addDay(Date date, int iDate) {
        return addDate(date, Calendar.DAY_OF_MONTH, iDate);
    }

    public static Date addMonth(Date date, int iMonth) {
        return addDate(date, Calendar.MONTH, iMonth);
    }

    public static Date addYear(Date date, int iYear) {
        return addDate(date, Calendar.YEAR, iYear);
    }

    public static Date addSecond(Date date, String sSecond) {
        return addSecond(date, getRightNumber(sSecond));
    }

    public static Date addMi(Date date, String sMinute) {
        return addMinute(date, getRightNumber(sMinute));
    }

    public static Date addHo(Date date, String sHour) {
        return addHour(date, getRightNumber(sHour));
    }

    public static Date addDa(Date date, String sDate) {
        return addDay(date, getRightNumber(sDate));
    }

    public static Date addMo(Date date, String sMonth) {
        return addMonth(date, getRightNumber(sMonth));
    }

    public static Date addYe(Date date, String sYear) {
        return addYear(date, getRightNumber(sYear));
    }

    public static String getDateFormate(Date date, String formate) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat simpleDateFormate = new SimpleDateFormat(formate);
        return simpleDateFormate.format(date);
    }

    public static String get4yMdHmsS(Date date) {
        return getDateFormate(date, "yyyy-MM-dd HH:mm:ss.SSS");
    }

    public static String get4yMdHms(Date date) {
        return getDateFormate(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String get4yMdHm(Date date) {
        return getDateFormate(date, "yyyy-MM-dd HH:mm");
    }

    public static String get4yMd(Date date) {
        return getDateFormate(date, "yyyy-MM-dd");
    }

    public static String get4yMM(Date date) {
        return getDateFormate(date, "yyyyMM");
    }

    public static String get4yHh(Date date) {
        return getDateFormate(date, "hh:mm:ss");
    }

    public static String get4yMdNoDash(Date date) {
        return getDateFormate(date, "yyyyMMdd");
    }
    
    public static String getDD(Date date) {
        return getDateFormate(date, "dd");
    }

    public static Date parseDateFullYear(String sDate) {
        SimpleDateFormat simpleDateFormate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return simpleDateFormate.parse(sDate);
        }
        catch (ParseException e) {
            return null;
        }
    }

    public static Date parseDate(String sDate, String formate) {
        SimpleDateFormat simpleDateFormate = new SimpleDateFormat(formate);
        try {
            return sDate == null ? null : simpleDateFormate.parse(sDate);
        }
        catch (ParseException e) {
            return null;
        }
    }

    public static int getPartOfTime(Date date, String part) {
        Calendar canlendar = Calendar.getInstance();
        canlendar.clear();
        canlendar.setTime(date);
        if (part.equals("year")) {
            return canlendar.get(Calendar.YEAR);
        }
        if (part.equals("month")) {
            return canlendar.get(Calendar.MONTH);
        }
        if (part.equals("date")) {
            return canlendar.get(Calendar.DAY_OF_MONTH);
        }
        if (part.equals("hour")) {
            return canlendar.get(Calendar.HOUR_OF_DAY);
        }
        if (part.equals("minute")) {
            return canlendar.get(Calendar.MINUTE);
        }
        if (part.equals("second")) {
            return canlendar.get(Calendar.SECOND);
        }
        if (part.equals("milliSecond")) {
            return canlendar.get(Calendar.MILLISECOND);
        }
        return -1;
    }

    public static boolean isToday(Date date) {
        if (DateUtil.get4yMd(date).equals(DateUtil.get4yMd(Calendar.getInstance().getTime()))) {
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean isLeapYear(int yearNum) {
        boolean isLeep = false;
        if ((yearNum % 4 == 0) && (yearNum % 100 != 0)) {
            isLeep = true;
        }
        else if (yearNum % 400 == 0) {
            isLeep = true;
        }
        else {
            isLeep = false;
        }
        return isLeep;
    }

    public static String getYearMonthEndDay(int yearNum, int monthNum) throws ParseException {
        String tempYear = Integer.toString(yearNum);
        String tempMonth = Integer.toString(monthNum);
        String tempDay = "31";
        if (tempMonth.equals("1") || tempMonth.equals("3") || tempMonth.equals("5") || tempMonth.equals("7")
                || tempMonth.equals("8") || tempMonth.equals("10") || tempMonth.equals("12")) {
            tempDay = "31";
        }
        if (tempMonth.equals("4") || tempMonth.equals("6") || tempMonth.equals("9") || tempMonth.equals("11")) {
            tempDay = "30";
        }
        if (tempMonth.equals("2")) {
            if (isLeapYear(yearNum)) {
                tempDay = "29";
            }
            else {
                tempDay = "28";
            }
        }

        String tempDate = tempYear + "-" + tempMonth + "-" + tempDay;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(sdf.parse(tempDate));
    }

    public static int getWeekDay(Date strDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(strDate);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static boolean nowIsBetweenDates(Date start1, Date end1) {
        Date startTmp = start1;
        Date endTmp = end1;
        Date temp = null;
        if (startTmp.getTime() > endTmp.getTime()) {
            temp = startTmp;
            startTmp = endTmp;
            endTmp = temp;
        }

        long nowTime = new Date().getTime();
        if (nowTime < startTmp.getTime() || nowTime > endTmp.getTime()) {
            return false;
        }

        return true;
    }

    public static Date getStartDateByDate(Date date) {
        Date firstDate = null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        firstDate = cal.getTime();
        return firstDate;
    }

    public static Date getEndDateByDate(Date date) {
        Date firstDate = null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        firstDate = cal.getTime();
        return firstDate;
    }

    public static Date getFirstDayOfWeek(Date date) {
        Date firstDay = null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        firstDay = addDay(cal.getTime(), 1);
        return firstDay;
    }

    public static Date getLastDayOfWeek(Date date) {
        Date lastDay = null;
        lastDay = addDay(getFirstDayOfWeek(date), 6);
        return lastDay;
    }

    public static int getYearByDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    public static int getMonthByDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = 0;
        month = cal.get(Calendar.MONTH) + 1;
        if (month == 13) {
            month = 1;
        }
        return month;
    }

    public static int getDayByDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static int getHourByDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinuteByDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MINUTE);
    }

    public static Date getStartDateByMonth(Date date) {
        Date firstDate = null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        firstDate = cal.getTime();
        return firstDate;
    }

    public static Date getEndDateByMonth(Date date) {
        Date firstDate = null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        firstDate = cal.getTime();
        return firstDate;
    }

    public static Date getDatePart(Date date) throws ParseException {
        String strdate = get4yMd(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(strdate);
    }

    public static String getWeekOfDate(Date dt) {
        String[] weekDays = { "周一","周二","周三","周四","周五","周六","周日"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }

        return weekDays[w];
    }

    public static Long getTimeInMillis(Date date) {
        Calendar cal = Calendar.getInstance();
        Date curDate = null;
        if (date == null) {
            curDate = new Date();
        }
        else {
            curDate = date;
        }
        cal.setTime(curDate);
        return cal.getTimeInMillis();
    }

    public static Date getDateNoTime(Date date) {
        return getDate(getYearByDate(date), getMonthByDate(date), getDayByDate(date));
    }

    public static Date getWeekDate(Date date) {
        Calendar c = toCalendar(date);
        if (c != null) {
            int m = c.get(Calendar.DAY_OF_WEEK);
            if (m - 1 == 0) {
                c.add(Calendar.DAY_OF_WEEK, -6);
            }
            else {
                c.add(Calendar.DAY_OF_WEEK, -(m - 2));
            }
            DateFormat df = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
            df.format(c.getTime());
            return c.getTime();
        }
        else {
            return null;
        }
    }
    

    public static Date getFirstDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();  
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        return c.getTime();
    }
    
    /**
     * 根据日期判断该周是本月的第几周
     * 星期一是一周的第一天
     * author: liang.he 2017年1月10日
     * @param date
     * @return
     */
    public static int getWeekOfMonth(Date date) {
        Date firstWeekFirstDay = DateUtil.getDate(1900); 
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);  
        // 当月的第一天
        Date monthFirstDay = getFirstDayOfMonth(date);
        calendar.setTime(monthFirstDay);
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                firstWeekFirstDay = monthFirstDay;
                break;
            case Calendar.TUESDAY:
                firstWeekFirstDay = DateUtils.addDays(monthFirstDay, -1);
                break;
            case Calendar.WEDNESDAY:
                firstWeekFirstDay = DateUtils.addDays(monthFirstDay, -2);
                break;
            case Calendar.THURSDAY:
                firstWeekFirstDay = DateUtils.addDays(monthFirstDay, -3);
                break;
            case Calendar.FRIDAY:
                firstWeekFirstDay = DateUtils.addDays(monthFirstDay, -4);
                break;
            case Calendar.SATURDAY:
                firstWeekFirstDay = DateUtils.addDays(monthFirstDay, -5);
                break;
            case Calendar.SUNDAY:
                firstWeekFirstDay = DateUtils.addDays(monthFirstDay, -6);
                break;
        }

        int timeSpan = diffDayOfYear(date, firstWeekFirstDay);
        return 1 + timeSpan / 7;

    }
    
    /**
     * 计算两个日期之间的间隔天数
     * author: liang.he 2017年1月10日
     * @param date1
     * @param date2
     * @return
     */
    private static int diffDayOfYear(Date date1, Date date2) {
        Calendar can1 = Calendar.getInstance();
        can1.setTime(date1);
        Calendar can2 = Calendar.getInstance();
        can2.setTime(date2);
        //拿出两个年份
        int year1 = can1.get(Calendar.YEAR);
        int year2 = can2.get(Calendar.YEAR);
        //天数
        int days = 0;
        Calendar can = null;
        //如果can1 < can2
        //减去小的时间在这一年已经过了的天数
        //加上大的时间已过的天数
        if(can1.before(can2)){
            days -= can1.get(Calendar.DAY_OF_YEAR);
            days += can2.get(Calendar.DAY_OF_YEAR);
            can = can1;
        }else{
            days -= can2.get(Calendar.DAY_OF_YEAR);
            days += can1.get(Calendar.DAY_OF_YEAR);
            can = can2;
        }
        for (int i = 0; i < Math.abs(year2-year1); i++) {
            //获取小的时间当前年的总天数
            days += can.getActualMaximum(Calendar.DAY_OF_YEAR);
            //再计算下一年。
            can.add(Calendar.YEAR, 1);
        }
        return days;
    }

    /**
     * 专栏文章日期转化
     * 当天文章,则显示HH:mm
     * 昨天文章,则显示昨天
     * 往后文章,则显示yyyy/MM/dd
     * @param date
     * @return
     */
    public static String articleTimeTransfer(Date date){
        String result;
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String input = sdf.format(date);
        String today = sdf.format(now);
        String yesterday = sdf.format(addDay(now, -1));
        if (input.equals(today)) {
            result = getDateFormate(date, "HH:mm");
        }else if(input.equals(yesterday)) {
            result = "昨天";
        }else{
            result = getDateFormate(date, "yyyy/MM/dd");
        }
        return result;
    }
    

    public static Date get19900101() {
        SimpleDateFormat simpleDateFormate = new SimpleDateFormat("yyyyMMdd");
        try {
            return simpleDateFormate.parse("19900101");
        }
        catch (ParseException e) {
            return null;
        }
    }
}
