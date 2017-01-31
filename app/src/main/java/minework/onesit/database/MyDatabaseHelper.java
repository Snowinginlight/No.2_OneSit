package minework.onesit.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by 无知 on 2016/12/22.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_USER = "create table User ("
            + "no_ integer primary key autoincrement,"
            + "object_id text,"
            + "user_login text,"
            + "user_id text,"
            + "user_name text,"
            + "user_signature text,"
            + "user_image text,"
            + "user_email text,"
            + "user_publish text,"
            + "user_care text,"
            + "user_book text)";

    public static final String CREATE_NEWS = "create table News ("
            + "no_ integer primary key autoincrement,"
            + "object_id text,"
            + "send_user_id text,"
            + "receive_user_id text,"
            + "news_date text,"
            + "news_message text)";

    public static final String CREATE_PLAN = "create table Plan ("
            + "no_ integer primary key autoincrement,"
            + "object_id text,"
            + "plan_title text,"
            + "start_time text,"
            + "stop_time text,"
            + "plan_place text,"
            + "seat_table text,"
            + "plan_tips text)";

    public static final String CREATE_PLANSELF = "create table PlanSelf ("
            + "no_ integer primary key autoincrement,"
            + "plan_id text,"
            + "plan_title text,"
            + "start_time text,"
            + "stop_time text,"
            + "remind_time text,"
            + "plan_place text,"
            + "plan_seat text,"
            + "plan_tips text)";

    public static final String CREATE_MARKET = "create table Market ("
            + "no_ integer primary key autoincrement,"
            + "object_id text,"
            + "user_id text,"
            + "publish_title text,"
            + "start_time text,"
            + "stop_time text,"
            + "publish_place text,"
            + "seat_table text,"
            + "seat_column integer,"
            + "pictures text,"
            + "information_text text)";

    public static final String CREATE_CARE = "create table Care ("
            + "no_ integer primary key autoincrement,"
            + "object_id text,"
            + "user_id text,"
            + "create_at text,"
            + "publish_title text,"
            + "start_time text,"
            + "stop_time text,"
            + "publish_place text,"
            + "seat_table text,"
            + "seat_column integer,"
            + "pictures text,"
            + "information_text text)";

    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_USER);
        database.execSQL(CREATE_NEWS);
        database.execSQL(CREATE_PLAN);
        database.execSQL(CREATE_PLANSELF);
        database.execSQL(CREATE_MARKET);
        database.execSQL(CREATE_CARE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
