package minework.onesit.fragment.find;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import minework.onesit.R;
import minework.onesit.activity.Main;
import minework.onesit.activity.MyApplication;
import minework.onesit.activity.PublishShow;
import minework.onesit.activity.UserShow;
import minework.onesit.util.MyRomateSQLUtil;
import minework.onesit.util.MyTimeUtil;

/**
 * Created by 无知 on 2016/12/22.
 */

public class FindMarketListAdapter extends RecyclerView.Adapter<FindMarketListAdapter.MyViewHolder>  {
    private static List<Map<String, String>> mDatas;

    public FindMarketListAdapter(List<Map<String, String>> mDatas) {
        super();
        this.mDatas = mDatas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                MyApplication.getInstance()).inflate(R.layout.find_market_list_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.findMarketListItemDate.setText(MyTimeUtil.startTimeToDayString(mDatas.get(position).get("start_time")));
        holder.findMarketListItemIcon.setImageBitmap(BitmapFactory.decodeFile(mDatas.get(position).get("user_image")));
        holder.findMarketListItemTitle.setText(mDatas.get(position).get("publish_title"));
        holder.findMarketListItemPlace.setText(mDatas.get(position).get("publish_place"));
        holder.findMarketListItemMore.setText(mDatas.get(position).get("information_text"));
        holder.findMarketListItemName.setText(mDatas.get(position).get("user_name"));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private CardView findMarketListItemCard;
        private ImageView findMarketListItemIcon;
        private TextView findMarketListItemName;
        private TextView findMarketListItemTitle;
        private TextView findMarketListItemMore;
        private TextView findMarketListItemPlace;
        private TextView findMarketListItemDate;

        public MyViewHolder(View view) {
            super(view);
            findMarketListItemCard = (CardView) view.findViewById(R.id.find_market_list_item_card);
            findMarketListItemIcon = (ImageView) view.findViewById(R.id.find_market_list_item_icon);
            findMarketListItemName = (TextView) view.findViewById(R.id.find_market_list_item_name);
            findMarketListItemTitle = (TextView) view.findViewById(R.id.find_market_list_item_title);
            findMarketListItemMore = (TextView) view.findViewById(R.id.find_market_list_item_more);
            findMarketListItemPlace = (TextView) view.findViewById(R.id.find_market_list_item_place);
            findMarketListItemDate = (TextView) view.findViewById(R.id.find_market_list_item_date);
            findMarketListItemCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            findMarketListItemIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MyApplication.getInstance(),UserShow.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("user",mDatas.get(getAdapterPosition()).get("user_id"));
                    intent.putExtra("type","market");
                    MyApplication.getInstance().startActivity(intent);
                }
            });
            findMarketListItemCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MyApplication.getInstance(),PublishShow.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("object_id",mDatas.get(getAdapterPosition()).get("object_id"));
                    intent.putExtra("type","market");
                    MyApplication.getInstance().startActivity(intent);
                }
            });
        }
    }
}
