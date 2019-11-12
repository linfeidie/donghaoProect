package ac.scri.com.donghaoproect;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件描述：.
 * <p> 一些常用工具类
 * 作者：Created by 林飞堞 on 2019/10/23
 * <p>
 * 版本号：donghaoProect
 */
public class Tools {
    private static Toast mToast;
    public static final boolean isDebug = true;
    public static void showToast(String message) {

        if (mToast == null) {
            mToast = Toast.makeText(DonghaoApplication.getApplication(), message,
                    Toast.LENGTH_SHORT);
        } else {
            mToast.setText(message);
        }
        if(isDebug) {
            mToast.show();
        }

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

    //集合里过滤掉所有负整数
    public static <T> List<Integer> positive_number(List<Integer> list) {
        List<Integer> rList = new ArrayList<>();
        if(list != null && list.size()>1){
            for (int i = 0; i < list.size(); i++) {
                if(list.get(i) > 0) {
                    rList.add(list.get(i));
                }
            }
        }
        ((ArrayList<T>) rList).trimToSize();//释放申请的多余的空间
        return rList;
    }

}
