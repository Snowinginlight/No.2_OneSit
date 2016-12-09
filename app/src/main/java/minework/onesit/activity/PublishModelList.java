package minework.onesit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import minework.onesit.R;
import minework.onesit.fragment.plan.ListAdapter;
import minework.onesit.fragment.plan.SeatTableAdapter;
import minework.onesit.module.Publish;
import minework.onesit.module.Seat;
import minework.onesit.util.MyRomateSQLUtil;

/**
 * Created by 无知 on 2016/12/9.
 */

public class PublishModelList extends BaseActivity {
    private static List<String> mDatas = new ArrayList<String>();
    private static List<Publish> publishList;
    private static SeatTableAdapter seatAdapter;
    private RecyclerView publishModelList;
    private ListAdapter mAdapter;
    private List<Integer> seatDatas;
    private Button publishModelBack;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case -1:
                    findViewById(R.id.publish_model_list_toast).setVisibility(View.VISIBLE);
                    publishModelList.setVisibility(View.GONE);
                    break;
                case 0:
                    publishList = (List<Publish>) message.obj;
                    for (Publish publish : publishList) {
                        if (!mDatas.contains(publish.getPublish_title()))
                            mDatas.add(publish.getPublish_title());
                    }
                    if (mDatas.size() != 0) {
                        publishModelList.setLayoutManager(new LinearLayoutManager(PublishModelList.this));
                        publishModelList.setAdapter(mAdapter = new ListAdapter(mDatas, mHandler));
                    } else {
                        findViewById(R.id.publish_model_list_toast).setVisibility(View.VISIBLE);
                        publishModelList.setVisibility(View.GONE);
                    }
                    break;
                case 1:
                    seatDatas = new ArrayList<Integer>();
                    for (int i = 0; i < publishList.get((int) message.obj).getSeat_table().length(); i++) {
                        try {
                            seatDatas.add(publishList.get((int) message.obj).getSeat_table().getInt(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    seatAdapter = new SeatTableAdapter(seatDatas);
                    Intent intentFinish = new Intent(PublishModelList.this, minework.onesit.activity.Publish.class);
                    intentFinish.putExtra("hasPublishModel", true);
                    intentFinish.putExtra("publish_title", publishList.get((int) message.obj).getPublish_title());
                    intentFinish.putExtra("start_time", publishList.get((int) message.obj).getStart_time());
                    intentFinish.putExtra("stop_time", publishList.get((int) message.obj).getStop_time());
                    intentFinish.putExtra("seat_column", publishList.get((int) message.obj).getSeat_column());
                    intentFinish.putExtra("people_number", publishList.get((int) message.obj).getPeople_number());
                    intentFinish.putExtra("publish_place", publishList.get((int) message.obj).getPublish_place());
                    intentFinish.putExtra("information_text", publishList.get((int) message.obj).getInformation_text());
                    startActivity(intentFinish);
                default:
                    return true;
            }
            return true;
        }
    });

    public static Publish getPublishList(int position) {
        return publishList.get(position);
    }

    public static SeatTableAdapter getRecyclerViewAdapter() {
        return seatAdapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_model_list_layout);
        init();
    }

    @Override
    protected void init() {
        MyRomateSQLUtil.getPublishModelList(mHandler);
        publishModelList = (RecyclerView) findViewById(R.id.publish_model_list);
        publishModelBack = (Button) findViewById(R.id.publish_model_back);
        publishModelBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

}
