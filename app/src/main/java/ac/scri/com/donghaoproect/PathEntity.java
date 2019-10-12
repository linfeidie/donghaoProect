package ac.scri.com.donghaoproect;

/**
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2019/9/23
 * <p>
 * 版本号：donghaoProect
 */
public class PathEntity {

    /**
     * from_id : 1
     * points : null
     * to_id : -9
     * type : path
     */

    private int from_id;
    private Object points;
    private int to_id;
    private String type;

    public int getFrom_id() {
        return from_id;
    }

    public void setFrom_id(int from_id) {
        this.from_id = from_id;
    }

    public Object getPoints() {
        return points;
    }

    public void setPoints(Object points) {
        this.points = points;
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
}
