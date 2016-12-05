package minework.onesit.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.maxleap.FindCallback;
import com.maxleap.MLDataManager;
import com.maxleap.MLQuery;
import com.maxleap.MLQueryManager;
import com.maxleap.SaveCallback;
import com.maxleap.exception.MLException;

import java.util.List;

import minework.onesit.activity.MyApplication;
import minework.onesit.module.User;

/**
 * Created by 无知 on 2016/11/24.
 */

public class MyRomateSQLUtil {
    private static Context mContext = MyApplication.getInstance();

    public static boolean clickLogin(final String user_id, final String user_password) {
        final boolean[] loginOK = {false};
        MLQuery<User> query = MLQuery.getQuery("OneSit_User");
        query.whereEqualTo("user_id", user_id);
        query.whereEqualTo("user_password", user_password);
        MLQueryManager.findAllInBackground(query, new FindCallback<User>() {
            public void done(List<User> scoreList, MLException e) {
                if (e == null) {
                    Toast.makeText(mContext, "登陆成功!", Toast.LENGTH_SHORT).show();
                    SharedPreferences localUser = mContext.getSharedPreferences("local_user",
                            Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = localUser.edit();
                    editor.putString("user_id", user_id);
                    editor.putString("user_password", user_password);
                    editor.commit();
                    loginOK[0] = true;
                } else {
                    Toast.makeText(mContext, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return loginOK[0];
    }

    public static boolean signUp(final String user_id, final String user_password, String confirm_password, final String user_email) {
        final boolean[] loginOK = {false};
        if (!user_password.equals(confirm_password)) {
            Toast.makeText(mContext, "两次密码不一致", Toast.LENGTH_SHORT).show();
        } else {
            MLQuery<User> query = MLQuery.getQuery("OneSit_User");
            query.whereEqualTo("user_id", user_id);
            MLQueryManager.findAllInBackground(query, new FindCallback<User>() {
                public void done(List<User> scoreList, MLException e) {
                    if (e == null) {
                        Toast.makeText(mContext, "该用户已存在！", Toast.LENGTH_SHORT).show();
                    } else {
                        User user = new User();
                        user.setUser_id(user_id);
                        user.setUser_password(user_password);
                        user.setUser_email(user_email);
                        MLDataManager.saveInBackground(user, new SaveCallback() {
                            @Override
                            public void done(MLException e) {
                                if (e == null) {
                                    Toast.makeText(mContext, "注册成功！", Toast.LENGTH_SHORT).show();
                                    SharedPreferences localUser = mContext.getSharedPreferences("local_user",
                                            Activity.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = localUser.edit();
                                    editor.putString("user_id", user_id);
                                    editor.putString("user_password", user_password);
                                    editor.commit();
                                    loginOK[0] = true;
                                } else {
                                    Toast.makeText(mContext, "注册失败，请重试！", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            });
        }
        return loginOK[0];
    }
}
