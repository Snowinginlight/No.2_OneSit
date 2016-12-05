package minework.onesit.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.NumberPicker;
import cn.qqtheme.framework.picker.OptionPicker;
import minework.onesit.R;
import minework.onesit.fragment.plan.DividerItemDecoration;
import minework.onesit.fragment.plan.SeatTableAdapter;

/**
 * Created by 无知 on 2016/11/26.
 */

public class SeatTable extends BaseActivity implements View.OnClickListener {
    private static int row = 1;
    private static int column = 1;
    private static boolean isItemDecoration = false;
    private static RecyclerView mRecyclerView;
    private static DividerItemDecoration decor = null;
    private static List<Integer> mDatas;
    private static SeatTableAdapter mAdapter;
    private static TextView seatPosition;
    private boolean isToastPreview = false;
    private Button seatBack;
    private Button seatMenu;
    private Button seatAdd;
    private Button seatMinus;
    private Button seatGrid;
    private Button seatScale;
    private TextView seatColumn;
    private TextView seatRow;
    private Context mContext;
    //菜单
    private PopupWindow seatMenuWindow;
    private View seatMenuView;
    private Button seatFinish;
    private Button seatSave;
    private Button seatImport;
    private Animation clockAnimation;
    private Animation counter_clockAnimation;

