package minework.onesit.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import minework.onesit.R;

/**
 * Created by 无知 on 2017/1/6.
 */

public class EditActivity extends BaseActivity implements View.OnClickListener {

    private Context mContext;
    private Button editBack;
    private Button editFinish;
    private TextView editName;
    private TextView editTitle;
    private EditText editContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_layout);
        mContext = MyApplication.getInstance();
        init();
    }

    @Override
    protected void init() {
        editBack = (Button) findViewById(R.id.edit_back);
        editFinish = (Button) findViewById(R.id.edit_finish);
        editName = (TextView) findViewById(R.id.edit_name);
        editTitle = (TextView) findViewById(R.id.edit_title);
        editContent = (EditText) findViewById(R.id.edit_content);

        editBack.setOnClickListener(this);
        editFinish.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit_back:
                onBackPressed();
                break;
            case R.id.edit_finish:
                switch (getIntent().getStringExtra("activity")){
                    case "PlanCreate":
                        startActivity(new Intent(this, PlanCreate.class).putExtra("name", editTitle.getText().toString()).putExtra("content", editContent.getText().toString()));
                        break;
                    case "SeatTable":
                        startActivity(new Intent(this, SeatTable.class).putExtra("name", editTitle.getText().toString()).putExtra("content", editContent.getText().toString()));
                        break;
                    case "Publish":
                        startActivity(new Intent(this, Publish.class).putExtra("name", editTitle.getText().toString()).putExtra("content", editContent.getText().toString()));
                        break;
                    case "User":
                        startActivity(new Intent(this, User.class).putExtra("name", editTitle.getText().toString()).putExtra("content", editContent.getText().toString()));
                        break;
                    default:
                        break;
                }

                break;
            default:
                return;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(getIntent().getStringExtra("name"))) {
            Log.d("Test","edit");
            editName.setText(getIntent().getStringExtra("name"));
            editTitle.setText(getIntent().getStringExtra("name"));
        }
    }

}
