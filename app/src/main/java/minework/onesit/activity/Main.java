package minework.onesit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import minework.onesit.R;
import minework.onesit.fragment.find.FindFragment;
import minework.onesit.fragment.mine.MineFragment;
import minework.onesit.fragment.news.NewsFragment;
import minework.onesit.fragment.plan.PlanFragment;

/**
 * Created by 无知 on 2016/11/22.
 */

public class Main extends BaseActivity implements View.OnClickListener {

    private long exitTime = 0;
    //四大板块
    private FragmentManager fragmentManager;
    private PlanFragment planFragment;
    private FindFragment findFragment;
    private NewsFragment newsFragment;
    private MineFragment mineFragment;
    // 四大板块
    private View planLayout;
    private View findLayout;
    private View newsLayout;
    private View mineLayout;
    private View planUnderline;
    private View findUnderline;
    private View newsUnderline;
    private View mineUnderline;
    //按钮
    private View planButton;
    private View findButton;
    private View newsButton;
    private View mineButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        init();
    }

    @Override
    protected void init() {
        planLayout = findViewById(R.id.plan_layout);
        planButton = findViewById(R.id.plan_button);
        planUnderline = findViewById(R.id.plan_underline);
        findLayout = findViewById(R.id.find_layout);
        findButton = findViewById(R.id.find_button);
        findUnderline = findViewById(R.id.find_underline);
        newsLayout = findViewById(R.id.news_layout);
        newsButton = findViewById(R.id.news_button);
        newsUnderline = findViewById(R.id.news_underline);
        mineLayout = findViewById(R.id.mine_layout);
        mineButton = findViewById(R.id.mine_button);
        mineUnderline = findViewById(R.id.mine_underline);

        planLayout.setOnClickListener(this);
        findLayout.setOnClickListener(this);
        newsLayout.setOnClickListener(this);
        mineLayout.setOnClickListener(this);

        fragmentManager = getSupportFragmentManager();
        setTabSelection(0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.plan_layout:
                setTabSelection(0);
                break;
            case R.id.find_layout:
                setTabSelection(1);
                break;
            case R.id.news_layout:
                setTabSelection(2);
                break;
            case R.id.mine_layout:
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
                planLayoutTab(transaction);
                break;
            case 1:
                findLayoutTab(transaction);
                break;
            case 2:
                newsLayoutTab(transaction);
                break;
            case 3:
                mineLayoutTab(transaction);
                break;
            default:
                return;
        }
        transaction.commit();
    }

    private void planLayoutTab(FragmentTransaction transaction) {
        planButton.setBackgroundResource(R.mipmap.plan_green);
        planUnderline.setVisibility(View.VISIBLE);
        if (planFragment == null) {
            planFragment = new PlanFragment();
            transaction.add(R.id.fragment_content, planFragment);
        } else {
            transaction.show(planFragment);
        }
    }

    private void findLayoutTab(FragmentTransaction transaction) {
        findButton.setBackgroundResource(R.mipmap.find_green);
        findUnderline.setVisibility(View.VISIBLE);
        if (findFragment == null) {
            findFragment = new FindFragment();
            transaction.add(R.id.fragment_content, findFragment);
        } else {
            transaction.show(findFragment);
        }
    }

    private void newsLayoutTab(FragmentTransaction transaction) {
        newsButton.setBackgroundResource(R.mipmap.news_green);
        newsUnderline.setVisibility(View.VISIBLE);
        if (newsFragment == null) {
            newsFragment = new NewsFragment();
            transaction.add(R.id.fragment_content, newsFragment);
        } else {
            transaction.show(newsFragment);
        }
    }

    private void mineLayoutTab(FragmentTransaction transaction) {
        mineButton.setBackgroundResource(R.mipmap.mine_green);
        mineUnderline.setVisibility(View.VISIBLE);
        if (mineFragment == null) {
            mineFragment = new MineFragment();
            transaction.add(R.id.fragment_content, mineFragment);
        } else {
            transaction.show(mineFragment);
        }
    }

    private void clearSelection() {

        planButton.setBackgroundResource(R.mipmap.plan_gray);
        planUnderline.setVisibility(View.INVISIBLE);
        findButton.setBackgroundResource(R.mipmap.find_gray);
        findUnderline.setVisibility(View.INVISIBLE);
        newsButton.setBackgroundResource(R.mipmap.news_gray);
        newsUnderline.setVisibility(View.INVISIBLE);
        mineButton.setBackgroundResource(R.mipmap.mine_gray);
        mineUnderline.setVisibility(View.INVISIBLE);

    }

    private void hideFragment(FragmentTransaction transaction) {
        if (planFragment != null) {
            transaction.hide(planFragment);
        }
        if (findFragment != null) {
            transaction.hide(findFragment);
        }
        if (newsFragment != null) {
            transaction.hide(newsFragment);
        }
        if (mineFragment != null) {
            transaction.hide(mineFragment);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().getBooleanExtra("updataUser", false)) {
            setTabSelection(3);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }
}