    public static void setSeatPosition(int position) {
        if (position < column) {
            seatPosition.setText(String.valueOf(1) + "," + String.valueOf(position + 1));
        } else {
            if ((position + 1) % column != 0) {
                seatPosition.setText(String.valueOf(position / column + 1) + "," + String.valueOf((position + 1) % column));
            } else {
                seatPosition.setText(String.valueOf((position + 1) / column) + "," + String.valueOf(column));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seat_table_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.seat_view);
        mRecyclerView.setDrawingCacheEnabled(true);
        mContext = MyApplication.getInstance();
        init();
        initRecycleView();
    }

    @Override
    protected void init() {
        seatBack = (Button) findViewById(R.id.seat_back);
        seatMenu = (Button) findViewById(R.id.seat_menu);
        seatAdd = (Button) findViewById(R.id.seat_add);
        seatMinus = (Button) findViewById(R.id.seat_minus);
        seatGrid = (Button) findViewById(R.id.seat_grid);
        seatScale = (Button) findViewById(R.id.seat_scale);
        seatRow = (TextView) findViewById(R.id.seat_row);
        seatColumn = (TextView) findViewById(R.id.seat_column);
        seatPosition = (TextView) findViewById(R.id.seat_position);

        seatRow.setText(String.valueOf(row));
        seatColumn.setText(String.valueOf(column));

        seatBack.setOnClickListener(this);
        seatMenu.setOnClickListener(this);
        seatAdd.setOnClickListener(this);
        seatMinus.setOnClickListener(this);
        seatGrid.setOnClickListener(this);
        seatScale.setOnClickListener(this);
        seatColumn.setOnClickListener(this);
        seatRow.setOnClickListener(this);

        clockAnimation = AnimationUtils.loadAnimation(this, R.anim.clockwise_rotate_90);
        clockAnimation.setDuration(500);
        clockAnimation.setFillAfter(true);
        counter_clockAnimation = AnimationUtils.loadAnimation(this, R.anim.counter_clockwise_rotate_90);
        counter_clockAnimation.setDuration(500);
        counter_clockAnimation.setFillAfter(true);
    }

    protected void initRecycleView() {
        initData(row * column);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, column));
        mRecyclerView.setAdapter(mAdapter = new SeatTableAdapter(mDatas));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    protected void initData(int totalNum) {
        mDatas = new ArrayList<Integer>();
        for (int i = 0; i < totalNum; i++) {
            mDatas.add(R.drawable.seat_gray);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.seat_back:
                onBackPressed();
                break;
            case R.id.seat_menu:

                if (seatMenuWindow != null && seatMenuWindow.isShowing()) {
                    seatMenuWindow.dismiss();
                    seatMenu.startAnimation(clockAnimation);
                    return;
                } else {
                    if (seatMenuWindow == null) {
                        initSeatMenuWindow();
                    }
                    seatMenuWindow.showAsDropDown(seatMenu, 0, 20);
                    seatMenu.startAnimation(counter_clockAnimation);
                }
                break;
            case R.id.seat_add:
                if (mAdapter.getItemCount() > 0) {
                    mAdapter.addData(0);
                }
                if ((mAdapter.getItemCount() - 1) - (column * row) != 0) {
                    seatRow.setText(String.valueOf(row));
                } else {
                    seatRow.setText(String.valueOf(row + 1));
                    row++;
                }
                if (isItemDecoration) {
                    mRecyclerView.removeItemDecoration(decor);
                    mRecyclerView.addItemDecoration(decor);
                }
                break;
            case R.id.seat_minus:
                if (mAdapter.getItemCount() > 1) {
                    mAdapter.removeData(mAdapter.getItemCount() - 1);
                }
                if (mAdapter.getItemCount() - (column * (row - 1)) == 0) {
                    seatRow.setText(String.valueOf(row - 1));
                    row--;
                } else {
                    seatRow.setText(String.valueOf(row));
                }
                if (isItemDecoration) {
                    mRecyclerView.removeItemDecoration(decor);
                    mRecyclerView.addItemDecoration(decor);
                }
                break;
            case R.id.seat_grid:
                if (isItemDecoration == false && mAdapter.getItemCount() > 0) {
                    decor = new DividerItemDecoration(this, column);
                    mRecyclerView.addItemDecoration(decor);
                    seatGrid.setBackgroundResource(R.drawable.grid_green);
                    isItemDecoration = true;
                } else {
                    mRecyclerView.removeItemDecoration(decor);
                    seatGrid.setBackgroundResource(R.drawable.grid_gray);
                    isItemDecoration = false;
                }
                break;
            case R.id.seat_scale:
                if (mRecyclerView.getDrawingCache() != null) {
                    mRecyclerView.destroyDrawingCache();
                }
                mRecyclerView.setItemViewCacheSize(mAdapter.getItemCount());
                AlertDialog.Builder scaleView = new AlertDialog.Builder(this);
                ImageView imageView = new ImageView(this);
                imageView.setImageBitmap(mRecyclerView.getDrawingCache());
                scaleView.setView(imageView);
                AlertDialog preview = scaleView.create();
                preview.show();
                break;
            case R.id.seat_row:
                NumberPicker picker1 = new NumberPicker(this);
                picker1.setOffset(1);//偏移量
                picker1.setRange(1, 1000);//数字范围
                picker1.setSelectedItem(row);
                picker1.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(String option) {
                        seatRow.setText(option);
                        row = Integer.valueOf(option);
                        initRecycleView();
                        if (isItemDecoration) {
                            mRecyclerView.removeItemDecoration(decor);
                            mRecyclerView.addItemDecoration(decor);
                        }
                    }
                });
                picker1.show();
                break;
            case R.id.seat_column:
                NumberPicker picker2 = new NumberPicker(this);
                picker2.setOffset(1);//偏移量
                picker2.setRange(1, 1000);//数字范围
                picker2.setSelectedItem(column);
                picker2.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(String option) {
                        seatColumn.setText(option);
                        column = Integer.valueOf(option);
                        initRecycleView();
                        if (isItemDecoration) {
                            mRecyclerView.removeItemDecoration(decor);
                            mRecyclerView.addItemDecoration(decor);
                        }
                    }
                });
                picker2.show();
                break;
            case R.id.seat_finish:
                Intent intent = new Intent(this,Publish.class);
                intent.putExtra("hasSeatTable",true);
                intent.putExtra("column",column);
                intent.putExtra("isItemDecoration",isItemDecoration);
                startActivity(intent);
                break;
            case R.id.seat_save:

                break;
            case R.id.seat_import:
                break;
            default:
                return;
        }
    }

    private void initSeatMenuWindow() {
        seatMenuView = getLayoutInflater().inflate(R.layout.seat_menu_layout, null, false);
        seatMenuWindow = new PopupWindow(seatMenuView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        seatMenuWindow.setAnimationStyle(R.style.AnimationFade);
        seatMenuWindow.setOutsideTouchable(true);
        seatSave = (Button) seatMenuView.findViewById(R.id.seat_save);
        seatFinish = (Button) seatMenuView.findViewById(R.id.seat_finish);
        seatImport = (Button) seatMenuView.findViewById(R.id.seat_import);
        seatSave.setOnClickListener(this);
        seatFinish.setOnClickListener(this);
        seatImport.setOnClickListener(this);
    }
    public static SeatTableAdapter getRecyclerViewAdapter(){
        return mAdapter;
    }
    public static DividerItemDecoration getRecyclerViewDecoration(){
        return decor;
    }

}


