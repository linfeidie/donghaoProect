package ac.scri.com.donghaoproect;

/**
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2019/9/23
 * <p>
 * 版本号：donghaoProect
 */
public class ControlEntity {

    /**
     * from_id : -9
     * to_id : 1
     * type : set_speed
     * linear_speed : 0.0
     * angular_speed : 0.1
     * timeout : 10
     * stop : false
     */

    private int from_id;
    private int to_id;
    private String type;
    private double linear_speed;
    private double angular_speed;
    private int timeout;
    private boolean stop;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getLinear_speed() {
        return linear_speed;
    }

    public void setLinear_speed(double linear_speed) {
        this.linear_speed = linear_speed;
    }

    public double getAngular_speed() {
        return angular_speed;
    }

    public void setAngular_speed(double angular_speed) {
        this.angular_speed = angular_speed;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
}
