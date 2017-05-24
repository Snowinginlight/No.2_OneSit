package minework.onesit.database;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import minework.onesit.activity.MyApplication;
import minework.onesit.activity.UserShow;
import minework.onesit.module.News;
import minework.onesit.module.Plan;
import minework.onesit.module.Publish;
import minework.onesit.module.User;
import minework.onesit.util.MyRomateSQLUtil;
import minework.onesit.util.MyUtil;

/**
 * Created by 无知 on 2016/12/22.
 */

public class MyDatabaseUtil {

    private static SQLiteDatabase db = new MyDatabaseHelper(MyApplication.getInstance(), "OneSit.db", null, 1).getWritableDatabase();

    public static void insertUser(User user, String user_image, String login) {
        if (!queryExist(user.getObjectId(), "User")) {
            ContentValues values = new ContentValues();
            values.put("object_id", user.getObjectId());
            values.put("user_login", login);
            values.put("user_id", user.getUser_id());
            values.put("user_name", user.getUser_name());
            values.put("user_signature", user.getUser_signature());
            values.put("user_image", user_image);
            values.put("user_email", user.getUser_email());
            values.put("user_publish", MyUtil.listToString(user.getUser_publish()));
            values.put("user_care", MyUtil.listToString(user.getUser_care()));
            values.put("user_book", MyUtil.listToString(user.getUser_book()));
            db.insert("User", null, values);
            values.clear();
        }
    }

    public static void insertUser(User user, String user_image, String login, Handler handler) {
        if (!queryExist(user.getObjectId(), "User")) {
            ContentValues values = new ContentValues();
            values.put("object_id", user.getObjectId());
            values.put("user_login", login);
            values.put("user_id", user.getUser_id());
            values.put("user_name", user.getUser_name());
            values.put("user_signature", user.getUser_signature());
            values.put("user_image", user_image);
            values.put("user_email", user.getUser_email());
            values.put("user_publish", MyUtil.listToString(user.getUser_publish()));
            values.put("user_care", MyUtil.listToString(user.getUser_care()));
            values.put("user_book", MyUtil.listToString(user.getUser_book()));
            db.insert("User", null, values);
            values.clear();
        }
        Message msg = new Message();
        msg.what = 1;
        handler.sendMessage(msg);
    }

    public static void deleteUser() {
        db.delete("User", null, null);
    }

    public static Map<String, String> queryUser(String userId) {
        Map<String, String> map = new HashMap<String, String>();
        Cursor cursor = db.query("User", null, "user_id = ?", new String[]{userId}, null, null, null);
        while (cursor.moveToNext()) {
            map.put("object_id", cursor.getString(cursor.getColumnIndex("object_id")));
            map.put("user_login", cursor.getString(cursor.getColumnIndex("user_login")));
            map.put("user_id", cursor.getString(cursor.getColumnIndex("user_id")));
            map.put("user_name", cursor.getString(cursor.getColumnIndex("user_name")));
            map.put("user_signature", cursor.getString(cursor.getColumnIndex("user_signature")));
            map.put("user_image", cursor.getString(cursor.getColumnIndex("user_image")));
            map.put("user_email", cursor.getString(cursor.getColumnIndex("user_email")));
            map.put("user_publish", cursor.getString(cursor.getColumnIndex("user_publish")));
            map.put("user_care", cursor.getString(cursor.getColumnIndex("user_care")));
            map.put("user_book", cursor.getString(cursor.getColumnIndex("user_book")));
        }
        return map;
    }

    public static Map<String, String> queryUserExist() {
        Map<String, String> map = null;
        Cursor cursor = db.query("User", null, "user_login = ?", new String[]{"true"}, null, null, null);
        while (cursor.moveToNext()) {
            map = new HashMap<String, String>();
            map.put("object_id", cursor.getString(cursor.getColumnIndex("object_id")));
            map.put("user_login", cursor.getString(cursor.getColumnIndex("user_login")));
            map.put("user_id", cursor.getString(cursor.getColumnIndex("user_id")));
            map.put("user_name", cursor.getString(cursor.getColumnIndex("user_name")));
            map.put("user_signature", cursor.getString(cursor.getColumnIndex("user_signature")));
            map.put("user_image", cursor.getString(cursor.getColumnIndex("user_image")));
            map.put("user_email", cursor.getString(cursor.getColumnIndex("user_email")));
            map.put("user_publish", cursor.getString(cursor.getColumnIndex("user_publish")));
            map.put("user_care", cursor.getString(cursor.getColumnIndex("user_care")));
            map.put("user_book", cursor.getString(cursor.getColumnIndex("user_book")));
        }
        return map;
    }

