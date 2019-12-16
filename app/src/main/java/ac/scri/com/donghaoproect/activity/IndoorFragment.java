package ac.scri.com.donghaoproect.activity;

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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import ac.scri.com.donghaoproect.Contanst;
import ac.scri.com.donghaoproect.R;
import ac.scri.com.donghaoproect.entity.DataEntity;
import ac.scri.com.donghaoproect.entity.MapdataEntity;
import ac.scri.com.donghaoproect.entity.SatusEntity;
import ac.scri.com.donghaoproect.manager.ComBitmapManager;
import ac.scri.com.donghaoproect.manager.ControlSendManager;
import ac.scri.com.donghaoproect.manager.HandleSubpackageManager;
import ac.scri.com.donghaoproect.manager.ObtainMapManager;
import ac.scri.com.donghaoproect.manager.ThreadManager;
import ac.scri.com.donghaoproect.observer.DataChanger;
import ac.scri.com.donghaoproect.observer.DataWatcher;
import ac.scri.com.donghaoproect.tool.GsonUtil;
import ac.scri.com.donghaoproect.tool.Tools;
import ac.scri.com.donghaoproect.view.PinchImageView;
import me.caibou.rockerview.JoystickView;

/**
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2019/10/23
 * <p>
 * 版本号：donghaoProect
 */
public class IndoorFragment extends Fragment {

    private PinchImageView iv_bitmap;
    private CheckBox cb_init_post ;
    private JoystickView joystickView;
    private boolean is_init_post = false;//是否在重定位
    private int touchX = 0;
    private int touchY = 0;
    private float yServer = 0;//上传到服务端的描点
    private float xServer = 0;
    private Rect rect = new Rect();//覆盖的位置

    private DataWatcher watcher = new DataWatcher() {
        @Override
        public void notifyUpdata(Object data) {
            if (data instanceof DataEntity) {
                DataEntity dataEntity = (DataEntity) data;
                if (dataEntity.getType().equalsIgnoreCase(Contanst.MAP_DATA)) {
                   // Tools.showToast("地图数据");
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

               // Log.e("linfd", satusEntity.getAxis_x() + "===" + satusEntity.getAxis_y());
                // Log.e("linfd",left+"==="+top);
                //弧度转角度
                float angle = (float) (360 * satusEntity.getRobot_yaw() / (2 * Math.PI));
                //Tools.showToast("yaw=="+satusEntity.getRobot_yaw());
                rect.left = (int) left;
                rect.top = (int) top;
                /*
                * 解析出定位点位置和角度，画上去
                * */
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
        cb_init_post = view.findViewById(R.id.cb_init_post);
        joystickView = view.findViewById(R.id.joystick);

        joystickView.setAngleUpdateListener(new JoystickView.OnAngleUpdateListener() {
            @Override
            public void onAngleUpdate(double angle, int action) {
                // 解析
                double  angle2 = (angle+90)>180?-((angle+90)-360):-(angle+90);
                double yaw = (2 * Math.PI * angle2)/360;//自己调整的角度转为弧度
                Rect rect1 = new Rect();
                rect1.left = (int) touchX;
                rect1.top = (int) touchY;
                if (action == JoystickView.ACTION_RELEASE){
                    //取消
                    ComBitmapManager.getInstance().clearResetPoint();
                    ControlSendManager.set_init_pose(xServer,yServer,yaw);
                    joystickView.setVisibility(View.GONE);
                    cb_init_post.setChecked(false);
                } else {

                    ComBitmapManager.getInstance().startComposite(rect1, (float) angle2, new ComBitmapManager.CompositeMapListener() {
                        @Override
                        public void compositeMapCallBack(Bitmap mapComposite) {
                            iv_bitmap.setImageBitmap(mapComposite);
                        }
                    });
                    //Tools.showToast(action+"=="+yaw);
                }
            }
        });
        cb_init_post.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                is_init_post = b;
                if(b) {
                    joystickView.setVisibility(View.VISIBLE);
                }
            }
        });
        iv_bitmap.setOnTouchListener(imgSourceOnTouchListener);

        /*
        *
        * 长按地图
        * */
        iv_bitmap.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(Contanst.MAPPARAMENTITY == null) {
                    Tools.showToast("还没有地图哦");
                    cb_init_post.setChecked(false);
                    joystickView.setVisibility(View.GONE);
                    //直接返回
                    return false;
                }
                yServer = (float) (-(touchX - Contanst.MAPPARAMENTITY.getWidth()) * Contanst.MAPPARAMENTITY.getResolution() + Contanst.MAPPARAMENTITY.getOrigin().getY());
                Log.e("IndoorFragment", "yServer"
                        + yServer);
                xServer = (float) (-(touchY - Contanst.MAPPARAMENTITY.getHeight()) * Contanst.MAPPARAMENTITY.getResolution() + Contanst.MAPPARAMENTITY.getOrigin().getX());
                Log.e("IndoorFragment", "xServer"
                        + xServer);


                if(is_init_post) {//如果是重定位

                    //ControlSendManager.set_init_pose(xServer,yServer,0f);
                    Rect rect = new Rect(touchX, touchY, 0, 0);
                    ComBitmapManager.getInstance().setResetPoint(rect);

                    cb_init_post.setChecked(false);

                }else{//否则是描点
                    Rect rect = new Rect(touchX, touchY, 0, 0);
                    ComBitmapManager.getInstance().addTouchPoint(rect);

                    ControlSendManager.set_click_point(xServer, yServer);
                }

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
                //Tools.showToast("完成拼接");
                Log.e("linfd", "完成拼接");
                ThreadManager.getInstance().createLongPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        ObtainMapManager.getInstance(supperMapData).loadMap(new ObtainMapManager.MapListenter() {
                            @Override
                            public void getMap(final Bitmap map) {


                            }
                        });
                    }
                });
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

            }

            return false;
        }
    };
}
