package minework.onesit.fragment.plan;

/**
 * Created by 无知 on 2016/11/29.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import minework.onesit.R;
import minework.onesit.activity.MyApplication;

public class SeatTableAdapter extends RecyclerView.Adapter<SeatTableAdapter.SeatHolder> {

    private List<Integer> mDatas;

    public SeatTableAdapter(int row, int column) {
        super();
        getList(row * column);
    }

    public SeatTableAdapter(List<Integer> mDatas) {
        super();
        this.mDatas = mDatas;
    }

    public List<Integer> getList(int size) {
        mDatas = new ArrayList<Integer>();
        for (int i = 0; i < size; i++)
            mDatas.add(1);
        return mDatas;
    }


    @Override
    public SeatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SeatHolder holder = new SeatHolder(LayoutInflater.from(
                MyApplication.getInstance()).inflate(R.layout.seat_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(SeatHolder holder, int position) {
        switch (mDatas.get(position)) {
            case 1:
                holder.seatItem.setImageResource(R.mipmap.seat_b);
                break;
            case 0:
                holder.seatItem.setImageResource(0);
                break;
            case 2:
                holder.seatItem.setImageResource(R.mipmap.seat_g);
                break;
        }
    }

    public List<Integer> getList() {
        return mDatas;
    }

    public void addItem() {
        mDatas.add(1);
        notifyDataSetChanged();
    }

    public void removeItem() {
        mDatas.remove(mDatas.size() - 1);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class SeatHolder extends RecyclerView.ViewHolder {

        private ImageView seatItem;

        public SeatHolder(View view) {
            super(view);
            seatItem = (ImageView) view.findViewById(R.id.seat_item);
            seatItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (mDatas.get(getAdapterPosition())) {
                        case 1:
                            seatItem.setImageResource(0);
                            mDatas.set(getAdapterPosition(), 0);
                            break;
                        case 0:
                            seatItem.setImageResource(R.mipmap.seat_b);
                            mDatas.set(getAdapterPosition(),1);
                            break;
                        case 2:
                            seatItem.setImageResource(0);
                            mDatas.set(getAdapterPosition(), 0);
                            break;
                        default:
                            return;
                    }
                }
            });
            seatItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    switch (mDatas.get(getAdapterPosition())) {
                        case 1:
                            seatItem.setImageResource(R.mipmap.seat_g);
                            mDatas.set(getAdapterPosition(),2);
                            break;
                        case 2:
                            seatItem.setImageResource(R.mipmap.seat_b);
                            mDatas.set(getAdapterPosition(),1);
                            break;
                        default:
                            return true;
                    }
                    return true;
                }
            });
        }
    }

}