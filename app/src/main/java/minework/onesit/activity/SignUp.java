package minework.onesit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import minework.onesit.R;
import minework.onesit.util.MyRomateSQLUtil;

/**
 * Created by 无知 on 2016/11/13.
 */

public class SignUp extends BaseActivity implements View.OnClickListener {

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
        //初始化
        init();
    }

    protected void init() {
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
                if (MyRomateSQLUtil.signUp(idSignUp.getText().toString(), passwordSignUp.getText().toString(), confirmSignUp.getText().toString(), emailSignUp.getText().toString())) {
                    SignUp.this.finish();
                    startActivity(new Intent(SignUp.this, MainActivity.class));
                }
                break;
            default:
                return;
        }
    }
}
