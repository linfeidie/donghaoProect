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
    private int touchX = 0;
    private int touchY = 0;
    private float yServer = 0;//上传到服务端的描点
    private float xServer = 0;

    private DataWatcher watcher = new DataWatcher() {
        @Override
        public void notifyUpdata(Object data) {
            if (data instanceof DataEntity) {
                DataEntity dataEntity = (DataEntity) data;
                if (dataEntity.getType().equalsIgnoreCase(Contanst.MAP_DATA)) {
                    Tools.showToast("地图数据");
                    Log.e("linfd", "地图数据");
                    SpliceMap(dataEntity.message);
                } else if (dataEntity.getType().equalsIgnoreCase(Contanst.GET_STATUS)) {
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
        if (satusEntity == null || Contanst.MAPPARAMENTITY == null) {
            return;
        }
        Tools.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Tools.showToast(360*satusEntity.getRobot_yaw()/(2*Math.PI)+"");
                //Log.e("linfd", "完成拼接2");
                if (Contanst.MAPPARAMENTITY == null) {
                    return;
                }

                double left = Contanst.MAPPARAMENTITY.getWidth() - (-(Contanst.MAPPARAMENTITY.getOrigin().getY() - satusEntity.getAxis_y()) / Contanst.MAPPARAMENTITY.getResolution());
                double top = Contanst.MAPPARAMENTITY.getHeight() - (-(Contanst.MAPPARAMENTITY.getOrigin().getX() - satusEntity.getAxis_x()) / Contanst.MAPPARAMENTITY.getResolution());

                Log.e("linfd", satusEntity.getAxis_x() + "===" + satusEntity.getAxis_y());
                // Log.e("linfd",left+"==="+top);
                float angle = (float) (360 * satusEntity.getRobot_yaw() / (2 * Math.PI));
                Rect rect = new Rect((int) left, (int) top, 0, 0);
                ComBitmapManager.getInstance().startComposite(rect, angle, new ComBitmapManager.CompositeMapListener() {
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
       // ControlSendManager.set_work_mode("navi_straight");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_indoor, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iv_bitmap = view.findViewById(R.id.iv_bitmap);
        iv_bitmap.setOnTouchListener(imgSourceOnTouchListener);
        iv_bitmap.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Rect rect = new Rect(touchX, touchY, 0, 0);
                ComBitmapManager.getInstance().addTouchPoint(rect);
                return false;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void SpliceMap(String messageJson) {
        HandleSubpackageManager.getInstance(new HandleSubpackageManager.FinishListener() {
            @Override
            public void MapDateFinish(final MapdataEntity supperMapData) {
                Tools.showToast("完成拼接");
                Log.e("linfd", "完成拼接");
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

    private View.OnTouchListener imgSourceOnTouchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View view, MotionEvent event) {

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                float eventX = event.getX();
                float eventY = event.getY();
                float[] eventXY = new float[]{eventX, eventY};

                Matrix invertMatrix = new Matrix();
                ((ImageView) view).getImageMatrix().invert(invertMatrix);

                invertMatrix.mapPoints(eventXY);
                touchX = Integer.valueOf((int) eventXY[0]);
                touchY = Integer.valueOf((int) eventXY[1]);


                Drawable imgDrawable = ((ImageView) view).getDrawable();
                Bitmap bitmap = ((BitmapDrawable) imgDrawable).getBitmap();

                if (touchX < 0) {
                    touchX = 0;
                } else if (touchX > bitmap.getWidth() - 1) {
                    touchX = bitmap.getWidth() - 1;
                }

                if (touchY < 0) {
                    touchY = 0;
                } else if (touchY > bitmap.getHeight() - 1) {
                    touchY = bitmap.getHeight() - 1;
                }

                yServer = (float) (-(touchX - Contanst.MAPPARAMENTITY.getWidth()) * Contanst.MAPPARAMENTITY.getResolution() + Contanst.MAPPARAMENTITY.getOrigin().getY());
                Log.e("IndoorFragment", "yServer"
                        + yServer);
                xServer = (float) (-(touchY - Contanst.MAPPARAMENTITY.getHeight()) * Contanst.MAPPARAMENTITY.getResolution() + Contanst.MAPPARAMENTITY.getOrigin().getX());
                Log.e("IndoorFragment", "xServer"
                        + xServer);
                ControlSendManager.set_click_point(xServer, yServer);

            }

            return false;
        }
    };
}
