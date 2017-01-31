package minework.onesit.activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.maxleap.MLFile;
import com.maxleap.MLFileManager;
import com.maxleap.SaveCallback;
import com.maxleap.exception.MLException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.NumberPicker;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.picker.TimePicker;
import minework.onesit.R;
import minework.onesit.util.MyRomateSQLUtil;
import minework.onesit.util.MyUtil;
import minework.onesit.util.camera.CameraCore;
import minework.onesit.util.camera.CameraProxy;

/**
 * Created by 无知 on 2016/11/24.
 */

public class Publish extends BaseActivity implements View.OnClickListener, CameraCore.CameraResult {

    //主体
    private Context mContext;
    private CameraProxy cameraProxy;
    private ScrollView publishMain;
    private Button publishBackButton;
    private Button publishManagerButton;
    private CardView publishFinishButton;
    private View publishTitleButton;
    private View publishStartTimeButton;
    private View publishStopTimeButton;
    private View publishPeopleNumberButton;
    private View publishPlaceButton;
    private View publishSeatButton;
    private View publishMoreButton;
    private View publishPicturesButton;
    private TextView publishTitle;
    private TextView publishStartTime;
    private TextView publishStopTime;
    private TextView publishPeopleNumber;
    private TextView publishPlace;
    private TextView publishSeatL;
    private TextView publishSeatR;
    private TextView publishMore;
    private ImageView publishPicture1;
    private ImageView publishPicture2;
    private ImageView publishPicture3;
    private List<Integer> mDatas;
    private int row;
    private int column;
    private String url_1 = null;
    private String url_2 = null;
    private String url_3 = null;
    //菜单
    private PopupWindow publishMenuWindow;
    private View publishMenuView;
    private Button publishSeat;
    private Button publishSave;
    private Button publishImport;
    private Animation clockAnimation;
    private Animation counter_clockAnimation;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 3:
                    if (message.arg1 == 0) {
                        publishPicture1.setImageBitmap((Bitmap) message.obj);
                    } else if (message.arg1 == 1) {
                        publishPicture2.setImageBitmap((Bitmap) message.obj);
                    } else if (message.arg1 == 2) {
                        publishPicture3.setImageBitmap((Bitmap) message.obj);
                    }
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
        setContentView(R.layout.publish_layout);
        cameraProxy = new CameraProxy(this, Publish.this);
        mContext = MyApplication.getInstance();
        init();
    }

    @Override
    protected void init() {
        publishMain = (ScrollView) findViewById(R.id.publish_main);
        publishBackButton = (Button) findViewById(R.id.publish_back);
        publishManagerButton = (Button) findViewById(R.id.publish_manager);
        publishFinishButton = (CardView) findViewById(R.id.publish_finish);
        publishTitleButton = findViewById(R.id.publish_title_button);
        publishStartTimeButton = findViewById(R.id.publish_start_time_button);
        publishStopTimeButton = findViewById(R.id.publish_stop_time_button);
        publishPeopleNumberButton = findViewById(R.id.publish_people_number_button);
        publishPlaceButton = findViewById(R.id.publish_place_button);
        publishSeatButton = findViewById(R.id.publish_seat_button);
        publishMoreButton = findViewById(R.id.publish_more_button);
        publishPicturesButton = findViewById(R.id.publish_pictures_button);
        publishTitle = (TextView) findViewById(R.id.publish_title);
        publishStartTime = (TextView) findViewById(R.id.publish_start_time);
        publishStopTime = (TextView) findViewById(R.id.publish_stop_time);
        publishPeopleNumber = (TextView) findViewById(R.id.publish_people_number);
        publishPlace = (TextView) findViewById(R.id.publish_place);
        publishSeatL = (TextView) findViewById(R.id.publish_seat_l);
        publishSeatR = (TextView) findViewById(R.id.publish_seat_r);
        publishMore = (TextView) findViewById(R.id.publish_more);
        publishPicture1 = (ImageView) findViewById(R.id.publish_pictures_1);
        publishPicture2 = (ImageView) findViewById(R.id.publish_pictures_2);
        publishPicture3 = (ImageView) findViewById(R.id.publish_pictures_3);

        publishBackButton.setOnClickListener(this);
        publishManagerButton.setOnClickListener(this);
        publishFinishButton.setOnClickListener(this);
        publishTitleButton.setOnClickListener(this);
        publishStartTimeButton.setOnClickListener(this);
        publishStopTimeButton.setOnClickListener(this);
        publishPeopleNumberButton.setOnClickListener(this);
        publishPlaceButton.setOnClickListener(this);
        publishSeatButton.setOnClickListener(this);
        publishMoreButton.setOnClickListener(this);
        publishPicturesButton.setOnClickListener(this);

        clockAnimation = AnimationUtils.loadAnimation(this, R.anim.clockwise_rotate_90);
        clockAnimation.setDuration(500);
        clockAnimation.setFillAfter(true);
        counter_clockAnimation = AnimationUtils.loadAnimation(this, R.anim.counter_clockwise_rotate_90);
        counter_clockAnimation.setDuration(500);
        counter_clockAnimation.setFillAfter(true);

        row = 1;
        column = 1;
        mDatas = new ArrayList<Integer>();
        mDatas.add(R.mipmap.seat_b);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.publish_back:
                onBackPressed();
                break;
            case R.id.publish_manager:
                if (publishMenuWindow != null && publishMenuWindow.isShowing()) {
                    publishManagerButton.startAnimation(clockAnimation);
                    ObjectAnimator.ofFloat(publishMain, "alpha", 0.3f, 1.0f).setDuration(500).start();
                    publishMenuWindow.dismiss();
                } else {
                    if (publishMenuWindow == null) {
                        initSeatMenuWindow();
                    }
                    publishMenuWindow.showAsDropDown(publishManagerButton, -68, -20);
                    publishManagerButton.startAnimation(counter_clockAnimation);
                    ObjectAnimator.ofFloat(publishMain, "alpha", 1.0f, 0.3f).setDuration(500).start();
                }
                break;
            case R.id.publish_finish:
                if (!TextUtils.isEmpty(publishTitle.getText()) && !TextUtils.isEmpty(publishStartTime.getText()) && !TextUtils.isEmpty(publishPlace.getText())) {
                    AlertDialog.Builder saveView = new AlertDialog.Builder(this);
                    saveView.setMessage("确定发布么？发布成功将收在“活动”中").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            List<String> picture = new ArrayList<String>();
                            if (url_1 != null) {
                                picture.add(url_1);
                            }
                            if (url_2 != null) {
                                picture.add(url_2);
                            }
                            if (url_3 != null) {
                                picture.add(url_3);
                            }
                            MyRomateSQLUtil.savePublish(publishTitle.getText().toString(), mDatas, row, column, publishStartTime.getText().toString(), publishStopTime.getText().toString(), Integer.parseInt(publishPeopleNumber.getText().toString()), publishPlace.getText().toString(), publishMore.getText().toString(), picture);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    AlertDialog saveDialog = saveView.create();
                    saveDialog.show();
                } else {
                    Toast.makeText(mContext, "标题、时间、地点均不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.publish_save:
                if (!TextUtils.isEmpty(publishTitle.getText()) && !TextUtils.isEmpty(publishStartTime.getText()) && !TextUtils.isEmpty(publishPlace.getText())) {
                    AlertDialog.Builder saveView = new AlertDialog.Builder(this);
                    saveView.setMessage("确定上传到云端？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            List<String> picture = new ArrayList<String>();
                            if (url_1 != null) {
                                picture.add(url_1);
                            }
                            if (url_2 != null) {
                                picture.add(url_2);
                            }
                            if (url_3 != null) {
                                picture.add(url_3);
                            }
                            MyRomateSQLUtil.savePublishModel(publishTitle.getText().toString(), mDatas, row, column, publishStartTime.getText().toString(), publishStopTime.getText().toString(), Integer.parseInt(publishPeopleNumber.getText().toString()), publishPlace.getText().toString(), publishMore.getText().toString(), picture);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    AlertDialog saveDialog = saveView.create();
                    saveDialog.show();
                } else {
                    Toast.makeText(mContext, "标题、时间、地点均不能为空", Toast.LENGTH_SHORT).show();
                }
                if (publishMenuWindow != null && publishMenuWindow.isShowing()) {
                    publishManagerButton.startAnimation(clockAnimation);
                    ObjectAnimator.ofFloat(publishMain, "alpha", 0.3f, 1.0f).setDuration(500).start();
                    publishMenuWindow.dismiss();
                }
                break;
            case R.id.publish_import:
                startActivity(new Intent(this, PublishModelList.class));
                break;
            case R.id.publish_seat:
                startActivity(new Intent(this, SeatTable.class).putExtra("hasSeatTable", true).putIntegerArrayListExtra("seat_table", (ArrayList<Integer>) mDatas).putExtra("column", column));
                break;
            case R.id.publish_title_button:
                startActivity(new Intent(this, EditActivity.class).putExtra("name", "标题").putExtra("activity", "Publish"));
                break;
            case R.id.publish_people_number_button:
                NumberPicker picker2 = new NumberPicker(this);
                picker2.setOffset(1);//偏移量
                picker2.setRange(1, 500);//数字范围
                picker2.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(String option) {
                        publishPeopleNumber.setText(option);
                    }
                });
                picker2.show();
                break;
            case R.id.publish_seat_button:
                startActivity(new Intent(this, SeatTable.class).putExtra("hasSeatTable", true).putIntegerArrayListExtra("seat_table", (ArrayList<Integer>) mDatas).putExtra("column", column));
                break;
            case R.id.publish_start_time_button:
                DatePicker startTimePicker = new DatePicker(this);
                startTimePicker.setRange(2017, 2060);//年份范围
                startTimePicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        final String myYear = year;
                        final String myMonth = month;
                        final String myDay = day;
                        publishStartTime.setText(myYear + "年" + myMonth + "月" + myDay + "日");
                        final TimePicker picker = new TimePicker(Publish.this);
                        picker.setTopLineVisible(false);
                        picker.setOnTimePickListener(new TimePicker.OnTimePickListener() {
                            @Override
                            public void onTimePicked(String hour, String minute) {
                                publishStartTime.setText(myYear + "年" + myMonth + "月" + myDay + "日" + hour + ":" + minute);
                            }
                        });
                        picker.show();
                    }
                });
                startTimePicker.show();
                break;
            case R.id.publish_stop_time_button:
                DatePicker stopTimePicker = new DatePicker(this);
                stopTimePicker.setRange(2017, 2060);//年份范围
                stopTimePicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        final String myYear = year;
                        final String myMonth = month;
                        final String myDay = day;
                        publishStopTime.setText(myYear + "年" + myMonth + "月" + myDay + "日");
                        final TimePicker picker = new TimePicker(Publish.this);
                        picker.setTopLineVisible(false);
                        picker.setOnTimePickListener(new TimePicker.OnTimePickListener() {
                            @Override
                            public void onTimePicked(String hour, String minute) {
                                publishStopTime.setText(myYear + "年" + myMonth + "月" + myDay + "日" + hour + ":" + minute);
                            }
                        });
                        picker.show();
                    }
                });
                stopTimePicker.show();
                break;
            case R.id.publish_place_button:
                startActivity(new Intent(this, EditActivity.class).putExtra("name", "地点").putExtra("activity", "Publish"));
                break;
            case R.id.publish_more_button:
                startActivity(new Intent(this, EditActivity.class).putExtra("name", "详情").putExtra("activity", "Publish"));
                break;
            case R.id.publish_pictures_button:
                String[] items = {"拍照", "本地图片"};
                AlertDialog.Builder imageBulider = new AlertDialog.Builder(this);
                imageBulider.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        switch (which) {
                            case 0:
                                break;
                            case 1:
                                cameraProxy.getPhoto2AlbumCrop();
                                break;
                            default:
                                return;
                        }
                    }
                });
                AlertDialog imageView = imageBulider.create();
                imageView.show();
                break;
            default:
                return;
        }
    }

    private void initSeatMenuWindow() {
        publishMenuView = getLayoutInflater().inflate(R.layout.publish_manager_layout, null, false);
        publishMenuWindow = new PopupWindow(publishMenuView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        publishMenuWindow.setAnimationStyle(R.style.AnimationFade);
        publishMenuWindow.setOutsideTouchable(true);
        publishSave = (Button) publishMenuView.findViewById(R.id.publish_save);
        publishSeat = (Button) publishMenuView.findViewById(R.id.publish_seat);
        publishImport = (Button) publishMenuView.findViewById(R.id.publish_import);
        publishSave.setOnClickListener(this);
        publishSeat.setOnClickListener(this);
        publishImport.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(getIntent().getStringExtra("content"))) {
            switch (getIntent().getStringExtra("name")) {
                case "标题":
                    publishTitle.setText(getIntent().getStringExtra("content"));
                    break;
                case "地点":
                    publishPlace.setText(getIntent().getStringExtra("content"));
                    break;
                case "详情":
                    publishMore.setText(getIntent().getStringExtra("content"));
                    break;
                case "座位":
                    publishSeatL.setText(getIntent().getStringExtra("content"));
                    publishSeatR.setText(getIntent().getStringExtra("content1"));
                    mDatas = getIntent().getIntegerArrayListExtra("seat");
                    row = getIntent().getIntExtra("row", 1);
                    column = getIntent().getIntExtra("column", 1);
                    break;
                default:
                    break;
            }
        }
        if (getIntent().getBooleanExtra("hasPublishModel", false)) {
            publishTitle.setText(getIntent().getStringExtra("publish_title"));
            publishStartTime.setText(getIntent().getStringExtra("start_time"));
            publishStopTime.setText(getIntent().getStringExtra("stop_time"));
            publishPlace.setText(getIntent().getStringExtra("publish_place"));
            publishMore.setText(getIntent().getStringExtra("information_text"));
            mDatas = getIntent().getIntegerArrayListExtra("seat_table");
            publishPeopleNumber.setText(String.valueOf(getIntent().getIntExtra("people_number", 1)));
            row = getIntent().getIntExtra("seat_row", 1);
            column = getIntent().getIntExtra("seat_column", 1);
            int blackNumber = 0;
            int greenNumber = 0;
            for (Integer colorType : mDatas) {
                switch (colorType) {
                    case 1:
                        blackNumber++;
                        break;
                    case 2:
                        greenNumber++;
                        break;
                    default:
                        break;
                }
            }
            publishSeatL.setText(String.valueOf(greenNumber));
            publishSeatR.setText(String.valueOf(greenNumber + blackNumber));
            for (int i = 0; i < 3; i++) {
                MyRomateSQLUtil.getBitmap(MyUtil.stringToListString(getIntent().getStringExtra("pictures")).get(i), i, mHandler);
                if (i == 0) {
                    url_1 = MyUtil.stringToListString(getIntent().getStringExtra("pictures")).get(i);
                } else if(i==1) {
                    url_2 = MyUtil.stringToListString(getIntent().getStringExtra("pictures")).get(i);
                }else if(i==2){
                    url_3 = MyUtil.stringToListString(getIntent().getStringExtra("pictures")).get(i);
                }
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    public void onSuccess(final String filePath) {
        final File file = new File(filePath);
        if (file.exists()) {
            new Thread() {
                public void run() {
                    final Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                    Publish.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            byte[] image = stream.toByteArray();
                            final MLFile myFile = new MLFile(System.currentTimeMillis() + ".png", image);
                            MLFileManager.saveInBackground(myFile, new SaveCallback() {
                                @Override
                                public void done(MLException e) {
                                    Toast.makeText(mContext, "图片上传成功", Toast.LENGTH_SHORT).show();
                                    if (url_1 != null && !Objects.equals(url_1, "")) {
                                        if (url_2 != null && !Objects.equals(url_2, "")) {
                                            url_3 = myFile.getUrl();
                                            publishPicture3.setImageBitmap(bitmap);
                                        } else {
                                            url_2 = myFile.getUrl();
                                            publishPicture2.setImageBitmap(bitmap);
                                        }
                                    } else {
                                        url_1 = myFile.getUrl();
                                        publishPicture1.setImageBitmap(bitmap);
                                    }
                                }
                            });
                        }
                    });
                    file.delete();
                }
            }.start();
        }
    }

    //拍照选图片失败回调
    @Override
    public void onFail(String message) {
        Toast.makeText(mContext, "获取失败，请重试", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cameraProxy.onResult(requestCode, resultCode, data);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        cameraProxy.onRestoreInstanceState(savedInstanceState);
    }

}
