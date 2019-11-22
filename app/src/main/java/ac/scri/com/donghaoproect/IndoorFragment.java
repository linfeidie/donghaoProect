package ac.scri.com.donghaoproect;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2019/10/23
 * <p>
 * 版本号：donghaoProect
 */
public class IndoorFragment extends Fragment {

    private PinchImageView iv_bitmap;

    private DataWatcher watcher=new DataWatcher() {
        @Override
        public void notifyUpdata(Object data) {
            if(data instanceof DataEntity) {
                DataEntity dataEntity = (DataEntity)data;
                if(dataEntity.getType().equalsIgnoreCase(Contanst.MAP_DATA)) {
                    Tools.showToast("地图数据");
                    Log.e("linfd","地图数据");
                    SpliceMap(dataEntity.message);
                }else if (dataEntity.getType().equalsIgnoreCase(Contanst.GET_STATUS)) {
                    try {
                        SatusEntity satusEntity = GsonUtil.GsonToBean(dataEntity.message, SatusEntity.class);
                        updateLocation(satusEntity);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("linfd", e.getMessage());
                    }
                }
            }
        }
    };

    private void updateLocation(final SatusEntity satusEntity) {
        if(satusEntity == null || Contanst.MAPPARAMENTITY == null) {
            return;
        }
        Tools.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Tools.showToast(360*satusEntity.getRobot_yaw()/(2*Math.PI)+"");
                //Log.e("linfd", "完成拼接2");
                if(Contanst.MAPPARAMENTITY == null) {
                    return;
                }

                double left = Contanst.MAPPARAMENTITY.getWidth() - (-(Contanst.MAPPARAMENTITY.getOrigin().getY() - satusEntity.getAxis_y())/Contanst.MAPPARAMENTITY.getResolution());
                double top = Contanst.MAPPARAMENTITY.getHeight() - (-(Contanst.MAPPARAMENTITY.getOrigin().getX() - satusEntity.getAxis_x())/Contanst.MAPPARAMENTITY.getResolution());

                Log.e("linfd",satusEntity.getAxis_x()+"==="+satusEntity.getAxis_y());
               // Log.e("linfd",left+"==="+top);
                float angle = (float) (360*satusEntity.getRobot_yaw()/(2*Math.PI));
                Rect rect = new Rect((int)left,(int)top,0,0);
                ComBitmapManager.getInstance().startComposite(rect, angle,new ComBitmapManager.CompositeMapListener() {
                    @Override
                    public void compositeMapCallBack(Bitmap mapComposite) {
                        iv_bitmap.setImageBitmap(mapComposite);
                    }
                });
            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataChanger.getInstance().addObserver(watcher);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View rootView  = inflater.inflate(R.layout.fragment_indoor,container,false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iv_bitmap = view.findViewById(R.id.iv_bitmap);
        iv_bitmap.setOnTouchListener(imgSourceOnTouchListener);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private  void SpliceMap(String messageJson) {
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


                            }
                        });
                    }
                }).start();
            }
        }).handerMap(messageJson);
    }

    private View.OnTouchListener imgSourceOnTouchListener = new View.OnTouchListener()
    {

        @Override
        public boolean onTouch(View view, MotionEvent event)
        {
            float eventX = event.getX();
            float eventY = event.getY();
            float[] eventXY = new float[] {eventX, eventY};

            Matrix invertMatrix = new Matrix();
            ((ImageView)view).getImageMatrix().invert(invertMatrix);

            invertMatrix.mapPoints(eventXY);
            int x = Integer.valueOf((int)eventXY[0]);
            int y = Integer.valueOf((int)eventXY[1]);


            Log.e("IndoorFragment", "touched position: "
                    + String.valueOf(eventX) + " / "
                    + String.valueOf(eventY));

            Log.e("IndoorFragment","touched position: "
                    + String.valueOf(x) + " / "
                    + String.valueOf(y));

            Drawable imgDrawable = ((ImageView)view).getDrawable();
            Bitmap bitmap = ((BitmapDrawable)imgDrawable).getBitmap();

            Log.e("IndoorFragment","drawable size: "
                    + String.valueOf(bitmap.getWidth()) + " / "
                    + String.valueOf(bitmap.getHeight()));
            //Limit x, y range within bitmap
            if(x < 0)
            {
                x = 0;
            }
            else if(x > bitmap.getWidth()-1)
            {
                x = bitmap.getWidth()-1;
            }

            if(y < 0)
            {
                y = 0;
            }
            else if(y > bitmap.getHeight()-1)
            {
                y = bitmap.getHeight()-1;
            }

            int touchedRGB = bitmap.getPixel(x, y);


            return true;
        }
    };
}
