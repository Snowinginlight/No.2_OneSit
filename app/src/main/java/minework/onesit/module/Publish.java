package minework.onesit.module;

import com.maxleap.MLClassName;
import com.maxleap.MLObject;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by 无知 on 2016/12/10.
 */
@MLClassName("OneSit_Publish")
public class Publish extends MLObject {

    public Publish() {
    }

    //用户名
    public String getUser_id() {
        return getString("user_id");
    }

    public void setUser_id(String user_id) {
        put("user_id", user_id);
    }

    //计划标题
    public String getPublish_title() {
        return getString("publish_title");
    }

    public void setPublish_title(String publish_title) {
        put("publish_title", publish_title);
    }

    //起始时间
    public String getStart_time() {
        return getString("start_time");
    }

    public void setStart_time(String start_time) {
        put("start_time",start_time);
    }
    //终止时间
    public String getStop_time() {
        return getString("stop_time");
    }

    public void setStop_time(String stop_time) {
        put("stop_time", stop_time);
    }

    //地点
    public String getPublish_place() {
        return getString("publish_place");
    }

    public void setPublish_place(String publish_place) {
        put("publish_place", publish_place);
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
        put("seat_table", seat_table);
    }
    //人员名录
    public JSONArray getJoin_list() {
        return getJSONArray("join_list");
    }

    public void setJoin_list(String user_name,String position) {
        JSONObject mDatas = new JSONObject();
        try {
            mDatas.put("user_name",user_name);
            mDatas.put("user_position",position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray join_list;
        if(getJSONArray("join_list")==null){
            join_list = new JSONArray();
        }else{
            join_list = getJSONArray("join_list");
        }
        join_list.put(mDatas);
        put("join_list",join_list);
    }
    //列
    public int getSeat_column() {
        return getInt("seat_column");
    }

    public void setSeat_column(int seat_column) {
        put("seat_column", seat_column);
    }
    //行
    public int getSeat_row() {
        return getInt("seat_row");
    }

    public void setSeat_row(int seat_row) {
        put("seat_row", seat_row);
    }
    //详情
    public String getInformation_text() {
        return getString("information_text");
    }

    public void setInformation_text(String information_text) {
        put("information_text", information_text);
    }

    //图片
    public List<String> getPictures(){
        return getList("pictures");
    }
    public void setPictures(List<String> pictures){
        put("pictures", pictures);
    }
}
