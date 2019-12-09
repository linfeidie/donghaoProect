package ac.scri.com.donghaoproect.manager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ac.scri.com.donghaoproect.DonghaoApplication;
import ac.scri.com.donghaoproect.R;

/**
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2019/10/14  合成图片管理类
 * <p>
 * 版本号：Socket_learning
 */
public class ComBitmapManager {
    private static volatile ComBitmapManager ourInstance;
    private Bitmap mapBackground;
    private Bitmap mapLocation;
    private Bitmap mapComposite;
    private CompositeMapListener listener;
    private List<Rect> points = new ArrayList<>();
    private Rect resetPoint ;//重定位的点
    private Matrix matrix = null;
    private Paint paint;//画描点的笔
    private Canvas cv;
    public Path path;
    private Bitmap newbmp; //产生的第三方图


    public static ComBitmapManager getInstance() {
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
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3f);
        paint.setAntiAlias(true);
        paint.setPathEffect(new CornerPathEffect(5));


    }


    public void addTouchPoint(Rect rect) {
        if (rect != null) {
            points.add(rect);
        }
    }

    /*
     *
     *设置重定位的点
     * */
    public void setResetPoint(Rect resetPoint) {
        this.resetPoint = resetPoint;
    }
/*
*  清除重定位点
*
* */
    public void clearResetPoint(){
        resetPoint = null;
    }

    /*
    *全部清除描点
    * */
    public void clearPoints(){
        points.clear();
    }

    /*
    *
    * 不等于空说明有定位点
    * */
    public boolean isHasResetPoint(){

        return resetPoint != null;
    }

    //开始合成
    public void startComposite(Rect rect, float angle, CompositeMapListener listener) {
        obtainBitmap();
        mapComposite = toConformBitmap(rotateBitmap(mapBackground, 90), mapLocation, rect, angle);
        if (listener != null && mapComposite != null) {
            listener.compositeMapCallBack(mapComposite);
        }
    }

    /*
     * 获取背景图片和箭头
     * */
    private void obtainBitmap() {
        if(mapBackground == null || mapLocation == null) {
            File file = new File(DonghaoApplication.getApplication().getExternalCacheDir().getAbsolutePath(), "11.png");
            mapBackground = BitmapFactory.decodeFile(file.getAbsolutePath());
            mapLocation = BitmapFactory.decodeResource(DonghaoApplication.getApplication().getResources(), R.mipmap.jiantou); // 间接调用
        }

    }

    /*
     * 背景图和定位图结合
     * */

    private Bitmap toConformBitmap(Bitmap background, Bitmap foreground, Rect rect, float angle) {
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
        cv.drawBitmap(adjustPhotoRotation(foreground, angle), rect.left - fgWidth / 2, rect.top - fgHeight / 2, null);//在 0，0坐标开始画入fg ，可以从任意位置画入
       // cv.drawBitmap(adjustPhotoRotation(foreground, angle), rect.left , rect.top , null);
        //save all clip
//        cv.save(Canvas.ALL_SAVE_FLAG);//保存
//        //store
//        cv.restore();//存储adjustPhotoRotation(foreground,angle),

        if (points.size() > 1) {

//设置Path
            path = new Path();
            path.moveTo(points.get(0).left, points.get(0).top);
            for (int i = 1; i < points.size(); i++) {
                path.lineTo(points.get(i).left, points.get(i).top);
            }

            cv.drawPath(path, paint);
//            Float[] ff= points.toArray(new Float[points.size()]);
//            cv.drawLines(ff,paint);
        } else if (points.size() == 1) {//只是一个点
            cv.drawPoint(points.get(0).left, points.get(0).top, paint);
        }
        if(resetPoint != null) {
            paint.setColor(Color.BLUE);
            cv.drawPoint(resetPoint.left, resetPoint.top, paint);
            paint.setColor(Color.GREEN);
        }
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
        //newBM.recycle();zj
        if (origin != null && !origin.isRecycled())//zj
        {
            origin=null;
        }
        //origin.recycle();
        return newBM;
    }

    public interface CompositeMapListener {
        void compositeMapCallBack(Bitmap mapComposite);
    }
/*
*
* 调整角度
* */
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
