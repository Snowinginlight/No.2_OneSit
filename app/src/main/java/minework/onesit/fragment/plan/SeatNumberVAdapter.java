package minework.onesit.fragment.plan;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import minework.onesit.R;
import minework.onesit.activity.MyApplication;

/**
 * Created by 无知 on 2017/1/9.
 */

public class SeatNumberVAdapter extends RecyclerView.Adapter<SeatNumberVAdapter.SeatNumberHolder> {

    private List<Integer> mDatas;

    public SeatNumberVAdapter(int row) {
        super();
        mDatas = new ArrayList<Integer>();
        for (int i = 1; i <= row; i++)
            mDatas.add(i);
    }

    @Override
    public SeatNumberVAdapter.SeatNumberHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SeatNumberHolder holder = new SeatNumberHolder(LayoutInflater.from(
                MyApplication.getInstance()).inflate(R.layout.seat_number_item_v, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(SeatNumberVAdapter.SeatNumberHolder holder, int position) {
        holder.seatNumber.setText(String.valueOf(mDatas.get(position)));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class SeatNumberHolder extends RecyclerView.ViewHolder {

        private TextView seatNumber;
        public SeatNumberHolder(View itemView) {
            super(itemView);
            seatNumber = (TextView)itemView.findViewById(R.id.seat_number_item_v);
        }
    }
}
