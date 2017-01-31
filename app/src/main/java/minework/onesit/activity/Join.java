package minework.onesit.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import minework.onesit.R;
import minework.onesit.fragment.plan.JoinSeatTableAdapter;
import minework.onesit.fragment.plan.SeatNumberHAdapter;
import minework.onesit.fragment.plan.SeatNumberVAdapter;
import minework.onesit.module.Publish;
import minework.onesit.util.MyRomateSQLUtil;
import minework.onesit.util.MyUtil;

/**
 * Created by 无知 on 2017/1/25.
 */

public class Join extends BaseActivity implements View.OnClickListener {

    private Button joinBack;
    private CardView joinFinish;
    private RecyclerView seatTable;
    private RecyclerView seatNumberV;
    private RecyclerView seatNumberH;
    private TextView joinSeat1, joinSeat2, joinSeat3, joinSeat4;
    private int column;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    seatTable.setLayoutManager(new GridLayoutManager(Join.this, ((Publish) message.obj).getSeat_column()));
                    seatTable.setAdapter(new JoinSeatTableAdapter(MyUtil.jsonArrayToListInteger(((Publish) message.obj).getSeat_table()), mHandler));
                    seatNumberV.setLayoutManager(new LinearLayoutManager(Join.this));
                    seatNumberV.setAdapter(new SeatNumberVAdapter(((Publish) message.obj).getSeat_row()));
                    seatNumberH.setLayoutManager(new GridLayoutManager(Join.this, ((Publish) message.obj).getSeat_column()));
                    seatNumberH.setAdapter(new SeatNumberHAdapter(((Publish) message.obj).getSeat_column()));
                    column = ((Publish) message.obj).getSeat_column();
                    break;
                case 1:
                    if (message.arg1 == 1) {
                        String seat = ((message.arg2 + 1) % column == 0 ? ((message.arg2 + 1) / column) : ((message.arg2 + 1) / column + 1)) + "行" + ((message.arg2 + 1) % column == 0 ? column : ((message.arg2 + 1) % column)) + "列";
                        if (Objects.equals(seat, joinSeat1.getText().toString())) {
                            joinSeat1.setText("");
                            joinSeat1.setVisibility(View.GONE);
                        } else {
                            if (Objects.equals(seat, joinSeat2.getText().toString())) {
                                joinSeat2.setText("");
                                joinSeat2.setVisibility(View.GONE);
                            } else {
                                if (Objects.equals(seat, joinSeat3.getText().toString())) {
                                    joinSeat3.setText("");
                                    joinSeat3.setVisibility(View.GONE);
                                } else {
                                    if (Objects.equals(seat, joinSeat4.getText().toString())) {
                                        joinSeat4.setText("");
                                        joinSeat4.setVisibility(View.GONE);
                                    }
                                }
                            }
                        }
                    } else if (message.arg1 == 3) {
                        if (joinSeat1.getVisibility() == View.VISIBLE) {
                            if (joinSeat2.getVisibility() == View.VISIBLE) {
                                if (joinSeat3.getVisibility() == View.VISIBLE) {
                                    joinSeat4.setText(((message.arg2 + 1) % column == 0 ? ((message.arg2 + 1) / column) : ((message.arg2 + 1) / column + 1)) + "行" + ((message.arg2 + 1) % column == 0 ? column : ((message.arg2 + 1) % column)) + "列");
                                    joinSeat4.setVisibility(View.VISIBLE);
                                } else {
                                    joinSeat3.setText(((message.arg2 + 1) % column == 0 ? ((message.arg2 + 1) / column) : ((message.arg2 + 1) / column + 1)) + "行" + ((message.arg2 + 1) % column == 0 ? column : ((message.arg2 + 1) % column)) + "列");
                                    joinSeat3.setVisibility(View.VISIBLE);
                                }
                            } else {
                                joinSeat2.setText(((message.arg2 + 1) % column == 0 ? ((message.arg2 + 1) / column) : ((message.arg2 + 1) / column + 1)) + "行" + ((message.arg2 + 1) % column == 0 ? column : ((message.arg2 + 1) % column)) + "列");
                                joinSeat2.setVisibility(View.VISIBLE);
                            }
                        } else {
                            joinSeat1.setText(((message.arg2 + 1) % column == 0 ? ((message.arg2 + 1) / column) : ((message.arg2 + 1) / column + 1)) + "行" + ((message.arg2 + 1) % column == 0 ? column : ((message.arg2 + 1) % column)) + "列");
                            joinSeat1.setVisibility(View.VISIBLE);
                        }
                    }
                    break;
                default:
                    return true;
            }
            return true;
        }
    }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);
        init();
    }

    @Override
    protected void init() {
        joinBack = (Button) findViewById(R.id.join_back);
        joinFinish = (CardView) findViewById(R.id.join_finish);
        seatTable = (RecyclerView) findViewById(R.id.join_seat_table);
        seatNumberV = (RecyclerView) findViewById(R.id.join_seat_number_v);
        seatNumberH = (RecyclerView) findViewById(R.id.join_seat_number_h);
        joinSeat1 = (TextView) findViewById(R.id.join_seat_1);
        joinSeat2 = (TextView) findViewById(R.id.join_seat_2);
        joinSeat3 = (TextView) findViewById(R.id.join_seat_3);
        joinSeat4 = (TextView) findViewById(R.id.join_seat_4);

        joinFinish.setOnClickListener(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(getIntent().getStringExtra("object_id"))) {
            MyRomateSQLUtil.getPublish(getIntent().getStringExtra("object_id"), mHandler);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.join_back:
                onBackPressed();
                break;
            case R.id.join_finish:
                if (joinSeat1.getVisibility() == View.VISIBLE || joinSeat2.getVisibility() == View.VISIBLE || joinSeat3.getVisibility() == View.VISIBLE || joinSeat4.getVisibility() == View.VISIBLE) {
                    final StringBuilder stringBuilder = new StringBuilder();
                    if(joinSeat1.getVisibility() == View.VISIBLE){
                        stringBuilder.append(joinSeat1.getText().toString()+" ");
                    }
                    if(joinSeat2.getVisibility() == View.VISIBLE){
                        stringBuilder.append(joinSeat2.getText().toString()+" ");
                    }
                    if(joinSeat3.getVisibility() == View.VISIBLE){
                        stringBuilder.append(joinSeat3.getText().toString()+" ");
                    }
                    if(joinSeat4.getVisibility() == View.VISIBLE){
                        stringBuilder.append(joinSeat4.getText().toString()+" ");
                    }
                    AlertDialog.Builder saveView = new AlertDialog.Builder(this);
                    saveView.setMessage("确定选择 " + stringBuilder.toString() + "么？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int a) {
                            List<Integer> oldDatas = ((JoinSeatTableAdapter) seatTable.getAdapter()).getList();
                            List<Integer> newDatas = new ArrayList<>();
                            for (int i = 0; i < oldDatas.size(); i++) {
                                if (oldDatas.get(i) == 3) {
                                    newDatas.add(2);
                                } else {
                                    newDatas.add(oldDatas.get(i));
                                }
                            }
                            MyRomateSQLUtil.savePublish(getIntent().getStringExtra("object_id"), newDatas, stringBuilder.toString());
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    AlertDialog saveDialog = saveView.create();
                    saveDialog.show();
                } else {
                    Toast.makeText(this, "还没有任何选择", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
