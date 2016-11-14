package minework.onesit.activity;

import android.app.Activity;
import android.content.Context;
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
import com.maxleap.MLDataManager;
import com.maxleap.MLObject;
import com.maxleap.MLQuery;
import com.maxleap.MLQueryManager;
import com.maxleap.SaveCallback;
import com.maxleap.exception.MLException;

import java.util.List;

import minework.onesit.R;
import minework.onesit.module.User;

/**
 * Created by 无知 on 2016/11/13.
 */

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private static boolean signUpStatus = false;

    private Button signUp;
    private EditText userId;
    private EditText userPassword;
    private EditText userEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);
        getSupportActionBar().setTitle("注册");
        init();
    }

    private void init() {
        signUp = (Button) findViewById(R.id.sign_up);
        userId = (EditText) findViewById(R.id.sign_up_user_id_edit);
        userPassword = (EditText) findViewById(R.id.sign_up_user_password_edit);
        userEmail = (EditText) findViewById(R.id.sign_up_user_email_edit);

        signUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_up:
                query();
                break;
            default:
                return;
        }
    }

    private void query() {
        MLQuery<User> query = MLQuery.getQuery("OneSit_User");
        query.whereEqualTo("user_id", userId.getText().toString());
        MLQueryManager.findAllInBackground(query, new FindCallback<User>() {
            public void done(List<User> scoreList, MLException e) {
                if (e == null) {
                    Toast.makeText(SignUp.this, "用户名已存在！" , Toast.LENGTH_SHORT).show();
                } else {
                    signUp();
                }
            }
        });
    }

    private void signUp() {
        User user = new User();
        user.setUser_id(userId.getText().toString());
        user.setUser_password(userPassword.getText().toString());
        user.setUser_email(userEmail.getText().toString());
        MLDataManager.saveInBackground(user, new SaveCallback() {
            @Override
            public void done(MLException e) {
                if (e == null) {
                    Toast.makeText(SignUp.this, "注册成功！", Toast.LENGTH_SHORT).show();
                    SharedPreferences localUser = getSharedPreferences("local_user",
                            Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = localUser.edit();
                    editor.putString("user_id", userId.getText().toString());
                    editor.putString("user_password", userPassword.getText().toString());
                    editor.commit();
                    SignUp.this.finish();
                    startActivity(new Intent(SignUp.this, MainActivity.class));
                } else {
                    Toast.makeText(SignUp.this, "注册失败!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
