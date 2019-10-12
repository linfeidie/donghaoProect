package ac.scri.com.donghaoproect;

import android.os.Handler;

/**
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2019/9/24
 * <p>
 * 版本号：donghaoProect
 */
public class TimerManager {
    private static final TimerManager ourInstance = new TimerManager();
    Handler mHandler = new Handler();
    Runnable r;
    public static TimerManager getInstance() {
        return ourInstance;
    }

    private TimerManager() {
    }

    public void postDelayed(Runnable r){
        this.r = r;
        mHandler.postDelayed(r, 500);
    }

    public void removeMessage(){
        mHandler.removeCallbacks(r);
    }
}
