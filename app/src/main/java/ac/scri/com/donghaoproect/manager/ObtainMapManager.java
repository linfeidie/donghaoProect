package ac.scri.com.donghaoproect.manager;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ac.scri.com.donghaoproect.Contanst;
import ac.scri.com.donghaoproect.DonghaoApplication;
import ac.scri.com.donghaoproect.tool.Tools;
import ac.scri.com.donghaoproect.entity.MapdataEntity;

/**
 * 文件描述：.
 * <p>作用，把非解压数据变成最原始数据，转成bitmap 存储和回调
 * 作者：Created by 林飞堞 on 2019/9/29
 * <p>
 * 版本号：Socket_learning
 */
public class ObtainMapManager {

    private List<Integer> compressData ;//非解压数据
    private List<Integer> tempData = new ArrayList<>();
    private List<Integer> fillData ;//解压后的原始数据
    private MapdataEntity mMapdata;
    private int height;
    private int width;


    public static ObtainMapManager getInstance(MapdataEntity mapdata) {
        return new ObtainMapManager(mapdata) ;
    }

    private ObtainMapManager(MapdataEntity mapdata) {
        this.mMapdata = mapdata;
        if(Contanst.MAPPARAMENTITY == null) {
            return;
        }else {
            width = Contanst.MAPPARAMENTITY.getWidth();
            height = Contanst.MAPPARAMENTITY.getHeight();
            fillData = new ArrayList<>(width * height);
        }

    }

    public void loadMap(MapListenter mapListenter){
        //还原数据
        restoreData();
        //画点
        drawBitmap(mapListenter);
    }
    //根据队列里存储的数据描点
    private void drawBitmap(MapListenter mapListenter) {


        int index = 0 ;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        for (int i = 0; i < height-1; i++) {
            for (int j = 0; j < width-1 ; j++) {
                index = i * width + j;
                int color = Color.GREEN;
                if(fillData.get(index) == -1) {
                    color = Color.GRAY;
                }else if(fillData.get(index) == 0) {
                    color = Color.WHITE;
                }else if(fillData.get(index) == 100) {
                    color = Color.BLACK;
                }

                try {
                    bitmap.setPixel(j, i, color);
                } catch (Exception e) {
                    e.printStackTrace();
                    Tools.showToast("地图异常");
                }
            }
        }
        if(bitmap != null && mapListenter != null) {
            mapListenter.getMap(bitmap);
            saveBitmap("",bitmap);
        }

    }

    private void restoreData() {
        compressData = mMapdata.getData();
        int count = 0;
        for (int i = 0; i < compressData.size(); i++) {
            //获取数量
            if(i%2 ==0){
                count = compressData.get(i);

            }else{
                for (int j = 0; j < count ; j++) {
                    tempData.add(j,compressData.get(i));
                }
                fillData.addAll(tempData);
                //获取值
                tempData.clear();

            }

        }
    }
    /**
     * 保存bitmap到本地
     *
     * @param path    路径
     * @param mBitmap 图片
     * @return 路径
     */
    public String saveBitmap(String path, Bitmap mBitmap) {

        File file = new File(DonghaoApplication.getApplication().getExternalCacheDir().getAbsolutePath(),"11.png");
        Log.e("linfd",file.getAbsolutePath());
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //File file = new File(FileUtils.getCacheDir().getAbsolutePath(), "11.png");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return file.getAbsolutePath();
    }

    public interface  MapListenter{
        void getMap(Bitmap map);
    }
}
