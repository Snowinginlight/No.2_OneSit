package minework.onesit.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import minework.onesit.R;
import minework.onesit.database.MyDatabaseUtil;
import minework.onesit.module.*;
import minework.onesit.module.Publish;
import minework.onesit.module.User;
import minework.onesit.util.MyRomateSQLUtil;
import minework.onesit.util.MyUtil;

/**
 * Created by 无知 on 2017/1/11.
 */

public class PublishShow extends BaseActivity implements View.OnClickListener {

    private TextView publishShowTitle;
    private TextView publishShowName;
    private TextView publishShowSignature;
    private ImageView publishShowImage;
    private TextView publishShowHour;
    private TextView publishShowDay;
    private TextView publishShowStartTime;
    private TextView publishShowStopTime;
    private TextView publishShowPlace;
    private TextView publishShowMore;
    private View publishShowShareButton;
    private TextView publishShowShareNumber;
    private View publishShowCommentButton;
    private TextView publishShowCommentNumber;
    private View publishShowLikeButton;
    private TextView publishShowLikeNumber;
    private View publishShowJoinButton;
    private Button publishShowAttentionButton;
    private String object_id;
    private Map<String, String> map;
    //滚动图片
    private ViewPager imagePager;
    private PagerAdapter pagerAdapter;
    private ArrayList<View> imageList;
    private ImageView publishShowImage1, publishShowImage2, publishShowImage3;
    private ImageView imageView1,imageView2, imageView3;
    private View view1,view2,view3;
    //
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    if ((boolean) message.obj) {
                        publishShowAttentionButton.setText("已关注");
                        publishShowAttentionButton.setTextColor(Color.parseColor("#f0a36f"));
                    }
                    break;
                case 1:
                    publishShowAttentionButton.setText("已关注");
                    publishShowAttentionButton.setTextColor(Color.parseColor("#f0a36f"));
                    break;
                case 2:
                    publishShowAttentionButton.setText("关注");
                    publishShowAttentionButton.setTextColor(Color.parseColor("#8bab8b"));
                    break;
                case 6:
                    if (message.arg1 == 0) {
                        imageView1.setImageBitmap((Bitmap) message.obj);
                        pagerAdapter.notifyDataSetChanged();
                    }else if (message.arg1==1){
                        imageView2.setImageBitmap((Bitmap) message.obj);
                        pagerAdapter.notifyDataSetChanged();
                    }else if(message.arg1==2){
                        imageView3.setImageBitmap((Bitmap) message.obj);
                        pagerAdapter.notifyDataSetChanged();
                    }else if(message.arg1==4){
                        publishShowImage.setImageBitmap((Bitmap) message.obj);
                    }
                    break;
                case 4:
                    Publish publish = (Publish)message.obj;
                    MyRomateSQLUtil.getSearchUser(publish.getUser_id(), mHandler);
                    publishShowTitle.setText(publish.getPublish_title());
                    publishShowStartTime.setText(publish.getStart_time());
                    publishShowStopTime.setText(publish.getStop_time());
                    publishShowPlace.setText(publish.getPublish_place());
                    publishShowMore.setText(publish.getInformation_text());
                    //滚动图片
                    if (publish.getPictures().size() > 0) {
                        for (int i = 0; i < publish.getPictures().size(); i++) {
                            if (publish.getPictures().get(i) != null && !Objects.equals(publish.getPictures().get(i), "")) {
                                MyRomateSQLUtil.getBitmap(publish.getPictures().get(i),i,mHandler);
                            }
                        }
                    }
                    break;
                case 5:
                    User user = (User)message.obj;
                    MyRomateSQLUtil.hasAttention(user.getObjectId(), mHandler);
                    MyRomateSQLUtil.getBitmap(user.getUser_image(),4,mHandler);
                    publishShowName.setText(user.getUser_name());
                    publishShowSignature.setText(user.getUser_signature());
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
        setContentView(R.layout.publish_show_layout);
        init();
        initData();
    }

    @Override
    protected void init() {
        //其他
        publishShowImage1 = (ImageView) findViewById(R.id.publish_show_image_1);
        publishShowImage2 = (ImageView) findViewById(R.id.publish_show_image_2);
        publishShowImage3 = (ImageView) findViewById(R.id.publish_show_image_3);
        publishShowTitle = (TextView) findViewById(R.id.publish_show_title);
        publishShowName = (TextView) findViewById(R.id.publish_show_name);
        publishShowSignature = (TextView) findViewById(R.id.publish_show_signature);
        publishShowImage = (ImageView) findViewById(R.id.publish_show_image);
        publishShowHour = (TextView) findViewById(R.id.publish_show_hour);
        publishShowDay = (TextView) findViewById(R.id.publish_show_day);
        publishShowStartTime = (TextView) findViewById(R.id.publish_show_start_time);
        publishShowStopTime = (TextView) findViewById(R.id.publish_show_stop_time);
        publishShowPlace = (TextView) findViewById(R.id.publish_show_place);
        publishShowMore = (TextView) findViewById(R.id.publish_show_more);
        publishShowShareButton = findViewById(R.id.publish_show_share_button);
        publishShowShareNumber = (TextView) findViewById(R.id.publish_show_share_number);
        publishShowCommentButton = findViewById(R.id.publish_show_comment_button);
        publishShowCommentNumber = (TextView) findViewById(R.id.publish_show_comment_number);
        publishShowLikeButton = findViewById(R.id.publish_show_like_button);
        publishShowLikeNumber = (TextView) findViewById(R.id.publish_show_like_number);
        publishShowJoinButton = findViewById(R.id.publish_show_join_button);
        publishShowAttentionButton = (Button) findViewById(R.id.publish_show_attention_button);
        view1 = getLayoutInflater().inflate(R.layout.image_1, null);
        view2 = getLayoutInflater().inflate(R.layout.image_2, null);
        view3 = getLayoutInflater().inflate(R.layout.image_3, null);
        imageView1 = (ImageView)view1.findViewById(R.id.image_1);
        imageView2 = (ImageView)view2.findViewById(R.id.image_2);
        imageView3 = (ImageView)view3.findViewById(R.id.image_3);
        imagePager = (ViewPager) findViewById(R.id.publish_show_view_pager);
        imageList = new ArrayList<View>();
        imageList.add(view1);
        imageList.add(view2);
        imageList.add(view3);
        pagerAdapter = new PagerAdapter() {
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(imageList.get(position));
                return imageList.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(imageList.get(position));
            }

            @Override
            public int getCount() {
                return imageList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        };
        imagePager.setAdapter(pagerAdapter);
        imagePager.setCurrentItem(0);
        imagePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    publishShowImage1.setImageResource(R.color.white);
                    publishShowImage2.setImageResource(R.color.myGray);
                    publishShowImage3.setImageResource(R.color.myGray);
                } else if (position == 1) {
                    publishShowImage1.setImageResource(R.color.myGray);
                    publishShowImage2.setImageResource(R.color.white);
                    publishShowImage3.setImageResource(R.color.myGray);
                } else if (position == 2) {
                    publishShowImage1.setImageResource(R.color.myGray);
                    publishShowImage2.setImageResource(R.color.myGray);
                    publishShowImage3.setImageResource(R.color.white);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        publishShowImage.setOnClickListener(this);
        publishShowJoinButton.setOnClickListener(this);
        publishShowAttentionButton.setOnClickListener(this);
        publishShowShareButton.setOnClickListener(this);
        publishShowCommentButton.setOnClickListener(this);
        publishShowLikeButton.setOnClickListener(this);
    }

    private void initData() {
        if (!TextUtils.isEmpty(getIntent().getStringExtra("object_id"))) {
            switch (getIntent().getStringExtra("type")) {
                case "market":
                    map = MyDatabaseUtil.queryMarket(getIntent().getStringExtra("object_id"));
                    publishShowTitle.setText(map.get("publish_title"));
                    object_id = MyDatabaseUtil.queryUser(map.get("user_id")).get("object_id");
                    MyRomateSQLUtil.hasAttention(object_id, mHandler);
                    publishShowImage.setImageBitmap(BitmapFactory.decodeFile(map.get("user_image")));
                    publishShowName.setText(map.get("user_name"));
                    publishShowSignature.setText(MyDatabaseUtil.queryUser(map.get("user_id")).get("user_signature"));
                    publishShowStartTime.setText(map.get("start_time"));
                    publishShowStopTime.setText(map.get("stop_time"));
                    publishShowPlace.setText(map.get("publish_place"));
                    publishShowMore.setText(map.get("information_text"));
                    //滚动图片
                    if (MyUtil.stringToListString(map.get("pictures")).size() > 0) {
                        for (int i = 0; i < MyUtil.stringToListString(map.get("pictures")).size(); i++) {
                            if (MyUtil.stringToListString(map.get("pictures")).get(i) != null && !Objects.equals(MyUtil.stringToListString(map.get("pictures")).get(i), "")) {
                                MyRomateSQLUtil.getBitmap(MyUtil.stringToListString(map.get("pictures")).get(i),i,mHandler);

                            }
                        }
                    }
                    break;
                case "care":
                    map = MyDatabaseUtil.queryCare(getIntent().getStringExtra("object_id"));
                    publishShowTitle.setText(map.get("publish_title"));
                    object_id = MyDatabaseUtil.queryUser(map.get("user_id")).get("object_id");
                    MyRomateSQLUtil.hasAttention(object_id, mHandler);
                    publishShowImage.setImageBitmap(BitmapFactory.decodeFile(map.get("user_image")));
                    publishShowName.setText(map.get("user_name"));
                    publishShowSignature.setText(MyDatabaseUtil.queryUser(map.get("user_id")).get("user_signature"));
                    publishShowStartTime.setText(map.get("start_time"));
                    publishShowStopTime.setText(map.get("stop_time"));
                    publishShowPlace.setText(map.get("publish_place"));
                    publishShowMore.setText(map.get("information_text"));
                    //滚动图片
                    if (MyUtil.stringToListString(map.get("pictures")).size() > 0) {
                        for (int i = 0; i < MyUtil.stringToListString(map.get("pictures")).size(); i++) {
                            if (MyUtil.stringToListString(map.get("pictures")).get(i) != null && !Objects.equals(MyUtil.stringToListString(map.get("pictures")).get(i), "")) {
                                MyRomateSQLUtil.getBitmap(MyUtil.stringToListString(map.get("pictures")).get(i),i,mHandler);
                            }
                        }
                    }
                    break;
                case "search":
                    MyRomateSQLUtil.getSearchPublish(getIntent().getStringExtra("object_id"),mHandler);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.publish_show_attention_button:
                if (Objects.equals(publishShowAttentionButton.getText().toString(), "关注")) {
                    MyRomateSQLUtil.addAttention(object_id, mHandler);
                } else {
                    MyRomateSQLUtil.removeAttention(object_id, mHandler);
                }
                break;
            case R.id.publish_show_join_button:
                startActivity(new Intent(this,Join.class).putExtra("object_id",map.get("object_id")));
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
