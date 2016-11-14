package minework.onesit.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import java.util.List;

import minework.onesit.R;
import minework.onesit.module.User;

/**
 * Created by 无知 on 2016/11/11.
 */

public class Login extends AppCompatActivity implements View.OnClickListener {
    private static boolean flag;

    private Button loginSignIn;
    private Button loginSignUp;
    private EditText loginUserId;
    private EditText loginUserPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        init();
    }

    private void init() {
        loginSignIn = (Button) findViewById(R.id.login_page_sign_in);
        loginSignUp = (Button) findViewById(R.id.login_page_sign_up);
        loginUserId = (EditText) findViewById(R.id.login_user_id_edit);
        loginUserPassword = (EditText) findViewById(R.id.login_user_password_edit);

        loginSignIn.setOnClickListener(this);
        loginSignUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_page_sign_in:
                chickSignIn();
                break;
            case R.id.login_page_sign_up:
                startActivity(new Intent(this, SignUp.class));
                break;
        }
    }

    private void chickSignIn() {
        MLQuery<User> query = MLQuery.getQuery("User");
        query.whereEqualTo("user_id", loginUserId.getText().toString());
        query.whereEqualTo("user_password", loginUserPassword.getText().toString());
        MLQueryManager.findAllInBackground(query, new FindCallback<User>() {
            public void done(List<User> scoreList, MLException e) {
                if (e == null) {
                    Toast.makeText(Login.this, "登陆成功!", Toast.LENGTH_SHORT).show();
                    SharedPreferences localUser = getSharedPreferences("local_user",
                            Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = localUser.edit();
                    editor.putString("user_id",loginUserId.getText().toString());
                    editor.putString("user_password", loginUserPassword.getText().toString());
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
