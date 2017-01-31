package minework.onesit.util;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by 无知 on 2016/11/11.
 */

public class MyUtil {
    public static final void sleep(long times) {
        try {
            Thread.sleep(times);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String jsonArrayToString(JSONArray jsonArray) {
        StringBuilder stringBuilder = new StringBuilder();
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    stringBuilder.append(jsonArray.get(i)+",");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuilder.toString();
    }
    public static List<Integer> jsonArrayToListInteger(JSONArray jsonArray){
        List<Integer> mDatas = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                mDatas.add(jsonArray.getInt(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return mDatas;
    }

    public static String listToString(List<String> list) {
        StringBuilder stringBuilder = new StringBuilder();
        if(list!=null){
        for (int i = 0;i<list.size();i++) {
            stringBuilder.append(list.get(i));
            stringBuilder.append(",");
        }}
        return stringBuilder.toString();
    }

    public static int getNumber(String string) {
        if (!TextUtils.isEmpty(string)){
            return string.split(",").length;
        }
        return 0;
    }

    public static int getNumber(JSONArray jsonArray) {
        return jsonArray.length();
    }

    public static List<String> stringToListString(String string) {
        List<String> list = new ArrayList<String>();
        if(string!=null){
        String[] array = string.split(",");
        for (int i = 0; i < array.length; i++)
            list.add(array[i]);}
        return list;
    }

    public static List<Map<String, String>> orderByTime(List<Map<String, String>> list1, List<Map<String, String>> list2) {
        List<Map<String, String>> order = new ArrayList<Map<String, String>>();
        order.addAll(list1);
        order.addAll(list2);
        Collections.sort(order, new Comparator<Map<String, String>>() {
            @Override
            public int compare(Map<String, String> m1, Map<String, String> m2) {
                long timeDifference = MyTimeUtil.startTimeToMillis(m1.get("start_time")) - MyTimeUtil.startTimeToMillis(m2.get("start_time"));
                if (timeDifference > 0) {
                    return 1;
                } else if (timeDifference < 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        return order;
    }

    public static List<Map<String, String>> orderByTime(List<Map<String, String>> list) {
        Collections.sort(list, new Comparator<Map<String, String>>() {
            @Override
            public int compare(Map<String, String> m1, Map<String, String> m2) {
                long timeDifference = MyTimeUtil.startTimeToMillis(m1.get("start_time")) - MyTimeUtil.startTimeToMillis(m2.get("start_time"));
                if (timeDifference > 0) {
                    return 1;
                } else if (timeDifference < 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        return list;
    }
    public static List<Map<String, String>> orderByTime2(List<Map<String, String>> list) {
        Collections.sort(list, new Comparator<Map<String, String>>() {
            @Override
            public int compare(Map<String, String> m1, Map<String, String> m2) {
                long timeDifference = MyTimeUtil.createTimeToMillis(m1.get("create_at")) - MyTimeUtil.createTimeToMillis(m2.get("create_at"));
                if (timeDifference > 0) {
                    return 1;
                } else if (timeDifference < 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        return list;
    }

    public static List<Map<String, String>> orderNewsListByTime(List<Map<String, String>> list) {
        Collections.sort(list, new Comparator<Map<String, String>>() {
            @Override
            public int compare(Map<String, String> m1, Map<String, String> m2) {
                long timeDifference = MyTimeUtil.startTimeToMillis(m1.get("news_date")) - MyTimeUtil.startTimeToMillis(m2.get("news_date"));
                if (timeDifference > 0) {
                    return 1;
                } else if (timeDifference < 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        return list;
    }

    public static List<Map<String, String>> orderNews(List<Map<String, String>> list1, List<Map<String, String>> list2) {
        List<Map<String, String>> order = new ArrayList<Map<String, String>>();
        order.addAll(list1);
        order.addAll(list2);
        Collections.sort(order, new Comparator<Map<String, String>>() {
            @Override
            public int compare(Map<String, String> m1, Map<String, String> m2) {
                long timeDifference = MyTimeUtil.TimeToMillis(m1.get("news_date")) - MyTimeUtil.TimeToMillis(m2.get("news_date"));
                if (timeDifference > 0) {
                    return 1;
                } else if (timeDifference < 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        return order;
    }

    public static List<Map<String, String>> chooseDatas(List<Map<String, String>> mDatas, int month, int day) {
        List<Map<String,String>> myData = new ArrayList<Map<String, String>>();
        for (int i = 0;i<mDatas.size();i++) {
            if ((month == MyTimeUtil.startTimeToDateTime(mDatas.get(i).get("start_time")).getMonthOfYear())&(day == MyTimeUtil.startTimeToDateTime(mDatas.get(i).get("start_time")).getDayOfMonth()) ) {
                myData.add(mDatas.get(i));
            }
        }
        return myData;
    }
}
