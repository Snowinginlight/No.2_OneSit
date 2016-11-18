package minework.onesit.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import com.maxleap.MLObject;
import com.maxleap.MaxLeap;
import minework.onesit.R;
import minework.onesit.module.User;
import minework.onesit.util.MyUtil;

/**
 * Created by 无知 on 2016/11/11.
 */

public class WelcomeActivity extends AppCompatActivity {

    private static SharedPreferences localUser;
    private static boolean loginOK = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);
        MLObject.registerSubclass(User.class);
        MaxLeap.initialize(this, "58286e3069dbd100071784e6", "QWlDNllkZU8zUzRuQXBsY3VJLXBZQQ", MaxLeap.REGION_CN);
        //隐藏状态栏、导航栏
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(option);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //加载数据，跳转至登录界面或主界面
        new Thread(new Runnable() {
            @Override
            public void run() {
                //此处运行耗时任务
                if (getLocalUser()) {
                    loginOK = true;
                    Log.d("TAG", localUser.getAll().toString());
                } else {
                    loginOK = false;
                }
                MyUtil.sleep(5000);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent;
                        if (loginOK) {
                            intent = new Intent(WelcomeActivity.this, MainActivity.class);
                        } else {
                            intent = new Intent(WelcomeActivity.this, Login.class);
                        }
                        startActivity(intent);
                        WelcomeActivity.this.finish();
                    }
                });
            }
        }).start();
    }

    private boolean getLocalUser() {
        localUser = getSharedPreferences("local_user",
                Activity.MODE_PRIVATE);
            String id = localUser.getString("user_id", "");
            String password = localUser.getString("user_password", "");
            String email = localUser.getString("user_email", "");
        if (!id.equals("")) {
            return true;
        } else {
            return false;
        }
    }
}
