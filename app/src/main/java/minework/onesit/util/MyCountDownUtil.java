package minework.onesit.util;

import android.os.Handler;
import android.os.Message;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 无知 on 2016/12/14.
 */

public class MyCountDownUtil {
    public static void countDownSecond(final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);//1秒
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    } catch (Exception e) {
                    }
                }
            }
        }).start();
    }

    public static void countDownMinute(final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(60000);//1分钟
                        Message message = new Message();
                        message.what = 2;
                        handler.sendMessage(message);
                    } catch (Exception e) {
                    }
                }
            }
        }).start();
    }

    public static void countDownHour(final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(3600000);//1小时
                        Message message = new Message();
                        message.what = 3;
                        handler.sendMessage(message);
                    } catch (Exception e) {
                    }
                }
            }
        }).start();
    }

    public static List<Integer> castUpTime(String oldTime) {
        int newTime = 0;
        int flag = 0;
        List<Integer> list = new ArrayList<Integer>();
        DateTime dateTime = null;
        DateTimeFormatter dateFormat1 = DateTimeFormat.forPattern("yyyy年MM月dd日HH：mm");
        DateTimeFormatter dateFormat2 = DateTimeFormat.forPattern("yyyy年MM月dd日");
        try {
             dateTime = dateFormat1.parseDateTime(oldTime);
        }catch (IllegalArgumentException e){
            dateTime = dateFormat2.parseDateTime(oldTime);
        }
        if (dateTime.getYear() == DateTime.now().getYear()) {
            if (dateTime.getMonthOfYear() == DateTime.now().getMonthOfYear()) {
                if (dateTime.getDayOfMonth() == DateTime.now().getDayOfMonth()) {
                    if (dateTime.getHourOfDay() == DateTime.now().getHourOfDay()) {
                        newTime = Math.abs(dateTime.getSecondOfMinute() - DateTime.now().getSecondOfMinute());
                        flag = 1;
                    } else {
                        newTime = Math.abs(dateTime.getMinuteOfHour() - DateTime.now().getMinuteOfHour());
                        flag = 2;
                    }
                } else {
                    newTime = Math.abs(24-DateTime.now().getHourOfDay()+dateTime.getHourOfDay());
                    flag = 3;
                }
            } else {
                newTime = Math.abs(24-DateTime.now().getHourOfDay()+dateTime.getHourOfDay());
                flag = 3;
            }
        } else {
            newTime = Math.abs(24-DateTime.now().getHourOfDay()+dateTime.getHourOfDay());
            flag = 3;
        }
        list.add(newTime);
        list.add(flag);
        return list;
    }

    public static String castDownTime(String oldTime) {
        int newTime = 0;
        DateTime dateTime = null;
        DateTimeFormatter dateFormat1 = DateTimeFormat.forPattern("yyyy年MM月dd日HH：mm");
        DateTimeFormatter dateFormat2 = DateTimeFormat.forPattern("yyyy年MM月dd日");
        try {
            dateTime = dateFormat1.parseDateTime(oldTime);
        }catch (IllegalArgumentException e){
            dateTime = dateFormat2.parseDateTime(oldTime);
        }
        if (dateTime.getYear() == DateTime.now().getYear()) {
            if (dateTime.getMonthOfYear() == DateTime.now().getMonthOfYear()) {
                if (dateTime.getDayOfMonth() == DateTime.now().getDayOfMonth()) {
                    if (dateTime.getHourOfDay() == DateTime.now().getHourOfDay()) {
                        newTime = (dateTime.getMinuteOfHour() - DateTime.now().getMinuteOfHour());
                    } else {
                        newTime = (dateTime.getHourOfDay() - DateTime.now().getHourOfDay());
                    }
                } else {
                    newTime = (dateTime.getDayOfMonth() - DateTime.now().getDayOfMonth());
                }
            } else {
                newTime = (dateTime.getDayOfYear() - DateTime.now().getDayOfYear());
            }
        } else {
            newTime = ((365-DateTime.now().getDayOfYear())+dateTime.getDayOfYear()+365*Math.abs(dateTime.getYear()-DateTime.now().getYear()));
        }
        return String.valueOf(newTime);
    }

}
