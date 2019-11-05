package ac.scri.com.donghaoproect;

import java.util.List;

/**
 * 文件描述：.
 * <p> ids 返回实例
 * 作者：Created by 林飞堞 on 2019/11/4
 * <p>
 * 版本号：donghaoProect
 */
public class OnlineIdsEntity extends TypeEntity {

    /**
     * from_id : 0
     * ids : [-9,-3,1]
     * to_id : -3
     */

    private int from_id;
    private int to_id;
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

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}
