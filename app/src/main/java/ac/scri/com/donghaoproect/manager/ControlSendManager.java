package ac.scri.com.donghaoproect.manager;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.blanke.xsocket.tcp.client.TcpConnConfig;
import com.blanke.xsocket.tcp.client.XTcpClient;
import com.blanke.xsocket.tcp.client.bean.TargetInfo;
import com.blanke.xsocket.tcp.client.helper.stickpackage.AbsStickPackageHelper;
import com.blanke.xsocket.tcp.client.helper.stickpackage.BaseStickPackageHelper;
import com.blanke.xsocket.tcp.client.listener.TcpClientListener;
import com.blanke.xsocket.tcp.client.manager.TcpClientManager;
import com.blanke.xsocket.utils.StringValidationUtils;

import ac.scri.com.donghaoproect.Contanst;
import ac.scri.com.donghaoproect.DonghaoApplication;
import ac.scri.com.donghaoproect.entity.ActionEntity;
import ac.scri.com.donghaoproect.entity.IinitPoseEntity;
import ac.scri.com.donghaoproect.entity.OrderEntity;
import ac.scri.com.donghaoproect.entity.OrderEntityDist;
import ac.scri.com.donghaoproect.entity.OrderEntitySetMaxSpeed;
import ac.scri.com.donghaoproect.entity.OrderEntitySetSpeed;
import ac.scri.com.donghaoproect.entity.OrderEntitySetWorkMode;
import ac.scri.com.donghaoproect.entity.TouchPointEntity;
import ac.scri.com.donghaoproect.tool.GsonUtil;
import ac.scri.com.donghaoproect.tool.Tools;

/**
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2019/9/25
 * <p>
 * 版本号：donghaoProect
 */
public class ControlSendManager {
    private static XTcpClient xTcpClient;
    private static Context mContext = DonghaoApplication.getApplication();
    private static int id = -9;
    private static int click_count = 1;//描点数

    public static void  init(Context context,String ipAddress, TcpClientListener listener){
        TcpClientManager.sMXTcpClients.clear();
        mContext = context;
        AbsStickPackageHelper stickHelper = new BaseStickPackageHelper(8000);//tcpclientStaticpackagelayout.getStickPackageHelper();
        if (stickHelper == null) {
            Toast.makeText(context, "粘包参数设置错误", Toast.LENGTH_SHORT).show();
            //addMsg("粘包参数设置错误");
            return;
        }
        if(TextUtils.isEmpty(ipAddress)) {
            Tools.showToast("请输入完整的IP地址");
        }
       // String temp = "192.168.1.101:7665";//tcpclientEditIp.getText().toString().trim();

        if ( StringValidationUtils.validateRegex(ipAddress, StringValidationUtils.RegexIP)
                && StringValidationUtils.validateRegex(Contanst.PORT, StringValidationUtils.RegexPort)) {
            TargetInfo targetInfo = new TargetInfo(ipAddress, Integer.parseInt(Contanst.PORT));
            xTcpClient = XTcpClient.getTcpClient(targetInfo);
            xTcpClient.addTcpClientListener(listener);
            xTcpClient.config(new TcpConnConfig.Builder()
                    .setStickPackageHelper(stickHelper)//粘包
                    .setIsReconnect(true)//tcpclientSwitchReconnect.isChecked()
                    .create());

        } else {
            Toast.makeText(context, "服务器地址必须是", Toast.LENGTH_SHORT).show();
            //addMsg("服务器地址必须是 ip:port 形式");
        }
    }

    public static void connect() {
        if(xTcpClient == null) {
            return;
        }
        if(xTcpClient.isConnected()) {
            Toast.makeText(mContext,"已经连接",Toast.LENGTH_SHORT).show();
            return;
        }
        if (xTcpClient.isDisconnected()) {
            xTcpClient.connect();
        } else {
            Toast.makeText(mContext, "已经存在该连接", Toast.LENGTH_SHORT).show();
            //addMsg("已经存在该连接");
        }

    }

    public static void disconnect( ){
        if (xTcpClient != null && xTcpClient.isConnected()) {
            xTcpClient.disconnect();
        }
    }

    //注册在线
    public static  void set_online(){
        send_order(0,"set_online");
    }

    //设置连接机器
    public static  void set_connet(){
        send_order(Contanst.CARID,"set_connet");
    }
    //查询状态
    public static void get_status(){
        send_order(Contanst.CARID,"get_status");
    }

    public static void get_path(){
        send_order(Contanst.CARID,"get_path");
    }

    public static void get_map(){
        send_order(Contanst.CARID,"get_nav_map");
    }
    //查询gps
    public static void get_GPS(){
        send_order(Contanst.CARID,"get_GPS");
    }
    //查询本机IPs
    public static void get_inet_ip(){
        send_order(0,"get_inet_ip");
    }

