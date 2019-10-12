package ac.scri.com.donghaoproect;

/**
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2019/9/26
 * <p>
 * 版本号：donghaoProect
 */
public class OrderEntityWorkMode extends OrderEntity {


    public String work_mode;
    public OrderEntityWorkMode(int from_id, int to_id, String type) {
        super(from_id, to_id, type);
    }
}
