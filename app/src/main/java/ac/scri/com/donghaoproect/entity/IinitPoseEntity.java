package ac.scri.com.donghaoproect.entity;

/**
 * 文件描述：.
 * <p> 重定位  上传服务器
 * 作者：Created by 林飞堞 on 2019/12/3
 * <p>
 * 版本号：donghaoProect
 */
public class IinitPoseEntity extends TypeEntity {

    /**
     * from_id : -1
     * to_id : 1
     * pose : {"x":1.8,"y":1,"z":0,"yaw":0}
     */

    private int from_id;
    private int to_id;
    private PoseEntity pose;

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

    public PoseEntity getPose() {
        return pose;
    }

    public void setPose(PoseEntity pose) {
        this.pose = pose;
    }

    public static class PoseEntity {
        /**
         * x : 1.8
         * y : 1.0
         * z : 0.0
         * yaw : 0.0
         */

        private double x;
        private double y;
        private double z;
        private double yaw;

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

        public double getYaw() {
            return yaw;
        }

        public void setYaw(double yaw) {
            this.yaw = yaw;
        }
    }
}
