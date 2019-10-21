package ac.scri.com.donghaoproect;

import android.graphics.Bitmap;
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

import java.util.ArrayList;
import java.util.List;
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
    private List<MapdataEntity> mapdataEntities = new ArrayList<>();
    private StringBuffer incompleteJson = new StringBuffer();
    private int packNum = -1;
    private MapdataEntity supperMapData;
    private ImageView iv_map;

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
        if(tcpMsg.getSourceDataString().startsWith("{") && tcpMsg.getSourceDataString().endsWith("}")) {
            TypeEntity typeEntity = GsonUtil.GsonToBean(tcpMsg.getSourceDataString(), TypeEntity.class);
            handerEntity(typeEntity, tcpMsg);
        }else{
            SpliceMap(tcpMsg);
        }



    }

    private void SpliceMap(TcpMsg tcpMsg) {
        HandleSubpackageManager.getInstance(new HandleSubpackageManager.FinishListener() {
            @Override
            public void MapDateFinish(final MapdataEntity supperMapData) {
                Toast.makeText(SocetTestActivity.this, "完成拼接", Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ObtainMapManager.getInstance(supperMapData).loadMap(new ObtainMapManager.MapListenter() {
                            @Override
                            public void getMap(final Bitmap map) {
                                SocetTestActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        iv_map.setImageBitmap(map);
                                        Toast.makeText(SocetTestActivity.this, "显示", Toast.LENGTH_SHORT).show();
                                        Log.e("linfd", "完成拼接");
                                    }
                                });

                            }
                        });
                    }
                }).start();
            }
        }).handerMap(tcpMsg);
    }

    private void handerEntity(TypeEntity typeEntity, TcpMsg tcpMsg) {
        switch (typeEntity.getType()) {
            case Contanst.GET_STATUS:
                SatusEntity satusEntity = GsonUtil.GsonToBean(tcpMsg.getSourceDataString(), SatusEntity.class);
                Toast.makeText(this, "状态", Toast.LENGTH_SHORT).show();
                break;
            case Contanst.GET_MAP:
                Toast.makeText(this, "地图", Toast.LENGTH_SHORT).show();
                SpliceMap(tcpMsg);
                break;
            case Contanst.GET_GPS:
                Toast.makeText(this, "GPS", Toast.LENGTH_SHORT).show();
                break;
            case Contanst.GET_PATH:
                Toast.makeText(this, "路径", Toast.LENGTH_SHORT).show();
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
        ControlSendManager.get_path();
    }
}

