package ac.scri.com.donghaoproect;

import android.widget.Toast;

/**
 * 文件描述：.
 * <p> 一些常用工具类
 * 作者：Created by 林飞堞 on 2019/10/23
 * <p>
 * 版本号：donghaoProect
 */
public class Tools {
    private static Toast mToast;
    public static void showToast(String message) {

        if (mToast == null) {
            mToast = Toast.makeText(DonghaoApplication.getApplication(), message,
                    Toast.LENGTH_SHORT);
        } else {
            mToast.setText(message);
        }
        mToast.show();
    }

    public static void runOnUiThread(Runnable runnable) {
        // 在主线程运行
        if(android.os.Process.myTid()==DonghaoApplication.getMainTid()){
            runnable.run();
        }else{
            //获取handler
            DonghaoApplication.getHandler().post(runnable);
        }
    }
}
