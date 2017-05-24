package minework.onesit.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.maxleap.DeleteCallback;
import com.maxleap.FindCallback;
import com.maxleap.GetCallback;
import com.maxleap.MLDataManager;
import com.maxleap.MLQuery;
import com.maxleap.MLQueryManager;
import com.maxleap.SaveCallback;
import com.maxleap.exception.MLException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import minework.onesit.activity.Main;
import minework.onesit.activity.MyApplication;
import minework.onesit.database.MyDatabaseUtil;
import minework.onesit.module.News;
import minework.onesit.module.Publish;
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
        MLQueryManager.getFirstInBackground(query, new GetCallback<User>() {
            public void done(User user, MLException e) {
                if (e == null) {
                    Toast.makeText(mContext, "登陆成功!", Toast.LENGTH_SHORT).show();
                    MyDatabaseUtil.insertUser(user, bitmapUrlToFilePath(user.getUser_image()), "true");
                    Message message = new Message();
                    message.what = 0;
                    message.obj = true;
                    handler.sendMessage(message);
                } else {
                    Toast.makeText(mContext, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    Log.d("test", e.getMessage());
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
                        final User user = new User();
                        user.setUser_id(user_id);
                        user.setUser_name("我的昵称");
                        user.setUser_signature("我的签名");
                        user.setUser_password(user_password);
                        user.setUser_email(user_email);
                        user.setUser_image("我的头像");
                        user.addUser_publish(null);
                        user.addUser_care(null);
                        user.addUser_book(null);
                        MLDataManager.saveInBackground(user, new SaveCallback() {
                            @Override
                            public void done(MLException e) {
                                if (e == null) {
                                    Toast.makeText(mContext, "注册成功！", Toast.LENGTH_SHORT).show();
                                    MyDatabaseUtil.insertUser(user, "", "true");
                                    Message message = new Message();
                                    message.what = 0;
                                    message.obj = true;
                                    handler.sendMessage(message);
                                    return;
                                } else {
                                    Log.d("test", e.getMessage());
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
        query.whereEqualTo("user_id", MyDatabaseUtil.queryUserExist().get("user_id"));
        query.orderByDescending("createdAt");
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
                }
            }
        });
    }

    public static void saveSeatTable(String title, List<Integer> mDatas, int row, int column) {
        Seat seat = new Seat();
        seat.setUser_id(MyDatabaseUtil.queryUserExist().get("user_id"));
        seat.setSeat_title(title);
        seat.setSeat_table(mDatas);
        seat.setSeat_row(row);
        seat.setSeat_column(column);
        MLDataManager.saveInBackground(seat, new SaveCallback() {
            @Override
            public void done(MLException e) {
                if (e == null) {
                    Toast.makeText(mContext, "上传成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "上传失败，请重试！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void savePublishModel(String title, List<Integer> mDatas, int row, int column, String start_time, String stop_time, int people_number, String plan_place, String information_text, List<String> pictures) {
        PublishModel publishModel = new PublishModel();
        publishModel.setUser_id(MyDatabaseUtil.queryUserExist().get("user_id"));
        publishModel.setPublish_Model_title(title);
        publishModel.setSeat_table(mDatas);
        publishModel.setSeat_column(column);
        publishModel.setSeat_row(row);
        publishModel.setStart_time(start_time);
        publishModel.setStop_time(stop_time);
        publishModel.setPeople_number(people_number);
        publishModel.setPublish_Model_place(plan_place);
        publishModel.setInformation_text(information_text);
        publishModel.setPictures(pictures);
        MLDataManager.saveInBackground(publishModel, new SaveCallback() {
            @Override
            public void done(MLException e) {
                if (e == null) {
                    Toast.makeText(mContext, "上传成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "上传失败，请重试！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void getPublishModelList(final Handler handler) {
        MLQuery<PublishModel> query = MLQuery.getQuery("OneSit_PublishModel");
        query.whereEqualTo("user_id", MyDatabaseUtil.queryUserExist().get("user_id"));
        MLQueryManager.findAllInBackground(query, new FindCallback<PublishModel>() {
            public void done(List<PublishModel> publishModelList, MLException e) {
                if (e == null) {
                    Message message = new Message();
                    message.what = 0;
                    message.obj = publishModelList;
                    handler.sendMessage(message);
                } else {
                    Message message = new Message();
                    message.what = -1;
                    handler.sendMessage(message);
                }
            }
        });
    }


    public static void savePublish(String title, List<Integer> mDatas, int row, int column, String start_time, String stop_time, int people_number, String plan_place, String information_text, List<String> pictures) {
        Publish publish = new Publish();
        publish.setUser_id(MyDatabaseUtil.queryUserExist().get("user_id"));
        publish.setPublish_title(title);
        publish.setSeat_table(mDatas);
        publish.setSeat_row(row);
        publish.setSeat_column(column);
        publish.setStart_time(start_time);
        publish.setStop_time(stop_time);
        publish.setPeople_number(people_number);
        publish.setPublish_place(plan_place);
        publish.setInformation_text(information_text);
        publish.setPictures(pictures);
        MLDataManager.saveInBackground(publish, new SaveCallback() {
            @Override
            public void done(MLException e) {
                if (e == null) {
                    Toast.makeText(mContext, "发布成功", Toast.LENGTH_SHORT).show();
                    mContext.startActivity(new Intent(mContext, Main.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                } else {
                    Toast.makeText(mContext, "发布失败，请重试！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void getMarketList() {
        MLQuery<Publish> query = MLQuery.getQuery("OneSit_Publish");
        query.whereStartsWith("start_time", DateTime.now().toString(DateTimeFormat.forPattern("yyyy年MM")));
        MLQueryManager.findAllInBackground(query, new FindCallback<Publish>() {
            public void done(List<Publish> publishList, MLException e) {
                if (e == null) {
                    for (Publish publish : publishList) {
                        MyDatabaseUtil.insertMarket(publish, MyUtil.listToString(publish.getPictures()));
                    }
                } else {
                    Log.d("Test", e.getMessage());
                }
            }
        });
    }

    public static void getCareList() {
        for (int i = 0; i < MyDatabaseUtil.queryUserExist().get("user_care").split(",").length; i++) {
            MLQuery<User> query = MLQuery.getQuery("OneSit_User");
            String objId = MyDatabaseUtil.queryUserExist().get("user_care").split(",")[i];
            MLQueryManager.getInBackground(query, objId, new GetCallback<User>() {
                @Override
                public void done(User user, MLException e) {
                    if (e == null) {
                        MLQuery<Publish> query = MLQuery.getQuery("OneSit_Publish");
                        query.whereEqualTo("user_id", user.getUser_id());
                        MLQueryManager.findAllInBackground(query, new FindCallback<Publish>() {
                            public void done(List<Publish> publishList, MLException e) {
                                if (e == null) {
                                    for (Publish publish : publishList) {
                                        MyDatabaseUtil.insertCare(publish, MyUtil.listToString(publish.getPictures()));
                                    }
                                } else {
                                    Toast.makeText(mContext, "请求失败，请重试", Toast.LENGTH_SHORT).show();
                                    Log.d("Test", "publish" + e.getMessage());
                                }
                            }
                        });
                    } else {
                        Log.d("Test", "user" + e.getMessage());
                    }
                }
            });
        }
    }

    public static void getUserPublishList(final Handler handler) {
        MLQuery<Publish> query = MLQuery.getQuery("OneSit_Publish");
        query.whereEqualTo("user_id", MyDatabaseUtil.queryUserExist().get("user_id"));
        MLQueryManager.findAllInBackground(query, new FindCallback<Publish>() {
            public void done(List<Publish> publishList, MLException e) {
                if (e == null) {
                    Message message = new Message();
                    message.what = 0;
                    message.obj = publishList;
                    handler.sendMessage(message);
                } else {
                    Message message = new Message();
                    message.what = -1;
                    handler.sendMessage(message);
                }
            }
        });
    }

    public static void getPublish(String object_id, final Handler handler) {
        MLQuery<Publish> query = MLQuery.getQuery("OneSit_Publish");
        String objId = object_id;
        MLQueryManager.getInBackground(query, objId, new GetCallback<Publish>() {
            @Override
            public void done(Publish publish, MLException e) {
                if (e == null) {
                    Message msg = new Message();
                    msg.what = 0;
                    msg.obj = publish;
                    handler.sendMessage(msg);
                    Log.d("Test", "publish");
                } else {
                    Log.d("Test", e.getMessage());
                }
            }
        });
    }

    public static void getSearchUser(String user_id, final Handler handler){
        MLQuery<User> query = MLQuery.getQuery("OneSit_User");
        query.whereEqualTo("user_id", user_id);
        MLQueryManager.getFirstInBackground(query, new GetCallback<User>() {
            @Override
            public void done(User user, MLException e) {
                if (e == null) {
                    Message msg = new Message();
                    msg.what = 5;
                    msg.obj = user;
                    handler.sendMessage(msg);
                    Log.d("Test", "publish");
                } else {
                    Log.d("Test", e.getMessage());
                }
            }
        });
    }

    public static void getSearchPublish(String object_id, final Handler handler){
        MLQuery<Publish> query = MLQuery.getQuery("OneSit_Publish");
        String objId = object_id;
        MLQueryManager.getInBackground(query, objId, new GetCallback<Publish>() {
            @Override
            public void done(Publish publish, MLException e) {
                if (e == null) {
                    Message msg = new Message();
                    msg.what = 4;
                    msg.obj = publish;
                    handler.sendMessage(msg);
                    Log.d("Test", "publish");
                } else {
                    Log.d("Test", e.getMessage());
                }
            }
        });
    }

    public static void getLastPublish(String user_id, final Handler handler) {
        MLQuery<Publish> query = MLQuery.getQuery("OneSit_Publish");
        query.whereEqualTo("user_id", user_id);
        query.orderByDescending("createdAt");
        MLQueryManager.getFirstInBackground(query, new GetCallback<Publish>() {
            @Override
            public void done(Publish publish, MLException e) {
                if (e == null) {
                    Message msg = new Message();
                    msg.what = 3;
                    msg.obj = publish;
                    handler.sendMessage(msg);
                } else {
                    Log.d("Test", e.getMessage());
                }
            }
        });
    }

    public static void savePublish(String object_id, final List<Integer> seat, final String position) {
        MLQuery<Publish> query = MLQuery.getQuery("OneSit_Publish");
        String objId = object_id;
        MLQueryManager.getInBackground(query, objId, new GetCallback<Publish>() {
            @Override
            public void done(final Publish publish, MLException e) {
                if (e == null) {
                    publish.setSeat_table(seat);
                    publish.setJoin_list(MyDatabaseUtil.queryUserExist().get("user_name"), position);
                    MLDataManager.saveInBackground(publish, new SaveCallback() {
                        @Override
                        public void done(MLException e) {
                            Toast.makeText(mContext, "选择成功！", Toast.LENGTH_SHORT).show();
                            Map<String, String> plan = new HashMap<String, String>();
                            //plan.put("plan_id", String.valueOf(planSelfNum));
                            plan.put("plan_title", publish.getPublish_title());
                            plan.put("start_time", publish.getStart_time());
                            plan.put("stop_time", publish.getStop_time());
                            plan.put("remind_time", publish.getStart_time());
                            plan.put("plan_place", publish.getPublish_place());
                            plan.put("plan_tips", publish.getInformation_text());
                            MyDatabaseUtil.insertPlanSelf(plan);
                            mContext.startActivity(new Intent(mContext, Main.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        }
                    });
                }
            }
        });
    }

    public static void getUserInformation(final String user_id) {
        MLQuery<User> query = MLQuery.getQuery("OneSit_User");
        query.whereEqualTo("user_id", user_id);
        MLQueryManager.getFirstInBackground(query, new GetCallback<User>() {
            @Override
            public void done(User user, MLException e) {
                if (e == null) {
                    MyDatabaseUtil.insertUser(user, bitmapUrlToFilePath(user.getUser_image()), "false");
                } else {
                    Log.d("Test", e.getMessage());
                }
            }
        });
    }
    public static void getUserName(final String object_id) {
        MLQuery<User> query = MLQuery.getQuery("OneSit_User");
        query.whereEqualTo("object_id", object_id);
        MLQueryManager.getFirstInBackground(query, new GetCallback<User>() {
            @Override
            public void done(User user, MLException e) {
                if (e == null) {

                } else {
                    Log.d("Test", e.getMessage());
                }
            }
        });
    }

    public static void getUserInformation(final String user_id, final Handler handler) {
        MLQuery<User> query = MLQuery.getQuery("OneSit_User");
        query.whereEqualTo("user_id", user_id);
        MLQueryManager.getFirstInBackground(query, new GetCallback<User>() {
            @Override
            public void done(User user, MLException e) {
                if (e == null) {
                    MyDatabaseUtil.insertUser(user, bitmapUrlToFilePath(user.getUser_image()), "false",handler);
                } else {
                    Log.d("Test", e.getMessage());
                }
            }
        });
    }

    public static void saveUserInformation(final String user_id, final String user_image, final String user_name, final String user_signature, final Handler handler) {
        MLQuery<User> query = MLQuery.getQuery("OneSit_User");
        query.whereEqualTo("user_id", user_id);
        MLQueryManager.getFirstInBackground(query, new GetCallback<User>() {
            @Override
            public void done(User user, MLException e) {
                if (e == null) {
                    if (user_image != null) {
                        user.setUser_image(user_image);
                    }
                    user.setUser_name(user_name);
                    user.setUser_signature(user_signature);
                    MLDataManager.saveInBackground(user, new SaveCallback() {
                        @Override
                        public void done(MLException e) {
                            if (e == null) {
                                Toast.makeText(mContext, "修改成功", Toast.LENGTH_SHORT).show();
                                if (user_image != null) {
                                    MyDatabaseUtil.updateUser(user_id, bitmapUrlToFilePath(user_image), "user_image");
                                }
                                MyDatabaseUtil.updateUser(user_id, user_name, "user_name");
                                MyDatabaseUtil.updateUser(user_id, user_signature, "user_signature");
                                Message message = new Message();
                                message.what = 0;
                                message.obj = true;
                                handler.sendMessage(message);
                            } else {
                                Toast.makeText(mContext, "修改失败，请重试！", Toast.LENGTH_SHORT).show();
                                Log.d("test", e.getMessage());
                            }
                        }
                    });
                } else {
                    Toast.makeText(mContext, "修改失败，请重试！", Toast.LENGTH_SHORT).show();
                    Log.d("Test", e.getMessage());
                }
            }
        });
    }

    public static void addAttention(final String object_id, final Handler handler) {
        MLQuery<User> query = MLQuery.getQuery("OneSit_User");
        String objId = MyDatabaseUtil.queryUserExist().get("object_id");
        MLQueryManager.getInBackground(query, objId, new GetCallback<User>() {
            @Override
            public void done(User user, MLException e) {
                if (e == null) {
                    user.addUser_care(object_id);
                    MLDataManager.saveInBackground(user, new SaveCallback() {
                        @Override
                        public void done(MLException e) {
                            if (e == null) {
                                MLQuery<User> query = MLQuery.getQuery("OneSit_User");
                                String objId = MyDatabaseUtil.queryUserExist().get("object_id");
                                MLQueryManager.getInBackground(query, objId, new GetCallback<User>() {
                                    @Override
                                    public void done(User user, MLException e) {
                                        if (e == null) {
                                            MyDatabaseUtil.updateUser(MyDatabaseUtil.queryUserExist().get("user_id"), MyUtil.listToString(user.getUser_care()), "user_care");
                                            MLQuery<User> query = MLQuery.getQuery("OneSit_User");
                                            String objId = object_id;
                                            MLQueryManager.getInBackground(query, objId, new GetCallback<User>() {
                                                @Override
                                                public void done(User user2, MLException e) {
                                                    if (e == null) {
                                                        user2.addUser_book(MyDatabaseUtil.queryUserExist().get("object_id"));
                                                        MLDataManager.saveInBackground(user2, new SaveCallback() {
                                                            @Override
                                                            public void done(MLException e) {
                                                                if (e == null) {
                                                                    Toast.makeText(mContext, "关注成功", Toast.LENGTH_SHORT).show();
                                                                    Message msg = new Message();
                                                                    msg.what = 1;
                                                                    handler.sendMessage(msg);
                                                                } else {
                                                                    Log.d("Test", "保存失败*2" + e.getMessage());
                                                                }
                                                            }
                                                        });
                                                    } else {
                                                        Log.d("Test", "找不到用户*2" + e.getMessage());
                                                    }
                                                }
                                            });
                                        } else {
                                            Log.d("Test", "返回失败" + e.getMessage());
                                        }
                                    }
                                });
                            } else {
                                Log.d("Test", "保存失败" + e.getMessage());
                            }
                        }
                    });
                } else {
                    Log.d("Test", "找不到用户" + e.getMessage());
                }
            }
        });
    }

    public static void removeAttention(final String object_id, final Handler handler) {
        MLQuery<User> query = MLQuery.getQuery("OneSit_User");
        String objId = MyDatabaseUtil.queryUserExist().get("object_id");
        MLQueryManager.getInBackground(query, objId, new GetCallback<User>() {
            @Override
            public void done(User user, MLException e) {
                if (e == null) {
                    user.removeUser_care(object_id);
                    MLDataManager.saveInBackground(user, new SaveCallback() {
                        @Override
                        public void done(MLException e) {
                            if (e == null) {
                                MLQuery<User> query = MLQuery.getQuery("OneSit_User");
                                String objId = MyDatabaseUtil.queryUserExist().get("object_id");
                                MLQueryManager.getInBackground(query, objId, new GetCallback<User>() {
                                    @Override
                                    public void done(User user, MLException e) {
                                        if (e == null) {
                                            MyDatabaseUtil.updateUser(MyDatabaseUtil.queryUserExist().get("user_id"), MyUtil.listToString(user.getUser_care()), "user_care");
                                            MLQuery<User> query = MLQuery.getQuery("OneSit_User");
                                            String objId = object_id;
                                            MLQueryManager.getInBackground(query, objId, new GetCallback<User>() {
                                                @Override
                                                public void done(User user2, MLException e) {
                                                    if (e == null) {
                                                        user2.removeUser_book(MyDatabaseUtil.queryUserExist().get("object_id"));
                                                        MLDataManager.saveInBackground(user2, new SaveCallback() {
                                                            @Override
                                                            public void done(MLException e) {
                                                                if (e == null) {
                                                                    Toast.makeText(mContext, "已取消", Toast.LENGTH_SHORT).show();
                                                                    Message msg = new Message();
                                                                    msg.what = 2;
                                                                    handler.sendMessage(msg);
                                                                }
                                                            }
                                                        });
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }

    public static void hasAttention(final String object_id, final Handler handler) {
        MLQuery<User> query = MLQuery.getQuery("OneSit_User");
        String objId = MyDatabaseUtil.queryUserExist().get("object_id");
        MLQueryManager.getInBackground(query, objId, new GetCallback<User>() {
            @Override
            public void done(User user, MLException e) {
                if (e == null) {
                    if (user.getUser_care() == null) {
                        Message msg = new Message();
                        msg.what = 0;
                        msg.obj = false;
                        handler.sendMessage(msg);
                    } else {
                        List<String> list = user.getUser_care();
                        boolean attention = false;
                        for (String string : list) {
                            if (Objects.equals(string, object_id)) {
                                attention = true;
                            }
                            Message msg = new Message();
                            msg.what = 0;
                            msg.obj = attention;
                            handler.sendMessage(msg);
                        }
                    }
                }
            }
        });
    }

    public static void getNews(final Handler handler) {
        MLQuery<News> query = MLQuery.getQuery("OneSit_News");
        query.whereEqualTo("receive_user_id", MyDatabaseUtil.queryUserExist().get("user_id"));
        query.whereEqualTo("news_obtain", false);
        query.orderByDescending("createdAt");
        Log.d("Test", "寻找消息");
        MLQueryManager.findAllInBackground(query, new FindCallback<News>() {
            public void done(final List<News> newsList, MLException e) {
                if (e == null) {
                    MLDataManager.deleteAllInBackground(newsList, new DeleteCallback() {
                        @Override
                        public void done(MLException e) {
                            if (e == null) {
                                for (News news : newsList) {
                                    MyDatabaseUtil.insertNews(news);
                                    if(MyDatabaseUtil.isUserExist(news.getSend_user_id())){
                                        Log.d("Test","here");
                                        Message msg = new Message();
                                        msg.what = 1;
                                        handler.sendMessage(msg);
                                    }else {
                                        getUserInformation(news.getSend_user_id(),handler);
                                    }
                                }
                            } else {
                                Log.d("Test", e.getMessage());
                            }
                        }
                    });
                } else {
                    Log.d("Test", e.getMessage());
                }
            }
        });
    }

    public static void sendNews(final String send_user_id, final String receive_user_id, String message, final Handler handler) {
        final News news = new News();
        news.setNews_date();
        news.setSend_user_id(send_user_id);
        news.setReceive_user_id(receive_user_id);
        news.setNews_message(message);
        news.setNews_obtain(false);
        MLDataManager.saveInBackground(news, new SaveCallback() {
            @Override
            public void done(MLException e) {
                if (e == null) {
                    MLQuery<News> query = MLQuery.getQuery("OneSit_News");
                    query.whereEqualTo("send_user_id", send_user_id);
                    query.whereEqualTo("receive_user_id", receive_user_id);
                    query.orderByDescending("createdAt");
                    MLQueryManager.getFirstInBackground(query, new GetCallback<News>() {
                        @Override
                        public void done(News thisNews, MLException e) {
                            if (e == null) {
                                MyDatabaseUtil.insertNews(thisNews);
                                Message msg = new Message();
                                msg.what = 1;
                                msg.obj = true;
                                handler.sendMessage(msg);
                            } else {
                                Toast.makeText(mContext, "发送失败！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(mContext, "发送失败！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void getSearchResult(String keyPoint, int type, final Handler handler){
        switch (type){
            case 0:
                MLQuery<Publish> query0 = MLQuery.getQuery("OneSit_Publish");
                query0.whereContains("publish_title",keyPoint);
                MLQueryManager.findAllInBackground(query0, new FindCallback<Publish>() {
                    public void done(List<Publish> publishList, MLException e) {
                        if (e == null) {
                            Message message = new Message();
                            message.what = 0;
                            message.obj = publishList;
                            handler.sendMessage(message);
                        } else {
                            Log.d("Test", e.getMessage());
                        }
                    }
                });
                break;
            case 1:
                MLQuery<User> query1 = MLQuery.getQuery("OneSit_User");
                query1.whereContains("user_name",keyPoint);
                MLQueryManager.findAllInBackground(query1, new FindCallback<User>() {
                    public void done(List<User> userList, MLException e) {
                        if (e == null) {
                            Message message = new Message();
                            message.what = 1;
                            message.obj = userList;
                            handler.sendMessage(message);
                        } else {
                            Log.d("Test", e.getMessage());
                        }
                    }
                });
                break;
            case 2:
                MLQuery<Seat> query2 = MLQuery.getQuery("OneSit_Seat");
                query2.whereContains("seat_title",keyPoint);
                MLQueryManager.findAllInBackground(query2, new FindCallback<Seat>() {
                    public void done(List<Seat> seatList, MLException e) {
                        if (e == null) {
                            Message message = new Message();
                            message.what = 2;
                            message.obj = seatList;
                            handler.sendMessage(message);
                        } else {
                            Log.d("Test", e.getMessage());
                        }
                    }
                });
                break;
        }
    }

    public static String bitmapUrlToFilePath(final String bitmapUrl) {
        String sdCardDir = Environment.getExternalStorageDirectory() + "/OneSit/temp/";
        File dirFile = new File(sdCardDir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        final File file = new File(sdCardDir, System.currentTimeMillis() + ".png");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://" + bitmapUrl);
                    InputStream is = url.openStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                    FileOutputStream out = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                    out.flush();
                    out.close();
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }).start();
        return file.getPath();
    }

    public static void getBitmap(final String bitmapUrl, final int i, final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://" + bitmapUrl);
                    InputStream is = url.openStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    Message msg = new Message();
                    msg.what = 6;
                    msg.arg1 = i;
                    msg.obj = bitmap;
                    handler.sendMessage(msg);
                    is.close();
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }).start();
    }
}
