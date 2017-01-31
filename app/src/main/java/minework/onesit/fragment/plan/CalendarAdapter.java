package minework.onesit.fragment.plan;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import minework.onesit.R;
import minework.onesit.activity.MyApplication;

/**
 * Created by 无知 on 2017/1/3.
 */

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarHolder> {

    private static int oldType = -10;
    private static int oldVisi = -10;
    private static int oldPosi = -10;
    private static int mType = -1;
    private List<Map<String, Integer>> mDatas;
    private Handler mHandler;

    public CalendarAdapter(DateTime dateTime, int type, Handler handler) {
        super();
        mType = type;
        if (mType == 0) {
            mDatas = getWeekDate(dateTime);
        } else {
            mDatas = getMonthDate(dateTime);
        }
        mHandler = handler;
    }

    public static List<Map<String, Integer>> getMonthDate(DateTime dateTime) {
        DateTime temp = new DateTime(dateTime.getYear(), dateTime.getMonthOfYear(), 1, 0, 0);
        List<Map<String, Integer>> list = new ArrayList<Map<String, Integer>>();
        //上月
        int week = temp.getDayOfWeek();
        if (week != 1) {
            for (int i = 1; i < week; i++) {
                Map<String, Integer> map = new HashMap<String, Integer>();
                map.put("date", temp.minusDays((week - i)).getDayOfMonth());
                map.put("type", -1);
                map.put("visibility", View.INVISIBLE);
                map.put("month", dateTime.getMonthOfYear() - 1);
                list.add(map);
            }
        }
        //本月
        for (int i = 1; i <= temp.plusMonths(1).minusDays(1).getDayOfMonth(); i++) {
            Map<String, Integer> map = new HashMap<String, Integer>();
            map.put("date", i);
            if (dateTime.getDayOfYear() == i) {
                map.put("type", 1);
                map.put("visibility", View.VISIBLE);
                map.put("month", dateTime.getMonthOfYear());
                oldPosi = list.size();
            } else {
                map.put("type", 0);
                map.put("visibility", View.INVISIBLE);
                map.put("month", dateTime.getMonthOfYear());
            }
            list.add(map);
        }
        //下月(根据需求进行改变)
        if (temp.plusMonths(1).minusDays(1).getDayOfWeek() != 7) {
            for (int i = 1; i <= (7 - temp.plusMonths(1).minusDays(1).getDayOfWeek()); i++) {
                Map<String, Integer> map = new HashMap<String, Integer>();
                map.put("date", i);
                map.put("type", -1);
                map.put("visibility", View.INVISIBLE);
                map.put("month", dateTime.getMonthOfYear() + 1);
                list.add(map);
            }
        }
        return list;
    }

    public static List<Map<String, Integer>> getMonthDate() {
        DateTime dateTime = DateTime.now();
        DateTime temp = new DateTime(dateTime.getYear(), dateTime.getMonthOfYear(), 1, 0, 0);
        List<Map<String, Integer>> list = new ArrayList<Map<String, Integer>>();
        //上月
        int week = temp.getDayOfWeek();
        if (week != 1) {
            for (int i = 1; i < week; i++) {
                Map<String, Integer> map = new HashMap<String, Integer>();
                map.put("date", temp.minusDays((week - i)).getDayOfMonth());
                map.put("type", -1);
                map.put("visibility", View.INVISIBLE);
                map.put("month", dateTime.getMonthOfYear() - 1);
                list.add(map);
            }
        }
        //本月
        for (int i = 1; i <= temp.plusMonths(1).minusDays(1).getDayOfMonth(); i++) {
            Map<String, Integer> map = new HashMap<String, Integer>();
            map.put("date", i);
            map.put("type", 0);
            map.put("visibility", View.INVISIBLE);
            map.put("month", dateTime.getMonthOfYear());
            list.add(map);
        }
        //下月(根据需求进行改变)
        if (temp.plusMonths(1).minusDays(1).getDayOfWeek() != 7) {
            for (int i = 1; i <= (7 - temp.plusMonths(1).minusDays(1).getDayOfWeek()); i++) {
                Map<String, Integer> map = new HashMap<String, Integer>();
                map.put("date", i);
                map.put("type", -1);
                map.put("visibility", View.INVISIBLE);
                map.put("month", dateTime.getMonthOfYear() + 1);
                list.add(map);
            }
        }
        return list;
    }

    public static List<Map<String, Integer>> getWeekDate(DateTime dateTime) {
        List<Map<String, Integer>> list = new ArrayList<Map<String, Integer>>();
        //本周
        for (int i = 1; i <= 7; i++) {
            Map<String, Integer> map = new HashMap<String, Integer>();
            map.put("date", dateTime.getDayOfMonth() - (dateTime.getDayOfWeek() - i));
            if (dateTime.getDayOfWeek() == i) {
                map.put("type", 1);
                map.put("visibility", View.VISIBLE);
                map.put("month", dateTime.getMonthOfYear());
                oldPosi = list.size();
            } else {
                map.put("type", 0);
                map.put("visibility", View.INVISIBLE);
                map.put("month", dateTime.getMonthOfYear());
            }
            list.add(map);
        }
        return list;
    }

    public static List<Map<String, Integer>> getWeekDate() {
        DateTime dateTime = DateTime.now();
        List<Map<String, Integer>> list = new ArrayList<Map<String, Integer>>();
        //本周
        for (int i = 1; i <= 7; i++) {
            Map<String, Integer> map = new HashMap<String, Integer>();
            map.put("date", dateTime.getDayOfMonth() - (dateTime.getDayOfWeek() - i));
            map.put("type", 0);
            map.put("visibility", View.INVISIBLE);
            map.put("month", dateTime.getMonthOfYear());
            list.add(map);
        }
        return list;
    }

    @Override
    public CalendarHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CalendarHolder holder = new CalendarHolder(LayoutInflater.from(
                MyApplication.getInstance()).inflate(R.layout.date_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(CalendarHolder holder, int position) {
        holder.dateText.setText(String.valueOf(mDatas.get(position).get("date")));
        switch (mDatas.get(position).get("type")) {
            case -1:
                holder.dateText.setTextColor(Color.parseColor("#767676"));
                break;
            case 0:
                holder.dateText.setTextColor(Color.WHITE);
                break;
            case 1:
                holder.dateText.setTextColor(Color.parseColor("#8bab8b"));
                break;
        }
        switch (mDatas.get(position).get("visibility")) {
            case View.INVISIBLE:
                holder.dateCircle.setVisibility(View.INVISIBLE);
                break;
            case View.VISIBLE:
                holder.dateCircle.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void changeItem(int direction) {
        switch (direction) {
            case -1:
                if (oldPosi > 0) {
                    if (mType == 0) {
                        mDatas = getWeekDate();
                    } else {
                        mDatas = getMonthDate();
                    }
                    mDatas.get(oldPosi - 1).put("visibility", View.VISIBLE);
                    mDatas.get(oldPosi - 1).put("type", 1);
                    oldPosi = (oldPosi - 1);
                    Message msg = new Message();
                    msg.what = 1;
                    msg.arg1 = mDatas.get(oldPosi).get("month");
                    msg.arg2 = mDatas.get(oldPosi).get("date");
                    mHandler.sendMessage(msg);
                    notifyDataSetChanged();
                }
                break;
            case 1:
                if (oldPosi < (mDatas.size() - 1)) {
                    if (mType == 0) {
                        mDatas = getWeekDate();
                    } else {
                        mDatas = getMonthDate();
                    }
                    mDatas.get(oldPosi + 1).put("visibility", View.VISIBLE);
                    mDatas.get(oldPosi + 1).put("type", 1);
                    oldPosi = (oldPosi + 1);
                    Message msg = new Message();
                    msg.what = 1;
                    msg.arg1 = mDatas.get(oldPosi).get("month");
                    msg.arg2 = mDatas.get(oldPosi).get("date");
                    mHandler.sendMessage(msg);
                    notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
    }

    class CalendarHolder extends RecyclerView.ViewHolder {

        private ImageView dateCircle;
        private TextView dateText;
        private View dateButton;

        public CalendarHolder(final View itemView) {
            super(itemView);
            dateCircle = (ImageView) itemView.findViewById(R.id.date_circle);
            dateText = (TextView) itemView.findViewById(R.id.date_text);
            dateButton = itemView.findViewById(R.id.date_button);
            dateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mType == 0) {
                        mDatas = getWeekDate();
                    } else {
                        mDatas = getMonthDate();
                    }
                    mDatas.get(getAdapterPosition()).put("visibility", View.VISIBLE);
                    mDatas.get(getAdapterPosition()).put("type", 1);
                    oldPosi = getAdapterPosition();
                    Message msg = new Message();
                    msg.what = 1;
                    msg.arg1 = mDatas.get(getAdapterPosition()).get("month");
                    msg.arg2 = mDatas.get(getAdapterPosition()).get("date");
                    mHandler.sendMessage(msg);
                    notifyDataSetChanged();
                }
            });
        }
    }
}
