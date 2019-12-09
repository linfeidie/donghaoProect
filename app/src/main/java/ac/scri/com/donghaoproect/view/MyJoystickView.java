package ac.scri.com.donghaoproect.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import me.caibou.rockerview.JoystickView;

/**
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2019/12/5
 * <p>
 * 版本号：donghaoProect
 */
public class MyJoystickView extends JoystickView {
    public MyJoystickView(Context context) {
        super(context);
    }

    public MyJoystickView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyJoystickView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        return true;
    }


}
