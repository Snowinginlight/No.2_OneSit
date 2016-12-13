package minework.onesit.module;

import com.maxleap.MLClassName;
import com.maxleap.MLObject;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by 无知 on 2016/12/13.
 */
@MLClassName("OneSit_PlanSelf")
public class PlanSelf extends MLObject {
    public PlanSelf(){

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
    //提醒时间
    public String getRemind_time() {
        return getString("remind_time");
    }

    public void setRemind_time(String remind_time) {
        put("remind_time", remind_time);
    }
    //地点
    public String getPlan_place() {
        return getString("plan_place");
    }

    public void setPlan_place(String plan_place) {
        put("plan_place", plan_place);
    }
    //座位
    public String getPlan_seat() {
        return getString("plan_seat");
    }

    public void setPlan_seat(String plan_seat) {
        put("plan_seat", plan_seat);
    }
    //备注
    public String getPlan_tips() {
        return getString("plan_tips");
    }

    public void setPlan_tips(String plan_tips) {
        put("plan_tips", plan_tips);
    }
}
