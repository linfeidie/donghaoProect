package ac.scri.com.donghaoproect;

/**
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2019/9/26
 * <p>
 * 版本号：donghaoProect
 */
public class OrderEntitySetSpeed extends OrderEntity {

    public double linear_speed;
    public double angular_speed;
    public int timeout;
    public boolean stop = false;

    public OrderEntitySetSpeed(int from_id, int to_id, String type) {
        super(from_id, to_id, type);
    }
}
