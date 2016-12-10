package minework.onesit.module;

import android.text.Editable;
import android.text.Html;

import com.maxleap.MLClassName;
import com.maxleap.MLObject;

import org.joda.time.DateTime;
import org.json.JSONArray;

import java.util.List;

/**
 * Created by 无知 on 2016/11/23.
 */
@MLClassName("OneSit_PublishModel")
public class PublishModel extends MLObject {

    public PublishModel() {
    }

    //用户名
    public String getUser_id() {
        return getString("user_id");
    }

    public void setUser_id(String user_id) {
        put("user_id", user_id);
    }

    //计划标题
    public String getPublish_Model_title() {
        return getString("publish_model_title");
    }

    public void setPublish_Model_title(String publish_model_title) {
        put("publish_model_title", publish_model_title);
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
    public String getPublish_Model_place() {
        return getString("publish_model_place");
    }

    public void setPublish_Model_place(String publish_model_place) {
        put("publish_model_place", publish_model_place);
    }
    //人数上限
    public int getPeople_number() {
        return getInt("people_number");
    }

    public void setPeople_number(int people_number) {
        put("people_number", people_number);
    }
    //座位图
    public JSONArray getSeat_table() {
        return getJSONArray("seat_table");
    }

    public void setSeat_table(List<Integer> mDatas) {
        JSONArray seat_table = new JSONArray();
        for (Integer i : mDatas)
            seat_table.put(i);
        put("seat_table",seat_table);
    }
    //列
    public int getSeat_column() {
        return getInt("seat_column");
    }

    public void setSeat_column(int seat_column) {
        put("seat_column", seat_column);
    }
    //详情
    public String getInformation_text(){
        return getString("information_text");
    }
    public void setInformation_text(String information_text){
        put("information_text", information_text);
    }
}
