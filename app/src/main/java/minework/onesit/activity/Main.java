package minework.onesit.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

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
    // 四大板块按钮
    private View planLayout;
    private View findLayout;
    private View newsLayout;
    private View mineLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        init();
    }

    @Override
    protected void init() {
        planLayout = findViewById(R.id.plan_button);
        findLayout = findViewById(R.id.find_button);
        newsLayout = findViewById(R.id.news_button);
        mineLayout = findViewById(R.id.mine_button);

        planLayout.setOnClickListener(this);
        findLayout.setOnClickListener(this);
        newsLayout.setOnClickListener(this);
        mineLayout.setOnClickListener(this);

        fragmentManager = getFragmentManager();
        setTabSelection(0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.plan_button:
                setTabSelection(0);
                break;
            case R.id.find_button:
                setTabSelection(1);
                break;
            case R.id.news_button:
                setTabSelection(2);
                break;
            case R.id.mine_button:
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
        planLayout.setBackgroundResource(R.mipmap.plan_g);
        if (planFragment == null) {
            planFragment = new PlanFragment();
            transaction.add(R.id.fragment_content, planFragment);
        } else {
            transaction.show(planFragment);
        }
    }

    private void findLayoutTab(FragmentTransaction transaction) {
        findLayout.setBackgroundResource(R.mipmap.find_g);
        if (findFragment == null) {
            findFragment = new FindFragment();
            transaction.add(R.id.fragment_content, findFragment);
        } else {
            transaction.show(findFragment);
        }
    }

    private void newsLayoutTab(FragmentTransaction transaction) {
        newsLayout.setBackgroundResource(R.mipmap.news_g);
        if (newsFragment == null) {
            newsFragment = new NewsFragment();
            transaction.add(R.id.fragment_content, newsFragment);
        } else {
            transaction.show(newsFragment);
        }
    }

    private void mineLayoutTab(FragmentTransaction transaction) {
        mineLayout.setBackgroundResource(R.mipmap.mine_g);
        if (mineFragment == null) {
            mineFragment = new MineFragment();
            transaction.add(R.id.fragment_content, mineFragment);
        } else {
            transaction.show(mineFragment);
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
}