    public static void updateUser(String userId, String updateContent, String updateType) {
        ContentValues values = new ContentValues();
        values.put(updateType, updateContent);
        db.update("User", values, "user_id = ?", new String[]{userId});
        values.clear();
    }

    public static void insertNews(News news) {
        ContentValues values = new ContentValues();
        if (!queryExist(news.getObjectId(), "News")) {
            values.put("object_id", news.getObjectId());
            values.put("send_user_id", news.getSend_user_id());
            values.put("receive_user_id", news.getReceive_user_id());
            values.put("news_date", news.getNews_date());
            values.put("news_message", news.getNews_message());
            db.insert("News", null, values);
            values.clear();
        }
    }

    public static List<Map<String, String>> queryNews(String send_user_id, String receive_user_id) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Cursor cursor = db.query("News", null, "send_user_id = ? and receive_user_id = ?", new String[]{send_user_id, receive_user_id}, null, null, "no_ ASC");
        if (cursor.moveToFirst()) {
            do {
                Map<String, String> map = new HashMap<String, String>();
                map.put("send_user_id", cursor.getString(cursor.getColumnIndex("send_user_id")));
                map.put("receive_user_id", cursor.getString(cursor.getColumnIndex("receive_user_id")));
                map.put("news_date", cursor.getString(cursor.getColumnIndex("news_date")));
                map.put("news_message", cursor.getString(cursor.getColumnIndex("news_message")));
                list.add(map);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    //消息列表（只需用户id,image,name.最新的message，date即可）
    public static List<Map<String, String>> queryNewsList(String user_id) {
        List<Map<String, String>> newsList = new ArrayList<Map<String, String>>();
        Set<String> nameSet = new HashSet<String>();
        Cursor sendCursor = db.query("News", null, "send_user_id = ?", new String[]{user_id}, null, null, "no_ ASC");
        Cursor receiveCursor = db.query("News", null, "receive_user_id = ?", new String[]{user_id}, null, null, "no_ ASC");
        if (sendCursor.moveToFirst()) {
            do {
                nameSet.add(sendCursor.getString(sendCursor.getColumnIndex("receive_user_id")));
            } while (sendCursor.moveToNext());
        }
        if (receiveCursor.moveToFirst()) {
            do {
                nameSet.add(receiveCursor.getString(receiveCursor.getColumnIndex("send_user_id")));
            } while (sendCursor.moveToNext());
        }
        sendCursor.close();
        receiveCursor.close();
        for (String name : nameSet) {
            Log.d("Test", "name:" + name);
            Map<String, String> map = new HashMap<String, String>();
            List<Map<String, String>> list = MyUtil.orderNews(MyDatabaseUtil.queryNews(name, user_id), MyDatabaseUtil.queryNews(user_id, name));
            if (list.size() > 0) {
                map.put("user_id", name);
                map.put("news_date", list.get(list.size() - 1).get("news_date"));
                map.put("news_message", list.get(list.size() - 1).get("news_message"));
                newsList.add(map);
            }
        }
        return MyUtil.orderNewsListByTime(newsList);
    }

    public static void deleteNews() {
        db.delete("News", null, null);
    }

    public static void insertPlan(Map<String, String> plan)  {
        ContentValues values = new ContentValues();
        values.put("plan_id", plan.get("plan_id"));
        values.put("plan_title", plan.get("plan_title"));
        values.put("start_time", plan.get("start_time"));
        values.put("stop_time", plan.get("stop_time"));
        values.put("remind_time", plan.get("remind_time"));
        values.put("plan_place", plan.get("plan_place"));
        values.put("plan_seat", plan.get("plan_seat"));
        values.put("plan_tips", plan.get("plan_tips"));
        db.insert("Plan", null, values);
        values.clear();
    }

    public static Map<String, String> queryPlan(String object_id) {
        Map<String, String> map = new HashMap<String, String>();
        Cursor cursor = db.query("Plan", null, "object_id = ?", new String[]{object_id}, null, null, "no_ ASC");
        if (cursor.moveToFirst()) {
            do {
                map.put("object_id", cursor.getString(cursor.getColumnIndex("object_id")));
                map.put("plan_title", cursor.getString(cursor.getColumnIndex("plan_title")));
                map.put("start_time", cursor.getString(cursor.getColumnIndex("start_time")));
                map.put("stop_time", cursor.getString(cursor.getColumnIndex("stop_time")));
                map.put("plan_place", cursor.getString(cursor.getColumnIndex("plan_place")));
                map.put("seat_table", cursor.getString(cursor.getColumnIndex("seat_table")));
                map.put("plan_tips", cursor.getString(cursor.getColumnIndex("plan_tips")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return map;
    }

    public static List<Map<String, String>> queryPlanAll() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Cursor cursor = db.query("Plan", null, null, null, null, null, "no_ ASC");
        if (cursor.moveToFirst()) {
            do {
                Map<String, String> map = new HashMap<String, String>();
                map.put("object_id", cursor.getString(cursor.getColumnIndex("object_id")));
                map.put("plan_title", cursor.getString(cursor.getColumnIndex("plan_title")));
                map.put("start_time", cursor.getString(cursor.getColumnIndex("start_time")));
                map.put("stop_time", cursor.getString(cursor.getColumnIndex("stop_time")));
                map.put("plan_place", cursor.getString(cursor.getColumnIndex("plan_place")));
                map.put("seat_table", cursor.getString(cursor.getColumnIndex("seat_table")));
                map.put("plan_tips", cursor.getString(cursor.getColumnIndex("plan_tips")));
                list.add(map);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public static void updataPlan(String objectId, String updateContent, String updateType) {
        ContentValues values = new ContentValues();
        values.put(updateType, updateContent);
        db.update("Plan", values, "object_id = ?", new String[]{objectId});
        values.clear();
    }

    public static void deletePlan(String objectId) {
        db.delete("Plan", "object_id = ?", new String[]{objectId});
    }

    public static void insertPlanSelf(Map<String, String> plan) {
        ContentValues values = new ContentValues();
        values.put("plan_id", plan.get("plan_id"));
        values.put("plan_title", plan.get("plan_title"));
        values.put("start_time", plan.get("start_time"));
        values.put("stop_time", plan.get("stop_time"));
        values.put("remind_time", plan.get("remind_time"));
        values.put("plan_place", plan.get("plan_place"));
        values.put("plan_seat", plan.get("plan_seat"));
        values.put("plan_tips", plan.get("plan_tips"));
        db.insert("PlanSelf", null, values);
        values.clear();
    }

    public static Map<String, String> queryPlanSelf(String planId) {
        Map<String, String> map = new HashMap<String, String>();
        Cursor cursor = db.query("PlanSelf", null, "plan_id = ?", new String[]{planId}, null, null, "no_ ASC");
        if (cursor.moveToFirst()) {
            do {
                map.put("plan_id", cursor.getString(cursor.getColumnIndex("plan_id")));
                map.put("plan_title", cursor.getString(cursor.getColumnIndex("plan_title")));
                map.put("start_time", cursor.getString(cursor.getColumnIndex("start_time")));
                map.put("stop_time", cursor.getString(cursor.getColumnIndex("stop_time")));
                map.put("plan_place", cursor.getString(cursor.getColumnIndex("plan_place")));
                map.put("seat_table", cursor.getString(cursor.getColumnIndex("seat_table")));
                map.put("plan_tips", cursor.getString(cursor.getColumnIndex("plan_tips")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return map;
    }

    public static List<Map<String, String>> queryPlanSelfAll() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Cursor cursor = db.query("PlanSelf", null, null, null, null, null, "no_ ASC");
        if (cursor.moveToFirst()) {
            do {
                Map<String, String> map = new HashMap<String, String>();
                map.put("plan_id", cursor.getString(cursor.getColumnIndex("plan_id")));
                map.put("plan_title", cursor.getString(cursor.getColumnIndex("plan_title")));
                map.put("start_time", cursor.getString(cursor.getColumnIndex("start_time")));
                map.put("stop_time", cursor.getString(cursor.getColumnIndex("stop_time")));
                map.put("plan_place", cursor.getString(cursor.getColumnIndex("plan_place")));
                map.put("plan_seat", cursor.getString(cursor.getColumnIndex("plan_seat")));
                map.put("plan_tips", cursor.getString(cursor.getColumnIndex("plan_tips")));
                list.add(map);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public static void updatePlanSelf(String planId, String updateContent, String updateType) {
        ContentValues values = new ContentValues();
        values.put(updateType, updateContent);
        db.update("PlanSelf", values, "plan_id = ?", new String[]{planId});
    }

    public static void deletePlanSelf(String planId) {
        db.delete("PlanSelf", "plan_id = ?", new String[]{planId});
    }

    public static void insertMarket(Publish publish, String pictures) {
        if (!queryExist(publish.getObjectId(), "Market")) {
            ContentValues values = new ContentValues();
            values.put("object_id", publish.getObjectId());
            values.put("user_id", publish.getUser_id());
            if (queryUser(publish.getUser_id()).isEmpty()) {
                MyRomateSQLUtil.getUserInformation(publish.getUser_id());
            }
            values.put("publish_title", publish.getPublish_title());
            values.put("start_time", publish.getStart_time());
            values.put("stop_time", publish.getStop_time());
            values.put("publish_place", publish.getPublish_place());
            values.put("seat_table", MyUtil.jsonArrayToString(publish.getSeat_table()));
            values.put("pictures", pictures);
            values.put("information_text", publish.getInformation_text());
            db.insert("Market", null, values);
            values.clear();
        }
    }

    public static List<Map<String, String>> queryMarket() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Cursor cursor = db.query("Market", null, null, null, null, null, "no_ ASC");
        if (cursor.moveToFirst()) {
            do {
                Map<String, String> map = new HashMap<String, String>();
                map.put("object_id", cursor.getString(cursor.getColumnIndex("object_id")));
                map.put("user_id", cursor.getString(cursor.getColumnIndex("user_id")));
                map.put("user_name", MyDatabaseUtil.queryUser(cursor.getString(cursor.getColumnIndex("user_id"))).get("user_name"));
                map.put("user_image", MyDatabaseUtil.queryUser(cursor.getString(cursor.getColumnIndex("user_id"))).get("user_image"));
                map.put("publish_title", cursor.getString(cursor.getColumnIndex("publish_title")));
                map.put("start_time", cursor.getString(cursor.getColumnIndex("start_time")));
                map.put("stop_time", cursor.getString(cursor.getColumnIndex("stop_time")));
                map.put("publish_place", cursor.getString(cursor.getColumnIndex("publish_place")));
                map.put("seat_table", cursor.getString(cursor.getColumnIndex("seat_table")));
                map.put("pictures", cursor.getString(cursor.getColumnIndex("pictures")));
                map.put("information_text", cursor.getString(cursor.getColumnIndex("information_text")));
                list.add(map);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public static Map<String, String> queryMarket(String object_id) {
        Map<String, String> map = new HashMap<String, String>();
        Cursor cursor = db.query("Market", null, "object_id = ?", new String[]{object_id}, null, null, "no_ ASC");
        if (cursor.moveToFirst()) {
            do {
                map.put("object_id", cursor.getString(cursor.getColumnIndex("object_id")));
                map.put("user_id", cursor.getString(cursor.getColumnIndex("user_id")));
                map.put("user_name", MyDatabaseUtil.queryUser(cursor.getString(cursor.getColumnIndex("user_id"))).get("user_name"));
                map.put("user_image", MyDatabaseUtil.queryUser(cursor.getString(cursor.getColumnIndex("user_id"))).get("user_image"));
                map.put("publish_title", cursor.getString(cursor.getColumnIndex("publish_title")));
                map.put("start_time", cursor.getString(cursor.getColumnIndex("start_time")));
                map.put("stop_time", cursor.getString(cursor.getColumnIndex("stop_time")));
                map.put("publish_place", cursor.getString(cursor.getColumnIndex("publish_place")));
                map.put("seat_table", cursor.getString(cursor.getColumnIndex("seat_table")));
                map.put("pictures", cursor.getString(cursor.getColumnIndex("pictures")));
                map.put("information_text", cursor.getString(cursor.getColumnIndex("information_text")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return map;
    }

    public static void deleteMarket() {
        db.delete("Market", null, null);
    }

    public static void insertCare(Publish publish, String pictures) {
        if (!queryExist(publish.getObjectId(), "Care")) {
            ContentValues values = new ContentValues();
            values.put("object_id", publish.getObjectId());
            values.put("user_id", publish.getUser_id());
            if (queryUser(publish.getUser_id()).isEmpty()) {
                MyRomateSQLUtil.getUserInformation(publish.getUser_id());
            }
            values.put("create_at", new DateTime(publish.getCreatedAt()).toString(DateTimeFormat.forPattern("yyyy.MM.dd HH:mm")));
            values.put("publish_title", publish.getPublish_title());
            values.put("start_time", publish.getStart_time());
            values.put("stop_time", publish.getStop_time());
            values.put("publish_place", publish.getPublish_place());
            values.put("pictures", pictures);
            values.put("seat_table", MyUtil.jsonArrayToString(publish.getSeat_table()));
            values.put("information_text", publish.getInformation_text());
            db.insert("Care", null, values);
            values.clear();
        }
    }

    public static List<Map<String, String>> queryCare() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Cursor cursor = db.query("Care", null, null, null, null, null, "no_ ASC");
        if (cursor.moveToFirst()) {
            do {
                Map<String, String> map = new HashMap<String, String>();
                map.put("object_id", cursor.getString(cursor.getColumnIndex("object_id")));
                map.put("user_id", cursor.getString(cursor.getColumnIndex("user_id")));
                map.put("create_at", cursor.getString(cursor.getColumnIndex("create_at")));
                map.put("user_name", MyDatabaseUtil.queryUser(cursor.getString(cursor.getColumnIndex("user_id"))).get("user_name"));
                map.put("user_image", MyDatabaseUtil.queryUser(cursor.getString(cursor.getColumnIndex("user_id"))).get("user_image"));
                map.put("publish_title", cursor.getString(cursor.getColumnIndex("publish_title")));
                map.put("start_time", cursor.getString(cursor.getColumnIndex("start_time")));
                map.put("stop_time", cursor.getString(cursor.getColumnIndex("stop_time")));
                map.put("publish_place", cursor.getString(cursor.getColumnIndex("publish_place")));
                map.put("seat_table", cursor.getString(cursor.getColumnIndex("seat_table")));
                map.put("pictures", cursor.getString(cursor.getColumnIndex("pictures")));
                map.put("information_text", cursor.getString(cursor.getColumnIndex("information_text")));
                list.add(map);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public static Map<String, String> queryCare(String object_id) {
        Map<String, String> map = new HashMap<String, String>();
        Cursor cursor = db.query("Care", null, "object_id = ?", new String[]{object_id}, null, null, "no_ ASC");
        if (cursor.moveToFirst()) {
            do {
                map.put("object_id", cursor.getString(cursor.getColumnIndex("object_id")));
                map.put("user_id", cursor.getString(cursor.getColumnIndex("user_id")));
                map.put("create_at", cursor.getString(cursor.getColumnIndex("create_at")));
                map.put("user_name", MyDatabaseUtil.queryUser(cursor.getString(cursor.getColumnIndex("user_id"))).get("user_name"));
                map.put("user_image", MyDatabaseUtil.queryUser(cursor.getString(cursor.getColumnIndex("user_id"))).get("user_image"));
                map.put("publish_title", cursor.getString(cursor.getColumnIndex("publish_title")));
                map.put("start_time", cursor.getString(cursor.getColumnIndex("start_time")));
                map.put("stop_time", cursor.getString(cursor.getColumnIndex("stop_time")));
                map.put("publish_place", cursor.getString(cursor.getColumnIndex("publish_place")));
                map.put("seat_table", cursor.getString(cursor.getColumnIndex("seat_table")));
                map.put("pictures", cursor.getString(cursor.getColumnIndex("pictures")));
                map.put("information_text", cursor.getString(cursor.getColumnIndex("information_text")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return map;
    }

    public static void deleteCare() {
        db.delete("Care", null, null);
    }

    public static boolean queryExist(String objectId, String table) {
        Cursor cursor = db.query(table, new String[]{"object_id"}, "object_id = ?", new String[]{objectId}, null, null, null);
        while (cursor.moveToNext()) {
            return true;
        }
        return false;
    }

    public static boolean isUserExist(String user_id) {
        Cursor cursor = db.query("User", new String[]{"user_id"}, "user_id = ?", new String[]{user_id}, null, null, null);
        while (cursor.moveToNext()) {
            return true;
        }
        return false;
    }
}
