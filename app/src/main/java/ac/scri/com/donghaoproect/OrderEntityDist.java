package ac.scri.com.donghaoproect;

/**
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2019/9/26
 * <p>
 * 版本号：donghaoProect
 */
public class OrderEntityDist extends OrderEntity {

    private double dist;
    private double turn;
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
}
