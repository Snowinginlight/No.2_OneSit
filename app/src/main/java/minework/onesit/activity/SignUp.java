package minework.onesit.activity;

import android.app.Activity;
import android.content.Context;
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

    private Button signUpSignUp;
    private EditText idSignUp;
    private EditText passwordSignUp;
    private EditText confirmSignUp;
    private EditText emailSignUp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);
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
        signUpSignUp = (Button) findViewById(R.id.sign_up_signup);
        idSignUp = (EditText) findViewById(R.id.id_signup);
        passwordSignUp = (EditText) findViewById(R.id.password_signup);
        confirmSignUp = (EditText) findViewById(R.id.confirm_signup);
        emailSignUp = (EditText) findViewById(R.id.email_signup);

        signUpSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_up_signup:
                query();
                break;
            default:
                return;
        }
    }

    private void query() {
        if(!passwordSignUp.getText().toString().equals(confirmSignUp.getText().toString())){
            Toast.makeText(SignUp.this, "Two password is not same！" , Toast.LENGTH_SHORT).show();
            return;
        }
        MLQuery<User> query = MLQuery.getQuery("OneSit_User");
        query.whereEqualTo("user_id", idSignUp.getText().toString());
        MLQueryManager.findAllInBackground(query, new FindCallback<User>() {
            public void done(List<User> scoreList, MLException e) {
                if (e == null) {
                    Toast.makeText(SignUp.this, "The user exists！" , Toast.LENGTH_SHORT).show();
                } else {
                    signUp();
                }
            }
        });
    }

    private void signUp() {
        User user = new User();
        user.setUser_id(idSignUp.getText().toString());
        user.setUser_password(passwordSignUp.getText().toString());
        user.setUser_email(emailSignUp.getText().toString());
        MLDataManager.saveInBackground(user, new SaveCallback() {
            @Override
            public void done(MLException e) {
                if (e == null) {
                    Toast.makeText(SignUp.this, "Sign up has succeeded！", Toast.LENGTH_SHORT).show();
                    SharedPreferences localUser = getSharedPreferences("local_user",
                            Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = localUser.edit();
                    editor.putString("user_id", idSignUp.getText().toString());
                    editor.putString("user_password", passwordSignUp.getText().toString());
                    editor.commit();
                    SignUp.this.finish();
                    startActivity(new Intent(SignUp.this, MainActivity.class));
                } else {
                    Toast.makeText(SignUp.this, "Sign up has failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
