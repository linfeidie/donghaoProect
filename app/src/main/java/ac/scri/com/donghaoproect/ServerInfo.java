package ac.scri.com.donghaoproect;

/**
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2019/9/23
 * <p>
 * 版本号：donghaoProect
 */
public class ServerInfo extends TypeEntity{

    /**
     * from_id : 0
     * state : target_fail
     * target_id : 1
     * to_id : -2
     * type : server_ack
     */

    private int from_id;
    private String state;
    private int target_id;
    private int to_id;


    public int getFrom_id() {
        return from_id;
    }

    public void setFrom_id(int from_id) {
        this.from_id = from_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getTarget_id() {
        return target_id;
    }

    public void setTarget_id(int target_id) {
        this.target_id = target_id;
    }

    public int getTo_id() {
        return to_id;
    }

    public void setTo_id(int to_id) {
        this.to_id = to_id;
    }


}
