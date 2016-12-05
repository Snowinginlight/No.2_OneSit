package minework.onesit.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import minework.onesit.activity.MyApplication;


/**
 * Created by 无知 on 2016/11/24.
 */

public class MyLocalSQLUtil {

    private static Context mContext = MyApplication.getInstance();

    public static boolean getLocalUser() {
        SharedPreferences localUser = mContext.getSharedPreferences("local_user",
                Activity.MODE_PRIVATE);
        String id = localUser.getString("user_id", "");
        String password = localUser.getString("user_password", "");
        if (!id.equals("")) {
            return true;
        } else {
            return false;
        }
    }
}
