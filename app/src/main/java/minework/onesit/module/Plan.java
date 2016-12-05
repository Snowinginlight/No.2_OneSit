package minework.onesit.module;

import com.maxleap.MLClassName;
import com.maxleap.MLObject;

import org.joda.time.DateTime;

/**
 * Created by 无知 on 2016/11/23.
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

    //计划标题
    public String getPlan_title() {
        return getString("plan_title");
    }

    public void setPlan_title(String plan_title) {
        put("plan_title", plan_title);
    }

    //起始时间
    public DateTime getStart_time() {
        return (DateTime) get("start_time");
    }

    public void setUser_password(DateTime start_time) {
        put("start_time", start_time);
    }

    //终止时间
    public DateTime getStop_time() {
        return (DateTime) get("stop_time");
    }

    public void setStop_time(DateTime stop_time) {
        put("stop_time", stop_time);
    }

    //地点
    public String getPlan_place() {
        return getString("plan_place");
    }

    public void setPlan_place(String plan_place) {
        put("plan_place", plan_place);
    }
        /*座位 数据类型待定
        public String getPlan_seat(){return getString("plan_seat");}

        public void setPlan_seat(String plan_seat) {put("plan_seat",plan_seat);}*/
}
