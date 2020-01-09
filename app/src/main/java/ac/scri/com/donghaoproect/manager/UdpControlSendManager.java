package ac.scri.com.donghaoproect.manager;

import com.blanke.xsocket.tcp.client.bean.TargetInfo;
import com.blanke.xsocket.tcp.client.bean.TcpMsg;
import com.blanke.xsocket.udp.client.UdpClientConfig;
import com.blanke.xsocket.udp.client.XUdp;
import com.blanke.xsocket.udp.client.bean.UdpMsg;
import com.blanke.xsocket.udp.client.listener.UdpClientListener;

/**
 * 文件描述：.
 * <p> UDP 管理类
 * 作者：Created by 林飞堞 on 2019/12/31
 * <p>
 * 版本号：donghaoProect
 */
public class UdpControlSendManager {

    public static UdpControlSendManager instance;

    private XUdp mXUdp;


    public static UdpControlSendManager getInstance(){

        if(instance == null) {
            synchronized (UdpControlSendManager.class){
                if(instance == null) {
                    instance = new UdpControlSendManager();
                }
            }
        }
        return instance;
    }

    public void addUdpClientListener(UdpClientListener listener){
        mXUdp.addUdpClientListener(listener);
    }

    /*
    * 构造函数
    * */
    public UdpControlSendManager() {
        if (mXUdp == null) {
            mXUdp = XUdp.getUdpClient();
           // mXUdp.addUdpClientListener(this);
        }
        mXUdp.config(new UdpClientConfig.Builder()
                .setLocalPort(8989).create());
    }

    public void sendOrder(String data, TargetInfo target){
        mXUdp.sendMsg(new UdpMsg(data, target, TcpMsg.MsgType.Send), true);
    }
}
