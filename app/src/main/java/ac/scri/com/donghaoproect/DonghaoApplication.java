package ac.scri.com.donghaoproect;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;

/**
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2019/9/19
 * <p>
 * 版本号：donghaoProect
 */
public class DonghaoApplication extends Application {
    private static DonghaoApplication application;
    private static int mainTid;
    private static Handler handler;
    @Override
    public void onCreate() {
        super.onCreate();
        application=this;
        mainTid = android.os.Process.myTid();
        handler=new Handler();
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }
    public static Context getApplication() {
        return application;
    }
    public static int getMainTid() {
        return mainTid;
    }
    public static Handler getHandler() {
        return handler;
    }
}
