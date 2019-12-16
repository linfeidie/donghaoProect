package ac.scri.com.donghaoproect.manager;

import android.os.Handler;

import ac.scri.com.donghaoproect.Contanst;

/**
 * 文件描述：.
 * <p> 循环动作的类  结合LooerR使用
 * 作者：Created by 林飞堞 on 2019/9/24
 * <p>
 * 版本号：donghaoProect
 */
public class TimerManager {
    private static final TimerManager ourInstance = new TimerManager();
    public static Handler mHandler = new Handler();
    static Runnable r;
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

    /*
    * 优化，延迟取消  解决消息队列问题（不知道有没解决）
    *
    * */
    public void removeMessage(){

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mHandler.removeCallbacks(r);
            }
        },100);

    }
}
