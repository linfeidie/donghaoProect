package ac.scri.com.donghaoproect;

/**
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2019/9/23
 * <p>
 * 版本号：donghaoProect
 */
public class ParamEntity {

    /**
     * from_id : 1
     * robot_hight : 0
     * robot_length : 0
     * robot_width : 0
     * to_id : -5
     * type : param
     */

    private int from_id;
    private int robot_hight;
    private int robot_length;
    private int robot_width;
    private int to_id;
    private String type;

    public int getFrom_id() {
        return from_id;
    }

    public void setFrom_id(int from_id) {
        this.from_id = from_id;
    }

    public int getRobot_hight() {
        return robot_hight;
    }

    public void setRobot_hight(int robot_hight) {
        this.robot_hight = robot_hight;
    }

    public int getRobot_length() {
        return robot_length;
    }

    public void setRobot_length(int robot_length) {
        this.robot_length = robot_length;
    }

    public int getRobot_width() {
        return robot_width;
    }

    public void setRobot_width(int robot_width) {
        this.robot_width = robot_width;
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