    //设置特定距离和角度
    public static void set_distance(double dist,double turn,double linear_speed,double angular_speed){
        if (xTcpClient != null) {
            OrderEntityDist entity = new OrderEntityDist(id,Contanst.CARID,"set_distance");
            entity.setDist(dist);
            entity.setTurn(turn);
            entity.setLinear_speed(linear_speed);
            entity.setAngular_speed(angular_speed);
            String s = GsonUtil.GsonString(entity)+ "\n";
            xTcpClient.sendMsg(s);
        } else {
            Tools.showToast("还没有连接到服务器");
        }
    }

    public static void set_work_mode(String work_mode){
        if (xTcpClient != null) {
            OrderEntitySetWorkMode entity = new OrderEntitySetWorkMode(id,Contanst.CARID,"set_work_mode");
            entity.work_mode = work_mode;
            String s = GsonUtil.GsonString(entity)+ "\n";
            xTcpClient.sendMsg(s);
        } else {
            Tools.showToast("还没有连接到服务器");
        }
    }
//手动控制包
    public static void set_speed(double linear_speed,double angular_speed){
        if (xTcpClient != null) {
            OrderEntitySetSpeed entity = new OrderEntitySetSpeed(id,Contanst.CARID,"set_speed");
            entity.linear_speed = linear_speed;
            entity.angular_speed = angular_speed;
            entity.timeout = 10;
            entity.stop = false;
            String s = GsonUtil.GsonString(entity)+ "\n";
            xTcpClient.sendMsg(s);
        } else {
            Tools.showToast("还没有连接到服务器");
        }
    }

    // 向前走
    public static void forward(){
        set_speed(Contanst.LINEAR_SPEED,0);
    }

    public static void backward(){
        set_speed(-Contanst.LINEAR_SPEED,0);
    }

    public static void leftward(){
        set_speed(0,Contanst.ANGULAR_SPEED);
    }

    public static void rightward(){
        set_speed(0,-Contanst.ANGULAR_SPEED);
    }

    public static void stop(){
        set_speed(0,0);
    }
    public static void send_order(int to_id,String order){
        if (xTcpClient != null) {
            OrderEntity entity = new OrderEntity(id,to_id,order);
            String s = GsonUtil.GsonString(entity)+ "\n";
            xTcpClient.sendMsg(s);
        } else {
            Tools.showToast("还没有连接到服务器");
        }
    }

    public static void get_online_ids(){
        send_order(0,"get_online_ids");
    }
    public static void set_max_speed(double max_linear_speed,double max_angular_speed){
        if (xTcpClient != null) {
            OrderEntitySetMaxSpeed entity = new OrderEntitySetMaxSpeed(id,Contanst.CARID,"set_max_speed");
            entity.max_linear_speed = max_linear_speed;
            entity.max_angular_speed = max_angular_speed;
            String s = GsonUtil.GsonString(entity)+ "\n";
            xTcpClient.sendMsg(s);
        } else {
            Tools.showToast("还没有连接到服务器");
        }
    }

    /*
    * 设置描点
    * */
    public static void set_click_point(float xServer,float yServer){
        if (xTcpClient != null) {
            TouchPointEntity entity = new TouchPointEntity();
            entity.setFrom_id(id);
            entity.setPoint_num(click_count);
            click_count++;
            entity.setTo_id(Contanst.CARID);
            TouchPointEntity.PointsEntity pointsEntity = new TouchPointEntity.PointsEntity();
            pointsEntity.setX(xServer);
            pointsEntity.setY(yServer);
            pointsEntity.setZ(0);
            entity.setPoints(pointsEntity);
            entity.setType(Contanst.SET_CLICK_POINT);
            String s = GsonUtil.GsonString(entity)+ "\n";
            xTcpClient.sendMsg(s);
        } else {
            Tools.showToast("还没有连接到服务器");
        }
    }
/*
*
* 描点后启动
* */
    public static void set_action(){
        if (xTcpClient != null) {
           ActionEntity entity = new ActionEntity();
           entity.setFrom_id(id);
           entity.setTo_id(Contanst.CARID);
           entity.setType("set_action");
           entity.setAction("start");
           entity.setCycle(false);
            String s = GsonUtil.GsonString(entity)+ "\n";
            xTcpClient.sendMsg(s);
        } else {
            Tools.showToast("还没有连接到服务器");
        }
    }


    /*
    * 重定位
    *
    * */

    public static void set_init_pose(double x,double y, double yaw){
        if (xTcpClient != null) {
            IinitPoseEntity entity = new IinitPoseEntity();
            entity.setFrom_id(id);
            entity.setTo_id(Contanst.CARID);
            entity.setType(Contanst.SET_INIT_POSE);
            IinitPoseEntity.PoseEntity poseEntity = new IinitPoseEntity.PoseEntity();
            poseEntity.setX(x);
            poseEntity.setY(y);
            poseEntity.setZ(0f);
            poseEntity.setYaw(0f);
            entity.setPose(poseEntity);
            String s = GsonUtil.GsonString(entity)+ "\n";
            xTcpClient.sendMsg(s);
        } else {
            Tools.showToast("还没有连接到服务器");
        }
    }
}
