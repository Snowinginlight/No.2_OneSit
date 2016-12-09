package minework.onesit.module;

import com.maxleap.MLClassName;
import com.maxleap.MLObject;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by 无知 on 2016/11/11.
 */
@MLClassName("OneSit_Seat")
public class Seat extends MLObject {

    public Seat() {
    }

    //用户名
    public String getUser_id() {
        return getString("user_id");
    }

    public void setUser_id(String user_id) {
        put("user_id", user_id);
    }

    //标题
    public String getSeat_title() {
        return getString("seat_title");
    }

    public void setSeat_title(String seat_title) {
        put("seat_title", seat_title);
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
    //行
    public int getSeat_row() {
        return getInt("seat_row");
    }

    public void setSeat_row(int seat_row) {
        put("seat_row", seat_row);
    }
    //列
    public int getSeat_column() {
        return getInt("seat_column");
    }

    public void setSeat_column(int seat_column) {
        put("seat_column", seat_column);
    }
    //分割线
    public boolean getSeat_divider() {
        return getBoolean("seat_divider");
    }

    public void setSeat_divider(Boolean seat_divider) {
        put("seat_divider", seat_divider);
    }

}
