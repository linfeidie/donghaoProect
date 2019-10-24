package ac.scri.com.donghaoproect;

import android.content.Context;
import android.widget.Toast;

import com.blanke.xsocket.tcp.client.TcpConnConfig;
import com.blanke.xsocket.tcp.client.XTcpClient;
import com.blanke.xsocket.tcp.client.bean.TargetInfo;
import com.blanke.xsocket.tcp.client.helper.stickpackage.AbsStickPackageHelper;
import com.blanke.xsocket.tcp.client.helper.stickpackage.BaseStickPackageHelper;
import com.blanke.xsocket.tcp.client.listener.TcpClientListener;
import com.blanke.xsocket.utils.StringValidationUtils;

/**
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2019/9/25
 * <p>
 * 版本号：donghaoProect
 */
public class ControlSendManager {
    private static XTcpClient xTcpClient;
    private static Context mContext;
    private static int id = -9;

    public static void  init(Context context, TcpClientListener listener){
        mContext = context;
        AbsStickPackageHelper stickHelper = new BaseStickPackageHelper(8000);//tcpclientStaticpackagelayout.getStickPackageHelper();
        if (stickHelper == null) {
            Toast.makeText(context, "粘包参数设置错误", Toast.LENGTH_SHORT).show();
            //addMsg("粘包参数设置错误");
            return;
        }
        String temp = "192.168.1.101:7665";//tcpclientEditIp.getText().toString().trim();
        String[] temp2 = temp.split(":");
        if (temp2.length == 2 && StringValidationUtils.validateRegex(temp2[0], StringValidationUtils.RegexIP)
                && StringValidationUtils.validateRegex(temp2[1], StringValidationUtils.RegexPort)) {
            TargetInfo targetInfo = new TargetInfo(temp2[0], Integer.parseInt(temp2[1]));
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
        if(xTcpClient != null && xTcpClient.isConnected()) {
            Toast.makeText(mContext,"已经连接",Toast.LENGTH_SHORT).show();
            return;
        }
        String temp = "192.168.1.100:7665";//tcpclientEditIp.getText().toString().trim();
        String[] temp2 = temp.split(":");
        if (temp2.length == 2 && StringValidationUtils.validateRegex(temp2[0], StringValidationUtils.RegexIP)
                && StringValidationUtils.validateRegex(temp2[1], StringValidationUtils.RegexPort)) {
            TargetInfo targetInfo = new TargetInfo(temp2[0], Integer.parseInt(temp2[1]));

            if (xTcpClient.isDisconnected()) {
                xTcpClient.connect();
            } else {
                Toast.makeText(mContext, "已经存在该连接", Toast.LENGTH_SHORT).show();
                //addMsg("已经存在该连接");
            }
        } else {
            Toast.makeText(mContext, "服务器地址必须是", Toast.LENGTH_SHORT).show();
            //addMsg("服务器地址必须是 ip:port 形式");
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
        send_order(1,"set_connet");
    }
    //查询状态
    public static void get_status(){
        send_order(1,"get_status");
    }

    public static void get_path(){
        send_order(1,"get_path");
    }

    public static void get_map(){
        send_order(1,"get_map");
    }
    //查询gps
    public static void get_GPS(){
        send_order(1,"get_GPS");
    }
    //查询本机IPs
    public static void get_inet_ip(){
        send_order(0,"get_inet_ip");
    }

    //设置特定距离和角度
    public static void set_distance(double dist,double turn){
        if (xTcpClient != null) {
            OrderEntityDist entity = new OrderEntityDist(id,1,"set_distance");
            entity.setDist(dist);
            entity.setTurn(turn);
            String s = GsonUtil.GsonString(entity)+ "\n";
            xTcpClient.sendMsg(s);
        } else {
            Toast.makeText(mContext, "还没有连接到服务器", Toast.LENGTH_SHORT).show();
        }
    }

    public static void set_work_mode(String work_mode){
        if (xTcpClient != null) {
            OrderEntitySetWorkMode entity = new OrderEntitySetWorkMode(id,1,"set_work_mode");
            entity.work_mode = work_mode;
            String s = GsonUtil.GsonString(entity)+ "\n";
            xTcpClient.sendMsg(s);
        } else {
            Toast.makeText(mContext, "还没有连接到服务器", Toast.LENGTH_SHORT).show();
        }
    }
//手动控制包
    public static void set_speed(double linear_speed,double angular_speed){
        if (xTcpClient != null) {
            OrderEntitySetSpeed entity = new OrderEntitySetSpeed(id,1,"set_speed");
            entity.angular_speed = linear_speed;
            entity.linear_speed = angular_speed;
            entity.timeout = 10;
            entity.stop = false;
            String s = GsonUtil.GsonString(entity)+ "\n";
            xTcpClient.sendMsg(s);
        } else {
            Toast.makeText(mContext, "还没有连接到服务器", Toast.LENGTH_SHORT).show();
        }
    }

    public static void send_order(int to_id,String order){
        if (xTcpClient != null) {
            OrderEntity entity = new OrderEntity(id,to_id,order);
            String s = GsonUtil.GsonString(entity)+ "\n";
            xTcpClient.sendMsg(s);
        } else {
            Toast.makeText(mContext, "还没有连接到服务器", Toast.LENGTH_SHORT).show();
        }
    }

    public static void set_max_speed(double max_linear_speed,double max_angular_speed){
        if (xTcpClient != null) {
            OrderEntitySetMaxSpeed entity = new OrderEntitySetMaxSpeed(id,1,"set_max_speed");
            entity.max_linear_speed = max_linear_speed;
            entity.max_angular_speed = max_angular_speed;
            String s = GsonUtil.GsonString(entity)+ "\n";
            xTcpClient.sendMsg(s);
        } else {
            Toast.makeText(mContext, "还没有连接到服务器", Toast.LENGTH_SHORT).show();
        }
    }
}
