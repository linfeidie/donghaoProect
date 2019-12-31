package ac.scri.com.donghaoproect.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

import ac.scri.com.donghaoproect.LooperRunnable;
import ac.scri.com.donghaoproect.R;
import ac.scri.com.donghaoproect.manager.TcpControlSendManager;
import ac.scri.com.donghaoproect.manager.ThreadManager;
import ac.scri.com.donghaoproect.manager.TimerManager;

/**
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2019/9/23
 * <p>
 * 版本号：donghaoProect
 */
public class SocetTestActivity extends AppCompatActivity  {
    public static final String TAG = SocetTestActivity.class.getSimpleName();
    private Button bt_rotate;
    private ImageView iv_map;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
//        ControlSendManager.init(this, new PackagesHandleCallback() {
//            @Override
//            public void messageCallback(TypeEntity typeEntity, String message) {
//                EntityHandlerManager.handerEntity(typeEntity, message);
//            }
//        });
        TcpControlSendManager.connect();
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
        TcpControlSendManager.get_status();
    }

    public void get_GPS(View view) {
        TcpControlSendManager.get_GPS();
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
                            TcpControlSendManager.get_GPS();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, 0, 1000);
            }
        });

    }

    public void set_speed(View view) {
        TcpControlSendManager.set_speed(0, 0.1);
    }

    public void set_distance(View view) {
        //ControlSendManager.set_distance(0, 0);
    }

    public void set_work_mode(View view) {
        TcpControlSendManager.set_work_mode("charging");
    }

    public void set_max_speed(View view) {
        TcpControlSendManager.set_max_speed(1.5, 1.5);
    }

    public void get_map(View view) {
        TcpControlSendManager.get_map();
    }

    public void get_path(View view) {
        TcpControlSendManager.get_path();
    }
}

