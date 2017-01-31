package minework.onesit.fragment.find;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import minework.onesit.R;
import minework.onesit.activity.MyApplication;
import minework.onesit.activity.UserShow;
import minework.onesit.util.MyRomateSQLUtil;

/**
 * Created by 无知 on 2016/12/22.
 */

public class SearchUserListAdapter extends RecyclerView.Adapter<SearchUserListAdapter.MyViewHolder> {
    private static List<Map<String, String>> mDatas;

    public SearchUserListAdapter(List<Map<String, String>> mDatas) {
        super();
        this.mDatas = mDatas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                MyApplication.getInstance()).inflate(R.layout.search_user_list_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.searchUserListItemIcon.setImageBitmap(BitmapFactory.decodeFile(mDatas.get(position).get("user_image")));
        holder.searchUserListItemName.setText(mDatas.get(position).get("user_name"));
        holder.searchUserListItemSignature.setText(mDatas.get(position).get("user_signature"));
        MyRomateSQLUtil.getBitmap(mDatas.get(position).get("user_image"),0,holder.mHandler);
        MyRomateSQLUtil.hasAttention(mDatas.get(position).get("object_id"),holder.mHandler);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView searchUserListItemIcon;
        private TextView searchUserListItemName;
        private TextView searchUserListItemSignature;
        private Button searchUserListItemAttention;
        private Handler mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                switch (message.what) {
                    case 0:
                        if ((boolean) message.obj) {
                            searchUserListItemAttention.setBackgroundResource(R.drawable.rectangle_6_r);
                            searchUserListItemAttention.setText("已关注");
                            searchUserListItemAttention.setTextColor(Color.parseColor("#f0a36f"));
                        }else {
                            searchUserListItemAttention.setBackgroundResource(R.drawable.rectangle_6_g);
                            searchUserListItemAttention.setText("+ 关注");
                            searchUserListItemAttention.setTextColor(Color.parseColor("#8bab8b"));
                        }
                        break;
                    case 1:
                        searchUserListItemAttention.setBackgroundResource(R.drawable.rectangle_6_r);
                        searchUserListItemAttention.setText("已关注");
                        searchUserListItemAttention.setTextColor(Color.parseColor("#f0a36f"));
                        break;
                    case 2:
                        searchUserListItemAttention.setBackgroundResource(R.drawable.rectangle_6_g);
                        searchUserListItemAttention.setText("+ 关注");
                        searchUserListItemAttention.setTextColor(Color.parseColor("#8bab8b"));
                        break;
                    case 6:
                        searchUserListItemIcon.setImageBitmap((Bitmap) message.obj);
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });

        public MyViewHolder(View view) {
            super(view);
            searchUserListItemIcon = (ImageView) view.findViewById(R.id.search_user_list_item_icon);
            searchUserListItemName = (TextView) view.findViewById(R.id.search_user_list_item_name);
            searchUserListItemSignature = (TextView) view.findViewById(R.id.search_user_list_item_signature);
            searchUserListItemAttention = (Button) view.findViewById(R.id.search_user_list_item_attention);
            searchUserListItemIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MyApplication.getInstance(),UserShow.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("user",mDatas.get(getAdapterPosition()).get("user_id"));
                    intent.putExtra("type","search");
                    MyApplication.getInstance().startActivity(intent);
                }
            });
            searchUserListItemAttention.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!Objects.equals(searchUserListItemAttention.getText().toString(), "已关注")) {
                        MyRomateSQLUtil.removeAttention(mDatas.get(getAdapterPosition()).get("object_id"), mHandler);
                    } else {
                        MyRomateSQLUtil.addAttention(mDatas.get(getAdapterPosition()).get("object_id"), mHandler);
                    }
                }
            });
        }
    }
}
