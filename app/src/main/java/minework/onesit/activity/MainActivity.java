package minework.onesit.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import minework.onesit.R;
import minework.onesit.fragment.data.DataFragment;
import minework.onesit.fragment.find.FindFragment;
import minework.onesit.fragment.list.ListFragment;
import minework.onesit.fragment.user.UserFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DataFragment dataFragment;
    private FindFragment findFragment;
    private ListFragment listFragment;
    private UserFragment userFragment;

    private View dataLayout;
    private View findLayout;
    private View listLayout;
    private View userLayout;

    private ImageView dataImage;
    private ImageView findImage;
    private ImageView listImage;
    private ImageView userImage;

    private TextView dataText;
    private TextView findText;
    private TextView listText;
    private TextView userText;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        init();
        fragmentManager = getFragmentManager();
        setTabSelection(0);
    }

    private void init() {

        dataLayout = findViewById(R.id.data_layout);
        dataImage = (ImageView) findViewById(R.id.data_image);
        dataText = (TextView) findViewById(R.id.data_text);
        findLayout = findViewById(R.id.find_layout);
        findImage = (ImageView) findViewById(R.id.find_image);
        findText = (TextView) findViewById(R.id.find_text);
        listLayout = findViewById(R.id.list_layout);
        listImage = (ImageView) findViewById(R.id.list_image);
        listText = (TextView) findViewById(R.id.list_text);
        userLayout = findViewById(R.id.user_layout);
        userImage = (ImageView) findViewById(R.id.user_image);
        userText = (TextView) findViewById(R.id.user_text);

        dataLayout.setOnClickListener(this);
        findLayout.setOnClickListener(this);
        listLayout.setOnClickListener(this);
        userLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.data_layout:
                setTabSelection(0);
                break;
            case R.id.find_layout:
                setTabSelection(1);
                break;
            case R.id.list_layout:
                setTabSelection(2);
                break;
            case R.id.user_layout:
                setTabSelection(3);
                break;
            default:
                return;
        }
    }

    private void setTabSelection(int index) {
        clearSelection();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        switch (index) {
            case 0:
                dataImage.setImageResource(R.mipmap.data_image_g);
                dataText.setTextColor(Color.parseColor("#008f07"));
                dataLayout.setBackgroundResource(R.drawable.tab_image_change);
                if (dataFragment == null) {
                    dataFragment = new DataFragment();
                    transaction.add(R.id.content, dataFragment);
                } else {
                    transaction.show(dataFragment);
                }
                break;
            case 1:
                findImage.setImageResource(R.mipmap.find_image_g);
                findText.setTextColor(Color.parseColor("#008f07"));
                findLayout.setBackgroundResource(R.drawable.tab_image_change);
                if (findFragment == null) {
                    findFragment = new FindFragment();
                    transaction.add(R.id.content, findFragment);
                } else {
                    transaction.show(findFragment);
                }
                break;
            case 2:
                listImage.setImageResource(R.mipmap.list_image_g);
                listText.setTextColor(Color.parseColor("#008f07"));
                listLayout.setBackgroundResource(R.drawable.tab_image_change);
                if (listFragment == null) {
                    listFragment = new ListFragment();
                    transaction.add(R.id.content, listFragment);
                } else {
                    transaction.show(listFragment);
                }
                break;
            case 3:
                userImage.setImageResource(R.mipmap.user_image_g);
                userText.setTextColor(Color.parseColor("#008f07"));
                userLayout.setBackgroundResource(R.drawable.tab_image_change);
                if (userFragment == null) {
                    userFragment = new UserFragment();
                    transaction.add(R.id.content, userFragment);
                } else {
                    transaction.show(userFragment);
                }
                break;
            default:
                return;
        }
    }

    private void clearSelection() {
        dataImage.setImageResource(R.mipmap.data_image_w);
        dataText.setTextColor(Color.WHITE);
        dataLayout.setBackgroundResource(R.drawable.tab_image);
        findImage.setImageResource(R.mipmap.find_image_w);
        findText.setTextColor(Color.WHITE);
        findLayout.setBackgroundResource(R.drawable.tab_image);
        listImage.setImageResource(R.mipmap.list_image_w);
        listText.setTextColor(Color.WHITE);
        listLayout.setBackgroundResource(R.drawable.tab_image);
        userImage.setImageResource(R.mipmap.user_image_w);
        userText.setTextColor(Color.WHITE);
        userLayout.setBackgroundResource(R.drawable.tab_image);

    }

    private void hideFragment(FragmentTransaction transaction) {
        if (dataFragment != null) {
            transaction.hide(dataFragment);
        }
        if (findFragment != null) {
            transaction.hide(findFragment);
        }
        if (listFragment != null) {
            transaction.hide(listFragment);
        }
        if (userFragment != null) {
            transaction.hide(userFragment);
        }
    }

}
