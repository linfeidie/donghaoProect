package ac.scri.com.donghaoproect;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

import java.io.File;

/**
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2019/10/14  合成图片管理类
 * <p>
 * 版本号：Socket_learning
 */
class ComBitmapManager {
    private static volatile ComBitmapManager ourInstance ;
    private Bitmap mapBackground;
    private Bitmap mapLocation;
    private Bitmap mapComposite;
    private CompositeMapListener listener;

    static ComBitmapManager getInstance() {
        if(ourInstance == null ) {
            synchronized (ComBitmapManager.class){
                if(ourInstance == null) {
                    ourInstance = new ComBitmapManager();
                }
            }
        }
        return ourInstance;
    }

    private ComBitmapManager() {
    }

    //开始合成
    public void startComposite(Rect rect, CompositeMapListener listener){
        obtainBitmap();
        mapComposite = toConformBitmap(rotateBitmap(mapBackground,180),mapLocation,rect);
        if(listener != null && mapComposite != null) {
            listener.compositeMapCallBack(mapComposite);
        }
    }

    private void obtainBitmap() {
        File file = new File(DonghaoApplication.getApplication().getExternalCacheDir().getAbsolutePath(),"11.png");
        mapBackground=BitmapFactory.decodeFile(file.getAbsolutePath());
        mapLocation = BitmapFactory.decodeResource(DonghaoApplication.getApplication().getResources(), R.mipmap.yuandian); // 间接调用
    }

    private Bitmap toConformBitmap(Bitmap background, Bitmap foreground, Rect rect) {
        if( background == null ) {
            return null;
        }

        int bgWidth = background.getWidth();
        int bgHeight = background.getHeight();
        //int fgWidth = foreground.getWidth();
        //int fgHeight = foreground.getHeight();
        //create the new blank bitmap 创建一个新的和SRC长度宽度一样的位图
        Bitmap newbmp = Bitmap.createBitmap(bgWidth, bgHeight, Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(newbmp);
        //draw bg into
        cv.drawBitmap(background, 0, 0, null);//在 0，0坐标开始画入bg
        //draw fg into
        cv.drawBitmap(foreground, rect.left, rect.top, null);//在 0，0坐标开始画入fg ，可以从任意位置画入
        //save all clip
//        cv.save(Canvas.ALL_SAVE_FLAG);//保存
//        //store
//        cv.restore();//存储
        return newbmp;
    }

    private Bitmap rotateBitmap(Bitmap origin, float alpha) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.setRotate(alpha);
        // 围绕原地进行旋转
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();
        return newBM;
    }

    public  interface CompositeMapListener{
         void compositeMapCallBack(Bitmap mapComposite);
    }
}
