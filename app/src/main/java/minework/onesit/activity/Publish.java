package minework.onesit.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.bither.util.NativeUtil;

import java.io.File;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.TimePicker;
import minework.onesit.R;
import minework.onesit.fragment.plan.DividerItemDecoration;
import minework.onesit.fragment.plan.SeatTableAdapter;
import minework.onesit.util.MyRichEditUtil;
import minework.onesit.util.MyRomateSQLUtil;
import minework.onesit.util.camera.CameraCore;
import minework.onesit.util.camera.CameraProxy;

/**
 * Created by 无知 on 2016/11/24.
 */

public class Publish extends BaseActivity implements View.OnClickListener, CameraCore.CameraResult {

    private static final float SCALE = MyApplication.getInstance().getResources().getDisplayMetrics().density;
    private static boolean isSeatTable = false;
    private static boolean isInformationText = false;
    private static boolean isSetSeatTable = false;
    private static boolean isFinishRich = false;
    private static boolean isItemDecoration = false;
    private static int start = -1;
    private static int end = -1;
    private static int size;
    private static int marginLength = 0;
    private static Rect r;
    private final String externalStorageDirectory = Environment.getExternalStorageDirectory().getPath() + "/picture/";
    private int column=0;
    //主体
    private Context mContext;
    private Button publishBackButton;
    private Button publishSeatButton;
    private Button publishInformationButton;
    private EditText publishTitleEdit;
    private TextView publishStartTimeText;
    private TextView publishStopTimeText;
    private EditText publishPeopleNumberEdit;
    private EditText publishPlaceEdit;
    private RelativeLayout publishSeatTableArea;
    //座位图
    private RecyclerView publishSeatTable;
    private TextView publishSeatTableToast;
    private HorizontalScrollView publishSeatScroll;
    private SeatTableAdapter seatTableAdapter;
    private DividerItemDecoration itemDecoration;
    private CameraProxy cameraProxy;
    private Bitmap bitmap;
    private View publishRootView;

