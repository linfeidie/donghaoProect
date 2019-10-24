package ac.scri.com.donghaoproect;

import android.graphics.Bitmap;
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
        DataEntity dataEntity = new DataEntity();
        switch (typeEntity.getType()) {

            case Contanst.GET_STATUS:
                try {
                    SatusEntity satusEntity = GsonUtil.GsonToBean(messageJson, SatusEntity.class);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("linfd",e.getMessage());
                }
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
                dataEntity.setType(typeEntity.getType());
                dataEntity.message = messageJson ;
                DataChanger.getInstance().postData(dataEntity);
               // SpliceMap(messageJson);

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
    private static void SpliceMap(String messageJson) {
        HandleSubpackageManager.getInstance(new HandleSubpackageManager.FinishListener() {
            @Override
            public void MapDateFinish(final MapdataEntity supperMapData) {
                Tools.showToast("完成拼接");
                Log.e("linfd","完成拼接");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ObtainMapManager.getInstance(supperMapData).loadMap(new ObtainMapManager.MapListenter() {
                            @Override
                            public void getMap(final Bitmap map) {
                               Tools.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                       // iv_map.setImageBitmap(map);
                                        Tools.showToast("显示");
                                        Log.e("linfd", "完成拼接2");
                                    }
                                });

                            }
                        });
                    }
                }).start();
            }
        }).handerMap(messageJson);
    }

}
