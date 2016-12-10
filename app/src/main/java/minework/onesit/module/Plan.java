package minework.onesit.module;

import com.maxleap.MLClassName;
import com.maxleap.MLObject;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by 无知 on 2016/12/10.
 */
@MLClassName("OneSit_Plan")
public class Plan extends MLObject {

    public Plan(){

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
        return getString("start_time");
    }

    public void setStart_time(String start_time) {
        put("start_time", start_time);
    }
    //终止时间
    public String getStop_time() {
        return getString("stop_time");
    }

    public void setStop_time(String stop_time) {
        put("stop_time", stop_time);
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
    //详情
    public String getInformation_text() {
        return getString("information_text");
    }

    public void setInformation_text(String information_text) {
        put("information_text", information_text);
    }

}
