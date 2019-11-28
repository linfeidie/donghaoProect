package ac.scri.com.donghaoproect;

/**
 * 文件描述：.
 * <p>
 *     用于用户描点  上传
 * 作者：Created by 林飞堞 on 2019/11/26
 * <p>
 * 版本号：donghaoProect
 */
public class TouchPointEntity extends TypeEntity{

    /**
     * from_id : 1
     * to_id : -1
     * type : set_click_point
     * point_num : 1
     * points : {"x":0.1,"y":0.2,"z":0}
     */

    private int from_id;
    private int to_id;
    private int point_num;
    private PointsEntity points;

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


    public int getPoint_num() {
        return point_num;
    }

    public void setPoint_num(int point_num) {
        this.point_num = point_num;
    }

    public PointsEntity getPoints() {
        return points;
    }

    public void setPoints(PointsEntity points) {
        this.points = points;
    }

    public static class PointsEntity {
        /**
         * x : 0.1
         * y : 0.2
         * z : 0.0
         */

        private double x;
        private double y;
        private double z;

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

        public double getZ() {
            return z;
        }

        public void setZ(double z) {
            this.z = z;
        }
    }
}
