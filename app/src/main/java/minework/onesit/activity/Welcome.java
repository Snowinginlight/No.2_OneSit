package minework.onesit.activity;

import android.content.Intent;
import android.os.Bundle;

import com.maxleap.MLObject;
import com.maxleap.MaxLeap;

import minework.onesit.R;
import minework.onesit.database.MyDatabaseHelper;
import minework.onesit.database.MyDatabaseUtil;
import minework.onesit.module.News;
import minework.onesit.module.Plan;
import minework.onesit.module.Publish;
import minework.onesit.module.PublishModel;
import minework.onesit.module.Seat;
import minework.onesit.module.User;
import minework.onesit.util.MyRomateSQLUtil;
import minework.onesit.util.MyUtil;

/**
 * Created by 无知 on 2016/11/11.
 */

public class Welcome extends BaseActivity {

    private static boolean loginOK = false;//判断是否登录过
    private static boolean isMaxLeapInit = false;
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);
        dbHelper = new MyDatabaseHelper(this, "OneSit.db", null, 1);
        dbHelper.getWritableDatabase();
        //MaxLeap数据库 接入
        if (!isMaxLeapInit) {
            MLObject.registerSubclass(News.class);
            MLObject.registerSubclass(User.class);
            MLObject.registerSubclass(Plan.class);
            MLObject.registerSubclass(PublishModel.class);
            MLObject.registerSubclass(Publish.class);
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
                if (MyDatabaseUtil.queryUserExist()!=null) {
                    loginOK = true;
                    MyDatabaseUtil.deleteMarket();
                    MyDatabaseUtil.deleteCare();
                    MyRomateSQLUtil.getMarketList();
                    MyRomateSQLUtil.getCareList();
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
