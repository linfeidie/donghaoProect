package ac.scri.com.donghaoproect;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.blanke.xsocket.tcp.client.XTcpClient;
import com.blanke.xsocket.tcp.client.bean.TcpMsg;
import com.blanke.xsocket.tcp.client.listener.TcpClientListener;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2019/9/23
 * <p>
 * 版本号：donghaoProect
 */
public class SocetTestActivity extends AppCompatActivity implements TcpClientListener {
    public static final String TAG = SocetTestActivity.class.getSimpleName();
    private Button bt_rotate;
    private boolean isConnect = false;
    private ImageView iv_map;
    private boolean isFirst = true ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        ControlSendManager.init(this, this);
        ControlSendManager.connect();
        // startGetGSP();
        bt_rotate = findViewById(R.id.bt_rotate);
        iv_map = (ImageView) findViewById(R.id.iv_map);

        bt_rotate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {


                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        TimerManager.getInstance().start(new LooperRunnable() {
                            @Override
                            public void call() {
                                Log.e("linfd","11111121");
                            }
                        });
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        TimerManager.getInstance().removeMessage();
                        break;
                }
                return true;
            }
        });

    }


    public void get_status(View view) {
        ControlSendManager.get_status();
    }

    @Override
    public void onConnected(XTcpClient xTcpClient) {
        isConnect = true;
        Toast.makeText(this, xTcpClient.getTargetInfo().getIp() + "连接成功", Toast.LENGTH_SHORT).show();
        ControlSendManager.set_online();
        ControlSendManager.set_connet();
    }

    @Override
    public void onSended(XTcpClient xTcpClient, TcpMsg tcpMsg) {
        //Toast.makeText(this,tcpMsg.getSourceDataString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisconnected(XTcpClient xTcpClient, String s, Exception e) {
        isConnect = false;
        Toast.makeText(this, "断开连接", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReceive(XTcpClient xTcpClient, final TcpMsg tcpMsg) {

        //Log.e("bm", "runnable线程： " + Thread.currentThread().getId() + " name:" + Thread.currentThread().getName());
//        ThreadManager.getInstance().createLongPool().execute(new Runnable() {
//            @Override
//            public void run() {
//                handerMap(tcpMsg);
//            }
//        });

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                handerMap(tcpMsg);
//            }
//        }).start();

       // TypeEntity typeEntity = GsonUtil.GsonToBean(tcpMsg.getSourceDataString(), TypeEntity.class);
        //handerEntity(typeEntity, tcpMsg);
       // handerMap(tcpMsg);
        if(isFirst && tcpMsg.getSourceDataString().contains("{\"data\":")) {
           String[] source =  tcpMsg.getSourceDataString().split("\n");
            handerEntity(GsonUtil.GsonToBean(source[1], TypeEntity.class), tcpMsg);
           tcpMsg.setSourceDataString(tcpMsg.getSourceDataString().substring(tcpMsg.getSourceDataString().indexOf("{\"data\":")));
           isFirst =false;
        }
        if(tcpMsg.getSourceDataString().startsWith("{") && tcpMsg.getSourceDataString().trim().endsWith("}")) {
            TypeEntity typeEntity = null;

                String[] data = tcpMsg.getSourceDataString().split("\n");
                if(data.length > 1) {
                    Toast.makeText(this,"222",Toast.LENGTH_SHORT).show();
                }
                for (int i = 0; i < data.length ; i++) {
                    typeEntity = GsonUtil.GsonToBean(data[i], TypeEntity.class);
                    handerEntity(typeEntity, tcpMsg);
                }



        }else{

//            String[] data = tcpMsg.getSourceDataString().split("\n");
//            TypeEntity typeEntity = null;
//            for (int i = 0; i < data.length -1 ; i++) {
//                typeEntity = GsonUtil.GsonToBean(data[i], TypeEntity.class);
//                handerEntity(typeEntity, tcpMsg);
//            }


            Toast.makeText(this,"3333",Toast.LENGTH_SHORT).show();


            SpliceMap(tcpMsg);
        }



    }

    private void SpliceMap(TcpMsg tcpMsg) {
        HandleSubpackageManager.getInstance(new HandleSubpackageManager.FinishListener() {
            @Override
            public void MapDateFinish(final MapdataEntity supperMapData) {
                Toast.makeText(SocetTestActivity.this, "完成拼接", Toast.LENGTH_SHORT).show();
                Log.e("linfd","完成拼接");
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        ObtainMapManager.getInstance(supperMapData).loadMap(new ObtainMapManager.MapListenter() {
//                            @Override
//                            public void getMap(final Bitmap map) {
//                                SocetTestActivity.this.runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        iv_map.setImageBitmap(map);
//                                        Toast.makeText(SocetTestActivity.this, "显示", Toast.LENGTH_SHORT).show();
//                                        Log.e("linfd", "完成拼接");
//                                    }
//                                });
//
//                            }
//                        });
//                    }
//                }).start();
            }
        }).handerMap(tcpMsg);
    }

    private void handerEntity(TypeEntity typeEntity, TcpMsg tcpMsg) {
        switch (typeEntity.getType()) {
            case Contanst.GET_STATUS:
                //SatusEntity satusEntity = GsonUtil.GsonToBean(tcpMsg.getSourceDataString(), SatusEntity.class);
                Toast.makeText(this, "状态", Toast.LENGTH_SHORT).show();
                break;
            case Contanst.GET_MAP:
                Toast.makeText(this, "地图", Toast.LENGTH_SHORT).show();
               // SpliceMap(tcpMsg);
                break;
            case Contanst.GET_GPS:
                Toast.makeText(this, "GPS", Toast.LENGTH_SHORT).show();
                break;
            case Contanst.GET_PATH:
                Toast.makeText(this, "路径", Toast.LENGTH_SHORT).show();
                break;
            case Contanst.GET_MAP_PARAM:
                Toast.makeText(this, "地图包信息", Toast.LENGTH_SHORT).show();
                String[] source =  tcpMsg.getSourceDataString().split("\n");
                MapParamEntity mapParamEntity = GsonUtil.GsonToBean(source[1],MapParamEntity.class);
                Contanst.MAPPARAMENTITY = mapParamEntity;

                Log.e("linfd","地图包信息");
                break;
            case Contanst.MAP_DATA:
                Toast.makeText(this, "地图数据", Toast.LENGTH_SHORT).show();
                Log.e("linfd","地图数据");
                SpliceMap(tcpMsg);
                break;
            case Contanst.SCAN:
                Toast.makeText(this, "激光", Toast.LENGTH_SHORT).show();
                break;
        }
    }




    @Override
    public void onValidationFail(XTcpClient xTcpClient, TcpMsg tcpMsg) {
    }

    public void get_GPS(View view) {
        ControlSendManager.get_GPS();
    }

    public void stop(View view) {
        //mHandler.removeCallbacks(r);
        // r = null;
    }

    private void startGetGSP() {
        ThreadManager.getInstance().createLongPool().execute(new Runnable() {
            @Override
            public void run() {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            ControlSendManager.get_GPS();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, 0, 1000);
            }
        });

    }

    public void set_speed(View view) {
        ControlSendManager.set_speed(0, 0.1);
    }

    public void set_distance(View view) {
        ControlSendManager.set_distance(0, 0);
    }

    public void set_work_mode(View view) {
        ControlSendManager.set_work_mode("charging");
    }

    public void set_max_speed(View view) {
        ControlSendManager.set_max_speed(1.5, 1.5);
    }

    public void get_map(View view) {
        ControlSendManager.get_map();
    }

    public void get_path(View view) {
       // ControlSendManager.get_path();
        String s = "{\"angular_speed\":0,\"axis_x\":-0.34514641761779785,\"axis_y\":-0.67194199562072754,\"axis_z\":0,\"battery_percent\":27,\"battery_volt\":26,\"from_id\":1,\"linear_speed\":0,\"robot_state\":{\"charging_state\":true,\"driver_fail\":false,\"emergency_stop\":false,\"goal_reach\":false,\"lidar_exception\":false,\"motor_overload\":false,\"path_fail\":false,\"slam_exception\":false},\"robot_yaw\":1.512751579284668,\"to_id\":0,\"type\":\"robot_status\",\"work_mode\":\"navi_straight\"}\n" +
                "{\"from_id\":1,\"height\":1728,\"origin\":{\"x\":-77.200000000000003,\"y\":-21.199999999999999,\"z\":0},\"pack_id\":1,\"pack_num\":342,\"resolution\":0.05000000074505806,\"to_id\":0,\"type\":\"map_param\",\"width\":1728}\n" +
                "{\"data\":[64164,-1,1,100,172";
        String ss = s.substring(s.indexOf("{\"data\":"));
        Log.e("linfd",ss);
    }
}

