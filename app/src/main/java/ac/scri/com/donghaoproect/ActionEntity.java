package ac.scri.com.donghaoproect;

/**
 * 文件描述：.
 * <p>  描点后行走
 * 作者：Created by 林飞堞 on 2019/11/28
 * <p>
 * 版本号：donghaoProect
 */
public class ActionEntity extends TypeEntity {

    /**
     * from_id : -1
     * to_id : 1
     * action : start/stop
     * cycle : false
     */

    private int from_id;
    private int to_id;
    private String action;
    private boolean cycle;

    public int getFrom_id() {
        return from_id;
    }

    public void setFrom_id(int from_id) {
        this.from_id = from_id;
    }

    public int getTo_id() {
        return to_id;
    }

    public void setTo_id(int to_id) {
        this.to_id = to_id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean isCycle() {
        return cycle;
    }

    public void setCycle(boolean cycle) {
        this.cycle = cycle;
    }
}
