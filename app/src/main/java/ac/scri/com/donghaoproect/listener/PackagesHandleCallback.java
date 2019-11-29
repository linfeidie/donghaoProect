package ac.scri.com.donghaoproect.listener;

import android.util.Log;

import com.blanke.xsocket.tcp.client.XTcpClient;
import com.blanke.xsocket.tcp.client.bean.TcpMsg;
import com.blanke.xsocket.tcp.client.listener.TcpClientListener;

import ac.scri.com.donghaoproect.Contanst;
import ac.scri.com.donghaoproect.entity.DataEntity;
import ac.scri.com.donghaoproect.entity.MapdataEntity;
import ac.scri.com.donghaoproect.entity.TypeEntity;
import ac.scri.com.donghaoproect.manager.ControlSendManager;
import ac.scri.com.donghaoproect.observer.DataChanger;
import ac.scri.com.donghaoproect.tool.GsonUtil;
import ac.scri.com.donghaoproect.tool.Tools;


/**
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2019/10/23
 * <p>
 * 版本号：donghaoProect
 */
public abstract class PackagesHandleCallback implements TcpClientListener {

    boolean flag = true;
    private StringBuffer incompleteJson = new StringBuffer();
    @Override
    public void onConnected(XTcpClient xTcpClient) {
        flag = true;
        Tools.showToast(xTcpClient.getTargetInfo().getIp() + "连接成功");

        ControlSendManager.set_online();//注册
        ControlSendManager.get_online_ids();// 获取ids

    }

    @Override
    public void onSended(XTcpClient xTcpClient, TcpMsg tcpMsg) {

    }

    @Override
    public void onDisconnected(XTcpClient xTcpClient, String s, Exception e) {

        if(flag) {
            Tools.showToast(s);
            DataEntity entity = new DataEntity();
            entity.setType(Contanst.DISCONNECT);
            DataChanger.getInstance().postData(entity);
            flag = false;
        }

    }

    @Override
    public void onReceive(XTcpClient xTcpClient, TcpMsg tcpMsg) {
        String[] packages = tcpMsg.getSourceDataString().split("\n");
        String message ;
        TypeEntity typeEntity = null;
        for (int i = 0; i < packages.length; i++) {
            message = packages[i];
            if(message.startsWith("{") && message.endsWith("}")) {//有开头 有结尾
                typeEntity = GsonUtil.GsonToBean(message, TypeEntity.class);
                messageCallback(typeEntity, message);
            }else if(message.startsWith("{") && !message.endsWith("}")) {//有开头 没结尾
                incompleteJson.append(message);//追加
            }else if(!message.startsWith("{") && message.endsWith("}") && incompleteJson.length() != 0) {//没开头，但有结尾
                String completeJson = incompleteJson.append(message).toString();
                typeEntity = GsonUtil.GsonToBean(completeJson, MapdataEntity.class);
                incompleteJson.setLength(0);
                messageCallback(typeEntity, completeJson);
            }else if(!message.startsWith("{") && !message.endsWith("}")){
                incompleteJson.append(message);//追加
            }else {
                Log.e("linfd","其他情况");
            }

        }
    }

    public abstract void messageCallback(TypeEntity typeEntity, String message) ;

    @Override
    public void onValidationFail(XTcpClient xTcpClient, TcpMsg tcpMsg) {

    }
}
