package minework.onesit.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.TimePicker;
import minework.onesit.R;
import minework.onesit.fragment.plan.PlanFragment;
import minework.onesit.util.MyRomateSQLUtil;

/**
 * Created by 无知 on 2016/12/13.
 */

public class PlanCreate extends BaseActivity implements View.OnClickListener {
    private static int planNumber = 1;
    private static Handler mHandler;
    private Context mContext;
    private Button planCreateBackButton;
    private Button planCreateSureButton;
    private EditText planCreateTitle;
    private TextView planCreateStartTime;
    private TextView planCreateStopTime;
    private TextView planCreateRemindTime;
    private EditText planCreatePlace;
    private EditText planCreateSeat;
    private EditText planCreateTips;

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
        planCreateSureButton = (Button) findViewById(R.id.plan_create_sure);
        planCreateTitle = (EditText) findViewById(R.id.plan_create_title);
        planCreateStartTime = (TextView) findViewById(R.id.plan_create_start_time);
        planCreateStopTime = (TextView) findViewById(R.id.plan_create_stop_time);
        planCreateRemindTime = (TextView) findViewById(R.id.plan_create_remind_time);
        planCreatePlace = (EditText) findViewById(R.id.plan_create_place);
        planCreateSeat = (EditText) findViewById(R.id.plan_create_seat);
        planCreateTips = (EditText) findViewById(R.id.plan_create_tips);
        planCreateBackButton.setOnClickListener(this);
        planCreateSureButton.setOnClickListener(this);
        planCreateStartTime.setOnClickListener(this);
        planCreateStopTime.setOnClickListener(this);
        planCreateRemindTime.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.plan_create_back:
                AnimationDrawable animBack = new AnimationDrawable();
                animBack.addFrame(mContext.getDrawable(R.mipmap.back_c), 200);
                animBack.addFrame(mContext.getDrawable(R.mipmap.back), 200);
                animBack.setOneShot(true);
                planCreateBackButton.setBackground(animBack);
                animBack.start();
                onBackPressed();
                break;
            case R.id.plan_create_sure:
                SharedPreferences plan = mContext.getSharedPreferences("local_plan_" + planNumber,
                        Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = plan.edit();
                editor.putString("plan_title", planCreateTitle.getText().toString());
                editor.putString("start_time", planCreateStartTime.getText().toString());
                editor.putString("stop_time", planCreateStopTime.getText().toString());
                editor.putString("remind_time", planCreateRemindTime.getText().toString());
                editor.putString("plan_place", planCreatePlace.getText().toString());
                editor.putString("plan_seat", planCreateSeat.getText().toString());
                editor.putString("plan_tips", planCreateTips.getText().toString());
                editor.commit();
                PlanFragment.setPlanNumber(planNumber);
                planNumber++;
                AnimationDrawable animSure = new AnimationDrawable();
                animSure.addFrame(mContext.getDrawable(R.mipmap.sure_c), 200);
                animSure.addFrame(mContext.getDrawable(R.mipmap.sure), 200);
                animSure.setOneShot(true);
                planCreateSureButton.setBackground(animSure);
                animSure.start();
                if (!TextUtils.isEmpty(planCreateTitle.getText()) && !TextUtils.isEmpty(planCreateStartTime.getText())
                        && !TextUtils.isEmpty(planCreateStopTime.getText()) && !TextUtils.isEmpty(planCreateRemindTime.getText())) {
                    AlertDialog.Builder saveView = new AlertDialog.Builder(this);
                    saveView.setMessage("确定添加到计划表？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MyRomateSQLUtil.savePlanSelf(planCreateTitle.getText().toString(), planCreateStartTime.getText().toString(), planCreateStopTime.getText().toString(), planCreateRemindTime.getText().toString(), planCreatePlace.getText().toString(), planCreateSeat.getText().toString(), planCreateTips.getText().toString());
                            Message msg = new Message();
                            msg.what = 1;
                            msg.obj = true;
                            mHandler.sendMessage(msg);
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
            case R.id.plan_create_start_time:
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
                                planCreateStartTime.setText(myYear + "年" + myMonth + "月" + myDay + "日" + hour + "：" + minute);
                            }
                        });
                        picker.show();
                    }
                });
                startTimePicker.show();
                break;
            case R.id.plan_create_stop_time:
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
                                planCreateStopTime.setText(myYear + "年" + myMonth + "月" + myDay + "日" + hour + "：" + minute);
                            }
                        });
                        picker.show();
                    }
                });
                stopTimePicker.show();
                break;
            case R.id.plan_create_remind_time:
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
                                planCreateRemindTime.setText(myYear + "年" + myMonth + "月" + myDay + "日" + hour + "：" + minute);
                            }
                        });
                        picker.show();
                    }
                });
                remindTimePicker.show();
                break;
            default:
                return;
        }
    }

    public static void setHandler(Handler otherHandler){
        mHandler = otherHandler;
    }
}
