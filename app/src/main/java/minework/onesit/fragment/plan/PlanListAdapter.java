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
 * Created by 无知 on 2016/12/10.
 */

public class PlanListAdapter extends RecyclerView.Adapter<PlanListAdapter.MyViewHolder> {
    private static List<String> mDatas;
    private Handler mHandler;
    public PlanListAdapter(List<String> mDatas, Handler mHandler) {
        super();
        this.mDatas = mDatas;
        this.mHandler = mHandler;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                MyApplication.getInstance()).inflate(R.layout.plan_list_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.planListItemTitle.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView planListItemTitle;
        public MyViewHolder(View view) {
            super(view);
            planListItemTitle = (TextView)view.findViewById(R.id.plan_list_item_title);
            planListItemTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = getAdapterPosition();
                    mHandler.sendMessage(msg);
                }
            });
        }
    }
}
