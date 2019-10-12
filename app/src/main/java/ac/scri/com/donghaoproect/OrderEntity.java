package ac.scri.com.donghaoproect;

/**
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2019/9/25
 * <p>
 * 版本号：donghaoProect
 */
public class OrderEntity {

    /**
     * from_id : 1
     * to_id : 0
     * type : set_online
     */

    private int from_id;
    private int to_id;
    private String type;



    public OrderEntity(int from_id, int to_id, String type) {
        this.from_id = from_id;
        this.to_id = to_id;
        this.type = type;
    }

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

    @Override
    public String toString() {
        return "{" +
                "from_id=" + from_id +
                ", to_id=" + to_id +
                ", type='" + type + '\'' +
                '}';
    }
}
