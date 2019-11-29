package ac.scri.com.donghaoproect.entity;

import ac.scri.com.donghaoproect.entity.OrderEntity;

/**
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2019/10/8
 * <p>
 * 版本号：donghaoProect
 */
public class OrderEntitySetWorkMode extends OrderEntity {
    public OrderEntitySetWorkMode(int from_id, int to_id, String type) {
        super(from_id, to_id, type);
    }

    public String work_mode ;

    public String getWork_mode() {
        return work_mode;
    }

    public void setWork_mode(String work_mode) {
        this.work_mode = work_mode;
    }
}
