package minework.onesit.fragment.plan;

import android.view.View;
import android.widget.PopupWindow;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 无知 on 2016/11/19.
 */

public class CalendarWindow extends PopupWindow {

    public CalendarWindow(View contentView, int width, int height) {
        super(contentView, width, height);
    }

    public static List<Map<String, Object>> getData(DateTime dateTime) {
        List<DateTime> list = getDateTimesByMonth(dateTime);
        List<Map<String, Object>> date_list = new ArrayList<Map<String, Object>>();
        for (DateTime day : list) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("date_text", day.getDayOfMonth());
            date_list.add(map);
        }
        return date_list;
    }

    public static List<DateTime> getDateTimesByMonth(DateTime d) {
        DateTime temp = new DateTime(d.getYear(), d.getMonthOfYear(), 1, 0, 0);

        List<DateTime> list = new ArrayList<DateTime>();
//上月(根据需求进行改变)
        int week = temp.getDayOfWeek();
        if (week != 7) {
            for (int i = 0; i < week; i++) {
                list.add(temp.plusDays(-week + i));
            }
        }
//本月
        list.add(temp);
        for (int i = 1; i <= 31; i++) {
            temp = temp.plusDays(1);
            if (temp.getMonthOfYear() != d.getMonthOfYear()) break;
            list.add(temp);
        }
//下月(根据需求进行改变)
        if (temp.getDayOfWeek() != 6) {
            list.add(temp);
            for (int i = 0; i < 7; i++) {
                temp = temp.plusDays(1);
                if (temp.getDayOfWeek() == 7) break;
                list.add(temp);
            }
        }
        return list;
    }
}
