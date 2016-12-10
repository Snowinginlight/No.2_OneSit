package minework.onesit.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.maxleap.FindCallback;
import com.maxleap.MLDataManager;
import com.maxleap.MLQuery;
import com.maxleap.MLQueryManager;
import com.maxleap.SaveCallback;
import com.maxleap.exception.MLException;

import java.util.List;

import minework.onesit.activity.MyApplication;
import minework.onesit.module.PublishModel;
import minework.onesit.module.Seat;
import minework.onesit.module.User;

/**
 * Created by 无知 on 2016/11/24.
 */

public class MyRomateSQLUtil {

    private static boolean LoginOK = false;
    private static boolean SignUpOK = false;
    private static Context mContext = MyApplication.getInstance();

    public static void clickLogin(final String user_id, final String user_password, final Handler handler) {
        MLQuery<User> query = MLQuery.getQuery("OneSit_User");
        query.whereEqualTo("user_id", user_id);
        query.whereEqualTo("user_password", user_password);
        MLQueryManager.findAllInBackground(query, new FindCallback<User>() {
            public void done(List<User> scoreList, MLException e) {
                if (e == null) {
                    Toast.makeText(mContext, "登陆成功!", Toast.LENGTH_SHORT).show();
                    SharedPreferences localUser = mContext.getSharedPreferences("local_user",
                            Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = localUser.edit();
                    editor.putString("user_id", user_id);
                    editor.putString("user_password", user_password);
                    editor.commit();
                    Message message = new Message();
                    message.what = 0;
                    message.obj = true;
                    handler.sendMessage(message);
                } else {
                    Toast.makeText(mContext, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void signUp(final String user_id, final String user_password, String confirm_password, final String user_email, final Handler handler) {
        if (!user_password.equals(confirm_password)) {
            Toast.makeText(mContext, "两次密码不一致", Toast.LENGTH_SHORT).show();
        } else {
            MLQuery<User> query = MLQuery.getQuery("OneSit_User");
            query.whereEqualTo("user_id", user_id);
            MLQueryManager.findAllInBackground(query, new FindCallback<User>() {
                public void done(List<User> scoreList, MLException e) {
                    if (e == null) {
                        Toast.makeText(mContext, "该用户已存在！", Toast.LENGTH_SHORT).show();
                    } else {
                        User user = new User();
                        user.setUser_id(user_id);
                        user.setUser_password(user_password);
                        user.setUser_email(user_email);
                        MLDataManager.saveInBackground(user, new SaveCallback() {
                            @Override
                            public void done(MLException e) {
                                if (e == null) {
                                    Toast.makeText(mContext, "注册成功！", Toast.LENGTH_SHORT).show();
                                    SharedPreferences localUser = mContext.getSharedPreferences("local_user",
                                            Activity.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = localUser.edit();
                                    editor.putString("user_id", user_id);
                                    editor.putString("user_password", user_password);
                                    editor.commit();
                                    Message message = new Message();
                                    message.what = 0;
                                    message.obj = true;
                                    handler.sendMessage(message);
                                    return;
                                } else {
                                    Toast.makeText(mContext, "注册失败，请重试！", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    public static void getSeatTableList(final Handler handler) {
        MLQuery<Seat> query = MLQuery.getQuery("OneSit_Seat");
        query.whereEqualTo("user_id", MyLocalSQLUtil.getLocalUserId());
        MLQueryManager.findAllInBackground(query, new FindCallback<Seat>() {
            public void done(List<Seat> seatTableList, MLException e) {
                if (e == null) {
                    Message message = new Message();
                    message.what = 0;
                    message.obj = seatTableList;
                    handler.sendMessage(message);
                } else {
                    Message message = new Message();
                    message.what = -1;
                    handler.sendMessage(message);
                    Log.d("result", "Error: " + e.getMessage());
                }
            }
        });
    }

    public static void saveSeatTable(String title, List<Integer> mDatas, int row, int column, boolean decoration) {
        Seat seat = new Seat();
        seat.setUser_id(MyLocalSQLUtil.getLocalUserId());
        seat.setSeat_title(title);
        seat.setSeat_table(mDatas);
        seat.setSeat_row(row);
        seat.setSeat_column(column);
        seat.setSeat_divider(decoration);
        MLDataManager.saveInBackground(seat, new SaveCallback() {
            @Override
            public void done(MLException e) {
                if (e == null) {
                    Toast.makeText(mContext, "上传成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "上传失败，请重试！", Toast.LENGTH_SHORT).show();
                    Log.d("result", "Error: " + e.getMessage());
                }
            }
        });
    }

    public static void savePublishModel(String title, List<Integer> mDatas,int column, String start_time, String stop_time, int people_number, String plan_place, String information_text) {
        PublishModel publishModel = new PublishModel();
        publishModel.setUser_id(MyLocalSQLUtil.getLocalUserId());
        publishModel.setPublish_Model_title(title);
        publishModel.setSeat_table(mDatas);
        publishModel.setSeat_column(column);
        publishModel.setStart_time(start_time);
        publishModel.setStop_time(stop_time);
        publishModel.setPeople_number(people_number);
        publishModel.setPublish_Model_place(plan_place);
        publishModel.setInformation_text(information_text);
        MLDataManager.saveInBackground(publishModel, new SaveCallback() {
            @Override
            public void done(MLException e) {
                if (e == null) {
                    Toast.makeText(mContext, "上传成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "上传失败，请重试！", Toast.LENGTH_SHORT).show();
                    Log.d("result", "Error: " + e.getMessage());
                }
            }
        });
    }

    public static void getPublishModelList(final Handler handler) {
        MLQuery<PublishModel> query = MLQuery.getQuery("OneSit_PublishModel");
        query.whereEqualTo("user_id", MyLocalSQLUtil.getLocalUserId());
        MLQueryManager.findAllInBackground(query, new FindCallback<PublishModel>() {
            public void done(List<PublishModel> publishModelModelList, MLException e) {
                if (e == null) {
                    Message message = new Message();
                    message.what = 0;
                    message.obj = publishModelModelList;
                    handler.sendMessage(message);
                } else {
                    Message message = new Message();
                    message.what = -1;
                    handler.sendMessage(message);
                    Log.d("result", "Error: " + e.getMessage());
                }
            }
        });
    }
}
