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
    private static volatile ComBitmapManager ourInstance;
    private Bitmap mapBackground;
    private Bitmap mapLocation;
    private Bitmap mapComposite;
    private CompositeMapListener listener;
    private Matrix matrix = null;

    static ComBitmapManager getInstance() {
        if (ourInstance == null) {
            synchronized (ComBitmapManager.class) {
                if (ourInstance == null) {
                    ourInstance = new ComBitmapManager();
                }
            }
        }
        return ourInstance;
    }

    private ComBitmapManager() {
        matrix = new Matrix();
    }

    //开始合成
    public void startComposite(Rect rect, float angle, CompositeMapListener listener) {
        obtainBitmap();
        mapComposite = toConformBitmap(rotateBitmap(mapBackground, 90), mapLocation, rect,angle);
        if (listener != null && mapComposite != null) {
            listener.compositeMapCallBack(mapComposite);
        }
    }

    private void obtainBitmap() {
        File file = new File(DonghaoApplication.getApplication().getExternalCacheDir().getAbsolutePath(), "11.png");
        mapBackground = BitmapFactory.decodeFile(file.getAbsolutePath());
        mapLocation = BitmapFactory.decodeResource(DonghaoApplication.getApplication().getResources(), R.mipmap.jiantou); // 间接调用
    }

    /*
    * 背景图和定位图结合
    * */

    private Bitmap toConformBitmap(Bitmap background, Bitmap foreground, Rect rect,float angle) {
        if (background == null) {
            return null;
        }

        int bgWidth = background.getWidth();
        int bgHeight = background.getHeight();
        int fgWidth = foreground.getWidth();
        int fgHeight = foreground.getHeight();
        //create the new blank bitmap 创建一个新的和SRC长度宽度一样的位图
        Bitmap newbmp = Bitmap.createBitmap(bgWidth, bgHeight, Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(newbmp);
        //draw bg into
        cv.drawBitmap(background, 0, 0, null);//在 0，0坐标开始画入bg
        //draw fg into
        cv.drawBitmap(adjustPhotoRotation(foreground,angle), rect.left - fgWidth / 2, rect.top - fgHeight / 2, null);//在 0，0坐标开始画入fg ，可以从任意位置画入
        //save all clip
//        cv.save(Canvas.ALL_SAVE_FLAG);//保存
//        //store
//        cv.restore();//存储adjustPhotoRotation(foreground,angle),
        return newbmp;
    }

    /*
    * 对背景图旋转
    * */

    private Bitmap rotateBitmap(Bitmap origin, float alpha) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.setRotate(alpha);
        matrix.postScale(1, -1);
        // 围绕原地进行旋转
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();
        return newBM;
    }

    public interface CompositeMapListener {
        void compositeMapCallBack(Bitmap mapComposite);
    }

    private Bitmap adjustPhotoRotation(Bitmap bm, final float orientationDegree) {


        matrix.setRotate(-orientationDegree, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);

        try {
            Bitmap bm1 = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);

            return bm1;

        } catch (OutOfMemoryError ex) {
        }
        return null;
    }


}
