package minework.onesit.fragment.plan;

/**
 * Created by 无知 on 2016/11/29.
 */

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import minework.onesit.R;
import minework.onesit.activity.MyApplication;

public class JoinSeatTableAdapter extends RecyclerView.Adapter<JoinSeatTableAdapter.SeatHolder> {

    private List<Integer> mDatas;
    private Handler mHandler;
    private int hasChoose = 0;

    public JoinSeatTableAdapter(List<Integer> mDatas, Handler mHandler) {
        super();
        this.mDatas = mDatas;
        this.mHandler = mHandler;
    }

    public List<Integer> getList() {
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
                            if (hasChoose < 4) {
                                seatItem.setImageResource(R.mipmap.seat_r);
                                mDatas.set(getAdapterPosition(), 3);
                                Message message = new Message();
                                message.what = 1;
                                message.arg1 = 3;
                                message.arg2 = getAdapterPosition();
                                mHandler.sendMessage(message);
                                hasChoose++;
                            }
                            break;
                        case 3:
                            if (hasChoose > 0) {
                                seatItem.setImageResource(R.mipmap.seat_b);
                                mDatas.set(getAdapterPosition(), 1);
                                Message message1 = new Message();
                                message1.what = 1;
                                message1.arg1 = 1;
                                message1.arg2 = getAdapterPosition();
                                mHandler.sendMessage(message1);
                                hasChoose--;
                            }
                            break;
                        default:
                            return;
                    }
                }
            });

        }
    }

}