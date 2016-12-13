package minework.onesit.fragment.plan;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import minework.onesit.R;
import minework.onesit.activity.MyApplication;
import minework.onesit.activity.PlanCreate;
import minework.onesit.activity.Publish;

/**
 * Created by 无知 on 2016/11/12.
 */

public class PlanFragment extends Fragment implements View.OnClickListener {

    private static int planNumber = 0;
    private Context mContext;
    //planfragment
    private View planLayout;
    private View planTopBar;
    private Button planCreateButton;
    private TextView planTopTitle;
    private Button planPublishButton;
    private LayoutInflater layoutInflater;
    /*//日历
    private static DateTime dateTime;
    private CalendarWindow calendarWindow;
    private SimpleAdapter calendarAdapter;
    private GridView calendarData;
    private View calendarView;
    private View lastSelectedView;
    private TextView monthEdit;
    private Button lastMonth;
    private Button nextMonth;
    private String[] calendarFrom;
    private int[] calendarTo;*/
    //计划条
    private RecyclerView planList;
    private PlanListAdapter planListAdapter;
    private List<String> mDatas;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    if ((boolean) message.obj) {
                        SharedPreferences localPlanNumber = mContext.getSharedPreferences("local_plan_number",
                            Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = localPlanNumber.edit();
                        SharedPreferences plan = mContext.getSharedPreferences("local_plan_" + planNumber,
                                Activity.MODE_PRIVATE);
                        mDatas.add(plan.getString("plan_title",""));
                        editor.putInt("number",planNumber);
                        editor.commit();
                        planList.getAdapter().notifyDataSetChanged();
                    }
                    break;
                default:
                    return true;
            }
            return true;
        }
    });

    public PlanFragment() {
        super();
        mContext = MyApplication.getInstance();
    }

    public static void setPlanNumber(int newPlanNumber) {
        planNumber = newPlanNumber;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        planLayout = inflater.inflate(R.layout.plan_layout, container, false);
        layoutInflater = inflater;
        initData();
        init();
        return planLayout;
    }

    private void initData() {
        mDatas = new ArrayList<String>();
        SharedPreferences localPlanNumber = mContext.getSharedPreferences("local_plan_number",
                Activity.MODE_PRIVATE);
        planNumber = localPlanNumber.getInt("number",0);
        for (int i = 1; i < (planNumber+1); i++){
            SharedPreferences plan = mContext.getSharedPreferences("local_plan_" + planNumber,
                    Activity.MODE_PRIVATE);
            mDatas.add(plan.getString("plan_title",""));
        }
    }

    private void init() {
        planTopBar = planLayout.findViewById(R.id.plan_top_bar);
        planCreateButton = (Button) planLayout.findViewById(R.id.plan_create_button);
        planTopTitle = (TextView) planLayout.findViewById(R.id.plan_top_title);
        planPublishButton = (Button) planLayout.findViewById(R.id.plan_publish_button);
        planList = (RecyclerView) planLayout.findViewById(R.id.plan_list);
        planList.setLayoutManager(new LinearLayoutManager(mContext));
        planList.setAdapter(planListAdapter = new PlanListAdapter(mDatas, mHandler));

        planCreateButton.setOnClickListener(this);
        planPublishButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            /*case R.id.plan_calendar_button:
                AnimationDrawable anim0 = new AnimationDrawable();
                anim0.addFrame(mContext.getDrawable(R.mipmap.create_icon_c),200);
                anim0.addFrame(mContext.getDrawable(R.mipmap.create_icon),200);
                anim0.setOneShot(true);
                planCalendarButton.setBackground(anim0);
                anim0.start();
                if (calendarWindow != null && calendarWindow.isShowing()) {
                    calendarWindow.dismiss();
                    return;
                } else {
                    if (calendarWindow == null) {
                        initCalendarWindow();
                    }
                    calendarWindow.showAsDropDown(planTopBar, 30, -8);
                }
                break;*/
            case R.id.plan_create_button:
                AnimationDrawable animCreate = new AnimationDrawable();
                animCreate.addFrame(mContext.getDrawable(R.mipmap.create_icon_c), 200);
                animCreate.addFrame(mContext.getDrawable(R.mipmap.create_icon), 200);
                animCreate.setOneShot(true);
                planCreateButton.setBackground(animCreate);
                animCreate.start();
                startActivity(new Intent(mContext, PlanCreate.class));
                PlanCreate.setHandler(mHandler);
                break;
            case R.id.plan_publish_button:
                AnimationDrawable animPublish = new AnimationDrawable();
                animPublish.addFrame(mContext.getDrawable(R.mipmap.publish_icon_c), 200);
                animPublish.addFrame(mContext.getDrawable(R.mipmap.publish_icon), 200);
                animPublish.setOneShot(true);
                planPublishButton.setBackground(animPublish);
                animPublish.start();
                startActivity(new Intent(mContext, Publish.class));
                break;
            /*case R.id.last_month_button:
                dateTime = dateTime.minusMonths(1);
                monthEdit.setText(String.valueOf(dateTime.getMonthOfYear()));
                calendarAdapter = new SimpleAdapter(mContext, calendarWindow.getData(dateTime), R.layout.date_item, calendarFrom, calendarTo);
                calendarData.setAdapter(calendarAdapter);
                break;
            case R.id.next_month_button:
                dateTime = dateTime.plusMonths(1);
                monthEdit.setText(String.valueOf(dateTime.getMonthOfYear()));
                calendarAdapter = new SimpleAdapter(mContext, calendarWindow.getData(dateTime), R.layout.date_item, calendarFrom, calendarTo);
                calendarData.setAdapter(calendarAdapter);
                break;*/
            default:
                return;
        }
    }

    private void initCalendarWindow() {
       /* calendarView = layoutInflater.inflate(R.layout.calendar_layout, null, false);
        dateTime = DateTime.now();
        initCalendar(calendarWindow.getData(dateTime));
        calendarWindow = new CalendarWindow(calendarView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        calendarWindow.setAnimationStyle(R.style.AnimationFade);
        calendarWindow.setOutsideTouchable(true);
        monthEdit = (TextView) calendarView.findViewById(R.id.month_edit);
        monthEdit.setText(String.valueOf(dateTime.getMonthOfYear()));
        lastMonth = (Button) calendarView.findViewById(R.id.last_month_button);
        lastMonth.setOnClickListener(this);
        nextMonth = (Button) calendarView.findViewById(R.id.next_month_button);
        nextMonth.setOnClickListener(this);
*/
    }

    private void initCalendar(List<Map<String, Object>> data) {
        /*calendarData = (GridView) calendarView.findViewById(R.id.calendar_data);
        calendarData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView text = (TextView) view.findViewById(R.id.date_text);
                text.setTextColor(Color.parseColor("#5e9a61"));
                text.setBackgroundResource(R.mipmap.choose_date);
                if (lastSelectedView != null && lastSelectedView != view) {
                    TextView lastText = (TextView) lastSelectedView.findViewById(R.id.date_text);
                    lastText.setTextColor(Color.WHITE);
                    lastText.setBackgroundResource(0);
                }
                lastSelectedView = view;
            }
        });
        calendarFrom = new String[]{"date_text"};
        calendarTo = new int[]{R.id.date_text};
        calendarAdapter = new SimpleAdapter(mContext, data, R.layout.date_item, calendarFrom, calendarTo);
        calendarData.setAdapter(calendarAdapter);
    */
    }
}
