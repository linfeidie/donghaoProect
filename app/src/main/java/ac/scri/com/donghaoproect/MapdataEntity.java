package ac.scri.com.donghaoproect;

import java.util.List;

/**
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2019/10/8
 * <p>
 * 版本号：donghaoProect
 */
public class MapdataEntity extends TypeEntity{

    /**
     * data : [64164,-1,1,100,1727,-1,1,0,1726,-1,1,0,1727,-1,1,0,1726,-1,1,0,1727,-1,1,0,1726,-1,1,0,1727,-1,1,0,1727,-1,1,0,1726,-1,1,0,1727,-1,1,0,1726,-1,1,0,1727,-1,1,0,1727,-1,1,0,1726,-1,1,0,1727,-1,1,0,1726,-1,1,0,1727,-1,1,0,1726,-1,1,0,1727,-1,1,0,1727,-1,1,0,1726,-1,1,0,1727,-1,1,0,1726,-1,1,0,1727,-1,1,0,1726,-1,1,0,1727,-1,1,0,1727,-1,1,0,1726,-1,1,0,1727,-1,1,0,1726,-1,1,0,1727,-1,1,0,1727,-1,1,0,1726,-1,1,0,1727,-1,1,0,1720,-1,7,100,1721,-1,14,100,24,-1,5,100,1685,-1,1,100,6,0,14,100,1,-1,1,100,14,-1,13,100,1678,-1,1,100,14,0,16,100,6,-1,1,0,1,100,7,0,10,100,1672,-1,1,100,21,0,14,100,1,-1,1,0,1,100,15,0,3,100,1670,-1,2,100,30,0,8,100,16,0,1,100,1,0,1534,-1,11,100,1,0,124,-1,2,100,35,0,2,100,17,0,2,100,1533,-1,1,100,1,0,1,-1,13,0,121,-1,2,100,55,0,1,100,1535,-1]
     * from_id : 1
     * height : 1728
     * origin : {"x":-77.2,"y":-21.2,"z":0}
     * pack_count : 1
     * pack_num : 342
     * resolution : 0.05000000074505806
     * to_id : -3
     * type : map
     * width : 1728
     */

    private int from_id;
    private int height;
    private OriginEntity origin;
    private int pack_count;
    private int pack_num;
    private double resolution;
    private int to_id;
    private int width;
    private List<Integer> data;

    public int getFrom_id() {
        return from_id;
    }

    public void setFrom_id(int from_id) {
        this.from_id = from_id;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public OriginEntity getOrigin() {
        return origin;
    }

    public void setOrigin(OriginEntity origin) {
        this.origin = origin;
    }

    public int getPack_count() {
        return pack_count;
    }

    public void setPack_count(int pack_count) {
        this.pack_count = pack_count;
    }

    public int getPack_num() {
        return pack_num;
    }

    public void setPack_num(int pack_num) {
        this.pack_num = pack_num;
    }

    public double getResolution() {
        return resolution;
    }

    public void setResolution(double resolution) {
        this.resolution = resolution;
    }

    public int getTo_id() {
        return to_id;
    }

    public void setTo_id(int to_id) {
        this.to_id = to_id;
    }


    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }

    public static class OriginEntity {
        /**
         * x : -77.2
         * y : -21.2
         * z : 0
         */

        private double x;
        private double y;
        private int z;

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public int getZ() {
            return z;
        }

        public void setZ(int z) {
            this.z = z;
        }
    }
}
