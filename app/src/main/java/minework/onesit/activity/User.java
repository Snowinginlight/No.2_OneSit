package minework.onesit.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.maxleap.MLFile;
import com.maxleap.MLFileManager;
import com.maxleap.SaveCallback;
import com.maxleap.exception.MLException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Map;

import minework.onesit.R;
import minework.onesit.database.MyDatabaseUtil;
import minework.onesit.util.MyRomateSQLUtil;
import minework.onesit.util.camera.CameraCore;
import minework.onesit.util.camera.CameraProxy;

/**
 * Created by 无知 on 2016/12/19.
 */

public class User extends BaseActivity implements View.OnClickListener, CameraCore.CameraResult {

    private Context mContext;
    private Map<String,String> user = null;
    private CameraProxy cameraProxy;
    private Button userBack;
    private Button userFinish;
    private View userImageButton;
    private View userNameButton;
    private View userSignatureButton;
    private ImageView userImageEdit;
    private TextView userNameEdit;
    private TextView userIdText;
    private TextView userSignatureEdit;
    private TextView userEmialText;
    private String url = null;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    if ((boolean) message.obj) {
                        Intent intent = new Intent(mContext,Main.class);
                        intent.putExtra("updataUser",true);
                        startActivity(intent);
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
        setContentView(R.layout.user_layout);
        cameraProxy = new CameraProxy(this, User.this);
        mContext = MyApplication.getInstance();
        init();
        initData();
    }

    @Override
    protected void init() {
        userBack = (Button) findViewById(R.id.user_back);
        userFinish = (Button) findViewById(R.id.user_finish);
        userImageButton = findViewById(R.id.user_image_button);
        userImageEdit = (ImageView) findViewById(R.id.user_image_edit);
        userNameButton = findViewById(R.id.user_name_button);
        userNameEdit = (TextView) findViewById(R.id.user_name_edit);
        userIdText = (TextView) findViewById(R.id.user_id_text);
        userSignatureButton = findViewById(R.id.user_signature_button);
        userSignatureEdit = (TextView) findViewById(R.id.user_signature_edit);
        userEmialText = (TextView) findViewById(R.id.user_email_text);

        userBack.setOnClickListener(this);
        userFinish.setOnClickListener(this);
        userImageButton.setOnClickListener(this);
        userNameButton.setOnClickListener(this);
        userSignatureButton.setOnClickListener(this);
    }

    private void initData() {
        user = MyDatabaseUtil.queryUserExist();
        userIdText.setText(user.get("user_id"));
        userNameEdit.setText(user.get("user_name"));
        userSignatureEdit.setText(user.get("user_signature"));
        userEmialText.setText(user.get("user_email"));
        if (!user.get("user_image").isEmpty()) {;
            userImageEdit.setImageBitmap(BitmapFactory.decodeFile(user.get("user_image")));
        } else {
            userImageEdit.setImageResource(R.mipmap.mine_icon);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_back:
                onBackPressed();
                break;
            case R.id.user_finish:
                MyRomateSQLUtil.saveUserInformation(userIdText.getText().toString(),url, userNameEdit.getText().toString(), userSignatureEdit.getText().toString(), mHandler);
                break;
            case R.id.user_image_button:
                String[] items = {"拍照", "本地图片"};
                AlertDialog.Builder imageBulider = new AlertDialog.Builder(this);
                imageBulider.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        switch (which)  {
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
            case R.id.user_name_button:
                startActivity(new Intent(this, EditActivity.class).putExtra("name", "昵称").putExtra("activity","User"));
                break;
            case R.id.user_signature_button:
                startActivity(new Intent(this, EditActivity.class).putExtra("name", "签名").putExtra("activity","User"));
                break;
            default:
                return;
        }
    }

    @Override
    public void onSuccess(final String filePath) {
        final File file = new File(filePath);
        if (file.exists()) {
            new Thread() {
                public void run() {
                    final Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                    User.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            userImageEdit.setImageBitmap(bitmap);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            byte[] image = stream.toByteArray();
                            final MLFile myFile = new MLFile(System.currentTimeMillis() + ".png", image);
                            MLFileManager.saveInBackground(myFile, new SaveCallback() {
                                @Override
                                public void done(MLException e) {
                                    Toast.makeText(mContext,"图片上传成功",Toast.LENGTH_SHORT).show();
                                    url = myFile.getUrl();
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
    protected void onResume() {
        super.onResume();
        if(!TextUtils.isEmpty(getIntent().getStringExtra("content"))){
            switch (getIntent().getStringExtra("name")) {
                case "昵称":
                    userNameEdit.setText(getIntent().getStringExtra("content"));
                    break;
                case "签名":
                    userSignatureEdit.setText(getIntent().getStringExtra("content"));
                    break;
                default:
                    return;
            }
        }
    }
}
