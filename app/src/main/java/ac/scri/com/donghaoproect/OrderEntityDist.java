package ac.scri.com.donghaoproect;

/**
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2019/9/26
 * <p>
 * 版本号：donghaoProect
 */
public class OrderEntityDist extends OrderEntity {


    /**
     * dist : 1.0
     * turn : 90.0
     * linear_speed : 0.3
     * angular_speed : 0.0
     */

    private double dist;
    private double turn;
    private double linear_speed;
    private double angular_speed;

    public OrderEntityDist(int from_id, int to_id, String type) {
        super(from_id, to_id, type);
    }

    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    public double getTurn() {
        return turn;
    }

    public void setTurn(double turn) {
        this.turn = turn;
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
}
