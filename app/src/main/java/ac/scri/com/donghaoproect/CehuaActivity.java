package ac.scri.com.donghaoproect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.blanke.xsocket.tcp.client.XTcpClient;
import com.qiantao.coordinatormenu.CoordinatorMenu;
import com.suke.widget.SwitchButton;
import com.warkiz.widget.IndicatorSeekBar;

import java.util.ArrayList;

import ac.scri.com.donghaoproect.nicedialog.BaseNiceDialog;
import ac.scri.com.donghaoproect.nicedialog.NiceDialog;
import ac.scri.com.donghaoproect.nicedialog.ViewConvertListener;
import ac.scri.com.donghaoproect.nicedialog.ViewHolder;
import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * https://github.com/qiantao94/CoordinatorMenu
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2019/10/18
 * <p>
 * 版本号：donghaoProect
 */
public class CehuaActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private String[] STATE_ITEMS = {"","idle", "sleep", "navi_straight", "navi_smart", "charge", "navi_auto"};

    private ImageView mHeadIv;
    private CoordinatorMenu mCoordinatorMenu;
    private ArrayList<Fragment> pages = null;
    private CommnetViewPager viewpager = null;
    private RegionView mRegionView;
    private TextView tv_work_mode,tv_car_num,tv_battery_percent,tv_battery_volt,tv_linear_speed,tv_angular_speed,tv_charging_state,tv_driver_fail,tv_motor_overload,tv_slam_exception,
            tv_emergency_stop,tv_goal_reach,tv_lidar_exception;
    private IPEditText ip_address;
    private SwitchButton switch_button;
    private MaterialSpinner state_spinner,ids_spinner;
    private TextView tv_connected_tip;
    private TextView tv_setting;


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

                }else if(dataEntity.getType().equalsIgnoreCase(Contanst.GET_ONLINE_IDS)) {
                    OnlineIdsEntity onlineIdsEntity = GsonUtil.GsonToBean(dataEntity.message, OnlineIdsEntity.class);
                    showIds(onlineIdsEntity);
                }else if(((DataEntity) data).getType().equalsIgnoreCase(Contanst.DISCONNECT)) {
                    if(switch_button.isChecked()) {
                        switch_button.toggle();
                        clearShow();
                    }


                }
            }
        }
    };

    private void showIds(final OnlineIdsEntity onlineIdsEntity) {
        if(onlineIdsEntity != null && onlineIdsEntity.getIds().size() < 1) {
            switch_button.setChecked(false);
            return;
        }
        //  ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, Tools.positive_number(onlineIdsEntity.getIds()));
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, onlineIdsEntity.getIds());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ids_spinner.setAdapter(adapter);
        ids_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Contanst.CARID = onlineIdsEntity.getIds().get(i);
                ControlSendManager.set_connet();
                ControlSendManager.get_map();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Tools.showToast("onNothingSelected");
            }
        });
    }

    private void updateUI(SatusEntity satusEntity) {
        if(satusEntity == null || satusEntity.getRobot_state() == null) {
            return;
        }
        tv_work_mode.setText("工作模式:"+satusEntity.getWork_mode());
        tv_battery_percent.setText("电量:"+satusEntity.getBattery_percent()+"%");
        tv_battery_volt.setText("电压:"+satusEntity.getBattery_volt()+"V");
        tv_linear_speed.setText("线速度:"+satusEntity.getLinear_speed()+"m/s");
        tv_angular_speed.setText("角速度:"+satusEntity.getAngular_speed()+"rad/s");
        tv_charging_state.setText("充电状态:"+(satusEntity.getRobot_state().isCharging_state()?"是":"否"));
        tv_driver_fail.setText("驱动异常:"+(satusEntity.getRobot_state().isDriver_fail()?"是":"否"));
        tv_motor_overload.setText("电机过载:"+(satusEntity.getRobot_state().isMotor_overload()?"是":"否"));
        tv_slam_exception.setText("slam异常："+(satusEntity.getRobot_state().isSlam_exception()?"是":"否"));
        tv_emergency_stop.setText("急停:"+(satusEntity.getRobot_state().isEmergency_stop()?"是":"否"));
        tv_goal_reach.setText("达到目的地:"+(satusEntity.getRobot_state().isGoal_reach()?"是":"否"));
        tv_lidar_exception.setText("雷达异常:"+(satusEntity.getRobot_state().isLidar_exception()?"是":"否"));

    }
    private void clearShow(){
        tv_work_mode.setText("工作模式:");
        tv_battery_percent.setText("电量:");
        tv_battery_volt.setText("电压:");
        tv_linear_speed.setText("线速度:");
        tv_angular_speed.setText("角速度:");
        tv_charging_state.setText("充电状态:");
        tv_driver_fail.setText("驱动异常:");
        tv_motor_overload.setText("电机过载:");
        tv_slam_exception.setText("slam异常：");
        tv_emergency_stop.setText("急停:");
        tv_goal_reach.setText("达到目的地:");
        tv_lidar_exception.setText("雷达异常:");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cehua);
        getSupportActionBar().hide();
        DataChanger.getInstance().addObserver(watcher);
        initView();
        ip_address.setIpAddress("192.168.1.107");
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
        switch_button.setChecked(false);
        state_spinner = (MaterialSpinner) findViewById(R.id.state_spinner);
        ids_spinner = findViewById(R.id.ids_spinner);
        tv_connected_tip = findViewById(R.id.tv_connected_tip);
        tv_setting = findViewById(R.id.tv_setting);

        tv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(CehuaActivity.this,SettingActivity.class);
                startActivity(intent);
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, STATE_ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        state_spinner.setAdapter(adapter);
        state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Tools.showToast(ITEMS[i]);
                ControlSendManager.set_work_mode(STATE_ITEMS[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Tools.showToast("onNothingSelected");
            }
        });
        switch_button.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {

                if(isChecked) {
                    ControlSendManager.init(CehuaActivity.this,ip_address.getIpAddress(), new PackagesHandleCallback() {
                        @Override
                        public void messageCallback(TypeEntity typeEntity, String message) {
                            EntityHandlerManager.handerEntity(typeEntity, message, new EntityHandlerManager.HandlerCallback() {
                                @Override
                                public void callback(boolean b) {
                                    //switch_button.setChecked(b);
                                }
                            });

                        }

                        @Override
                        public void onDisconnected(XTcpClient xTcpClient, String s, Exception e) {
                            super.onDisconnected(xTcpClient, s, e);
//                            if(switch_button.isChecked()) {
//                                switch_button.setChecked(false);
//                            }

                        }

                        @Override
                        public void onConnected(XTcpClient xTcpClient) {
                            super.onConnected(xTcpClient);

//                            if(!switch_button.isChecked()) {
//                                switch_button.setChecked(true);
//                            }
                        }
                    });
                    ControlSendManager.connect();
                }else {
                    ControlSendManager.disconnect();
                }
//                if(switch_button.isChecked()) {
//                    switch_button.setChecked(true);
//                }else  {
//                    switch_button.setChecked(false);
//                }
                ip_address.setVisibility(isChecked?View.GONE:View.VISIBLE);
                tv_connected_tip.setVisibility(isChecked?View.VISIBLE:View.GONE);
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
                Log.e("linfd","右消");
                TimerManager.getInstance().removeMessage();

            }

            @Override
            public void clickRightDown() {
                TimerManager.getInstance().start(new LooperRunnable() {
                    @Override
                    public void call() {
                        //Log.e("linfd","右");
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
                TimerManager.getInstance().removeMessage();
                ControlSendManager.stop();
            }

            @Override
            public void clickBottomUp() {
                Log.e("linfd","后消");
                TimerManager.getInstance().removeMessage();
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

     IndicatorSeekBar sb_distance;
    IndicatorSeekBar sb_angular;
    public void done(View view ){
        NiceDialog.init().setLayoutId(R.layout.dialog_panel).setConvertListener(new ViewConvertListener() {
            @Override
            public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                 sb_distance = holder.getView(R.id.sb_distance);
                 sb_angular = holder.getView(R.id.sb_angular);
                sb_distance.setOnSeekChangeListener(new OnSimpleSeekChangeListener(){
                    @Override
                    public void onStopTrackingTouch(IndicatorSeekBar seekBar) {
                        super.onStopTrackingTouch(seekBar);
                        sb_distance = seekBar;
                    }
                });
                sb_distance.setIndicatorTextFormat("${PROGRESS} 米");

                sb_angular.setOnSeekChangeListener(new OnSimpleSeekChangeListener(){
                    @Override
                    public void onStopTrackingTouch(IndicatorSeekBar seekBar) {
                        super.onStopTrackingTouch(seekBar);
                        sb_angular = seekBar;
                    }
                });
                sb_angular.setIndicatorTextFormat("${PROGRESS} °");
                holder.setOnClickListener(R.id.tv_sure, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ControlSendManager.set_distance(Math.abs(sb_distance.getProgressFloat()),sb_angular.getProgressFloat(),sb_distance.getProgressFloat()>0?0.3:-0.3,sb_angular.getProgressFloat()>0?0.3:-0.3);
                        sb_distance.setProgress(0);
                        sb_angular.setProgress(0);
                        dialog.dismiss();
                    }

                });

            }
        }).setWidth(0).setHeight(250).setPosition(Gravity.BOTTOM).show(getSupportFragmentManager());
    }
}
