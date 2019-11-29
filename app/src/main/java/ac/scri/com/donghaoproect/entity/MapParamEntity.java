package ac.scri.com.donghaoproect.entity;

/**
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2019/10/22
 * <p>
 * 版本号：donghaoProect
 */
public class MapParamEntity {

    /**
     * from_id : 1
     * height : 1728
     * origin : {"x":-77.2,"y":-21.2,"z":0}
     * pack_id : 1
     * pack_num : 342
     * resolution : 0.05000000074505806
     * to_id : 0
     * type : map_param
     * width : 1728
     */

    private int from_id;
    private int height;
    private OriginEntity origin;
    private int pack_id;
    private int pack_num;
    private double resolution;
    private int to_id;
    private String type;
    private int width;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
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
