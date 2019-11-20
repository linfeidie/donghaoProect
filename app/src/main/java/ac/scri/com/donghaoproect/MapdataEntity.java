package ac.scri.com.donghaoproect;

import java.util.List;

/**
 * 文件描述：.
 * <p>  新的数据格式  没有地图信息
 * 作者：Created by 林飞堞 on 2019/10/8
 * <p>
 * 版本号：donghaoProect
 */
public class MapdataEntity extends TypeEntity implements Comparable<MapdataEntity>{


    /**
     * data : [1672,-1,1,0,2,-1,1,0,17,-1,3,0,27,-1,5,0,1672,-1,1,0,2,-1,1,0,16,-1,3,0,28,-1,2,100,3,0,1671,-1,1,0,2,-1,1,0,17,-1,3,0,29,-1,1,100,1,0,1,100,2,0,1670,-1,1,100,2,-1,1,0,17,-1,3,0,29,-1,2,100,3,0,1672,-1,1,0,17,-1,3,0,31,-1,1,0,1,100,2,0,1690,-1,3,0,31,-1,1,100,3,0,1690,-1,3,0,32,-1,3,0,1689,-1,3,0,33,-1,1,0,1,-1,1,0,1689,-1,3,0,33,-1,1,0,1,-1,1,0,1689,-1,3,0,33,-1,1,0,1,-1,1,0,1688,-1,3,0,34,-1,1,0,1,-1,2,0,1687,-1,3,0,34,-1,1,0,1,-1,2,0,1687,-1,3,0,34,-1,1,0,1,-1,2,0,1686,-1,3,0,35,-1,1,0,1,-1,2,0,1686,-1,3,0,35,-1,1,0,1,-1,2,0,1685,-1,3,0,36,-1,1,0,1,-1,2,0,1685,-1,3,0,36,-1,1,0,2,-1,1,0,1685,-1,3,0,36,-1,1,0,2,-1,1,0,1684,-1,3,0,37,-1,1,0,2,-1,2,0,1683,-1,3,0]
     * from_id : 1
     * pack_count : 341
     * pack_id : 1
     * pack_num : 342
     * to_id : 0
     */

    private int from_id;
    private int pack_count;
    private int pack_id;
    private int pack_num;
    private int to_id;
    private List<Integer> data;

    public int getFrom_id() {
        return from_id;
    }

    public void setFrom_id(int from_id) {
        this.from_id = from_id;
    }

    public int getPack_count() {
        return pack_count;
    }

    public void setPack_count(int pack_count) {
        this.pack_count = pack_count;
    }

    public int getPack_id() {
        return pack_id;
    }

    public void setPack_id(int pack_id) {
        this.pack_id = pack_id;
    }

    public int getPack_num() {
        return pack_num;
    }

    public void setPack_num(int pack_num) {
        this.pack_num = pack_num;
    }

    public int getTo_id() {
        return to_id;
    }

    public void setTo_id(int to_id) {
        this.to_id = to_id;
    }

    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }

    @Override
    public int compareTo(MapdataEntity mapdataEntity) {
        int i = this.getPack_count() - mapdataEntity.getPack_count();//先按照年龄排序
        return i;

    }
}
