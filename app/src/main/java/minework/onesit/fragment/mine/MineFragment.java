package minework.onesit.fragment.mine;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Map;

import minework.onesit.R;
import minework.onesit.activity.ActivityManagerList;
import minework.onesit.activity.Login;
import minework.onesit.activity.MyApplication;
import minework.onesit.activity.User;
import minework.onesit.database.MyDatabaseUtil;
import minework.onesit.util.MyUtil;

/**
 * Created by 无知 on 2016/11/12.
 */

public class MineFragment extends Fragment implements View.OnClickListener {

    private static Bitmap bitmap = null;
    private static Map<String,String> user = null;
    private Context mContext;
    private LayoutInflater layoutInflater;
    private View mineLayout;
    private ImageView mineImage;
    private TextView mineName;
    private TextView mineSignature;
    private View minePublishButton;
    private View mineCareButton;
    private View mineBookButton;
    private TextView minePublishText;
    private TextView mineCareText;
    private TextView mineBookText;
    private View mineActivity;
    private View mineTicket;
    private View mineCollect;
    private View mineSet;
    private View mineLogOff;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mineLayout = inflater.inflate(R.layout.mine_layout, container, false);
        layoutInflater = inflater;
        mContext = MyApplication.getInstance();
        init();
        return mineLayout;
    }

    private void init() {
        mineImage = (ImageView)mineLayout.findViewById(R.id.mine_image);
        mineName = (TextView)mineLayout.findViewById(R.id.mine_name);
        mineSignature = (TextView)mineLayout.findViewById(R.id.mine_signature);
        minePublishButton = mineLayout.findViewById(R.id.mine_publish_button);
        mineCareButton = mineLayout.findViewById(R.id.mine_care_button);
        mineBookButton = mineLayout.findViewById(R.id.mine_book_button);
        minePublishText = (TextView) mineLayout.findViewById(R.id.mine_publish_text);
        mineCareText = (TextView)mineLayout.findViewById(R.id.mine_care_text);
        mineBookText = (TextView)mineLayout.findViewById(R.id.mine_book_text);
        mineActivity = mineLayout.findViewById(R.id.mine_activity);
        mineTicket = mineLayout.findViewById(R.id.mine_ticket);
        mineCollect = mineLayout.findViewById(R.id.mine_collect);
        mineSet = mineLayout.findViewById(R.id.mine_set);
        mineLogOff = mineLayout.findViewById(R.id.mine_logoff);

        mineImage.setOnClickListener(this);
        mineActivity.setOnClickListener(this);
        mineTicket.setOnClickListener(this);
        mineCollect.setOnClickListener(this);
        mineSet.setOnClickListener(this);
        mineLogOff.setOnClickListener(this);
    }

    private void initData() {
        user = MyDatabaseUtil.queryUserExist();
        mineName.setText(user.get("user_name"));
        mineSignature.setText(user.get("user_signature"));
        if (!user.get("user_image").isEmpty()) {;
            mineImage.setImageBitmap(BitmapFactory.decodeFile(user.get("user_image")));
        } else {
            mineImage.setImageResource(R.mipmap.mine_icon);
        }
        mineCareText.setText(String.valueOf(MyUtil.getNumber(user.get("user_care"))));
        minePublishText.setText(String.valueOf(MyUtil.getNumber(user.get("user_publish"))));
        mineBookText.setText(String.valueOf(MyUtil.getNumber(user.get("user_book"))));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_image:
                startActivity(new Intent(mContext,User.class));
                break;
            case R.id.mine_publish_button:
                ObjectAnimator animator0 = ObjectAnimator.ofFloat(minePublishButton, "alpha", 1f, 0.5f, 1f);
                animator0.setDuration(300);
                animator0.start();
                break;
            case R.id.mine_care_button:
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(mineCareButton, "alpha", 1f, 0.5f, 1f);
                animator1.setDuration(300);
                animator1.start();
                break;
            case R.id.mine_book_button:
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(mineBookButton, "alpha", 1f, 0.5f, 1f);
                animator2.setDuration(300);
                animator2.start();
                break;
            case R.id.mine_activity://活动管理
                ObjectAnimator animator3 = ObjectAnimator.ofFloat(mineActivity, "alpha", 1f, 0.5f, 1f);
                animator3.setDuration(300);
                animator3.start();
                startActivity(new Intent(mContext,ActivityManagerList.class));
                break;
            case R.id.mine_collect:
                ObjectAnimator animator4 = ObjectAnimator.ofFloat(mineCollect, "alpha", 1f, 0.5f, 1f);
                animator4.setDuration(300);
                animator4.start();
                break;
            case R.id.mine_ticket:
                ObjectAnimator animator5 = ObjectAnimator.ofFloat(mineTicket, "alpha", 1f, 0.5f, 1f);
                animator5.setDuration(300);
                animator5.start();
                break;
            case R.id.mine_set:
                ObjectAnimator animator6 = ObjectAnimator.ofFloat(mineSet, "alpha", 1f, 0.5f, 1f);
                animator6.setDuration(300);
                animator6.start();
                break;
            case R.id.mine_logoff:
                ObjectAnimator animator9 = ObjectAnimator.ofFloat(mineLogOff, "alpha", 1f, 0.5f, 1f);
                animator9.setDuration(300);
                animator9.start();
                MyDatabaseUtil.deleteUser();
                startActivity(new Intent(mContext, Login.class));
                onDestroy();
                break;
            default:
                return;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }
}
