package minework.onesit.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import minework.onesit.R;
import minework.onesit.fragment.plan.ModelListAdapter;
import minework.onesit.module.Publish;
import minework.onesit.util.MyRomateSQLUtil;
import minework.onesit.util.MyUtil;

/**
 * Created by 无知 on 2017/5/7.
 */

public class ActivityManagerList extends BaseActivity {
    private static List<String> mDatas = new ArrayList<String>();
    private static List<Publish> userPublishList;
    private RecyclerView userPublishRecycler;
    private ModelListAdapter mAdapter;
    private List<Integer> seatDatas;
    private Button activityManagerBack;
    private Context mContext;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case -1:
                    findViewById(R.id.activity_manager_list_toast).setVisibility(View.VISIBLE);
                    userPublishRecycler.setVisibility(View.GONE);
                    break;
                case 0:
                    userPublishList = (List<Publish>) message.obj;
                    for (Publish publish : userPublishList) {
                        if (!mDatas.contains(publish.getPublish_title()))
                            mDatas.add(publish.getPublish_title());
                    }
                    if (mDatas.size() != 0) {
                        userPublishRecycler.setLayoutManager(new LinearLayoutManager(ActivityManagerList.this));
                        userPublishRecycler.setAdapter(mAdapter = new ModelListAdapter(mDatas, mHandler));
                    } else {
                        findViewById(R.id.activity_manager_list_toast).setVisibility(View.VISIBLE);
                        userPublishRecycler.setVisibility(View.GONE);
                    }
                    break;
                case 1:
                    JSONArray join_list = userPublishList.get((int)message.obj).getJoin_list();
                    Intent intentFinish = new Intent(ActivityManagerList.this,JoinList.class);
                    intentFinish.putStringArrayListExtra("join_list",MyUtil.jsonArrayToJoinList(join_list));
                    startActivity(intentFinish);
                default:
                    return true;
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_list_layout);
        mContext = MyApplication.getInstance();
        init();
    }

    @Override
    protected void init() {
        MyRomateSQLUtil.getUserPublishList(mHandler);
        userPublishRecycler = (RecyclerView) findViewById(R.id.activity_manager_list);
        activityManagerBack = (Button) findViewById(R.id.activity_manager_list_back);
        activityManagerBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
