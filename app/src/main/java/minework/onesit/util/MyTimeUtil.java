package minework.onesit.util;

import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * Created by 无知 on 2016/12/21.
 */

public class MyTimeUtil {

    private static DateTimeFormatter dateFormat0 = DateTimeFormat.forPattern("HH:mm");
    private static DateTimeFormatter dateFormat1 = DateTimeFormat.forPattern("MM-dd");
    private static DateTimeFormatter dateFormat2 = DateTimeFormat.forPattern("yyyy MM-dd");
    private static DateTimeFormatter dateFormat3 = DateTimeFormat.forPattern("MM-dd HH:mm");
    private static DateTimeFormatter dateFormat4 = DateTimeFormat.forPattern("yyyy年MM月dd日HH:mm");

    public static String convertCommTimeToString2(String time) {
        DateTime oldTime = dateFormat4.parseDateTime(time);
        DateTime nowTime = DateTime.now();
        if (oldTime.getYear() == nowTime.getYear()) {
            if (oldTime.getDayOfYear() == nowTime.getDayOfYear()) {
                return oldTime.toString(dateFormat0);
            } else {
                return oldTime.toString(dateFormat3);
            }
        } else {
            return oldTime.toString(dateFormat2);
        }
    }

    public static String convertCommTimeToString1(Date Time) {
        DateTime oldTime = new DateTime(Time);
        return oldTime.toString(dateFormat4);
    }

    public static String convertNewsTimeToString(Date Time) {
        DateTime oldTime = new DateTime(Time);
        DateTime nowTime = DateTime.now();
        if (oldTime.getYear() == nowTime.getYear()) {
            if (oldTime.getDayOfYear() == nowTime.getDayOfYear()) {
                return oldTime.toString(dateFormat0);
            } else if (oldTime.getDayOfYear() == (nowTime.getDayOfYear() - 1)) {
                return "昨天";
            } else if (oldTime.getDayOfYear() == (nowTime.getDayOfYear() - 2)) {
                return "前天";
            } else if (oldTime.getWeekOfWeekyear() == nowTime.getWeekOfWeekyear() && (nowTime.getDayOfYear() - oldTime.getDayOfYear() > 2)) {
                if (oldTime.getDayOfWeek() == 7)
                    return "星期日";
                return "星期" + oldTime.getDayOfWeek();
            } else {
                return oldTime.toString(dateFormat1);
            }
        } else {
            return oldTime.toString(dateFormat2);
        }
    }

    public static String startTimeToDayString(String start_time) {
        DateTime dateTime = null;
        DateTimeFormatter dateFormat1 = DateTimeFormat.forPattern("yyyy年MM月dd日HH:mm");
        DateTimeFormatter dateFormat2 = DateTimeFormat.forPattern("yyyy年MM月dd日");
        try {
            dateTime = dateFormat1.parseDateTime(start_time);
        } catch (IllegalArgumentException e) {
            dateTime = dateFormat2.parseDateTime(start_time);
        }
        return String.valueOf(dateTime.getDayOfMonth());
    }

    public static String startTimeToHoursString(String start_time) {
        DateTime dateTime = null;
        DateTimeFormatter dateFormat1 = DateTimeFormat.forPattern("yyyy年MM月dd日HH:mm");
        DateTimeFormatter dateFormat2 = DateTimeFormat.forPattern("yyyy年MM月dd日");
        DateTimeFormatter dateFormat3 = DateTimeFormat.forPattern("HH:mm");
        try {
            dateTime = dateFormat1.parseDateTime(start_time);
        } catch (IllegalArgumentException e) {
            dateTime = dateFormat2.parseDateTime(start_time);
        }
        return String.valueOf(dateTime.toString(dateFormat3));
    }

    public static String startTimeToDaysString(String start_time) {
        DateTime dateTime = null;
        DateTimeFormatter dateFormat1 = DateTimeFormat.forPattern("yyyy年MM月dd日HH:mm");
        DateTimeFormatter dateFormat2 = DateTimeFormat.forPattern("yyyy年MM月dd日");
        DateTimeFormatter dateFormat3 = DateTimeFormat.forPattern("MM.dd");
        try {
            dateTime = dateFormat1.parseDateTime(start_time);
        } catch (IllegalArgumentException e) {
            dateTime = dateFormat2.parseDateTime(start_time);
        }
        return String.valueOf(dateTime.toString(dateFormat3));
    }

