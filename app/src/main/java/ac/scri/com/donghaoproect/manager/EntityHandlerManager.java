package ac.scri.com.donghaoproect.manager;

import android.text.TextUtils;
import android.util.Log;

import ac.scri.com.donghaoproect.Contanst;
import ac.scri.com.donghaoproect.entity.DataEntity;
import ac.scri.com.donghaoproect.entity.MapParamEntity;
import ac.scri.com.donghaoproect.entity.ServerInfo;
import ac.scri.com.donghaoproect.entity.TypeEntity;
import ac.scri.com.donghaoproect.observer.DataChanger;
import ac.scri.com.donghaoproect.tool.GsonUtil;

/**
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2019/10/23
 * <p>
 * 版本号：donghaoProect
 */
public class EntityHandlerManager {

    HandlerCallback mHandlerCallback;

    public static void handerEntity(TypeEntity typeEntity, String messageJson) {

        handerEntity(typeEntity,messageJson,null);
    }

    public static void handerEntity(TypeEntity typeEntity, String messageJson,HandlerCallback mHandlerCallback) {
        if(TextUtils.isEmpty(typeEntity.getType())) {
            return;
        }
        DataEntity dataEntity = new DataEntity();
        switch (typeEntity.getType()) {

            case Contanst.GET_STATUS:
//                try {
//                    SatusEntity satusEntity = GsonUtil.GsonToBean(messageJson, SatusEntity.class);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Log.e("linfd",e.getMessage());
//                }
//                Tools.showToast("状态");
                dataEntity.setType(typeEntity.getType());
                dataEntity.message = messageJson ;
                DataChanger.getInstance().postData(dataEntity);
                break;
            case Contanst.GET_MAP:
                //Toast.makeText(this, "地图", Toast.LENGTH_SHORT).show();
                break;
            case Contanst.GET_GPS:
                //Tools.showToast("GPS");
                break;
            case Contanst.GET_PATH:
               // Tools.showToast("路径");
                break;
            case Contanst.GET_MAP_PARAM:
                //Tools.showToast("地图包信息");
                MapParamEntity mapParamEntity = GsonUtil.GsonToBean(messageJson,MapParamEntity.class);
                Contanst.MAPPARAMENTITY = mapParamEntity;

                Log.e("linfd","地图包信息");
                break;
            case Contanst.MAP_DATA:
                dataEntity.setType(typeEntity.getType());
                dataEntity.message = messageJson ;
                DataChanger.getInstance().postData(dataEntity);
               // SpliceMap(messageJson);

                break;
            case Contanst.SCAN:
               // Tools.showToast("激光");
                //Log.e("linfd","激光");
                break;
            case Contanst.SERVER_ACK:
               // Tools.showToast("服务器返回");
                ServerInfo serverInfo = GsonUtil.GsonToBean(messageJson, ServerInfo.class);
                if(mHandlerCallback != null) {
                    mHandlerCallback.callback(serverInfo.getState().equalsIgnoreCase("online"));

                }
                break;

            case Contanst.GET_ONLINE_IDS:
               // Tools.showToast(messageJson);
                dataEntity.setType(typeEntity.getType());
                dataEntity.message = messageJson ;
                DataChanger.getInstance().postData(dataEntity);
                break;
        }
    }

    public interface HandlerCallback{
        void callback(boolean b);
    }

}
