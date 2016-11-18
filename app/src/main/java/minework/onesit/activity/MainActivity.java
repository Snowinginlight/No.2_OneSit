package minework.onesit.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import minework.onesit.R;
import minework.onesit.fragment.plan.PlanFragment;
import minework.onesit.fragment.find.FindFragment;
import minework.onesit.fragment.news.NewsFragment;
import minework.onesit.fragment.mine.MineFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private PlanFragment planFragment;
    private FindFragment findFragment;
    private NewsFragment newsFragment;
    private MineFragment mineFragment;

    private View planLayout;
    private View findLayout;
    private View newsLayout;
    private View mineLayout;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        //隐藏状态栏、导航栏
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(option);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //初始化
        init();
        fragmentManager = getFragmentManager();
        setTabSelection(0);
    }

    private void init() {
        planLayout = findViewById(R.id.plan_layout);
        findLayout = findViewById(R.id.find_layout);
        newsLayout = findViewById(R.id.news_layout);
        mineLayout = findViewById(R.id.mine_layout);

        planLayout.setOnClickListener(this);
        findLayout.setOnClickListener(this);
        newsLayout.setOnClickListener(this);
        mineLayout.setOnClickListener(this);
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
                planLayout.setBackgroundResource(R.mipmap.plan_g);
                if (planFragment == null) {
                    planFragment = new PlanFragment();
                    transaction.add(R.id.content, planFragment);
                } else {
                    transaction.show(planFragment);
                }
                break;
            case 1:
                findLayout.setBackgroundResource(R.mipmap.find_g);
                if (findFragment == null) {
                    findFragment = new FindFragment();
                    transaction.add(R.id.content, findFragment);
                } else {
                    transaction.show(findFragment);
                }
                break;
            case 2:
                newsLayout.setBackgroundResource(R.mipmap.news_g);
                if (newsFragment == null) {
                    newsFragment = new NewsFragment();
                    transaction.add(R.id.content, newsFragment);
                } else {
                    transaction.show(newsFragment);
                }
                break;
            case 3:
                mineLayout.setBackgroundResource(R.mipmap.mine_g);
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    transaction.add(R.id.content, mineFragment);
                } else {
                    transaction.show(mineFragment);
                }
                break;
            default:
                return;
        }
    }

    private void clearSelection() {

        planLayout.setBackgroundResource(R.mipmap.plan_w);
        findLayout.setBackgroundResource(R.mipmap.find_w);
        newsLayout.setBackgroundResource(R.mipmap.news_w);
        mineLayout.setBackgroundResource(R.mipmap.mine_w);

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

}