    public static DateTime startTimeToDateTime(String start_time) {
        DateTime dateTime = null;
        DateTimeFormatter dateFormat1 = DateTimeFormat.forPattern("yyyy年MM月dd日HH:mm");
        DateTimeFormatter dateFormat2 = DateTimeFormat.forPattern("yyyy年MM月dd日");
        try {
            dateTime = dateFormat1.parseDateTime(start_time);
        } catch (IllegalArgumentException e) {
            dateTime = dateFormat2.parseDateTime(start_time);
        }
        return dateTime;
    }

    public static Long startTimeToMillis(String start_time) {
        DateTime dateTime = null;
        DateTimeFormatter dateFormat1 = DateTimeFormat.forPattern("yyyy年MM月dd日HH:mm");
        DateTimeFormatter dateFormat2 = DateTimeFormat.forPattern("yyyy年MM月dd日");
        try {
            dateTime = dateFormat1.parseDateTime(start_time);
        } catch (IllegalArgumentException e) {
            dateTime = dateFormat2.parseDateTime(start_time);
        }
        return dateTime.getMillis();
    }

    public static Long createTimeToMillis(String create_time) {
        DateTime dateTime = null;
        DateTimeFormatter dateFormat1 = DateTimeFormat.forPattern("yyyy.MM.dd HH:mm");
        DateTimeFormatter dateFormat2 = DateTimeFormat.forPattern("yyyy年MM月dd日");
        try {
            dateTime = dateFormat1.parseDateTime(create_time);
        } catch (IllegalArgumentException e) {
            dateTime = dateFormat2.parseDateTime(create_time);
        }
        return dateTime.getMillis();
    }

    public static Long TimeToMillis(String time) {
        DateTime dateTime = null;
        DateTimeFormatter dateFormat1 = DateTimeFormat.forPattern("yyyy年MM月dd日HH:mm");
        try {
            dateTime = dateFormat1.parseDateTime(time);
        } catch (IllegalArgumentException e) {
            dateTime = dateFormat2.parseDateTime(time);
        }
        return dateTime.getMillis();
    }

    public static String changeToDuringTime(String start_time, String stop_time) {
        DateTime dateTime1 = null;
        DateTime dateTime2 = null;
        DateTimeFormatter dateFormat1 = DateTimeFormat.forPattern("yyyy年MM月dd日HH:mm");
        DateTimeFormatter dateFormat2 = DateTimeFormat.forPattern("yyyy年MM月dd日");
        try {
            dateTime1 = dateFormat1.parseDateTime(start_time);
        } catch (IllegalArgumentException e) {
            dateTime1 = dateFormat2.parseDateTime(start_time);
        }
        try {
            dateTime2 = dateFormat1.parseDateTime(stop_time);
        } catch (IllegalArgumentException e) {
            dateTime2 = dateFormat2.parseDateTime(stop_time);
        }
        if (dateTime1.getYear() == dateTime2.getYear()) {
            if (dateTime1.getMonthOfYear()==dateTime2.getMonthOfYear()) {
                if(dateTime1.getDayOfMonth()==dateTime2.getDayOfMonth()){
                    return dateTime1.toString(DateTimeFormat.forPattern("MM.dd HH:mm")) + "~" + dateTime2.toString(DateTimeFormat.forPattern("HH:mm"));
                }else {
                    return dateTime1.toString(DateTimeFormat.forPattern("MM.dd HH:mm")) + "~" + dateTime2.toString(DateTimeFormat.forPattern("dd HH:mm"));
                }
            } else {
                return dateTime1.toString(DateTimeFormat.forPattern("MM.dd")) + "~" + dateTime2.toString(DateTimeFormat.forPattern("MM.dd"));
            }
        } else {
            return dateTime1.toString(DateTimeFormat.forPattern("yyyy.MM.dd")) + "~" + dateTime2.toString(DateTimeFormat.forPattern("yyyy.MM.dd"));
        }
    }
}
