package minework.onesit.fragment.news;

import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import minework.onesit.R;
import minework.onesit.activity.MyApplication;
import minework.onesit.database.MyDatabaseUtil;
import minework.onesit.module.News;
import minework.onesit.util.MyTimeUtil;

/**
 * Created by 无知 on 2016/12/10.
 */

public class CommAdapter extends RecyclerView.Adapter<CommAdapter.MyViewHolder> {
    private static List<Map<String, String>> mDatas;

    public CommAdapter(List<Map<String, String>> mDatas) {
        super();
        this.mDatas = mDatas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                MyApplication.getInstance()).inflate(R.layout.comm_list_item, parent,
                false));
        return holder;

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (Objects.equals(mDatas.get(position).get("send_user_id"), MyDatabaseUtil.queryUserExist().get("user_id"))) {
            holder.commHerItem.setVisibility(View.GONE);
            holder.commIIcon.setImageBitmap(BitmapFactory.decodeFile(MyDatabaseUtil.queryUser(mDatas.get(position).get("send_user_id")).get("user_image")));
            holder.commIDate.setText(MyTimeUtil.convertCommTimeToString2(mDatas.get(position).get("news_date")));
            holder.commIContent.setText(mDatas.get(position).get("news_message"));
        } else {
            holder.commIItem.setVisibility(View.GONE);
            holder.commHerIcon.setImageBitmap(BitmapFactory.decodeFile(MyDatabaseUtil.queryUser(mDatas.get(position).get("send_user_id")).get("user_image")));
            holder.commHerDate.setText(MyTimeUtil.convertCommTimeToString2(mDatas.get(position).get("news_date")));
            holder.commHerContent.setText(mDatas.get(position).get("news_message"));
        }
    }

    public void addItem(Map<String,String> map){
        mDatas.add(map);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private View commHerItem, commIItem;
        private TextView commHerDate, commIDate;
        private ImageView commHerIcon, commIIcon;
        private TextView commHerContent, commIContent;

        public MyViewHolder(View view) {
            super(view);
            commHerItem = view.findViewById(R.id.comm_her_item);
            commIItem = view.findViewById(R.id.comm_i_item);
            commHerDate = (TextView) view.findViewById(R.id.comm_her_date);
            commIDate = (TextView) view.findViewById(R.id.comm_i_date);
            commHerIcon = (ImageView) view.findViewById(R.id.comm_her_icon);
            commIIcon = (ImageView) view.findViewById(R.id.comm_i_icon);
            commHerContent = (TextView) view.findViewById(R.id.comm_her_content);
            commIContent = (TextView) view.findViewById(R.id.comm_i_content);
        }
    }
}
