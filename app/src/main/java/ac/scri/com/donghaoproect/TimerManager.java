package ac.scri.com.donghaoproect;

import android.os.Handler;

/**
 * 文件描述：.
 * <p> 循环动作的类  结合LooerR使用
 * 作者：Created by 林飞堞 on 2019/9/24
 * <p>
 * 版本号：donghaoProect
 */
public class TimerManager {
    private static final TimerManager ourInstance = new TimerManager();
    public Handler mHandler = new Handler();
    Runnable r;
    public static TimerManager getInstance() {
        return ourInstance;
    }

    private TimerManager() {
    }

    public void startLoop(){
        mHandler.postDelayed(r, Contanst.ORDER_INTERVAL);
    }
    public void start(Runnable r){
        this.r = r;
        mHandler.postDelayed(r, Contanst.ORDER_INTERVAL);
    }

    public void removeMessage(){
        mHandler.removeCallbacks(r);
    }
}
