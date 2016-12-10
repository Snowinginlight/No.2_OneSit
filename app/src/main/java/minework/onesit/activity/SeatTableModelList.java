package minework.onesit.activity;

import android.content.Intent;
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
import minework.onesit.module.Seat;
import minework.onesit.util.MyRomateSQLUtil;

/**
 * Created by 无知 on 2016/12/7.
 */

public class SeatTableModelList extends BaseActivity {

    private static List<String> mDatas = new ArrayList<String>();
    private static List<Seat> seatList;
    private RecyclerView seatTableList;
    private ModelListAdapter mAdapter;
    private List<Integer> seatDatas;
    private Button seatTableBack;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case -1:
                    findViewById(R.id.seat_table_model_list_toast).setVisibility(View.VISIBLE);
                    seatTableList.setVisibility(View.GONE);
                    break;
                case 0:
                    seatList = (List<Seat>) message.obj;
                    for (Seat seat : seatList) {
                        if (!mDatas.contains(seat.getSeat_title()))
                            mDatas.add(seat.getSeat_title());
                    }
                    if (mDatas.size() != 0) {
                        seatTableList.setLayoutManager(new LinearLayoutManager(SeatTableModelList.this));
                        seatTableList.setAdapter(mAdapter = new ModelListAdapter(mDatas, mHandler));
                    } else {
                        findViewById(R.id.seat_table_model_list_toast).setVisibility(View.VISIBLE);
                        seatTableList.setVisibility(View.GONE);
                    }
                    break;
                case 1:
                    seatDatas = new ArrayList<Integer>();
                    for (int i = 0; i < seatList.get((int) message.obj).getSeat_table().length(); i++) {
                        try {
                            seatDatas.add(seatList.get((int) message.obj).getSeat_table().getInt(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    SeatTable.setRecyclerViewAdapter(new SeatTableAdapter(seatDatas));
                    Intent intentFinish = new Intent(SeatTableModelList.this, SeatTable.class);
                    intentFinish.putExtra("hasSeatTable", true);
                    intentFinish.putExtra("title", seatList.get((int) message.obj).getSeat_title());
                    intentFinish.putExtra("column", seatList.get((int) message.obj).getSeat_column());
                    intentFinish.putExtra("row", seatList.get((int) message.obj).getSeat_row());
                    startActivity(intentFinish);
                default:
                    return true;
            }
            return true;
        }
    });

    public static Seat getSeatList(int position) {
        return seatList.get(position);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seat_table_model_list_layout);
        init();
    }

    @Override
    protected void init() {
        MyRomateSQLUtil.getSeatTableList(mHandler);
        seatTableList = (RecyclerView) findViewById(R.id.seat_table_model_list);
        seatTableBack = (Button) findViewById(R.id.seat_table_model_back);
        seatTableBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

}
