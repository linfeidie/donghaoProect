package ac.scri.com.donghaoproect;

import ac.scri.com.donghaoproect.entity.MapParamEntity;

/**
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2019/10/12
 * <p>
 * 版本号：donghaoProect
 */
public class Contanst {

    public static MapParamEntity MAPPARAMENTITY = null;

    public static  String PORT = "7665"; //服务器端口

    public static final int ORDER_INTERVAL = 500 ; //按钮按下命令间隔

    public static final String GET_STATUS = "robot_status";

    public static final String GET_MAP = "map";

    public static final String GET_GPS = "GPS";

    public static final String GET_PATH = "path";

    public static final String GET_MAP_PARAM = "map_param"; //地图包信息

    public static final String MAP_DATA = "map_data";

    public static final String SCAN = "scan"; //激光数据包

    public static final String SERVER_ACK = "server_ack"; //服务器返回

    public static final String GET_ONLINE_IDS = "online_ids";//获取在线机器ID

    public static final String SET_CLICK_POINT = "set_click_point";//上传服务器描点类型

    public static  int CARID = 0;//缓冲选择小车的ID

    public static final String DISCONNECT = "disconnect";

    public static  double LINEAR_SPEED = 0; //线性速度

    public static double ANGULAR_SPEED = 0 ; //角速度
}
