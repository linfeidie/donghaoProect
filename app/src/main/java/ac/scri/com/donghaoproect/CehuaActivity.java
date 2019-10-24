package ac.scri.com.donghaoproect;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.blanke.xsocket.tcp.client.XTcpClient;
import com.qiantao.coordinatormenu.CoordinatorMenu;
import com.suke.widget.SwitchButton;

import java.util.ArrayList;

/**
 * https://github.com/qiantao94/CoordinatorMenu
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2019/10/18
 * <p>
 * 版本号：donghaoProect
 */
public class CehuaActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ImageView mHeadIv;
    private CoordinatorMenu mCoordinatorMenu;
    private ArrayList<Fragment> pages = null;
    private CommnetViewPager viewpager = null;
    private RegionView mRegionView;
    private TextView tv_work_mode,tv_car_num,tv_battery_percent,tv_battery_volt,tv_linear_speed,tv_angular_speed,tv_charging_state,tv_driver_fail,tv_motor_overload,tv_slam_exception,
            tv_emergency_stop,tv_goal_reach,tv_lidar_exception;
    private IPEditText ip_address;
    private SwitchButton switch_button;


    private DataWatcher watcher = new DataWatcher() {
        @Override
        public void notifyUpdata(Object data) {
            if (data instanceof DataEntity) {
                DataEntity dataEntity = (DataEntity) data;
                if (dataEntity.getType().equalsIgnoreCase(Contanst.GET_STATUS)) {
                    try {
                        SatusEntity satusEntity = GsonUtil.GsonToBean(dataEntity.message, SatusEntity.class);
                        updateUI(satusEntity);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("linfd", e.getMessage());
                    }
                    //Tools.showToast("状态");
                }
            }
        }
    };

    private void updateUI(SatusEntity satusEntity) {
        if(satusEntity == null || satusEntity.getRobot_state() == null) {
            return;
        }
        tv_work_mode.setText("工作模式:"+satusEntity.getWork_mode());
        tv_battery_percent.setText("电量:"+satusEntity.getBattery_percent()+"%");
        tv_battery_volt.setText("电压:"+satusEntity.getBattery_volt()+"V");
        tv_linear_speed.setText("线速度:"+satusEntity.getLinear_speed()+"m/s");
        tv_angular_speed.setText("角速度:"+satusEntity.getAngular_speed()+"rad/s");
        tv_charging_state.setText("充电状态:"+satusEntity.getRobot_state().isCharging_state()+"");
        tv_driver_fail.setText("驱动异常:"+satusEntity.getRobot_state().isDriver_fail()+"");
        tv_motor_overload.setText("电机过载:"+satusEntity.getRobot_state().isMotor_overload()+"");
        tv_slam_exception.setText("slam异常："+satusEntity.getRobot_state().isSlam_exception()+"");
        tv_emergency_stop.setText("急停:"+satusEntity.getRobot_state().isEmergency_stop()+"");
        tv_goal_reach.setText("达到目的地:"+satusEntity.getRobot_state().isGoal_reach()+"");
        tv_lidar_exception.setText("雷达异常:"+satusEntity.getRobot_state().isLidar_exception()+"");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cehua);
        DataChanger.getInstance().addObserver(watcher);
        initView();
        ip_address.setIpAddress("192.168.1.102");
    }


    private void initView() {
        tv_work_mode = findViewById(R.id.tv_work_mode);
        tv_car_num = findViewById(R.id.tv_car_num);
        tv_battery_percent = findViewById(R.id.tv_battery_percent);
        tv_battery_volt = findViewById(R.id.tv_battery_volt);
        tv_linear_speed = findViewById(R.id.tv_linear_speed);
        tv_angular_speed = findViewById(R.id.tv_angular_speed);
        tv_charging_state = findViewById(R.id.tv_charging_state);
        tv_driver_fail = findViewById(R.id.tv_driver_fail);
        tv_motor_overload = findViewById(R.id.tv_motor_overload);
        tv_slam_exception = findViewById(R.id.tv_slam_exception);
        tv_emergency_stop = findViewById(R.id.tv_emergency_stop);
        tv_goal_reach = findViewById(R.id.tv_goal_reach);
        tv_lidar_exception = findViewById(R.id.tv_lidar_exception);
        ip_address = findViewById(R.id.ip_address);
        switch_button = findViewById(R.id.switch_button);
//        tv_work_mode = findViewById(R.id.tv_work_mode);

        switch_button.setChecked(false);


        switch_button.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {

                if(isChecked) {
                    ControlSendManager.init(CehuaActivity.this,ip_address.getIpAddress(), new PackagesHandleCallback() {
                        @Override
                        public void messageCallback(TypeEntity typeEntity, String message) {
                            EntityHandlerManager.handerEntity(typeEntity, message);

                        }

                        @Override
                        public void onDisconnected(XTcpClient xTcpClient, String s, Exception e) {
                            super.onDisconnected(xTcpClient, s, e);
                            //switch_button.setChecked(false);
                        }
                    });
                    ControlSendManager.connect();
                }else {
                    ControlSendManager.disconnect();
                }

            }
        });

        mRegionView = (RegionView) findViewById(R.id.regionView);

        mRegionView.setListener(new RegionView.RegionViewClickListener() {

            @Override
            public void clickTopUp() {
                //Tools.showToast("view clickTop");
                TimerManager.getInstance().removeMessage();
            }

            @Override
            public void clickTopDown() {
                TimerManager.getInstance().start(new LooperRunnable() {
                    @Override
                    public void call() {
                         Log.e("linfd","前");
                        ControlSendManager.forward();
                    }
                });
            }

            @Override
            public void clickRightUp() {
                //Tools.showToast("view clickRight");
                Log.e("linfd","右消");
                TimerManager.getInstance().removeMessage();

            }

            @Override
            public void clickRightDown() {
                //Tools.showToast("view clickRightwdown");
                TimerManager.getInstance().start(new LooperRunnable() {
                    @Override
                    public void call() {
                        Log.e("linfd","右");
                        ControlSendManager.rightward();
                    }
                });
            }

            @Override
            public void clickLeftUp() {
                Log.e("linfd","左消");
                TimerManager.getInstance().removeMessage();
            }

            @Override
            public void clickLeftDown() {
                TimerManager.getInstance().start(new LooperRunnable() {
                    @Override
                    public void call() {
                         Log.e("linfd","左");
                        ControlSendManager.leftward();
                    }
                });
            }

            @Override
            public void clickCenter() {
                ControlSendManager.stop();
                //   showToast("view clickCenter");
            }

            @Override
            public void clickBottomUp() {
                Log.e("linfd","后消");
                TimerManager.getInstance().removeMessage();
                //  showToast("view clickBottom");
            }

            @Override
            public void clickBottomDown() {
                TimerManager.getInstance().start(new LooperRunnable() {
                    @Override
                    public void call() {
                         Log.e("linfd","后");
                        ControlSendManager.backward();
                    }
                });
            }
        });
        pages = new ArrayList<Fragment>();
        mCoordinatorMenu = (CoordinatorMenu) findViewById(R.id.menu);
        viewpager = findViewById(R.id.viewpager);

        // create new fragments
        pages.add(new BaiduFragment());
        pages.add(new IndoorFragment());

        // set adapter
        viewpager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        viewpager.setOnPageChangeListener(this);
        viewpager.setCurrentItem(0);
        //titles.get(0).setSelected(true);

//        mHeadIv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mCoordinatorMenu.isOpened()) {
//                    mCoordinatorMenu.closeMenu();
//                } else {
//                    mCoordinatorMenu.openMenu();
//                }
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        if (mCoordinatorMenu.isOpened()) {
            mCoordinatorMenu.closeMenu();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }


    // adapter
    private class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return pages.size();
        }

        @Override
        public Fragment getItem(int arg0) {
            return pages.get(arg0);
        }
    }


}
