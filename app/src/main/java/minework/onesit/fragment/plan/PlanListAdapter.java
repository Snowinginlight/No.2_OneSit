package minework.onesit.fragment.plan;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import minework.onesit.R;
import minework.onesit.activity.MyApplication;
import minework.onesit.util.MyTimeUtil;

/**
 * Created by 无知 on 2016/12/10.
 */

public class PlanListAdapter extends RecyclerView.Adapter<PlanListAdapter.PlanHolder> {
    private static int mType = -1;
    private List<Map<String, String>> mDatas;
    private Handler mHandler;

    public PlanListAdapter(List<Map<String, String>> mDatas, int type,Handler mHandler) {
        super();
        this.mDatas = mDatas;
        mType = type;
        this.mHandler = mHandler;
    }

    @Override
    public PlanHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        PlanHolder holder = new PlanHolder(LayoutInflater.from(
                MyApplication.getInstance()).inflate(R.layout.plan_list_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final PlanHolder holder, int position) {
        holder.planListItemTitle.setText(mDatas.get(position).get("plan_title"));
        holder.planListItemTips.setText(mDatas.get(position).get("plan_tips"));
        holder.planListItemPlace.setText(mDatas.get(position).get("plan_place"));
        if (mType == 0) {
            holder.planListItemTime.setText(MyTimeUtil.startTimeToDaysString(mDatas.get(position).get("start_time")));
        } else if (mType == 1) {
                holder.planListItemTime.setText(MyTimeUtil.startTimeToHoursString(mDatas.get(position).get("start_time")));
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class PlanHolder extends RecyclerView.ViewHolder {
        private View planItem;
        private CardView planListItemCard;
        private TextView planListItemTitle;
        private TextView planListItemPlace;
        private TextView planListItemTips;
        private TextView planListItemTime;

        public PlanHolder(View view) {
            super(view);
            planItem = view.findViewById(R.id.plan_item);
            planListItemCard = (CardView) view.findViewById(R.id.plan_list_item_card);
            planListItemTitle = (TextView) view.findViewById(R.id.plan_list_item_title);
            planListItemPlace = (TextView) view.findViewById(R.id.plan_list_item_place);
            planListItemTips = (TextView) view.findViewById(R.id.plan_list_item_tips);
            planListItemTime = (TextView) view.findViewById(R.id.plan_list_item_time);
            planListItemCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Message msg = new Message();
                    msg.what = 0;
                    msg.obj = getAdapterPosition();
                    mHandler.sendMessage(msg);
                }
            });
        }
    }

}
