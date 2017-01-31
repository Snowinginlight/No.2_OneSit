package minework.onesit.fragment.find;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import minework.onesit.R;
import minework.onesit.activity.MyApplication;
import minework.onesit.activity.Search;
import minework.onesit.database.MyDatabaseUtil;
import minework.onesit.util.MyUtil;

/**
 * Created by 无知 on 2016/11/12.
 */

public class FindFragment extends Fragment implements View.OnClickListener {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private View findLayout;
    private ViewPager findPager;
    private PagerAdapter pagerAdapter;
    private ArrayList<View> viewList;
    private View findMarketButton, findCareButton;
    private CardView findSearchButton;
    private TextView findMarketText, findCareText;
    private View findMarketView, findCareView;
    //广场
    private RecyclerView findMarketList,findCareList;
    private List<Map<String, String>> mMarketDatas;
    private List<Map<String, String>> mCareDatas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        findLayout = inflater.inflate(R.layout.find_layout, container, false);
        layoutInflater = inflater;
        mContext = MyApplication.getInstance();
        init();
        initMarketList();
        initCareList();
        return findLayout;
    }

    private void init() {

        findPager = (ViewPager) findLayout.findViewById(R.id.find_view_pager);
        findMarketButton = findLayout.findViewById(R.id.find_market_button);
        findMarketText = (TextView) findLayout.findViewById(R.id.find_market_text);
        findCareButton = findLayout.findViewById(R.id.find_care_button);
        findCareText = (TextView) findLayout.findViewById(R.id.find_care_text);
        findMarketView = layoutInflater.inflate(R.layout.find_market_layout, null);
        findCareView = layoutInflater.inflate(R.layout.find_care_layout, null);
        findSearchButton = (CardView)findLayout.findViewById(R.id.find_search_button);

        viewList = new ArrayList<View>();
        viewList.add(findMarketView);
        viewList.add(findCareView);
        pagerAdapter = new PagerAdapter() {
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(viewList.get(position));
                return viewList.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(viewList.get(position));
            }

            @Override
            public int getCount() {
                return viewList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        };
        findPager.setAdapter(pagerAdapter);
        findPager.setCurrentItem(0);

        findMarketButton.setOnClickListener(this);
        findCareButton.setOnClickListener(this);
        findSearchButton.setOnClickListener(this);

        findPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    findMarketText.setTextColor(Color.parseColor("#ced1d6"));
                    findMarketButton.setBackground(null);
                    findCareText.setTextColor(Color.parseColor("#8bab8b"));
                    findCareButton.setBackgroundResource(R.drawable.rectangle_8_r);
                } else {
                    findMarketText.setTextColor(Color.parseColor("#8bab8b"));
                    findMarketButton.setBackgroundResource(R.drawable.rectangle_8_l);
                    findCareText.setTextColor(Color.parseColor("#ced1d6"));
                    findCareButton.setBackground(null);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.find_market_button:
                findPager.setCurrentItem(0);
                findMarketText.setTextColor(Color.parseColor("#8bab8b"));
                findMarketButton.setBackgroundResource(R.drawable.rectangle_8_l);
                findCareText.setTextColor(Color.parseColor("#ced1d6"));
                findCareButton.setBackground(null);
                break;
            case R.id.find_care_button:
                findPager.setCurrentItem(1);
                findMarketText.setTextColor(Color.parseColor("#ced1d6"));
                findMarketButton.setBackground(null);
                findCareText.setTextColor(Color.parseColor("#8bab8b"));
                findCareButton.setBackgroundResource(R.drawable.rectangle_8_r);
                break;
            case R.id.find_search_button:
                startActivity(new Intent(mContext, Search.class));
                break;
            default:
                return;
        }
    }

    private void initMarketList() {
        mMarketDatas = new ArrayList<>();
        mMarketDatas = MyUtil.orderByTime(MyDatabaseUtil.queryMarket());
        findMarketList = (RecyclerView) findMarketView.findViewById(R.id.find_market_list);
        findMarketList.setLayoutManager(new LinearLayoutManager(mContext));
        findMarketList.setAdapter(new FindMarketListAdapter(mMarketDatas));
    }

    private void initCareList() {
        mCareDatas = new ArrayList<>();
        mCareDatas = MyUtil.orderByTime2(MyDatabaseUtil.queryCare());
        findCareList = (RecyclerView) findCareView.findViewById(R.id.find_care_list);
        findCareList.setLayoutManager(new LinearLayoutManager(mContext));
        findCareList.setAdapter(new FindCareListAdapter(mCareDatas));
    }
}
