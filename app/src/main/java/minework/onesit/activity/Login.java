package minework.onesit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import minework.onesit.R;
import minework.onesit.database.MyDatabaseUtil;
import minework.onesit.util.MyRomateSQLUtil;

/**
 * Created by 无知 on 2016/11/11.
 */

public class Login extends BaseActivity implements View.OnClickListener {
    private long exitTime = 0;
    private EditText idLogin;
    private EditText passwordLogin;
    private Button signUpLogin;
    private Button logInLogin;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case 0:
                    if ((boolean)message.obj) {
                        Login.this.finish();
                        MyDatabaseUtil.deleteMarket();
                        MyDatabaseUtil.deleteCare();
                        MyRomateSQLUtil.getMarketList();
                        MyRomateSQLUtil.getCareList();
                        startActivity(new Intent(Login.this, Main.class));
                    }
                    break;
                default:
                    return true;
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        //初始化
        init();
    }

    protected void init() {
        idLogin = (EditText) findViewById(R.id.login_id);
        passwordLogin = (EditText) findViewById(R.id.login_password);
        signUpLogin = (Button) findViewById(R.id.login_sign_up);
        logInLogin = (Button) findViewById(R.id.login_log_in);

        signUpLogin.setOnClickListener(this);
        logInLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_log_in:
                MyRomateSQLUtil.clickLogin(idLogin.getText().toString(), passwordLogin.getText().toString(),mHandler);
                break;
            case R.id.login_sign_up:
                startActivity(new Intent(this, SignUp.class));
                Login.this.finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
