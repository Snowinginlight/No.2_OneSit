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

import java.util.ArrayList;
import java.util.List;

import minework.onesit.R;
import minework.onesit.fragment.plan.JoinListAdapter;
import minework.onesit.fragment.plan.ModelListAdapter;
import minework.onesit.module.Publish;
import minework.onesit.util.MyRomateSQLUtil;

/**
 * Created by 无知 on 2017/5/7.
 */

public class JoinList extends BaseActivity {
    private static List<String> mDatas = new ArrayList<String>();
    private static List<String> joinUserList;
    private JoinListAdapter mAdapter;
    private RecyclerView joinUserRecycler;
    private Button joinUserBack;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_user_list_layout);
        mContext = MyApplication.getInstance();
        init();
    }

    @Override
    protected void init() {
        joinUserRecycler = (RecyclerView) findViewById(R.id.join_user_list);
        joinUserList = getIntent().getStringArrayListExtra("join_list");
        joinUserRecycler.setLayoutManager(new LinearLayoutManager(this));
        joinUserRecycler.setAdapter(mAdapter = new JoinListAdapter(joinUserList));
        joinUserBack = (Button) findViewById(R.id.join_user_list_back);
        joinUserBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }
}
