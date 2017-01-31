package minework.onesit.fragment.find;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import minework.onesit.R;
import minework.onesit.activity.MyApplication;
import minework.onesit.activity.PublishShow;
import minework.onesit.activity.UserShow;
import minework.onesit.util.MyTimeUtil;

/**
 * Created by 无知 on 2016/12/22.
 */

public class FindCareListAdapter extends RecyclerView.Adapter<FindCareListAdapter.MyViewHolder>  {
    private static List<Map<String, String>> mDatas;

    public FindCareListAdapter(List<Map<String, String>> mDatas) {
        super();
        this.mDatas = mDatas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                MyApplication.getInstance()).inflate(R.layout.find_care_list_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.findCareListItemDate.setText(mDatas.get(position).get("create_at"));
        holder.findCareListItemIcon.setImageBitmap(BitmapFactory.decodeFile(mDatas.get(position).get("user_image")));
        holder.findCareListItemName.setText(mDatas.get(position).get("user_name"));
        holder.findCareListItemTitle.setText(mDatas.get(position).get("publish_title"));
        holder.findCareListItemMore.setText(mDatas.get(position).get("information_text"));
        holder.findCareListItemPlace.setText(mDatas.get(position).get("publish_place"));
        holder.findCareListItemTime.setText(MyTimeUtil.changeToDuringTime(mDatas.get(position).get("start_time"),mDatas.get(position).get("stop_time")));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private CardView findCaretListItemCard;
        private ImageView findCareListItemIcon;
        private TextView findCareListItemDate;
        private TextView findCareListItemName;
        private TextView findCareListItemTitle;
        private TextView findCareListItemMore;
        private TextView findCareListItemPlace;
        private TextView findCareListItemTime;

        public MyViewHolder(View view) {
            super(view);
            findCaretListItemCard = (CardView) view.findViewById(R.id.find_care_list_item_card);
            findCareListItemIcon = (ImageView) view.findViewById(R.id.find_care_list_item_icon);
            findCareListItemName = (TextView) view.findViewById(R.id.find_care_list_item_name);
            findCareListItemDate = (TextView) view.findViewById(R.id.find_care_list_item_date);
            findCareListItemTitle = (TextView) view.findViewById(R.id.find_care_list_item_title);
            findCareListItemMore = (TextView) view.findViewById(R.id.find_care_list_item_more);
            findCareListItemPlace = (TextView) view.findViewById(R.id.find_care_list_item_place);
            findCareListItemTime = (TextView) view.findViewById(R.id.find_care_list_item_time);
            findCaretListItemCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MyApplication.getInstance(),PublishShow.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("object_id",mDatas.get(getAdapterPosition()).get("object_id"));
                    intent.putExtra("type","care");
                    MyApplication.getInstance().startActivity(intent);
                }
            });
            findCareListItemIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MyApplication.getInstance(),UserShow.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("user",mDatas.get(getAdapterPosition()).get("user_id"));
                    intent.putExtra("type","care");
                    MyApplication.getInstance().startActivity(intent);
                }
            });
        }
    }
}
