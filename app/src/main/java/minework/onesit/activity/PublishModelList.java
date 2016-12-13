package minework.onesit.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import minework.onesit.R;
import minework.onesit.fragment.plan.ModelListAdapter;
import minework.onesit.fragment.plan.SeatTableAdapter;
import minework.onesit.module.PublishModel;
import minework.onesit.util.MyRomateSQLUtil;

/**
 * Created by 无知 on 2016/12/9.
 */

public class PublishModelList extends BaseActivity {
    private static List<String> mDatas = new ArrayList<String>();
    private static List<PublishModel> publishModelList;
    private RecyclerView publishModelRecycler;
    private ModelListAdapter mAdapter;
    private List<Integer> seatDatas;
    private Button publishModelBack;
    private Context mContext;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case -1:
                    findViewById(R.id.publish_model_list_toast).setVisibility(View.VISIBLE);
                    publishModelRecycler.setVisibility(View.GONE);
                    break;
                case 0:
                    publishModelList = (List<PublishModel>) message.obj;
                    for (PublishModel publishModel : publishModelList) {
                        if (!mDatas.contains(publishModel.getPublish_Model_title()))
                            mDatas.add(publishModel.getPublish_Model_title());
                    }
                    if (mDatas.size() != 0) {
                        publishModelRecycler.setLayoutManager(new LinearLayoutManager(PublishModelList.this));
                        publishModelRecycler.setAdapter(mAdapter = new ModelListAdapter(mDatas, mHandler));
                    } else {
                        findViewById(R.id.publish_model_list_toast).setVisibility(View.VISIBLE);
                        publishModelRecycler.setVisibility(View.GONE);
                    }
                    break;
                case 1:
                    seatDatas = new ArrayList<Integer>();
                    for (int i = 0; i < publishModelList.get((int) message.obj).getSeat_table().length(); i++) {
                        try {
                            seatDatas.add(publishModelList.get((int) message.obj).getSeat_table().getInt(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    SeatTable.setRecyclerViewAdapter(new SeatTableAdapter(seatDatas));
                    Intent intentFinish = new Intent(PublishModelList.this, minework.onesit.activity.Publish.class);
                    intentFinish.putExtra("hasPublishModel", true);
                    intentFinish.putExtra("publish_title", publishModelList.get((int) message.obj).getPublish_Model_title());
                    intentFinish.putExtra("start_time", publishModelList.get((int) message.obj).getStart_time());
                    intentFinish.putExtra("stop_time", publishModelList.get((int) message.obj).getStop_time());
                    intentFinish.putExtra("seat_column", publishModelList.get((int) message.obj).getSeat_column());
                    intentFinish.putExtra("people_number", publishModelList.get((int) message.obj).getPeople_number());
                    intentFinish.putExtra("publish_place", publishModelList.get((int) message.obj).getPublish_Model_place());
                    intentFinish.putExtra("information_text", publishModelList.get((int) message.obj).getInformation_text());
                    startActivity(intentFinish);
                default:
                    return true;
            }
            return true;
        }
    });

    public static PublishModel getPublishList(int position) {
        return publishModelList.get(position);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_model_list_layout);
        mContext = MyApplication.getInstance();
        init();
    }

    @Override
    protected void init() {
        MyRomateSQLUtil.getPublishModelList(mHandler);
        publishModelRecycler = (RecyclerView) findViewById(R.id.publish_model_list);
        publishModelBack = (Button) findViewById(R.id.publish_model_back);
        publishModelBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimationDrawable animBack = new AnimationDrawable();
                animBack.addFrame(mContext.getDrawable(R.mipmap.back_c),200);
                animBack.addFrame(mContext.getDrawable(R.mipmap.back),200);
                animBack.setOneShot(true);
                publishModelBack.setBackground(animBack);
                animBack.start();
                onBackPressed();
            }
        });
    }

}
