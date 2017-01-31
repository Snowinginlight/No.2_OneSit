package minework.onesit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import minework.onesit.R;
import minework.onesit.util.MyRomateSQLUtil;

/**
 * Created by 无知 on 2016/11/13.
 */

public class SignUp extends BaseActivity implements View.OnClickListener {

    private Button signUpSignUp;
    private EditText idSignUp;
    private EditText passwordSignUp;
    private EditText confirmSignUp;
    private EditText emailSignUp;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    if ((boolean) message.obj) {
                        startActivity(new Intent(SignUp.this, Main.class));
                        SignUp.this.finish();
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
        setContentView(R.layout.signup_layout);
        //初始化
        init();
    }

    protected void init() {
        signUpSignUp = (Button) findViewById(R.id.sign_up_finish);
        idSignUp = (EditText) findViewById(R.id.sign_up_id);
        passwordSignUp = (EditText) findViewById(R.id.sign_up_password);
        confirmSignUp = (EditText) findViewById(R.id.sign_up_confirm);
        emailSignUp = (EditText) findViewById(R.id.sign_up_email);

        signUpSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_up_finish:
                MyRomateSQLUtil.signUp(idSignUp.getText().toString(), passwordSignUp.getText().toString(), confirmSignUp.getText().toString(), emailSignUp.getText().toString(), mHandler);
                break;
            default:
                return;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,Login.class));
        SignUp.this.finish();
    }
}
