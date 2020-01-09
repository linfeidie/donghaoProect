package ac.scri.com.donghaoproect.manager;


import android.os.Handler;

import ac.scri.com.donghaoproect.Contanst;
import ac.scri.com.donghaoproect.DonghaoApplication;

/**
 * 文件描述：.
 * <p>  心跳管理类
 * 作者：Created by 林飞堞 on 2020/1/7
 * <p>
 * 版本号：donghaoProect
 */
public class HeartbeatManager {

    private MyRunnable mRunnable;
    private Handler mHandler;
    private static HeartbeatManager instance;
    public static HeartbeatManager getInstance(){
        if(instance == null) {
            synchronized (HeartbeatManager.class){
                if(instance == null) {
                    instance = new HeartbeatManager();
                }
            }
        }
        return instance;
    }

    public HeartbeatManager() {
        mHandler = DonghaoApplication.getHandler();
    }

    public void start(){
        if (mRunnable == null) {
            mRunnable = new MyRunnable();
            mHandler.postDelayed(mRunnable, 0);
        }
    }

    public void stop(){
        mHandler.removeCallbacks(mRunnable);
        mRunnable = null;
    }

    private class MyRunnable implements Runnable {
        @Override
        public void run() {
           // Log.e("linfd","111");
            mHandler.postDelayed(this, Contanst.HEARTBEAT);
        }
    }
}
