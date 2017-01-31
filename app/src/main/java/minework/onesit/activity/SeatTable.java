package minework.onesit.activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.qqtheme.framework.picker.NumberPicker;
import cn.qqtheme.framework.picker.OptionPicker;
import minework.onesit.R;
import minework.onesit.fragment.plan.SeatNumberHAdapter;
import minework.onesit.fragment.plan.SeatNumberVAdapter;
import minework.onesit.fragment.plan.SeatTableAdapter;
import minework.onesit.util.MyRomateSQLUtil;

/**
 * Created by 无知 on 2016/11/26.
 */

public class SeatTable extends BaseActivity implements View.OnClickListener {

    //主体
    private Context mContext;
    private ScrollView seatMain;
    private TextView seatTitle;
    private Button seatBack;
    private Button seatManager;
    private View seatRowButton;
    private View seatColumnButton;
    private View seatAdd;
    private View seatRemove;
    private TextView seatColumn;
    private TextView seatRow;
    private RecyclerView seatTable;
    private RecyclerView seatNumberV;
    private RecyclerView seatNumberH;
    //菜单
    private PopupWindow seatMenuWindow;
    private View seatMenuView;
    private Button seatFinish;
    private Button seatSave;
    private Button seatImport;
    private Animation clockAnimation;
    private Animation counter_clockAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seat_table_layout);
        mContext = MyApplication.getInstance();
        init();
    }

    @Override
    protected void init() {
        seatMain = (ScrollView) findViewById(R.id.seat_main);
        seatTitle = (TextView) findViewById(R.id.seat_title);
        seatBack = (Button) findViewById(R.id.seat_back);
        seatManager = (Button) findViewById(R.id.seat_manager);
        seatRow = (TextView) findViewById(R.id.seat_row);
        seatColumn = (TextView) findViewById(R.id.seat_column);
        seatRowButton = findViewById(R.id.seat_row_button);
        seatColumnButton = findViewById(R.id.seat_column_button);
        seatAdd = findViewById(R.id.seat_add);
        seatRemove = findViewById(R.id.seat_remove);
        seatTable = (RecyclerView) findViewById(R.id.seat_table);
        seatNumberV = (RecyclerView) findViewById(R.id.seat_number_v);
        seatNumberH = (RecyclerView) findViewById(R.id.seat_number_h);
        seatTable.setLayoutManager(new GridLayoutManager(mContext, Integer.valueOf(seatColumn.getText().toString())));
        seatTable.setAdapter(new SeatTableAdapter(Integer.valueOf(seatRow.getText().toString()), Integer.valueOf(seatColumn.getText().toString())));
        seatNumberV.setLayoutManager(new LinearLayoutManager(mContext));
        seatNumberV.setAdapter(new SeatNumberVAdapter(Integer.valueOf(seatRow.getText().toString())));
        seatNumberH.setLayoutManager(new GridLayoutManager(mContext, Integer.valueOf(seatColumn.getText().toString())));
        seatNumberH.setAdapter(new SeatNumberHAdapter(Integer.valueOf(seatColumn.getText().toString())));

        seatTitle.setOnClickListener(this);
        seatBack.setOnClickListener(this);
        seatManager.setOnClickListener(this);
        seatRowButton.setOnClickListener(this);
        seatColumnButton.setOnClickListener(this);
        seatAdd.setOnClickListener(this);
        seatRemove.setOnClickListener(this);

        clockAnimation = AnimationUtils.loadAnimation(this, R.anim.clockwise_rotate_90);
        clockAnimation.setDuration(500);
        clockAnimation.setFillAfter(true);
        counter_clockAnimation = AnimationUtils.loadAnimation(this, R.anim.counter_clockwise_rotate_90);
        counter_clockAnimation.setDuration(500);
        counter_clockAnimation.setFillAfter(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.seat_title:
                startActivity(new Intent(this, EditActivity.class).putExtra("name", "标题").putExtra("activity", "SeatTable"));
                break;
            case R.id.seat_back:
                onBackPressed();
                break;
            case R.id.seat_manager:
                if (seatMenuWindow != null && seatMenuWindow.isShowing()) {
                    seatManager.startAnimation(clockAnimation);
                    ObjectAnimator.ofFloat(seatMain, "alpha", 0.3f, 1.0f).setDuration(500).start();
                    seatMenuWindow.dismiss();
                } else {
                    if (seatMenuWindow == null) {
                        initSeatMenuWindow();
                    }
                    seatMenuWindow.showAsDropDown(seatManager, -68, -20);
                    seatManager.startAnimation(counter_clockAnimation);
                    ObjectAnimator.ofFloat(seatMain, "alpha", 1.0f, 0.3f).setDuration(500).start();
                }
                break;
            case R.id.seat_finish:
                int blackNumber = 0;
                int greenNumber = 0;
                for (Integer colorType : ((SeatTableAdapter) seatTable.getAdapter()).getList()) {
                    switch (colorType){
                        case 1:
                            blackNumber++;
                            break;
                        case 2:
                            greenNumber++;
                            break;
                        default:
                            break;
                    }
                }
                startActivity(new Intent(this,Publish.class).putExtra("name","座位").putExtra("content",String.valueOf(greenNumber)).putExtra("content1",String.valueOf(greenNumber+blackNumber)).putExtra("row",Integer.valueOf(seatRow.getText().toString())).putExtra("column",Integer.valueOf(seatColumn.getText().toString())).putIntegerArrayListExtra("seat", (ArrayList<Integer>) ((SeatTableAdapter)seatTable.getAdapter()).getList()));
                break;
            case R.id.seat_import:
                startActivity(new Intent(this, SeatTableModelList.class));
                break;
            case R.id.seat_save:
                if (!TextUtils.isEmpty(seatTitle.getText())) {
                    MyRomateSQLUtil.saveSeatTable(seatTitle.getText().toString(), ((SeatTableAdapter) seatTable.getAdapter()).getList(), Integer.valueOf(seatRow.getText().toString()), Integer.valueOf(seatColumn.getText().toString()));
                }
                break;
            case R.id.seat_add:
                ((SeatTableAdapter) seatTable.getAdapter()).addItem();
                seatRow.setText(String.valueOf(seatTable.getAdapter().getItemCount() % Integer.valueOf(seatColumn.getText().toString()) == 0 ? seatTable.getAdapter().getItemCount() / Integer.valueOf(seatColumn.getText().toString()) : (seatTable.getAdapter().getItemCount() / Integer.valueOf(seatColumn.getText().toString())) + 1));
                seatNumberV.setAdapter(new SeatNumberVAdapter(Integer.valueOf(seatRow.getText().toString())));
                break;
            case R.id.seat_remove:
                if (seatTable.getAdapter().getItemCount() > 1) {
                    ((SeatTableAdapter) seatTable.getAdapter()).removeItem();
                    if (Integer.valueOf(seatRow.getText().toString()) == 1 && seatTable.getAdapter().getItemCount() > 0) {
                        seatColumn.setText(String.valueOf(Integer.valueOf(seatColumn.getText().toString()) - 1));
                        seatNumberH.setLayoutManager(new GridLayoutManager(mContext, Integer.valueOf(seatColumn.getText().toString())));
                        seatNumberH.setAdapter(new SeatNumberHAdapter(Integer.valueOf(seatColumn.getText().toString())));
                        seatTable.setLayoutManager(new GridLayoutManager(mContext, Integer.valueOf(seatColumn.getText().toString())));
                        seatTable.setAdapter(new SeatTableAdapter(Integer.valueOf(seatRow.getText().toString()), Integer.valueOf(seatColumn.getText().toString())));
                    }
                    seatRow.setText(String.valueOf(seatTable.getAdapter().getItemCount() % Integer.valueOf(seatColumn.getText().toString()) == 0 ? seatTable.getAdapter().getItemCount() / Integer.valueOf(seatColumn.getText().toString()) : seatTable.getAdapter().getItemCount() / Integer.valueOf(seatColumn.getText().toString()) + 1));
                    seatNumberV.setAdapter(new SeatNumberVAdapter(Integer.valueOf(seatRow.getText().toString())));
                }
                break;
            case R.id.seat_row_button:
                NumberPicker picker1 = new NumberPicker(this);
                picker1.setOffset(1);//偏移量
                picker1.setRange(1, 1000);//数字范围
                picker1.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(String option) {
                        seatRow.setText(option);
                        seatTable.setLayoutManager(new GridLayoutManager(mContext, Integer.valueOf(seatColumn.getText().toString())));
                        seatTable.setAdapter(new SeatTableAdapter(Integer.valueOf(seatRow.getText().toString()), Integer.valueOf(seatColumn.getText().toString())));
                        seatNumberV.setAdapter(new SeatNumberVAdapter(Integer.valueOf(seatRow.getText().toString())));
                    }
                });
                picker1.show();
                break;
            case R.id.seat_column_button:
                NumberPicker picker2 = new NumberPicker(this);
                picker2.setOffset(1);//偏移量
                picker2.setRange(1, 1000);//数字范围
                picker2.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(String option) {
                        seatColumn.setText(option);
                        seatTable.setLayoutManager(new GridLayoutManager(mContext, Integer.valueOf(seatColumn.getText().toString())));
                        seatTable.setAdapter(new SeatTableAdapter(Integer.valueOf(seatRow.getText().toString()), Integer.valueOf(seatColumn.getText().toString())));
                        seatNumberH.setLayoutManager(new GridLayoutManager(mContext, Integer.valueOf(seatColumn.getText().toString())));
                        seatNumberH.setAdapter(new SeatNumberHAdapter(Integer.valueOf(seatColumn.getText().toString())));
                    }
                });
                picker2.show();
                break;
            default:
                return;
        }
    }

    private void initSeatMenuWindow() {
        seatMenuView = getLayoutInflater().inflate(R.layout.seat_manager_layout, null, false);
        seatMenuWindow = new PopupWindow(seatMenuView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        seatMenuWindow.setAnimationStyle(R.style.AnimationFade);
        seatMenuWindow.setOutsideTouchable(true);
        seatSave = (Button) seatMenuView.findViewById(R.id.seat_save);
        seatFinish = (Button) seatMenuView.findViewById(R.id.seat_finish);
        seatImport = (Button) seatMenuView.findViewById(R.id.seat_import);
        seatSave.setOnClickListener(this);
        seatFinish.setOnClickListener(this);
        seatImport.setOnClickListener(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(getIntent().getStringExtra("content"))) {
            switch (getIntent().getStringExtra("name")) {
                case "标题":
                    seatTitle.setText(getIntent().getStringExtra("content"));
                    break;
                default:
                    return;
            }
        }
        if (getIntent().getBooleanExtra("hasSeatTable", false)) {
            seatTitle.setText(getIntent().getStringExtra("title"));
            seatColumn.setText(String.valueOf(getIntent().getIntExtra("column", 1)));
            seatTable.setLayoutManager(new GridLayoutManager(mContext, Integer.valueOf(seatColumn.getText().toString())));
            seatTable.setAdapter(new SeatTableAdapter(getIntent().getIntegerArrayListExtra("seat_table")));
            seatRow.setText(String.valueOf(seatTable.getAdapter().getItemCount() % Integer.valueOf(seatColumn.getText().toString()) == 0 ? seatTable.getAdapter().getItemCount() / Integer.valueOf(seatColumn.getText().toString()) : (seatTable.getAdapter().getItemCount() / Integer.valueOf(seatColumn.getText().toString())) + 1));
        }
        if (seatMenuWindow != null && seatMenuWindow.isShowing()) {
            seatManager.startAnimation(clockAnimation);
            ObjectAnimator.ofFloat(seatMain, "alpha", 0.3f, 1.0f).setDuration(500).start();
            seatMenuWindow.dismiss();
        }
    }

}


