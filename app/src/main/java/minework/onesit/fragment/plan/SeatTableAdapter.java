package minework.onesit.fragment.plan;

/**
 * Created by 无知 on 2016/11/29.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import minework.onesit.R;
import minework.onesit.activity.MyApplication;
import minework.onesit.activity.SeatTable;

public class SeatTableAdapter extends RecyclerView.Adapter<SeatTableAdapter.MyViewHolder> {

    private static List<Integer> mDatas;

    public SeatTableAdapter(List<Integer> mDatas) {
        super();
        this.mDatas = mDatas;
    }

    public List<Integer> getList(){
        return mDatas;
    }
    public void changeData(int position,int newColor){
        mDatas.set(position,newColor);
        notifyItemChanged(position);
    }

    public void addData(int position) {
        mDatas.add(position);
        notifyItemInserted(position);
    }

    public void removeData(int position) {
        if (mDatas.size() > 0) {
            mDatas.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                MyApplication.getInstance()).inflate(R.layout.seat_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.seatItem.setImageResource(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private static final int NOCOLOR = 0;
        private static final int GRAY = R.drawable.seat_gray;
        private static final int GREEN = R.drawable.seat_green;
        private ImageView seatItem;
        private int isVisible = 0;

        public MyViewHolder(View view) {
            super(view);
            seatItem = (ImageView) view.findViewById(R.id.seat_item);
            seatItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isVisible != GRAY) {
                        SeatTable.getRecyclerViewAdapter().changeData(getAdapterPosition(),GRAY);
                        isVisible = GRAY;
                    } else {
                        SeatTable.getRecyclerViewAdapter().changeData(getAdapterPosition(),NOCOLOR);
                        isVisible = NOCOLOR;
                    }
                    SeatTable.setSeatPosition(getAdapterPosition());
                }
            });
            seatItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    SeatTable.getRecyclerViewAdapter().changeData(getAdapterPosition(),GREEN);
                    SeatTable.setSeatPosition(getAdapterPosition());
                    return true;
                }
            });
        }
    }

}