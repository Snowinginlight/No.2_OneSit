package minework.onesit.module;

import com.maxleap.MLClassName;
import com.maxleap.MLObject;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;

import java.util.List;

/**
 * Created by 无知 on 2016/12/10.
 */
@MLClassName("OneSit_Plan")
public class Plan extends MLObject {

    public Plan() {

    }

    //用户名
    public String getUser_id() {
        return getString("user_id");
    }

    public void setUser_id(String user_id) {
        put("user_id", user_id);
    }

    //标题
    public String getPlan_title() {
        return getString("plan_title");
    }

    public void setPlan_title(String plan_title) {
        put("plan_title", plan_title);
    }

    //起始时间
    public String getStart_time() {
        DateTimeFormatter dateFormat = DateTimeFormat.forPattern("yyyy年MM月dd日HH：mm");
        DateTime dateTime = new DateTime(getDate("start_time"));
        return dateTime.toString(dateFormat);
    }

    public void setStart_time(String start_time) {
        DateTime dateTime = null;
        DateTimeFormatter dateFormat1 = DateTimeFormat.forPattern("yyyy年MM月dd日HH：mm");
        DateTimeFormatter dateFormat2 = DateTimeFormat.forPattern("yyyy年MM月dd日");
        try {
            dateTime = dateFormat1.parseDateTime(start_time);
        } catch (IllegalArgumentException e) {
            dateTime = dateFormat2.parseDateTime(start_time);
        }
        put("start_time", dateTime.toDate());
    }

    //终止时间
    public String getStop_time() {
        DateTimeFormatter dateFormat = DateTimeFormat.forPattern("yyyy年MM月dd日HH：mm");
        DateTime dateTime = new DateTime(getDate("stop_time"));
        return dateTime.toString(dateFormat);
    }

    public void setStop_time(String stop_time) {
        DateTime dateTime = null;
        DateTimeFormatter dateFormat1 = DateTimeFormat.forPattern("yyyy年MM月dd日HH：mm");
        DateTimeFormatter dateFormat2 = DateTimeFormat.forPattern("yyyy年MM月dd日");
        try {
            dateTime = dateFormat1.parseDateTime(stop_time);
        } catch (IllegalArgumentException e) {
            dateTime = dateFormat2.parseDateTime(stop_time);
        }
        put("stop_time", dateTime.toDate());
    }

    //地点
    public String getPlan_place() {
        return getString("plan_place");
    }

    public void setPlan_place(String plan_place) {
        put("plan_place", plan_place);
    }

    //座位
    public JSONArray getSeat_table() {
        return getJSONArray("seat_table");
    }

    public void setSeat_table(List<Integer> mDatas) {
        JSONArray seat_table = new JSONArray();
        for (Integer i : mDatas)
            seat_table.put(i);
        put("seat_table", seat_table);
    }

    //备注
    public String getPlan_tips() {
        return getString("plan_tips");
    }

    public void setPlan_tips(String plan_tips) {
        put("plan_tips", plan_tips);
    }

}
