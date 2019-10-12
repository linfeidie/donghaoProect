package ac.scri.com.donghaoproect;

import java.util.List;

/**
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2019/9/23
 * <p>
 * 版本号：donghaoProect
 */
public class ClientIdEntity {

    /**
     * from_id : 0
     * ids : [-9,-8,-7,-6,-5,-1,1]
     * to_id : -5
     * type : online_ids
     */

    private int from_id;
    private int to_id;
    private String type;
    private List<Integer> ids;

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

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}
