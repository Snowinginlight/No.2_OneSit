package minework.onesit.fragment.plan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import minework.onesit.R;
import minework.onesit.activity.MyApplication;
import minework.onesit.activity.PlanCreate;
import minework.onesit.activity.Publish;
import minework.onesit.database.MyDatabaseUtil;
import minework.onesit.util.MyUtil;

/**
 * Created by 无知 on 2016/11/12.
 */

public class PlanFragment extends Fragment implements View.OnClickListener, View.OnTouchListener {

    private static int planNum = 0;
    private static int planSelfNum = 0;
    private Context mContext;
    //planfragment
    private View planLayout;
    private Button planAdd;
    private TextView planTitle;
    private Button planPublish;
    private LayoutInflater layoutInflater;
    //日历
    private View calendarArea;
    private RecyclerView calendar;
    private CalendarAdapter calendarAdapter;
    //计划条
    private RecyclerView planList;
    private List<Map<String, String>> mDatas;
    private List<Map<String, String>> planNetList;
    private List<Map<String, String>> planSelfList;
    //滑动坐标
    private float x1 = 0;
    private float x2 = 0;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    //点击计划显示全部信息

                    break;
                case 1:
                    //更新计划
                    planList.setLayoutManager(new LinearLayoutManager(mContext));
                    planList.setAdapter(new PlanListAdapter(MyUtil.chooseDatas(mDatas, message.arg1, message.arg2), 1, mHandler));
                    break;
                default:
                    return true;
            }
            return true;
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        planLayout = inflater.inflate(R.layout.plan_layout, container, false);
        layoutInflater = inflater;
        mContext = MyApplication.getInstance();
        initAllData();
        init();
        return planLayout;
    }

    private void init() {
        planAdd = (Button) planLayout.findViewById(R.id.plan_add);
        planTitle = (TextView) planLayout.findViewById(R.id.plan_title);
        planPublish = (Button) planLayout.findViewById(R.id.plan_publish);
        planList = (RecyclerView) planLayout.findViewById(R.id.plan_list);
        planList.setLayoutManager(new LinearLayoutManager(mContext));
        planList.setAdapter(new PlanListAdapter(mDatas, 0, mHandler));
        calendarArea = planLayout.findViewById(R.id.calendar_area);
        calendarArea.setVisibility(View.GONE);
        calendar = (RecyclerView) planLayout.findViewById(R.id.calendar);

        planTitle.setOnClickListener(this);
        planAdd.setOnClickListener(this);
        planPublish.setOnClickListener(this);
        planLayout.setOnTouchListener(this);
        planList.setOnTouchListener(this);
    }

    private void initAllData() {
        mDatas = new ArrayList<Map<String, String>>();
        planNetList = MyUtil.orderByTime(MyDatabaseUtil.queryPlanAll());
        planSelfList = MyUtil.orderByTime(MyDatabaseUtil.queryPlanSelfAll());
        planNum = planNetList.size();
        planSelfNum = planSelfList.size();
        mDatas = MyUtil.orderByTime(planNetList, planSelfList);
    }

    private void initWeekData() {
        mDatas = new ArrayList<Map<String, String>>();
        planNetList = MyUtil.orderByTime(MyDatabaseUtil.queryPlanAll());
        planSelfList = MyUtil.orderByTime(MyDatabaseUtil.queryPlanSelfAll());
        planNum = planNetList.size();
        planSelfNum = planSelfList.size();
        mDatas = MyUtil.orderByTime(planNetList, planSelfList);
    }

    private void initMonthData() {
        mDatas = new ArrayList<Map<String, String>>();
        planNetList = MyUtil.orderByTime(MyDatabaseUtil.queryPlanAll());
        planSelfList = MyUtil.orderByTime(MyDatabaseUtil.queryPlanSelfAll());
        planNum = planNetList.size();
        planSelfNum = planSelfList.size();
        mDatas = MyUtil.orderByTime(planNetList, planSelfList);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.plan_add:
                Intent intent = new Intent(mContext, PlanCreate.class);
                intent.putExtra("planSelfNum", planSelfNum);
                startActivity(intent);
                break;
            case R.id.plan_publish:
                startActivity(new Intent(mContext, Publish.class));
                break;
            case R.id.plan_title:
                switch (planTitle.getText().toString()) {
                    case "全部":
                        planTitle.setText("本周");
                        calendarArea.setVisibility(View.VISIBLE);
                        calendar.setLayoutManager(new GridLayoutManager(mContext, 7));
                        calendar.setAdapter(new CalendarAdapter(DateTime.now(), 0, mHandler));
                        planList.setLayoutManager(new LinearLayoutManager(mContext));
                        planList.setAdapter(new PlanListAdapter(MyUtil.chooseDatas(mDatas, DateTime.now().getMonthOfYear(), DateTime.now().getDayOfMonth()), 1, mHandler));
                        break;
                    case "本周":
                        planTitle.setText("本月");
                        calendar.setLayoutManager(new GridLayoutManager(mContext, 7));
                        calendar.setAdapter(new CalendarAdapter(DateTime.now(), 1, mHandler));
                        planList.setLayoutManager(new LinearLayoutManager(mContext));
                        planList.setAdapter(new PlanListAdapter(MyUtil.chooseDatas(mDatas, DateTime.now().getMonthOfYear(), DateTime.now().getDayOfMonth()), 1, mHandler));
                        break;
                    case "本月":
                        planTitle.setText("全部");
                        calendarArea.setVisibility(View.GONE);
                        planList.setLayoutManager(new LinearLayoutManager(mContext));
                        planList.setAdapter(new PlanListAdapter(mDatas, 0, mHandler));
                        break;
                    default:
                        break;
                }
                break;
            default:
                return;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        planSelfList = MyUtil.orderByTime(MyDatabaseUtil.queryPlanSelfAll());
        planSelfNum = planSelfList.size();
        mDatas = MyUtil.orderByTime(planNetList, planSelfList);
        switch (planTitle.getText().toString()) {
            case "全部":
                planList.setLayoutManager(new LinearLayoutManager(mContext));
                planList.setAdapter(new PlanListAdapter(mDatas, 0, mHandler));
                break;
            case "本周":
                planList.setLayoutManager(new LinearLayoutManager(mContext));
                planList.setAdapter(new PlanListAdapter(MyUtil.chooseDatas(mDatas, DateTime.now().getMonthOfYear(), DateTime.now().getDayOfMonth()), 1, mHandler));
                break;
            case "本月":
                planList.setLayoutManager(new LinearLayoutManager(mContext));
                planList.setAdapter(new PlanListAdapter(MyUtil.chooseDatas(mDatas, DateTime.now().getMonthOfYear(), DateTime.now().getDayOfMonth()), 1, mHandler));
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //当手指按下的时候
            x1 = event.getX();
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            //当手指离开的时候
            x2 = event.getX();
            if (x1 - x2 > 70 && !Objects.equals(planTitle.getText().toString(), "全部")) {
                ((CalendarAdapter) calendar.getAdapter()).changeItem(-1);
            } else if (x2 - x1 > 70 && !Objects.equals(planTitle.getText().toString(), "全部")) {
                ((CalendarAdapter) calendar.getAdapter()).changeItem(1);
            }
        }
        return true;
    }
}
