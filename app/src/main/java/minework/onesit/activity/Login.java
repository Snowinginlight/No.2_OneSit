package minework.onesit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import minework.onesit.R;
import minework.onesit.util.MyRomateSQLUtil;

/**
 * Created by 无知 on 2016/11/11.
 */

public class Login extends BaseActivity implements View.OnClickListener {
    private static boolean flag;
    private long exitTime = 0;

    private EditText idLogin;
    private EditText passwordLogin;
    private Button signUpLogin;
    private Button logInLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        //初始化
        init();
    }

    protected void init() {
        idLogin = (EditText) findViewById(R.id.id_login);
        passwordLogin = (EditText) findViewById(R.id.password_login);
        signUpLogin = (Button) findViewById(R.id.sign_up_login);
        logInLogin = (Button) findViewById(R.id.log_in_login);

        signUpLogin.setOnClickListener(this);
        logInLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.log_in_login:
                if (MyRomateSQLUtil.clickLogin(idLogin.getText().toString(), passwordLogin.getText().toString())) {
                    Login.this.finish();
                    startActivity(new Intent(Login.this, MainActivity.class));
                }
                break;
            case R.id.sign_up_login:
                startActivity(new Intent(this, SignUp.class));
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
