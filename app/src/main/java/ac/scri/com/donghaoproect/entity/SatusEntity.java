package ac.scri.com.donghaoproect.entity;

/**
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2019/9/23
 * <p>
 * 版本号：donghaoProect
 */
public class SatusEntity {

    /**
     * angular_speed : 0
     * axis_x : 0.6297479867935181
     * axis_y : -0.002833999926224351
     * axis_z : 0
     * battery_percent : 82
     * battery_volt : 26
     * from_id : 1
     * linear_speed : 0
     * robot_state : {"charging_state":false,"driver_fail":false,"emergency_stop":false,"goal_reach":false,"lidar_exception":false,"motor_overload":false,"path_fail":false,"slam_exception":false}
     * robot_yaw : -0.06434307247400284
     * to_id : -9
     * type : robot_status
     * work_mode :
     */

    private double angular_speed;
    private double axis_x;
    private double axis_y;
    private int axis_z;
    private int battery_percent;
    private int battery_volt;
    private int from_id;
    private double linear_speed;
    private RobotStateEntity robot_state;
    private double robot_yaw;
    private int to_id;
    private String type;
    private String work_mode;

    public double getAngular_speed() {
        return angular_speed;
    }

    public void setAngular_speed(int angular_speed) {
        this.angular_speed = angular_speed;
    }

    public double getAxis_x() {
        return axis_x;
    }

    public void setAxis_x(double axis_x) {
        this.axis_x = axis_x;
    }

    public double getAxis_y() {
        return axis_y;
    }

    public void setAxis_y(double axis_y) {
        this.axis_y = axis_y;
    }

    public int getAxis_z() {
        return axis_z;
    }

    public void setAxis_z(int axis_z) {
        this.axis_z = axis_z;
    }

    public int getBattery_percent() {
        return battery_percent;
    }

    public void setBattery_percent(int battery_percent) {
        this.battery_percent = battery_percent;
    }

    public int getBattery_volt() {
        return battery_volt;
    }

    public void setBattery_volt(int battery_volt) {
        this.battery_volt = battery_volt;
    }

    public int getFrom_id() {
        return from_id;
    }

    public void setFrom_id(int from_id) {
        this.from_id = from_id;
    }

    public double getLinear_speed() {
        return linear_speed;
    }

    public void setLinear_speed(int linear_speed) {
        this.linear_speed = linear_speed;
    }

    public RobotStateEntity getRobot_state() {
        return robot_state;
    }

    public void setRobot_state(RobotStateEntity robot_state) {
        this.robot_state = robot_state;
    }

    public double getRobot_yaw() {
        return robot_yaw;
    }

    public void setRobot_yaw(double robot_yaw) {
        this.robot_yaw = robot_yaw;
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

    public String getWork_mode() {
        return work_mode;
    }

    public void setWork_mode(String work_mode) {
        this.work_mode = work_mode;
    }

    public static class RobotStateEntity {
        /**
         * charging_state : false
         * driver_fail : false
         * emergency_stop : false
         * goal_reach : false
         * lidar_exception : false
         * motor_overload : false
         * path_fail : false
         * slam_exception : false
         */

        private boolean charging_state;
        private boolean driver_fail;
        private boolean emergency_stop;
        private boolean goal_reach;
        private boolean lidar_exception;
        private boolean motor_overload;
        private boolean path_fail;
        private boolean slam_exception;

        public boolean isCharging_state() {
            return charging_state;
        }

        public void setCharging_state(boolean charging_state) {
            this.charging_state = charging_state;
        }

        public boolean isDriver_fail() {
            return driver_fail;
        }

        public void setDriver_fail(boolean driver_fail) {
            this.driver_fail = driver_fail;
        }

        public boolean isEmergency_stop() {
            return emergency_stop;
        }

        public void setEmergency_stop(boolean emergency_stop) {
            this.emergency_stop = emergency_stop;
        }

        public boolean isGoal_reach() {
            return goal_reach;
        }

        public void setGoal_reach(boolean goal_reach) {
            this.goal_reach = goal_reach;
        }

        public boolean isLidar_exception() {
            return lidar_exception;
        }

        public void setLidar_exception(boolean lidar_exception) {
            this.lidar_exception = lidar_exception;
        }

        public boolean isMotor_overload() {
            return motor_overload;
        }

        public void setMotor_overload(boolean motor_overload) {
            this.motor_overload = motor_overload;
        }

        public boolean isPath_fail() {
            return path_fail;
        }

        public void setPath_fail(boolean path_fail) {
            this.path_fail = path_fail;
        }

        public boolean isSlam_exception() {
            return slam_exception;
        }

        public void setSlam_exception(boolean slam_exception) {
            this.slam_exception = slam_exception;
        }
    }
}
