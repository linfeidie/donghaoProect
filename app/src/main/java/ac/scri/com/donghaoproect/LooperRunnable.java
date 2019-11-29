package ac.scri.com.donghaoproect;

import ac.scri.com.donghaoproect.manager.TimerManager;

/**
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2019/10/15
 * <p>
 * 版本号：donghaoProect
 */
public abstract class LooperRunnable implements Runnable {
    @Override
    public void run() {
        TimerManager.getInstance().startLoop();
        call();
    }
    abstract public void call();
}
