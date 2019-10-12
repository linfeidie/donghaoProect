package ac.scri.com.donghaoproect;

/**
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2019/10/8
 * <p>
 * 版本号：donghaoProect
 */
public class OrderEntitySetMaxSpeed extends OrderEntity{
    public OrderEntitySetMaxSpeed(int from_id, int to_id, String type) {
        super(from_id, to_id, type);
    }

    public double max_linear_speed;

    public double max_angular_speed;

    public double getMax_linear_speed() {
        return max_linear_speed;
    }

    public void setMax_linear_speed(double max_linear_speed) {
        this.max_linear_speed = max_linear_speed;
    }

    public double getMax_angular_speed() {
        return max_angular_speed;
    }

    public void setMax_angular_speed(double max_angular_speed) {
        this.max_angular_speed = max_angular_speed;
    }
}
