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

public class SearchPublishtListAdapter extends RecyclerView.Adapter<SearchPublishtListAdapter.MyViewHolder>  {
    private static List<Map<String, String>> mDatas;

    public SearchPublishtListAdapter(List<Map<String, String>> mDatas) {
        super();
        this.mDatas = mDatas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                MyApplication.getInstance()).inflate(R.layout.search_publish_list_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.searchPublishListItemTitle.setText(mDatas.get(position).get("publish_title"));
        holder.searchPublishListItemContent.setText(mDatas.get(position).get("information_text"));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView searchPublishListItemTitle;
        private TextView searchPublishListItemContent;

        public MyViewHolder(View view) {
            super(view);
            searchPublishListItemTitle = (TextView)view.findViewById(R.id.search_publish_list_item_title);
            searchPublishListItemContent = (TextView)view.findViewById(R.id.search_publish_list_item_content);
            searchPublishListItemTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MyApplication.getInstance(),PublishShow.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("object_id",mDatas.get(getAdapterPosition()).get("object_id"));
                    intent.putExtra("type","search");
                    MyApplication.getInstance().startActivity(intent);
                }
            });
            searchPublishListItemContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MyApplication.getInstance(),PublishShow.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("object_id",mDatas.get(getAdapterPosition()).get("object_id"));
                    intent.putExtra("type","search");
                    MyApplication.getInstance().startActivity(intent);
                }
            });
        }
    }
}
