package minework.onesit.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.maxleap.FindCallback;
import com.maxleap.MLQuery;
import com.maxleap.MLQueryManager;
import com.maxleap.exception.MLException;
import com.maxleap.sdk.B;

import java.util.List;

import minework.onesit.R;
import minework.onesit.module.User;

/**
 * Created by 无知 on 2016/11/11.
 */

public class Login extends AppCompatActivity implements View.OnClickListener {
    private static boolean flag;

    private EditText idLogin;
    private EditText passwordLogin;
    private Button signUpLogin;
    private Button logInLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        //隐藏状态栏、导航栏
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(option);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //初始化
        init();
    }

    private void init() {
        idLogin = (EditText)findViewById(R.id.id_login);
        passwordLogin = (EditText)findViewById(R.id.password_login);
        signUpLogin = (Button)findViewById(R.id.sign_up_login);
        logInLogin = (Button)findViewById(R.id.log_in_login);

        signUpLogin.setOnClickListener(this);
        logInLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.log_in_login:
                chickSignIn();
                break;
            case R.id.sign_up_login:
                startActivity(new Intent(this, SignUp.class));
                break;
        }
    }

    private void chickSignIn() {
        MLQuery<User> query = MLQuery.getQuery("OneSit_User");
        query.whereEqualTo("user_id", idLogin.getText().toString());
        query.whereEqualTo("user_password", passwordLogin.getText().toString());
        MLQueryManager.findAllInBackground(query, new FindCallback<User>() {
            public void done(List<User> scoreList, MLException e) {
                if (e == null) {
                    Toast.makeText(Login.this, "登陆成功!", Toast.LENGTH_SHORT).show();
                    SharedPreferences localUser = getSharedPreferences("local_user",
                            Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = localUser.edit();
                    editor.putString("user_id",idLogin.getText().toString());
                    editor.putString("user_password", passwordLogin.getText().toString());
                    editor.commit();
                    Login.this.finish();
                    startActivity(new Intent(Login.this, MainActivity.class));
                } else {
                    Toast.makeText(Login.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
