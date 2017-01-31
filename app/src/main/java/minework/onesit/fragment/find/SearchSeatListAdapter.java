package minework.onesit.fragment.find;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import minework.onesit.R;
import minework.onesit.activity.MyApplication;
import minework.onesit.activity.PublishShow;
import minework.onesit.util.MyRomateSQLUtil;

/**
 * Created by 无知 on 2016/12/22.
 */

public class SearchSeatListAdapter extends RecyclerView.Adapter<SearchSeatListAdapter.MyViewHolder>  {
    private static List<Map<String, String>> mDatas;

    public SearchSeatListAdapter(List<Map<String, String>> mDatas) {
        super();
        this.mDatas = mDatas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                MyApplication.getInstance()).inflate(R.layout.search_seat_list_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.searchSeatListItemTitle.setText(mDatas.get(position).get("seat_title"));
        holder.searchSeatListItemNumber.setText(mDatas.get(position).get("number"));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView searchSeatListItemTitle;
        private TextView searchSeatListItemNumber;

        public MyViewHolder(View view) {
            super(view);
            searchSeatListItemTitle = (TextView)view.findViewById(R.id.search_seat_list_item_title);
            searchSeatListItemNumber = (TextView)view.findViewById(R.id.search_seat_list_item_number);
            searchSeatListItemTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