    //菜单
    private Button publishManagerButton;
    private PopupWindow managerWindow;
    private View managerView;
    private Button publishEditSeat;
    private Button publishSaveModel;
    private Button publishImprtModel;
    private Button publishFinish;
    //详情
    private EditText publishInformationEdit;
    private PopupWindow RichEditorWindow;
    private View RichEditorView;
    private Button publishInformationBoldButton;
    private Button publishInformationItalicButton;
    private Button publishInformationUnderlineButton;
    private Button publishInformationTypefaceButton;
    private Button publishInformationSuperscriptButton;
    private Button publishInformationSubscriptButton;
    private Button publishInformationUpsizeButton;
    private Button publishInformationDownsizeButton;
    private Button publishInformationStrikethroughButton;
    private Button publishInformationQuoteButton;
    private Button publishInformationForegroundcolorButton;
    private Button publishInformationBackgroundcolorButton;
    private Button publishInformationAddindentButton;
    private Button publishInformationMinusindentButton;
    private Button publishInformationLinkButton;
    private Button publishInformationInsertimageButton;
    private Button publishInformationBulletButton;
    //动画
    private Animation clockAnimation;
    private Animation counter_clockAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_layout);
        cameraProxy = new CameraProxy(this, Publish.this);
        publishRootView = findViewById(R.id.publish_root_view);
        r = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
        init();
        mContext = MyApplication.getInstance();
    }

    @Override
    protected void init() {
        publishBackButton = (Button) findViewById(R.id.publish_back_button);
        publishManagerButton = (Button) findViewById(R.id.publish_manager_button);
        publishSeatButton = (Button) findViewById(R.id.publish_seat_button);
        publishInformationButton = (Button) findViewById(R.id.publish_information_button);
        publishTitleEdit = (EditText)findViewById(R.id.publish_title_edit);
        publishStartTimeText = (TextView) findViewById(R.id.publish_start_time_text);
        publishStopTimeText = (TextView) findViewById(R.id.publish_stop_time_text);
        publishPeopleNumberEdit = (EditText)findViewById(R.id.publish_people_number_edit);
        publishPlaceEdit = (EditText)findViewById(R.id.publish_place_edit);
        publishSeatTableArea = (RelativeLayout) findViewById(R.id.publish_seat_table_area);
        publishSeatTableToast = (TextView) findViewById(R.id.publish_seat_table_toast);
        publishSeatTable = (RecyclerView) findViewById(R.id.publish_seat_table);
        publishSeatScroll = (HorizontalScrollView) findViewById(R.id.publish_seat_scroll);
        //富文本
        publishInformationEdit = (EditText) findViewById(R.id.publish_information_edit);
        size = (int) publishInformationEdit.getTextSize();
        //监听
        publishBackButton.setOnClickListener(this);
        publishManagerButton.setOnClickListener(this);
        publishSeatButton.setOnClickListener(this);
        publishInformationButton.setOnClickListener(this);
        publishTitleEdit.setOnClickListener(this);
        publishStartTimeText.setOnClickListener(this);
        publishStopTimeText.setOnClickListener(this);
        publishPeopleNumberEdit.setOnClickListener(this);
        publishPlaceEdit.setOnClickListener(this);
        publishSeatTableToast.setOnClickListener(this);

        publishRootView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right,
                                       int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                int screenHeight = getWindowManager().getDefaultDisplay().getHeight();
                //阀值设置为屏幕高度的1/3
                int keyHeight = screenHeight / 3;
                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight) && isFinishRich && RichEditorWindow != null) {
                    RichEditorWindow.showAsDropDown(findViewById(R.id.plan_top_title), 0, 200);
                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
                    if (RichEditorWindow != null && RichEditorWindow.isShowing())
                        RichEditorWindow.dismiss();
                    publishInformationEdit.clearFocus();
                }
            }
        });

        clockAnimation = AnimationUtils.loadAnimation(this, R.anim.clockwise_rotate_90);
        clockAnimation.setDuration(500);
        clockAnimation.setFillAfter(true);
        counter_clockAnimation = AnimationUtils.loadAnimation(this, R.anim.counter_clockwise_rotate_90);
        counter_clockAnimation.setDuration(500);
        counter_clockAnimation.setFillAfter(true);

        publishInformationEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    if (RichEditorWindow == null)
                        initRichEditorWindow();
                    isFinishRich = true;
                    RichEditorWindow.showAsDropDown(findViewById(R.id.plan_top_title), 0, (int) (r.height() / (SCALE - 0.5f)));
                } else {
                    if (RichEditorWindow != null && RichEditorWindow.isShowing())
                        RichEditorWindow.dismiss();
                    isFinishRich = false;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.publish_back_button:
                onBackPressed();
                break;
            case R.id.publish_manager_button:
                if (managerWindow != null && managerWindow.isShowing()) {
                    managerWindow.dismiss();
                    publishManagerButton.startAnimation(clockAnimation);
                    return;
                } else {
                    if (managerWindow == null) {
                        initSeatMenuWindow();
                    }
                    managerWindow.showAsDropDown(publishManagerButton, 0, 20);
                    publishManagerButton.startAnimation(counter_clockAnimation);
                }
                break;
            case R.id.publish_edit_seat:
                startActivity(new Intent(mContext, SeatTable.class));
                if (managerWindow != null && managerWindow.isShowing()) {
                    managerWindow.dismiss();
                }
                break;
            case R.id.publish_save_model:
                if (!TextUtils.isEmpty(publishTitleEdit.getText()) && !TextUtils.isEmpty(publishStartTimeText.getText())
                        &&!TextUtils.isEmpty(publishStopTimeText.getText())&&!TextUtils.isEmpty(publishPlaceEdit.getText())
                        &&!TextUtils.isEmpty(publishPeopleNumberEdit.getText())) {
                    AlertDialog.Builder saveView = new AlertDialog.Builder(this);
                    saveView.setMessage("确定上传到云端？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                MyRomateSQLUtil.savePublishModel(publishTitleEdit.getText().toString(),seatTableAdapter.getList(),column,publishStartTimeText.getText().toString(),publishStopTimeText.getText().toString(),Integer.parseInt(publishPeopleNumberEdit.getText().toString()),publishPlaceEdit.getText().toString(),Html.toHtml(publishInformationEdit.getText(),Html.TO_HTML_PARAGRAPH_LINES_INDIVIDUAL));
                            } else {
                                MyRomateSQLUtil.savePublishModel(publishTitleEdit.getText().toString(),seatTableAdapter.getList(),column,publishStartTimeText.getText().toString(),publishStopTimeText.getText().toString(),Integer.parseInt(publishPeopleNumberEdit.getText().toString()),publishPlaceEdit.getText().toString(),Html.toHtml(publishInformationEdit.getText()));
                            }

                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            onBackPressed();
                        }
                    });
                    AlertDialog saveDialog = saveView.create();
                    saveDialog.show();
                } else {
                    Toast.makeText(mContext,"标题、时间、地点、人数均不能为空",Toast.LENGTH_SHORT).show();
                }
                if (managerWindow != null && managerWindow.isShowing()) {
                    managerWindow.dismiss();
                }
                break;
            case R.id.publish_import_model:
                startActivity(new Intent(this, PublishModelList.class));
                if (managerWindow != null && managerWindow.isShowing()) {
                    managerWindow.dismiss();
                }
                break;
            case R.id.publish_finish:
                if (managerWindow != null && managerWindow.isShowing()) {
                    managerWindow.dismiss();
                }
                break;
            case R.id.publish_seat_button:
                if (isSeatTable) {
                    publishSeatButton.setBackgroundResource(R.drawable.simple_button_normal);
                    isSeatTable = false;
                    publishSeatTableArea.setVisibility(View.GONE);
                } else {
                    publishSeatButton.setBackgroundResource(R.drawable.simple_button_choosed);
                    isSeatTable = true;
                    publishSeatTableArea.setVisibility(View.VISIBLE);
                    if (isSetSeatTable) {
                        publishSeatTableToast.setVisibility(View.GONE);
                        publishSeatScroll.setVisibility(View.VISIBLE);
                    } else {
                        publishSeatTableToast.setVisibility(View.VISIBLE);
                        publishSeatScroll.setVisibility(View.GONE);
                    }
                }
                break;
            case R.id.publish_start_time_text:
                DatePicker startTimePicker = new DatePicker(this);
                startTimePicker.setRange(2016, 2060);//年份范围
                startTimePicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        final String myYear = year;
                        final String myMonth = month;
                        final String myDay = day;
                        publishStartTimeText.setText(myYear + "年" + myMonth + "月" + myDay + "日");
                        final TimePicker picker = new TimePicker(Publish.this);
                        picker.setTopLineVisible(false);
                        picker.setOnTimePickListener(new TimePicker.OnTimePickListener() {
                            @Override
                            public void onTimePicked(String hour, String minute) {
                                publishStartTimeText.setText(myYear + "年" + myMonth + "月" + myDay + "日" + hour + "：" + minute);
                            }
                        });
                        picker.show();
                    }
                });
                startTimePicker.show();
                break;
            case R.id.publish_stop_time_text:
                DatePicker stopTimePicker = new DatePicker(this);
                stopTimePicker.setRange(2016, 2060);//年份范围
                stopTimePicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        final String myYear = year;
                        final String myMonth = month;
                        final String myDay = day;
                        publishStopTimeText.setText(myYear + "年" + myMonth + "月" + myDay + "日");
                        final TimePicker picker = new TimePicker(Publish.this);
                        picker.setTopLineVisible(false);
                        picker.setOnTimePickListener(new TimePicker.OnTimePickListener() {
                            @Override
                            public void onTimePicked(String hour, String minute) {
                                publishStopTimeText.setText(myYear + "年" + myMonth + "月" + myDay + "日" + hour + "：" + minute);
                            }
                        });
                        picker.show();
                    }
                });
                stopTimePicker.show();
                break;
            case R.id.publish_information_button:
                if (isInformationText) {
                    publishInformationButton.setBackgroundResource(R.drawable.simple_button_normal);
                    publishInformationEdit.setVisibility(View.GONE);
                    isInformationText = false;
                } else {
                    publishInformationButton.setBackgroundResource(R.drawable.simple_button_choosed);
                    publishInformationEdit.setVisibility(View.VISIBLE);
                    isInformationText = true;
                }
                break;
            case R.id.publish_seat_table_toast:
                startActivity(new Intent(mContext, SeatTable.class));
                break;
            case R.id.publish_information_bold:
                start = publishInformationEdit.getSelectionStart();
                end = publishInformationEdit.getSelectionEnd();
                if (start != end) {
                    SpannableStringBuilder newSub = MyRichEditUtil.BoldSpan(publishInformationEdit.getText().subSequence(start, end), start, end, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
                    publishInformationEdit.getEditableText().replace(start, end, newSub);
                } else {
                    SpannableStringBuilder newSub = MyRichEditUtil.BoldSpan("a", 0, 1, SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE);
                    publishInformationEdit.getEditableText().insert(start, newSub);
                    publishInformationEdit.getText().delete(start, start + 1);
                }
                break;
            case R.id.publish_information_italic:
                start = publishInformationEdit.getSelectionStart();
                end = publishInformationEdit.getSelectionEnd();
                if (start != end) {
                    SpannableStringBuilder newSub = MyRichEditUtil.ItalicSpan(publishInformationEdit.getText().subSequence(start, end), start, end, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
                    publishInformationEdit.getEditableText().replace(start, end, newSub);
                } else {
                    SpannableStringBuilder newSub = MyRichEditUtil.ItalicSpan("a", 0, 1, SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE);
                    publishInformationEdit.getEditableText().insert(start, newSub);
                    publishInformationEdit.getText().delete(start, start + 1);
                }
                break;
            case R.id.publish_information_underline:
                start = publishInformationEdit.getSelectionStart();
                end = publishInformationEdit.getSelectionEnd();
                if (start != end) {
                    SpannableStringBuilder newSub = MyRichEditUtil.UnderlineSpan(publishInformationEdit.getText().subSequence(start, end), start, end, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
                    publishInformationEdit.getEditableText().replace(start, end, newSub);
                } else {
                    SpannableStringBuilder newSub = MyRichEditUtil.UnderlineSpan("a", 0, 1, SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE);
                    publishInformationEdit.getEditableText().insert(start, newSub);
                    publishInformationEdit.getText().delete(start, start + 1);
                }
                break;
            case R.id.publish_information_typeface:
                start = publishInformationEdit.getSelectionStart();
                end = publishInformationEdit.getSelectionEnd();
                if (start != end) {
                    SpannableStringBuilder newSub = MyRichEditUtil.TypefaceSpan(publishInformationEdit.getText().subSequence(start, end), start, end, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
                    publishInformationEdit.getEditableText().replace(start, end, newSub);
                } else {
                    SpannableStringBuilder newSub = MyRichEditUtil.TypefaceSpan("a", 0, 1, SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE);
                    publishInformationEdit.getEditableText().insert(start, newSub);
                    publishInformationEdit.getText().delete(start, start + 1);
                }
                break;
            case R.id.publish_information_superscript:
                start = publishInformationEdit.getSelectionStart();
                end = publishInformationEdit.getSelectionEnd();
                if (start != end) {
                    SpannableStringBuilder newSub = MyRichEditUtil.SuperscriptSpan(publishInformationEdit.getText().subSequence(start, end), start, end, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
                    publishInformationEdit.getEditableText().replace(start, end, newSub);
                } else {
                    SpannableStringBuilder newSub = MyRichEditUtil.SuperscriptSpan("a", 0, 1, SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE);
                    publishInformationEdit.getEditableText().insert(start, newSub);
                    publishInformationEdit.getText().delete(start, start + 1);
                }
                break;
            case R.id.publish_information_subscript:
                start = publishInformationEdit.getSelectionStart();
                end = publishInformationEdit.getSelectionEnd();
                if (start != end) {
                    SpannableStringBuilder newSub = MyRichEditUtil.SubscriptSpan(publishInformationEdit.getText().subSequence(start, end), start, end, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
                    publishInformationEdit.getEditableText().replace(start, end, newSub);
                } else {
                    SpannableStringBuilder newSub = MyRichEditUtil.SubscriptSpan("a", 0, 1, SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE);
                    publishInformationEdit.getEditableText().insert(start, newSub);
                    publishInformationEdit.getText().delete(start, start + 1);
                }
                break;
            case R.id.publish_information_upsize:
                start = publishInformationEdit.getSelectionStart();
                end = publishInformationEdit.getSelectionEnd();
                size += 6;
                if (start != end) {
                    SpannableStringBuilder newSub = MyRichEditUtil.ChangeSizeSpan(publishInformationUpsizeButton, publishInformationDownsizeButton, publishInformationEdit.getText().subSequence(start, end), start, end, size, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
                    publishInformationEdit.getEditableText().replace(start, end, newSub);
                } else {
                    SpannableStringBuilder newSub = MyRichEditUtil.ChangeSizeSpan(publishInformationUpsizeButton, publishInformationDownsizeButton, "a", 0, 1, size, SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE);
                    publishInformationEdit.getEditableText().insert(start, newSub);
                    publishInformationEdit.getText().delete(start, start + 1);
                }
                break;
            case R.id.publish_information_downsize:
                start = publishInformationEdit.getSelectionStart();
                end = publishInformationEdit.getSelectionEnd();
                if (size > 6) {
                    size -= 6;
                    if (start != end) {
                        SpannableStringBuilder newSub = MyRichEditUtil.ChangeSizeSpan(publishInformationUpsizeButton, publishInformationDownsizeButton, publishInformationEdit.getText().subSequence(start, end), start, end, size, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
                        publishInformationEdit.getEditableText().replace(start, end, newSub);
                    } else {
                        SpannableStringBuilder newSub = MyRichEditUtil.ChangeSizeSpan(publishInformationUpsizeButton, publishInformationDownsizeButton, "a", 0, 1, size, SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE);
                        publishInformationEdit.getEditableText().insert(start, newSub);
                        publishInformationEdit.getText().delete(start, start + 1);
                    }
                }
                break;
            case R.id.publish_information_strikethrough:
                start = publishInformationEdit.getSelectionStart();
                end = publishInformationEdit.getSelectionEnd();
                if (start != end) {
                    SpannableStringBuilder newSub = MyRichEditUtil.StrikethroughSpan(publishInformationEdit.getText().subSequence(start, end), start, end, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
                    publishInformationEdit.getEditableText().replace(start, end, newSub);
                } else {
                    SpannableStringBuilder newSub = MyRichEditUtil.StrikethroughSpan("a", 0, 1, SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE);
                    publishInformationEdit.getEditableText().insert(start, newSub);
                    publishInformationEdit.getText().delete(start, start + 1);
                }
                break;
            case R.id.publish_information_quote:
                start = publishInformationEdit.getSelectionStart();
                end = publishInformationEdit.getSelectionEnd();
                if (start != end) {
                    SpannableStringBuilder newSub = MyRichEditUtil.QuoteSpan(publishInformationEdit.getText().subSequence(start, end), start, end, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
                    publishInformationEdit.getEditableText().replace(start, end, newSub);
                } else {
                    SpannableStringBuilder newSub = MyRichEditUtil.QuoteSpan("a", 0, 1, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
                    publishInformationEdit.getEditableText().insert(start, newSub);
                    publishInformationEdit.getText().delete(start, start + 1);
                }
                break;
            case R.id.publish_information_foregroundcolor:
                start = publishInformationEdit.getSelectionStart();
                end = publishInformationEdit.getSelectionEnd();
                if (start != end) {
                    SpannableStringBuilder newSub = MyRichEditUtil.ForegroundColorSpan(publishInformationForegroundcolorButton, publishInformationEdit.getText().subSequence(start, end), start, end, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
                    publishInformationEdit.getEditableText().replace(start, end, newSub);
                } else {
                    SpannableStringBuilder newSub = MyRichEditUtil.ForegroundColorSpan(publishInformationForegroundcolorButton, "a", 0, 1, SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE);
                    publishInformationEdit.getEditableText().insert(start, newSub);
                    publishInformationEdit.getText().delete(start, start + 1);
                }
                break;
            case R.id.publish_information_backgroundcolor:
                start = publishInformationEdit.getSelectionStart();
                end = publishInformationEdit.getSelectionEnd();
                if (start != end) {
                    SpannableStringBuilder newSub = MyRichEditUtil.BackgroundColorSpan(publishInformationBackgroundcolorButton, publishInformationEdit.getText().subSequence(start, end), start, end, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
                    publishInformationEdit.getEditableText().replace(start, end, newSub);
                } else {
                    SpannableStringBuilder newSub = MyRichEditUtil.BackgroundColorSpan(publishInformationBackgroundcolorButton, "a", 0, 1, SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE);
                    publishInformationEdit.getEditableText().insert(start, newSub);
                    publishInformationEdit.getText().delete(start, start + 1);
                }
                break;
            case R.id.publish_information_bullet:
                start = publishInformationEdit.getSelectionStart();
                end = publishInformationEdit.getSelectionEnd();
                if (start != end) {
                    SpannableStringBuilder newSub = MyRichEditUtil.BulletSpan(publishInformationEdit.getText().subSequence(start, end), start, end);
                    publishInformationEdit.getEditableText().replace(start, end, newSub);
                }
                break;
            case R.id.publish_information_addindent:
                start = publishInformationEdit.getSelectionStart();
                end = publishInformationEdit.getSelectionEnd();
                if (start != end) {
                    SpannableStringBuilder newSub = MyRichEditUtil.AddIndentSpan(publishInformationEdit.getText().subSequence(start, end), start, end);
                    publishInformationEdit.getEditableText().replace(start, end, newSub);
                }
                marginLength++;
                break;
            case R.id.publish_information_minusindent:
                if (marginLength > 0) {
                    start = publishInformationEdit.getSelectionStart();
                    end = publishInformationEdit.getSelectionEnd();
                    if (start != end) {
                        SpannableStringBuilder newSub = MyRichEditUtil.MinusIndentSpan(publishInformationEdit.getText().subSequence(start, end), start, end);
                        publishInformationEdit.getEditableText().replace(start, end, newSub);
                    }
                    marginLength--;
                }
                break;
            case R.id.publish_information_link:
                start = publishInformationEdit.getSelectionStart();
                end = publishInformationEdit.getSelectionEnd();
                if (start != end) {
                    AlertDialog.Builder linkDialogBuilder = new AlertDialog.Builder(this);
                    final View linkDialogView = LayoutInflater.from(this).inflate(R.layout.link_layout, null);
                    linkDialogBuilder.setTitle("输入网址");
                    linkDialogBuilder.setView(linkDialogView);
                    linkDialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText edit_text = (EditText) linkDialogView.findViewById(R.id.link_edit_text);
                            if (edit_text.getText().toString() != " ") {
                                SpannableStringBuilder newSub = MyRichEditUtil.LinkSpan(publishInformationEdit.getText().subSequence(start, end), start, end, edit_text.getText().toString(), SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
                                publishInformationEdit.getEditableText().replace(start, end, newSub);
                            }
                        }
                    });
                    linkDialogBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    AlertDialog linkDialog = linkDialogBuilder.create();
                    linkDialog.show();
                }
                break;
            case R.id.publish_information_insertimage:
                cameraProxy.getPhoto2AlbumCrop();
                break;
            default:
                return;
        }
    }

    private void initSeatMenuWindow() {
        managerView = getLayoutInflater().inflate(R.layout.publish_manager_layout, null, false);
        managerWindow = new PopupWindow(managerView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        managerWindow.setAnimationStyle(R.style.AnimationFade);
        managerWindow.setOutsideTouchable(true);
        publishEditSeat = (Button) managerView.findViewById(R.id.publish_edit_seat);
        publishSaveModel = (Button) managerView.findViewById(R.id.publish_save_model);
        publishImprtModel = (Button) managerView.findViewById(R.id.publish_import_model);
        publishFinish = (Button) managerView.findViewById(R.id.publish_finish);
        publishEditSeat.setOnClickListener(this);
        publishSaveModel.setOnClickListener(this);
        publishImprtModel.setOnClickListener(this);
        publishFinish.setOnClickListener(this);
    }

    private void initRichEditorWindow() {
        RichEditorView = getLayoutInflater().inflate(R.layout.rich_editor_layout, null, false);
        RichEditorWindow = new PopupWindow(RichEditorView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RichEditorWindow.setAnimationStyle(R.style.AnimationFade);
        //初始化
        publishInformationBoldButton = (Button) RichEditorView.findViewById(R.id.publish_information_bold);
        publishInformationItalicButton = (Button) RichEditorView.findViewById(R.id.publish_information_italic);
        publishInformationUnderlineButton = (Button) RichEditorView.findViewById(R.id.publish_information_underline);
        publishInformationTypefaceButton = (Button) RichEditorView.findViewById(R.id.publish_information_typeface);
        publishInformationSuperscriptButton = (Button) RichEditorView.findViewById(R.id.publish_information_superscript);
        publishInformationSubscriptButton = (Button) RichEditorView.findViewById(R.id.publish_information_subscript);
        publishInformationUpsizeButton = (Button) RichEditorView.findViewById(R.id.publish_information_upsize);
        publishInformationDownsizeButton = (Button) RichEditorView.findViewById(R.id.publish_information_downsize);
        publishInformationStrikethroughButton = (Button) RichEditorView.findViewById(R.id.publish_information_strikethrough);
        publishInformationQuoteButton = (Button) RichEditorView.findViewById(R.id.publish_information_quote);
        publishInformationForegroundcolorButton = (Button) RichEditorView.findViewById(R.id.publish_information_foregroundcolor);
        publishInformationBackgroundcolorButton = (Button) RichEditorView.findViewById(R.id.publish_information_backgroundcolor);
        publishInformationAddindentButton = (Button) RichEditorView.findViewById(R.id.publish_information_addindent);
        publishInformationMinusindentButton = (Button) RichEditorView.findViewById(R.id.publish_information_minusindent);
        publishInformationLinkButton = (Button) RichEditorView.findViewById(R.id.publish_information_link);
        publishInformationInsertimageButton = (Button) RichEditorView.findViewById(R.id.publish_information_insertimage);
        publishInformationBulletButton = (Button) RichEditorView.findViewById(R.id.publish_information_bullet);
        RichEditorWindow.setOutsideTouchable(true);
        publishInformationBoldButton.setOnClickListener(this);
        publishInformationItalicButton.setOnClickListener(this);
        publishInformationUnderlineButton.setOnClickListener(this);
        publishInformationTypefaceButton.setOnClickListener(this);
        publishInformationSuperscriptButton.setOnClickListener(this);
        publishInformationSubscriptButton.setOnClickListener(this);
        publishInformationUpsizeButton.setOnClickListener(this);
        publishInformationDownsizeButton.setOnClickListener(this);
        publishInformationStrikethroughButton.setOnClickListener(this);
        publishInformationQuoteButton.setOnClickListener(this);
        publishInformationForegroundcolorButton.setOnClickListener(this);
        publishInformationBackgroundcolorButton.setOnClickListener(this);
        publishInformationAddindentButton.setOnClickListener(this);
        publishInformationMinusindentButton.setOnClickListener(this);
        publishInformationLinkButton.setOnClickListener(this);
        publishInformationInsertimageButton.setOnClickListener(this);
        publishInformationBulletButton.setOnClickListener(this);
    }

    @Override
    public void onSuccess(final String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            new Thread() {
                public void run() {
                    final File file = new File(externalStorageDirectory + "/tempCompress.jpg");
                    NativeUtil.compressBitmap(filePath, file.getPath());
                    bitmap = BitmapFactory.decodeFile(file.getPath());
                    Publish.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            insertImage();
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
    protected void onResume() {
        super.onResume();
        if (getIntent().getBooleanExtra("hasSeatTable", false)) {
            Publish.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    column = getIntent().getIntExtra("column", 1);
                    seatTableAdapter = SeatTable.getRecyclerViewAdapter();
                    publishSeatTable.setLayoutManager(new GridLayoutManager(Publish.this,column ));
                    publishSeatTable.setAdapter(seatTableAdapter);
                    if (getIntent().getBooleanExtra("isItemDecoration", false)) {
                        itemDecoration = SeatTable.getRecyclerViewDecoration();
                        publishSeatTable.addItemDecoration(itemDecoration);
                        isItemDecoration = true;
                    } else {
                        if (isItemDecoration)
                            publishSeatTable.removeItemDecoration(itemDecoration);
                    }
                    isSetSeatTable = true;
                    publishSeatScroll.setVisibility(View.VISIBLE);
                    publishSeatTableToast.setVisibility(View.GONE);
                }
            });
        }
        if(getIntent().getBooleanExtra("hasPublishModel",false)){
            Publish.this.runOnUiThread(new Runnable() {//图片和座位纵向有问题
                @Override
                public void run() {
                    column = getIntent().getIntExtra("seat_column",0);
                    publishTitleEdit.setText(getIntent().getStringExtra("publish_title"));
                    publishStartTimeText.setText(getIntent().getStringExtra("start_time"));
                    publishStopTimeText.setText(getIntent().getStringExtra("stop_time"));
                    publishPeopleNumberEdit.setText(String.valueOf(getIntent().getIntExtra("people_number",0)));
                    publishPlaceEdit.setText(getIntent().getStringExtra("publish_place"));
                    seatTableAdapter = SeatTable.getRecyclerViewAdapter();
                    publishSeatTable.setLayoutManager(new GridLayoutManager(Publish.this, column));
                    publishSeatTable.setAdapter(seatTableAdapter);
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        publishInformationEdit.setText(Html.fromHtml(getIntent().getStringExtra("information_text"),Html.FROM_HTML_MODE_LEGACY));
                    } else {
                        publishInformationEdit.setText(Html.fromHtml(getIntent().getStringExtra("information_text")));
                    }

                    isSetSeatTable = true;
                    publishSeatScroll.setVisibility(View.VISIBLE);
                    publishSeatTableToast.setVisibility(View.GONE);
                }
            });
        }


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        cameraProxy.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        cameraProxy.onSaveInstanceState(outState);
    }

    private void insertImage() {
        if (bitmap != null) {
            start = publishInformationEdit.getSelectionStart();
            end = publishInformationEdit.getSelectionEnd();
            if (start != end) {
                SpannableStringBuilder newSub = MyRichEditUtil.InsertImageSpan(publishInformationEdit.getText().subSequence(start, end), bitmap);
                publishInformationEdit.getEditableText().replace(start, end, newSub);
            } else {
                SpannableStringBuilder newSub = MyRichEditUtil.InsertImageSpan("a", bitmap);
                publishInformationEdit.getEditableText().insert(start, newSub);
            }
        }
    }
}
