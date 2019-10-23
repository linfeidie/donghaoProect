package ac.scri.com.donghaoproect;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件描述：.
 * <p> 处理分包的管理类
 * 作者：Created by 林飞堞 on 2019/10/15
 * <p>
 * 版本号：donghaoProect
 */
public class HandleSubpackageManager {
    private static volatile HandleSubpackageManager ourInstance ;

    private int packNum = -1;
    private MapdataEntity supperMapData;
    private List<MapdataEntity> mapdataEntities ;
    private StringBuffer incompleteJson = new StringBuffer();
    private FinishListener listener;

    public static HandleSubpackageManager getInstance(FinishListener listener) {
        if(ourInstance == null) {
            synchronized (HandleSubpackageManager.class){
                if(ourInstance == null) {
                    ourInstance = new HandleSubpackageManager(listener);
                }
            }
        }
        return ourInstance;
    }

    private HandleSubpackageManager(FinishListener listener) {
        mapdataEntities = new ArrayList<>();
        this.listener = listener;
    }
    MapdataEntity entity = null;
    //主要方法
    public void handerMap(String messageJson) {


        entity = GsonUtil.GsonToBean(messageJson, MapdataEntity.class);
        packNum = entity.getPack_num();
        mapdataEntities.add(entity);

        if (mapdataEntities.size() == packNum) {

            for (int i = 0; i < packNum; i++) {
                if (i == 0) {
                    supperMapData = mapdataEntities.get(0);
                } else {
                    supperMapData.getData().addAll(mapdataEntities.get(i).getData());
                }

            }
            mapdataEntities.clear();
            if(listener != null) {
                listener.MapDateFinish(supperMapData);
            }

        }
        if(mapdataEntities.size() > packNum) {
            mapdataEntities.clear();
        }

    }

    public  interface FinishListener{
         void MapDateFinish(MapdataEntity supperMapData);
    }
}
