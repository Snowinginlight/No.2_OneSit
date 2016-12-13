package minework.onesit.activity;

import android.content.Intent;
import android.os.Bundle;

import com.maxleap.MLObject;
import com.maxleap.MaxLeap;

import minework.onesit.R;
import minework.onesit.module.*;
import minework.onesit.util.MyLocalSQLUtil;
import minework.onesit.util.MyUtil;

/**
 * Created by 无知 on 2016/11/11.
 */

public class Welcome extends BaseActivity {

    private static boolean loginOK = false;//判断是否登录过
    private static boolean isMaxLeapInit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);
        //MaxLeap数据库 接入
        if (!isMaxLeapInit) {
            MLObject.registerSubclass(User.class);
            MLObject.registerSubclass(Plan.class);
            MLObject.registerSubclass(PlanSelf.class);
            MLObject.registerSubclass(PublishModel.class);
            MLObject.registerSubclass(minework.onesit.module.Publish.class);
            MLObject.registerSubclass(Seat.class);
            MaxLeap.initialize(this, "58286e3069dbd100071784e6", "QWlDNllkZU8zUzRuQXBsY3VJLXBZQQ", MaxLeap.REGION_CN);
            isMaxLeapInit = true;
        }
        //初始化
        init();
    }

    //加载数据，跳转至登录界面或主界面
    @Override
    protected void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //此处运行耗时任务
                if (MyLocalSQLUtil.getLocalUser()) {
                    loginOK = true;
                } else {
                    loginOK = false;
                }
                MyUtil.sleep(800);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent;
                        if (loginOK) {
                            intent = new Intent(Welcome.this, Main.class);
                        } else {
                            intent = new Intent(Welcome.this, Login.class);
                        }
                        startActivity(intent);
                        Welcome.this.finish();
                    }
                });
            }
        }).start();
    }

}
