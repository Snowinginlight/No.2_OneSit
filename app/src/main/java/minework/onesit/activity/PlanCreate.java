package minework.onesit.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.TimePicker;
import minework.onesit.R;
import minework.onesit.database.MyDatabaseUtil;

/**
 * Created by 无知 on 2016/12/13.
 */

public class PlanCreate extends BaseActivity implements View.OnClickListener {

    private static Handler handler;
    private int planSelfNum = 0;
    private Context mContext;
    private Button planCreateBackButton;
    private CardView planCreateFinishButton;
    private View planCreateTitleButton;
    private TextView planCreateTitle;
    private View planCreateStartTimeButton;
    private TextView planCreateStartTime;
    private View planCreateStopTimeButton;
    private TextView planCreateStopTime;
    private View planCreateRemindTimeButton;
    private TextView planCreateRemindTime;
    private View planCreatePlaceButton;
    private TextView planCreatePlace;
    private View planCreateTipsButton;
    private TextView planCreateTips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plan_create_layout);
        mContext = MyApplication.getInstance();
        init();
    }

    @Override
    protected void init() {
        planCreateBackButton = (Button) findViewById(R.id.plan_create_back);
        planCreateFinishButton = (CardView) findViewById(R.id.plan_create_finish);
        planCreateTitleButton = findViewById(R.id.plan_create_title_button);
        planCreateTitle = (TextView) findViewById(R.id.plan_create_title_text);
        planCreateStartTimeButton = findViewById(R.id.plan_create_start_time_button);
        planCreateStartTime = (TextView) findViewById(R.id.plan_create_start_time_text);
        planCreateStopTimeButton = findViewById(R.id.plan_create_stop_time_button);
        planCreateStopTime = (TextView) findViewById(R.id.plan_create_stop_time_text);
        planCreateRemindTimeButton = findViewById(R.id.plan_create_remind_time_button);
        planCreateRemindTime = (TextView) findViewById(R.id.plan_create_remind_time_text);
        planCreatePlaceButton = findViewById(R.id.plan_create_place_button);
        planCreatePlace = (TextView) findViewById(R.id.plan_create_place_text);
        planCreateTipsButton = findViewById(R.id.plan_create_tips_button);
        planCreateTips = (TextView) findViewById(R.id.plan_create_tips_text);

        planCreateBackButton.setOnClickListener(this);
        planCreateFinishButton.setOnClickListener(this);
        planCreateTitleButton.setOnClickListener(this);
        planCreateStartTimeButton.setOnClickListener(this);
        planCreateStopTimeButton.setOnClickListener(this);
        planCreateRemindTimeButton.setOnClickListener(this);
        planCreatePlaceButton.setOnClickListener(this);
        planCreateTipsButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.plan_create_back:
                onBackPressed();
                break;
            case R.id.plan_create_finish:
                if (!TextUtils.isEmpty(planCreateTitle.getText()) && !TextUtils.isEmpty(planCreateStartTime.getText())
                        && !TextUtils.isEmpty(planCreateStopTime.getText()) && !TextUtils.isEmpty(planCreateRemindTime.getText())) {
                    AlertDialog.Builder saveView = new AlertDialog.Builder(this);
                    saveView.setMessage("确定添加到计划表？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Map<String, String> plan = new HashMap<String, String>();
                            plan.put("plan_id", String.valueOf(planSelfNum));
                            plan.put("plan_title", planCreateTitle.getText().toString());
                            plan.put("start_time", planCreateStartTime.getText().toString());
                            plan.put("stop_time", planCreateStopTime.getText().toString());
                            plan.put("remind_time", planCreateRemindTime.getText().toString());
                            plan.put("plan_place", planCreatePlace.getText().toString());
                            plan.put("plan_tips", planCreateTips.getText().toString());
                            MyDatabaseUtil.insertPlanSelf(plan);
                            startActivity(new Intent(mContext,Main.class));
                            finish();
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    AlertDialog saveDialog = saveView.create();
                    saveDialog.show();
                } else {
                    Toast.makeText(mContext, "标题、时间不能为空", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.plan_create_start_time_button:
                DatePicker startTimePicker = new DatePicker(this);
                startTimePicker.setRange(2016, 2060);//年份范围
                startTimePicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        final String myYear = year;
                        final String myMonth = month;
                        final String myDay = day;
                        planCreateStartTime.setText(myYear + "年" + myMonth + "月" + myDay + "日");
                        final TimePicker picker = new TimePicker(PlanCreate.this);
                        picker.setTopLineVisible(false);
                        picker.setOnTimePickListener(new TimePicker.OnTimePickListener() {
                            @Override
                            public void onTimePicked(String hour, String minute) {
                                planCreateStartTime.setText(myYear + "年" + myMonth + "月" + myDay + "日" + hour + ":" + minute);
                            }
                        });
                        picker.show();
                    }
                });
                startTimePicker.show();
                break;
            case R.id.plan_create_stop_time_button:
                DatePicker stopTimePicker = new DatePicker(this);
                stopTimePicker.setRange(2016, 2060);//年份范围
                stopTimePicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        final String myYear = year;
                        final String myMonth = month;
                        final String myDay = day;
                        planCreateStopTime.setText(myYear + "年" + myMonth + "月" + myDay + "日");
                        final TimePicker picker = new TimePicker(PlanCreate.this);
                        picker.setTopLineVisible(false);
                        picker.setOnTimePickListener(new TimePicker.OnTimePickListener() {
                            @Override
                            public void onTimePicked(String hour, String minute) {
                                planCreateStopTime.setText(myYear + "年" + myMonth + "月" + myDay + "日" + hour + ":" + minute);
                            }
                        });
                        picker.show();
                    }
                });
                stopTimePicker.show();
                break;
            case R.id.plan_create_remind_time_button:
                DatePicker remindTimePicker = new DatePicker(this);
                remindTimePicker.setRange(2016, 2060);//年份范围
                remindTimePicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        final String myYear = year;
                        final String myMonth = month;
                        final String myDay = day;
                        planCreateRemindTime.setText(myYear + "年" + myMonth + "月" + myDay + "日");
                        final TimePicker picker = new TimePicker(PlanCreate.this);
                        picker.setTopLineVisible(false);
                        picker.setOnTimePickListener(new TimePicker.OnTimePickListener() {
                            @Override
                            public void onTimePicked(String hour, String minute) {
                                planCreateRemindTime.setText(myYear + "年" + myMonth + "月" + myDay + "日" + hour + ":" + minute);
                            }
                        });
                        picker.show();
                    }
                });
                remindTimePicker.show();
                break;
            case R.id.plan_create_title_button:
                startActivity(new Intent(this, EditActivity.class).putExtra("name", "标题").putExtra("activity","PlanCreate"));
                break;
            case R.id.plan_create_place_button:
                startActivity(new Intent(this, EditActivity.class).putExtra("name", "地点").putExtra("activity","PlanCreate"));
                break;
            case R.id.plan_create_tips_button:
                startActivity(new Intent(this, EditActivity.class).putExtra("name", "备注").putExtra("activity","PlanCreate"));
                break;
            default:
                return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        planSelfNum = getIntent().getIntExtra("planSelfNum", 0);
        if (!TextUtils.isEmpty(getIntent().getStringExtra("content"))) {
            switch (getIntent().getStringExtra("name")) {
                case "标题":
                    planCreateTitle.setText(getIntent().getStringExtra("content"));
                    break;
                case "地点":
                    planCreatePlace.setText(getIntent().getStringExtra("content"));
                    break;
                case "备注":
                    planCreateTips.setText(getIntent().getStringExtra("content"));
                default:
                    return;
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }
}
