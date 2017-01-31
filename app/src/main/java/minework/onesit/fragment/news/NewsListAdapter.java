package minework.onesit.fragment.news;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.List;
import java.util.Map;

import minework.onesit.R;
import minework.onesit.activity.Communicate;
import minework.onesit.activity.MyApplication;
import minework.onesit.activity.UserShow;
import minework.onesit.database.MyDatabaseUtil;
import minework.onesit.util.MyTimeUtil;

/**
 * Created by 无知 on 2016/12/10.
 */

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.MyViewHolder> {
    private static List<Map<String,String>> mDatas;
    private Handler mHandler;

    public NewsListAdapter(List<Map<String,String>> mDatas, Handler mHandler) {
        super();
        this.mDatas = mDatas;
        this.mHandler = mHandler;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                MyApplication.getInstance()).inflate(R.layout.news_list_item, parent,
                false));
        return holder;

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.newsListItemIcon.setImageBitmap(BitmapFactory.decodeFile(MyDatabaseUtil.queryUser(mDatas.get(position).get("user_id")).get("user_image")));
        holder.newsListItemName.setText(MyDatabaseUtil.queryUser(mDatas.get(position).get("user_id")).get("user_name"));
        holder.newsListItemContent.setText(mDatas.get(position).get("news_message"));
        holder.newsListItemDate.setText(MyTimeUtil.convertNewsTimeToString(DateTime.parse(mDatas.get(position).get("news_date"), DateTimeFormat.forPattern("yyyy年MM月dd日HH:mm")).toDate()));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView newsListItemIcon;
        private TextView newsListItemDate;
        private TextView newsListItemName;
        private TextView newsListItemContent;

        public MyViewHolder(View view) {
            super(view);
            newsListItemIcon = (ImageView) view.findViewById(R.id.news_list_item_icon);
            newsListItemName = (TextView) view.findViewById(R.id.news_list_item_name);
            newsListItemContent = (TextView) view.findViewById(R.id.news_list_item_content);
            newsListItemDate = (TextView)view.findViewById(R.id.news_list_item_date);
            newsListItemContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MyApplication.getInstance(),Communicate.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("her_user_id",mDatas.get(getAdapterPosition()).get("user_id"));
                    MyApplication.getInstance().startActivity(intent);
                }
            });
            newsListItemName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MyApplication.getInstance(),Communicate.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("her_user_id",mDatas.get(getAdapterPosition()).get("user_id"));
                    MyApplication.getInstance().startActivity(intent);
                }
            });
            newsListItemIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MyApplication.getInstance(),UserShow.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("user",mDatas.get(getAdapterPosition()).get("user_id"));
                    intent.putExtra("type","news");
                    MyApplication.getInstance().startActivity(intent);
                }
            });
        }
    }
}
