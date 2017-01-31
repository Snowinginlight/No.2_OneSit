package minework.onesit.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import minework.onesit.fragment.news.NewsFragment;
import minework.onesit.util.MyRomateSQLUtil;

/**
 * Created by 无知 on 2017/1/17.
 */

public class CommService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
