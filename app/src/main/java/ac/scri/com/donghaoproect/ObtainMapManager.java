package ac.scri.com.donghaoproect;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2019/9/29
 * <p>
 * 版本号：Socket_learning
 */
public class ObtainMapManager {

    private List<Integer> compressData ;
    private List<Integer> tempData = new ArrayList<>();
    private List<Integer> fillData ;
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
//                if(i > 384) {
//                   Tools.showToast("超过了");
//                }
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
