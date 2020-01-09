package ac.scri.com.donghaoproect.activity;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2020/1/2
 * <p>
 * 版本号：donghaoProect
 */
public class PollingService extends Service {

    public static final String ACTION = "com.ryantang.service.PollingService";


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
       // initNotifiManager();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        new PollingThread().start();
    }


    /**
     * Polling thread
     * 模拟向Server轮询的异步线程
     * @Author Ryan
     * @Create 2013-7-13 上午10:18:34
     */
    class PollingThread extends Thread {
        @Override
        public void run() {
            Log.e("linfd","New message!");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Service:onDestroy");
    }

}

