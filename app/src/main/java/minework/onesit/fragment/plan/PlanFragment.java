package minework.onesit.fragment.plan;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.util.List;
import java.util.Map;

import minework.onesit.R;
import minework.onesit.activity.MyApplication;
import minework.onesit.activity.Publish;

/**
 * Created by 无知 on 2016/11/12.
 */

public class PlanFragment extends Fragment implements View.OnClickListener {

    private static DateTime dateTime;
    private Context mContext;
    //planfragment
    private View planLayout;
    private View planTopBar;
    private Button planCalendarButton;
    private TextView planTopTitle;
    private Button planPublishButton;
    private LayoutInflater layoutInflater;
    //日历
    private CalendarWindow calendarWindow;
    private SimpleAdapter calendarAdapter;
    private GridView calendarData;
    private View calendarView;
    private View lastSelectedView;
    private TextView monthEdit;
    private Button lastMonth;
    private Button nextMonth;
    private String[] calendarFrom;
    private int[] calendarTo;

    public PlanFragment() {
        super();
        mContext = MyApplication.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        planLayout = inflater.inflate(R.layout.plan_layout, container, false);
        layoutInflater = inflater;
        init();
        return planLayout;
    }

    private void init() {
        planTopBar = planLayout.findViewById(R.id.plan_top_bar);
        planCalendarButton = (Button) planLayout.findViewById(R.id.plan_calendar_button);
        planTopTitle = (TextView) planLayout.findViewById(R.id.plan_top_title);
        planPublishButton = (Button) planLayout.findViewById(R.id.plan_publish_button);

        planCalendarButton.setOnClickListener(this);
        planPublishButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.plan_calendar_button:
                if (calendarWindow != null && calendarWindow.isShowing()) {
                    calendarWindow.dismiss();
                    planCalendarButton.setBackgroundResource(R.mipmap.calendar_w);
                    return;
                } else {
                    if (calendarWindow == null) {
                        initCalendarWindow();
                    }
                    calendarWindow.showAsDropDown(planTopBar, 30, -8);
                    planCalendarButton.setBackgroundResource(R.mipmap.calendar_g);
                }
                break;
            case R.id.plan_publish_button:
                planPublishButton.setBackgroundResource(R.mipmap.publish_g);
                startActivity(new Intent(mContext, Publish.class));
                break;
            case R.id.last_month_button:
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
                break;
            default:
                return;
        }
    }


    private void initCalendarWindow() {
        calendarView = layoutInflater.inflate(R.layout.calendar_layout, null, false);
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

    }

    private void initCalendar(List<Map<String, Object>> data) {
        calendarData = (GridView) calendarView.findViewById(R.id.calendar_data);
        calendarData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView text = (TextView) view.findViewById(R.id.date_text);
                text.setTextColor(Color.WHITE);
                text.setBackgroundResource(R.mipmap.choose_date);
                if (lastSelectedView != null && lastSelectedView != view) {
                    TextView lastText = (TextView) lastSelectedView.findViewById(R.id.date_text);
                    lastText.setTextColor(Color.parseColor("#5e9a61"));
                    lastText.setBackgroundResource(0);
                }
                lastSelectedView = view;
            }
        });
        calendarFrom = new String[]{"date_text"};
        calendarTo = new int[]{R.id.date_text};
        calendarAdapter = new SimpleAdapter(mContext, data, R.layout.date_item, calendarFrom, calendarTo);
        calendarData.setAdapter(calendarAdapter);
    }

    private void initPlanList() {

    }

    @Override
    public void onResume() {
        super.onResume();
        planPublishButton.setBackgroundResource(R.mipmap.publish_w);
    }
}
