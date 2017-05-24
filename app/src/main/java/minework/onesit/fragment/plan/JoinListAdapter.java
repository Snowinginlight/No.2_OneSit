package minework.onesit.fragment.plan;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import minework.onesit.R;
import minework.onesit.activity.MyApplication;

/**
 * Created by 无知 on 2016/12/7.
 */

public class JoinListAdapter extends RecyclerView.Adapter<JoinListAdapter.MyViewHolder> {
    private static List<String> mDatas;
    public JoinListAdapter(List<String> mDatas) {
        super();
        this.mDatas = mDatas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                MyApplication.getInstance()).inflate(R.layout.join_user_list_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String[] array = mDatas.get(position).split(",");
        holder.joinUserListItemName.setText(array[0]);
        holder.joinUserListItemPosition.setText(array[1]);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView joinUserListItemName,joinUserListItemPosition;
        public MyViewHolder(View view) {
            super(view);
            joinUserListItemName = (TextView)view.findViewById(R.id.join_user_list_item_name);
            joinUserListItemPosition = (TextView)view.findViewById(R.id.join_user_list_item_position);
        }
    }
}
