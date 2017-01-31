package minework.onesit.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import net.qiujuer.genius.blur.StackBlur;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import minework.onesit.R;
import minework.onesit.database.MyDatabaseUtil;
import minework.onesit.module.Publish;
import minework.onesit.module.User;
import minework.onesit.util.MyRomateSQLUtil;
import minework.onesit.util.MyTimeUtil;
import minework.onesit.util.MyUtil;

/**
 * Created by 无知 on 2016/12/27.
 */

public class UserShow extends BaseActivity implements View.OnClickListener {

    private Map<String, String> user = new HashMap<String, String>();
    private Button userShowBack;
    private ImageView userShowImageBg;
    private ImageView userShowImage;
    private TextView userShowName;
    private TextView userShowSignature;
    private View userShowPublishButton;
    private TextView userShowPublishText;
    private View userShowCareButton;
    private TextView userShowCareText;
    private View userShowBookButton;
    private TextView userShowBookText;
    private ImageView userShowNewImage;
    private TextView userShowNewName;
    private TextView userShowNewDate;
    private TextView userShowNewTitle;
    private TextView userShowNewContent;
    private TextView userShowNewPlace;
    private TextView userShowNewTime;
    private View userShowNews, userShowAttention;
    private ImageView userShowAttentionBg;
    private TextView userShowAttentionText;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    if ((boolean) message.obj) {
                        userShowAttentionBg.setImageResource(R.mipmap.attention_r);
                        userShowAttentionText.setText("已关注");
                    } else {
                        userShowAttentionBg.setImageResource(R.mipmap.attention);
                        userShowAttentionText.setText("关注");
                    }
                    break;
                case 1:
                    userShowAttentionBg.setImageResource(R.mipmap.attention_r);
                    userShowAttentionText.setText("已关注");
                    break;
                case 2:
                    userShowAttentionBg.setImageResource(R.mipmap.attention);
                    userShowAttentionText.setText("关注");
                    break;
                case 3:
                    userShowNewDate.setText(new DateTime(((Publish) message.obj).getCreatedAt()).toString(DateTimeFormat.forPattern("yyyy.MM.dd HH:mm")));
                    userShowNewTitle.setText(((Publish) message.obj).getPublish_title());
                    userShowNewContent.setText(((Publish) message.obj).getInformation_text());
                    userShowNewPlace.setText(((Publish) message.obj).getPublish_place());
                    userShowNewTime.setText(MyTimeUtil.changeToDuringTime(((Publish) message.obj).getStart_time(), ((Publish) message.obj).getStop_time()));
                    break;
                case 5:
                    User user = (User) message.obj;
                    userShowName.setText(user.getUser_name());
                    userShowSignature.setText(user.getUser_signature());
                    userShowCareText.setText(String.valueOf(user.getUser_care().size()));
                    userShowPublishText.setText(String.valueOf(user.getUser_publish().size()));
                    userShowBookText.setText(String.valueOf(user.getUser_book().size()));
                    userShowNewName.setText(user.getUser_name());
                    MyRomateSQLUtil.hasAttention(user.getObjectId(), mHandler);
                    MyRomateSQLUtil.getLastPublish(user.getUser_id(), mHandler);
                    MyRomateSQLUtil.getBitmap(user.getUser_image(),4,mHandler);
                    break;
                case 6:
                    userShowImage.setImageBitmap((Bitmap) message.obj);
                    userShowNewImage.setImageBitmap((Bitmap) message.obj);
                    userShowImageBg.setImageBitmap(StackBlur.blurNativelyPixels((Bitmap) message.obj, 10, false));
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
        setContentView(R.layout.user_show_layout);
        init();
    }

    @Override
    protected void init() {
        userShowBack = (Button) findViewById(R.id.user_show_back);
        userShowImageBg = (ImageView) findViewById(R.id.user_show_image_bg);
        userShowImage = (ImageView) findViewById(R.id.user_show_image);
        userShowName = (TextView) findViewById(R.id.user_show_name);
        userShowSignature = (TextView) findViewById(R.id.user_show_signature);
        userShowPublishButton = findViewById(R.id.user_show_publish_button);
        userShowPublishText = (TextView) findViewById(R.id.user_show_publish_text);
        userShowCareButton = findViewById(R.id.user_show_care_button);
        userShowCareText = (TextView) findViewById(R.id.user_show_care_text);
        userShowBookButton = findViewById(R.id.user_show_book_button);
        userShowBookText = (TextView) findViewById(R.id.user_show_book_text);
        userShowNewImage = (ImageView) findViewById(R.id.user_show_new_image);
        userShowNewName = (TextView) findViewById(R.id.user_show_new_name);
        userShowNewDate = (TextView) findViewById(R.id.user_show_new_date);
        userShowNewTitle = (TextView) findViewById(R.id.user_show_new_title);
        userShowNewContent = (TextView) findViewById(R.id.user_show_new_content);
        userShowNewPlace = (TextView) findViewById(R.id.user_show_new_place);
        userShowNewTime = (TextView) findViewById(R.id.user_show_new_time);
        userShowNews = findViewById(R.id.user_show_news);
        userShowAttention = findViewById(R.id.user_show_attention);
        userShowAttentionBg = (ImageView) findViewById(R.id.user_show_attention_image);
        userShowAttentionText = (TextView) findViewById(R.id.user_show_attention_text);


        userShowBack.setOnClickListener(this);
        userShowPublishButton.setOnClickListener(this);
        userShowCareButton.setOnClickListener(this);
        userShowBookButton.setOnClickListener(this);
        userShowNews.setOnClickListener(this);
        userShowAttention.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_show_back:
                onBackPressed();
                break;
            case R.id.user_show_publish_button:
                break;
            case R.id.user_show_care_button:
                break;
            case R.id.user_show_book_button:
                break;
            case R.id.user_show_news:
                startActivity(new Intent(this, Communicate.class).putExtra("her_user_id", user.get("user_id")));
                break;
            case R.id.user_show_attention:
                if (Objects.equals(userShowAttentionText.getText().toString(), "关注")) {
                    MyRomateSQLUtil.addAttention(user.get("object_id"), mHandler);
                } else {
                    MyRomateSQLUtil.removeAttention(user.get("object_id"), mHandler);
                }
                break;
            default:
                return;
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
        switch (getIntent().getStringExtra("type")) {
            case "market":
                user = MyDatabaseUtil.queryUser(getIntent().getStringExtra("user"));
                if (!user.get("user_image").isEmpty()) {
                    userShowImage.setImageBitmap(BitmapFactory.decodeFile(user.get("user_image")));
                    userShowNewImage.setImageBitmap(BitmapFactory.decodeFile(user.get("user_image")));
                    userShowImageBg.setImageBitmap(StackBlur.blurNativelyPixels(BitmapFactory.decodeFile(user.get("user_image")), 10, false));
                } else {
                    userShowImage.setImageResource(R.mipmap.mine_icon);
                    userShowNewImage.setImageResource(R.mipmap.mine_icon);
                    userShowImageBg.setImageResource(R.color.white);
                }
                userShowName.setText(user.get("user_name"));
                userShowSignature.setText(user.get("user_signature"));
                userShowCareText.setText(String.valueOf(MyUtil.getNumber(user.get("user_care"))));
                userShowPublishText.setText(String.valueOf(MyUtil.getNumber(user.get("user_publish"))));
                userShowBookText.setText(String.valueOf(MyUtil.getNumber(user.get("user_book"))));
                userShowNewName.setText(user.get("user_name"));
                MyRomateSQLUtil.hasAttention(user.get("object_id"), mHandler);
                MyRomateSQLUtil.getLastPublish(user.get("user_id"), mHandler);
                break;
            case "care":
                user = MyDatabaseUtil.queryUser(getIntent().getStringExtra("user"));
                if (!user.get("user_image").isEmpty()) {
                    userShowImage.setImageBitmap(BitmapFactory.decodeFile(user.get("user_image")));
                    userShowNewImage.setImageBitmap(BitmapFactory.decodeFile(user.get("user_image")));
                    userShowImageBg.setImageBitmap(StackBlur.blurNativelyPixels(BitmapFactory.decodeFile(user.get("user_image")), 10, false));
                } else {
                    userShowImage.setImageResource(R.mipmap.mine_icon);
                    userShowNewImage.setImageResource(R.mipmap.mine_icon);
                    userShowImageBg.setImageResource(R.color.white);
                }
                userShowName.setText(user.get("user_name"));
                userShowSignature.setText(user.get("user_signature"));
                userShowCareText.setText(String.valueOf(MyUtil.getNumber(user.get("user_care"))));
                userShowPublishText.setText(String.valueOf(MyUtil.getNumber(user.get("user_publish"))));
                userShowBookText.setText(String.valueOf(MyUtil.getNumber(user.get("user_book"))));
                userShowNewName.setText(user.get("user_name"));
                MyRomateSQLUtil.hasAttention(user.get("object_id"), mHandler);
                MyRomateSQLUtil.getLastPublish(user.get("user_id"), mHandler);
                break;
            case "news":
                user = MyDatabaseUtil.queryUser(getIntent().getStringExtra("user"));
                if (!user.get("user_image").isEmpty()) {
                    userShowImage.setImageBitmap(BitmapFactory.decodeFile(user.get("user_image")));
                    userShowNewImage.setImageBitmap(BitmapFactory.decodeFile(user.get("user_image")));
                    userShowImageBg.setImageBitmap(StackBlur.blurNativelyPixels(BitmapFactory.decodeFile(user.get("user_image")), 10, false));
                } else {
                    userShowImage.setImageResource(R.mipmap.mine_icon);
                    userShowNewImage.setImageResource(R.mipmap.mine_icon);
                    userShowImageBg.setImageResource(R.color.white);
                }
                userShowName.setText(user.get("user_name"));
                userShowSignature.setText(user.get("user_signature"));
                userShowCareText.setText(String.valueOf(MyUtil.getNumber(user.get("user_care"))));
                userShowPublishText.setText(String.valueOf(MyUtil.getNumber(user.get("user_publish"))));
                userShowBookText.setText(String.valueOf(MyUtil.getNumber(user.get("user_book"))));
                userShowNewName.setText(user.get("user_name"));
                MyRomateSQLUtil.hasAttention(user.get("object_id"), mHandler);
                MyRomateSQLUtil.getLastPublish(user.get("user_id"), mHandler);
                break;
            case "search":
                MyRomateSQLUtil.getSearchUser(getIntent().getStringExtra("user"), mHandler);
                userShowImage.setImageResource(R.mipmap.mine_icon);
                userShowNewImage.setImageResource(R.mipmap.mine_icon);
                userShowImageBg.setImageResource(R.color.white);
                break;
            default:
                break;
        }

    }

}
