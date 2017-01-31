package minework.onesit.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import minework.onesit.R;
import minework.onesit.fragment.find.SearchPublishtListAdapter;
import minework.onesit.fragment.find.SearchSeatListAdapter;
import minework.onesit.fragment.find.SearchUserListAdapter;
import minework.onesit.module.Publish;
import minework.onesit.module.Seat;
import minework.onesit.module.User;
import minework.onesit.util.MyRomateSQLUtil;

/**
 * Created by 无知 on 2017/1/29.
 */

public class Search extends BaseActivity {

    private EditText searchEdit;
    private ViewPager searchPager;
    private PagerAdapter pagerAdapter;
    private View searchPublishView, searchUserView, searchSeatView;
    private TextView searchPublish, searchUser, searchSeat, searchToast;
    private View searchUnderline;
    private ArrayList<View> viewList;
    private List<Map<String, String>> publishDatas, userDatas, seatDatas;
    private RecyclerView searchPublishList, searchUserList, searchSeatList;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    searchToast.setVisibility(View.GONE);
                    publishDatas = new ArrayList<>();
                    for (Publish publish : (List<minework.onesit.module.Publish>) message.obj) {
                        Map<String, String> map = new HashMap<>();
                        map.put("object_id", publish.getObjectId());
                        map.put("publish_title", publish.getPublish_title());
                        map.put("information_text", publish.getInformation_text());
                        publishDatas.add(map);
                    }
                    searchPublishList.setLayoutManager(new LinearLayoutManager(Search.this));
                    searchPublishList.setAdapter(new SearchPublishtListAdapter(publishDatas));
                    break;
                case 1:
                    searchToast.setVisibility(View.GONE);
                    userDatas = new ArrayList<>();
                    for (User user : (List<minework.onesit.module.User>) message.obj) {
                        Map<String, String> map = new HashMap<>();
                        map.put("object_id", user.getObjectId());
                        map.put("user_name", user.getUser_name());
                        map.put("user_id", user.getUser_id());
                        map.put("user_signature", user.getUser_signature());
                        map.put("user_image", user.getUser_image());
                        userDatas.add(map);
                    }
                    searchUserList.setLayoutManager(new LinearLayoutManager(Search.this));
                    searchUserList.setAdapter(new SearchUserListAdapter(userDatas));
                    break;
                case 2:
                    searchToast.setVisibility(View.GONE);
                    seatDatas = new ArrayList<>();
                    for (Seat seat : (List<minework.onesit.module.Seat>) message.obj) {
                        Map<String, String> map = new HashMap<>();
                        map.put("object_id", seat.getObjectId());
                        map.put("seat_title", seat.getSeat_title());
                        map.put("number", String.valueOf(seat.getSeat_table().length()));
                        seatDatas.add(map);
                    }
                    searchSeatList.setLayoutManager(new LinearLayoutManager(Search.this));
                    searchSeatList.setAdapter(new SearchSeatListAdapter(seatDatas));
                    break;
                default:
                    return true;
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        init();
    }

    @Override
    protected void init() {
        searchEdit = (EditText) findViewById(R.id.search_edit);
        searchPager = (ViewPager) findViewById(R.id.search_view_pager);
        searchUnderline = findViewById(R.id.search_underline);
        searchPublish = (TextView) findViewById(R.id.search_publish);
        searchUser = (TextView) findViewById(R.id.search_user);
        searchSeat = (TextView) findViewById(R.id.search_seat);
        searchToast = (TextView) findViewById(R.id.search_toast);
        searchPublishView = getLayoutInflater().inflate(R.layout.search_publish_layout, null);
        searchUserView = getLayoutInflater().inflate(R.layout.search_user_layout, null);
        searchSeatView = getLayoutInflater().inflate(R.layout.search_seat_layout, null);
        searchPublishList = (RecyclerView) searchPublishView.findViewById(R.id.search_publish_list);
        searchUserList = (RecyclerView) searchUserView.findViewById(R.id.search_user_list);
        searchSeatList = (RecyclerView) searchSeatView.findViewById(R.id.search_seat_list);

        viewList = new ArrayList<>();
        viewList.add(searchPublishView);
        viewList.add(searchUserView);
        viewList.add(searchSeatView);
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
        searchPager.setAdapter(pagerAdapter);
        searchPager.setCurrentItem(0);

        searchPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0 && positionOffset != 0 && positionOffsetPixels != 0) {
                    searchUnderline.setTranslationX(searchUnderline.getMeasuredWidth() * positionOffset);
                } else if (position == 1 && positionOffset != 0 && positionOffsetPixels != 0) {
                    searchUnderline.setTranslationX(searchUnderline.getMeasuredWidth() + searchUnderline.getMeasuredWidth() * positionOffset);
                } else if (position == 2 && positionOffset != 0 && positionOffsetPixels != 0) {
                    searchUnderline.setTranslationX(2 * searchUnderline.getMeasuredWidth() + searchUnderline.getMeasuredWidth() * positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    searchPublish.setAlpha(1.0f);
                    searchUser.setAlpha(0.7f);
                    searchSeat.setAlpha(0.7f);
                    searchToast.setVisibility(View.VISIBLE);
                    if (!TextUtils.isEmpty(searchEdit.getText()))
                        MyRomateSQLUtil.getSearchResult(searchEdit.getText().toString(), 0, mHandler);
                } else if (position == 1) {
                    searchPublish.setAlpha(0.7f);
                    searchUser.setAlpha(1.0f);
                    searchSeat.setAlpha(0.7f);
                    searchToast.setVisibility(View.VISIBLE);
                    if (!TextUtils.isEmpty(searchEdit.getText()))
                        MyRomateSQLUtil.getSearchResult(searchEdit.getText().toString(), 1, mHandler);
                } else {
                    searchPublish.setAlpha(0.7f);
                    searchUser.setAlpha(0.7f);
                    searchSeat.setAlpha(1.0f);
                    searchToast.setVisibility(View.VISIBLE);
                    if (!TextUtils.isEmpty(searchEdit.getText()))
                        MyRomateSQLUtil.getSearchResult(searchEdit.getText().toString(), 2, mHandler);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(editable)) {
                    searchPublishList.setAdapter(null);
                    searchUserList.setAdapter(null);
                    searchSeatList.setAdapter(null);
                    searchToast.setVisibility(View.VISIBLE);
                    MyRomateSQLUtil.getSearchResult(editable.toString(), searchPager.getCurrentItem(), mHandler);
                }
            }
        });
    }

}
