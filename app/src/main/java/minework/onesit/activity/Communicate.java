package minework.onesit.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.maxleap.social.S;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import minework.onesit.R;
import minework.onesit.database.MyDatabaseUtil;
import minework.onesit.fragment.news.CommAdapter;
import minework.onesit.util.MyRomateSQLUtil;
import minework.onesit.util.MyTimeUtil;
import minework.onesit.util.MyUtil;

/**
 * Created by 无知 on 2016/12/19.
 */

public class Communicate extends BaseActivity implements View.OnClickListener {

    private Context mContext;
    private Map<String,String> herUser = new HashMap<String, String>();
    private Map<String,String> iUser = new HashMap<String, String>();
    private RecyclerView newsListView;
    private List<Map<String, String>> mDatas;
    private Button commBack,commEmoji,commImage,commSendButton;
    private View commSend;
    private TextView commTitle;
    private EditText commEdit;
    private Thread thread;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    if((boolean)message.obj){
                        Map<String,String> map = new HashMap<String, String>();
                        map.put("send_user_id",iUser.get("user_id"));
                        map.put("receive_user_id",herUser.get("user_id"));
                        map.put("news_date", MyTimeUtil.convertCommTimeToString1(DateTime.now().toDate()));
                        map.put("news_message",commEdit.getText().toString());
                        ((CommAdapter)(newsListView.getAdapter())).addItem(map);
                        commEdit.setText("");
                    }

                    break;
                case 2:
                    mDatas = MyUtil.orderNews(MyDatabaseUtil.queryNews(herUser.get("user_id"),iUser.get("user_id")),MyDatabaseUtil.queryNews(iUser.get("user_id"),herUser.get("user_id")));
                    newsListView.setAdapter(new CommAdapter(mDatas));
                    newsListView.scrollToPosition(mDatas.size()-1);
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
        setContentView(R.layout.comm_layout);
        mContext = MyApplication.getInstance();
        init();
        initData();
    }

    @Override
    protected void init() {
        commBack = (Button)findViewById(R.id.comm_back);
        commTitle = (TextView)findViewById(R.id.comm_title);
        commEmoji = (Button)findViewById(R.id.comm_emoji);
        commImage = (Button)findViewById(R.id.comm_image);
        commEdit = (EditText)findViewById(R.id.comm_edit);
        commSend = findViewById(R.id.comm_send);
        commSendButton = (Button)findViewById(R.id.comm_send_button);
        newsListView = (RecyclerView)findViewById(R.id.comm_news_list);

        commBack.setOnClickListener(this);
        commEmoji.setOnClickListener(this);
        commImage.setOnClickListener(this);
        commSend.setOnClickListener(this);
        commSendButton.setOnClickListener(this);
        commEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!TextUtils.isEmpty(editable)){
                    commSend.setBackgroundColor(Color.parseColor("#8bab8b"));
                }else {
                    commSend.setBackgroundColor(Color.parseColor("#ced1d6"));
                }
            }
        });
    }

    private void initData() {
        mDatas = new ArrayList<Map<String, String>>();
        if(!TextUtils.isEmpty(getIntent().getStringExtra("her_user_id"))){
            herUser = MyDatabaseUtil.queryUser(getIntent().getStringExtra("her_user_id"));
            iUser = MyDatabaseUtil.queryUserExist();
            commTitle.setText(herUser.get("user_name"));
            mDatas = MyUtil.orderNews(MyDatabaseUtil.queryNews(herUser.get("user_id"),iUser.get("user_id")),MyDatabaseUtil.queryNews(iUser.get("user_id"),herUser.get("user_id")));
            newsListView.setLayoutManager(new LinearLayoutManager(mContext));
            newsListView.setAdapter(new CommAdapter(mDatas));
        }
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Message msg = new Message();
                        msg.what=2;
                        mHandler.sendMessage(msg);
                        Thread.sleep(10000);//10秒
                    } catch (Exception e) {
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.comm_back:
                break;
            case R.id.comm_emoji:
                break;
            case R.id.comm_image:
                break;
            case R.id.comm_send:
                if(!TextUtils.isEmpty(commEdit.getText())){
                    MyRomateSQLUtil.sendNews(iUser.get("user_id"),herUser.get("user_id"),commEdit.getText().toString(),mHandler);
                }
                break;
            case R.id.comm_send_button:
                if(!TextUtils.isEmpty(commEdit.getText())){
                    MyRomateSQLUtil.sendNews(iUser.get("user_id"),herUser.get("user_id"),commEdit.getText().toString(),mHandler);
                }
                break;
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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        thread.interrupt();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        thread.interrupt();
    }
}
