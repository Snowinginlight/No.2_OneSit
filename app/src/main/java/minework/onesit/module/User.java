package minework.onesit.module;

import com.maxleap.MLClassName;
import com.maxleap.MLObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 无知 on 2016/11/11.
 */
@MLClassName("OneSit_User")
public class User extends MLObject {

    public User() {
    }

    //用户名
    public String getUser_id() {
        return getString("user_id");
    }

    public void setUser_id(String user_id) {
        put("user_id", user_id);
    }

    //昵称
    public String getUser_name() {
        return getString("user_name");
    }

    public void setUser_name(String user_name) {
        put("user_name", user_name);
    }

    //签名
    public String getUser_signature() {
        return getString("user_signature");
    }

    public void setUser_signature(String user_signature) {
        put("user_signature", user_signature);
    }

    //头像
    public String getUser_image() {
        return getString("user_image");
    }

    public void setUser_image(String user_image) {
        put("user_image", user_image);
    }

    //密码
    public String getUser_password() {
        return getString("user_password");
    }

    public void setUser_password(String user_password) {
        put("user_password", user_password);
    }

    //邮箱
    public String getUser_email() {
        return getString("user_email");
    }

    public void setUser_email(String user_email) {
        put("user_email", user_email);
    }

    //发布
    public void addUser_publish(String object_id) {
        if (getList("publish_list") == null) {
            List<String> list = new ArrayList<>();
            list.add(object_id);
            put("publish_list", list);
        } else {
            List<String> list = getList("publish_list");
            list.add(object_id);
            put("publish_list", list);
        }
    }

    public void removeUser_publish(String object_id) {
        List<String> list = getList("publish_list");
        list.remove(object_id);
        put("publish_list", list);
    }

    public List<String> getUser_publish() {
        return getList("publish_list");
    }

    //关注
    public void addUser_care(String object_id) {
        if (getList("care_list") == null) {
            List<String> list = new ArrayList<>();
            list.add(object_id);
            put("care_list", list);
        } else {
            List<String> list = getList("care_list");
            list.add(object_id);
            put("care_list", list);
        }
    }

    public void removeUser_care(String object_id) {
        List<String> list = getList("care_list");
        list.remove(object_id);
        put("care_list", list);
    }

    public List<String> getUser_care() {
        return getList("care_list");
    }

    //订阅
    public void addUser_book(String object_id) {
        if (getList("book_list") == null) {
            List<String> list = new ArrayList<>();
            list.add(object_id);
            put("book_list", list);
        } else {
            List<String> list = getList("book_list");
            list.add(object_id);
            put("book_list", list);
        }
    }

    public void removeUser_book(String object_id) {
        List<String> list = getList("book_list");
        list.remove(object_id);
        put("book_list", list);
    }

    public List<String> getUser_book() {
        return getList("book_list");
    }

}
