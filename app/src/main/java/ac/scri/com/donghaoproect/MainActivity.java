package ac.scri.com.donghaoproect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;

public class MainActivity extends AppCompatActivity {

    private MapView mMapView = null;
    private BaiduMap mBaiduMap;
    LatLng GEO_BEIJING = new LatLng(39.945, 116.404);
    LatLng GEO_SHANGHAI = new LatLng(31.227, 121.481);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        //地理坐标基本数据结构
        //描述地图状态将要发生的变化,通过当前经纬度来使地图显示到该位置
        MapStatusUpdate msu= MapStatusUpdateFactory.newLatLng(GEO_SHANGHAI);
        //改变地图状态
        mBaiduMap.animateMapStatus(msu);
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
}
