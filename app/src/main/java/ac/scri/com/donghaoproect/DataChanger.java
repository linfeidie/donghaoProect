package ac.scri.com.donghaoproect;

import java.util.Observable;

/**
 * 文件描述：.
 * <p> 被观察者
 * 作者：Created by 林飞堞 on 2019/10/23
 * <p>
 * 版本号：donghaoProect
 */
public class DataChanger extends Observable {
    private static volatile DataChanger ourInstance ;

    public static DataChanger getInstance() {
        if(ourInstance == null) {
            synchronized (DataChanger.class){
                if(ourInstance == null) {
                    ourInstance = new DataChanger();
                }
            }
        }
        return ourInstance;
    }

    private DataChanger() {
    }

    public void postData(Object object){
        setChanged();
        notifyObservers();
    }
}
