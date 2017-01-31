package minework.onesit.fragment.news;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import minework.onesit.R;
import minework.onesit.activity.MyApplication;
import minework.onesit.database.MyDatabaseUtil;
import minework.onesit.util.MyRomateSQLUtil;

/**
 * Created by 无知 on 2016/11/12.
 */

public class NewsFragment extends Fragment {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private View newsLayout;
    private ViewPager newsPager;
    private PagerAdapter pagerAdapter;
    private ArrayList<View> viewList;
    private View newsMessageView, newsInteractionView, newsNotificationView;
    private View newsMessageButton, newsInteractionButton, newsNotificationButton;
    private TextView newsMessage, newsInteraction, newsNotification;
    private View newsUnderline;
    private List<Map<String, String>> mDatas;
    private RecyclerView newsMessageList;
    public Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    mDatas = MyDatabaseUtil.queryNewsList(MyDatabaseUtil.queryUserExist().get("user_id"));
                    newsMessageList.setAdapter(new NewsListAdapter(mDatas, mHandler));
                    break;
                default:
                    return true;
            }
            return true;
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        newsLayout = inflater.inflate(R.layout.news_layout, container, false);
        layoutInflater = inflater;
        mContext = MyApplication.getInstance();
        init();
        initMessage();
        initInteraction();
        initNotification();
        return newsLayout;
    }

    private void init() {
        newsPager = (ViewPager) newsLayout.findViewById(R.id.news_view_pager);
        newsUnderline = newsLayout.findViewById(R.id.news_underline);
        newsMessage = (TextView) newsLayout.findViewById(R.id.news_message);
        newsInteraction = (TextView) newsLayout.findViewById(R.id.news_interaction);
        newsNotification = (TextView) newsLayout.findViewById(R.id.news_notification);
        newsMessageView = layoutInflater.inflate(R.layout.news_message_layout, null);
        newsInteractionView = layoutInflater.inflate(R.layout.news_interaction_layout, null);
        newsNotificationView = layoutInflater.inflate(R.layout.news_notification_layout, null);
        newsMessageList = (RecyclerView) newsMessageView.findViewById(R.id.news_message_list);

        viewList = new ArrayList<View>();
        viewList.add(newsMessageView);
        viewList.add(newsInteractionView);
        viewList.add(newsNotificationView);
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
        newsPager.setAdapter(pagerAdapter);
        newsPager.setCurrentItem(0);

        newsPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0 && positionOffset != 0 && positionOffsetPixels != 0) {
                    newsUnderline.setTranslationX(newsUnderline.getMeasuredWidth() * positionOffset);
                } else if (position == 1 && positionOffset != 0 && positionOffsetPixels != 0) {
                    newsUnderline.setTranslationX(newsUnderline.getMeasuredWidth() + newsUnderline.getMeasuredWidth() * positionOffset);
                } else if (position == 2 && positionOffset != 0 && positionOffsetPixels != 0) {
                    newsUnderline.setTranslationX(2 * newsUnderline.getMeasuredWidth() + newsUnderline.getMeasuredWidth() * positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    newsMessage.setAlpha(1.0f);
                    newsInteraction.setAlpha(0.7f);
                    newsNotification.setAlpha(0.7f);
                } else if (position == 1) {
                    newsUnderline.setTranslationX(newsUnderline.getWidth());
                    newsMessage.setAlpha(0.7f);
                    newsInteraction.setAlpha(1.0f);
                    newsNotification.setAlpha(0.7f);
                } else {
                    newsUnderline.setTranslationX(newsUnderline.getWidth());
                    newsMessage.setAlpha(0.7f);
                    newsInteraction.setAlpha(0.7f);
                    newsNotification.setAlpha(1.0f);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initMessage() {
        mDatas = new ArrayList<>();
        mDatas = MyDatabaseUtil.queryNewsList(MyDatabaseUtil.queryUserExist().get("user_id"));
        newsMessageList.setLayoutManager(new LinearLayoutManager(mContext));
        newsMessageList.setAdapter(new NewsListAdapter(mDatas, mHandler));
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        MyRomateSQLUtil.getNews(mHandler);
                        Thread.sleep(10000);//10秒
                    } catch (Exception e) {
                    }
                }
            }
        }).start();
    }

    private void initInteraction() {
    }

    private void initNotification() {
    }

}
