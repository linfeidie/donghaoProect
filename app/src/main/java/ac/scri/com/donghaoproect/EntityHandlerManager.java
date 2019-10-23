package ac.scri.com.donghaoproect;

import android.text.TextUtils;
import android.util.Log;

/**
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2019/10/23
 * <p>
 * 版本号：donghaoProect
 */
public class EntityHandlerManager {

    public static void handerEntity(TypeEntity typeEntity, String messageJson) {
        if(TextUtils.isEmpty(typeEntity.getType())) {
            return;
        }
        switch (typeEntity.getType()) {

            case Contanst.GET_STATUS:
                SatusEntity satusEntity = GsonUtil.GsonToBean(messageJson, SatusEntity.class);
                Tools.showToast("状态");
                break;
            case Contanst.GET_MAP:
                //Toast.makeText(this, "地图", Toast.LENGTH_SHORT).show();
                break;
            case Contanst.GET_GPS:
                Tools.showToast("GPS");
                break;
            case Contanst.GET_PATH:
                Tools.showToast("路径");
                break;
            case Contanst.GET_MAP_PARAM:
                Tools.showToast("地图包信息");
                MapParamEntity mapParamEntity = GsonUtil.GsonToBean(messageJson,MapParamEntity.class);
                Contanst.MAPPARAMENTITY = mapParamEntity;

                Log.e("linfd","地图包信息");
                break;
            case Contanst.MAP_DATA:
                Tools.showToast("地图数据");
                Log.e("linfd","地图数据");
              //  SpliceMap(messageJson);
                break;
            case Contanst.SCAN:
                Tools.showToast("激光");
                break;
            case Contanst.SERVER_ACK:
                Log.e("linfd","服务器返回");
                Tools.showToast("服务器返回");
                break;
        }
    }
}
