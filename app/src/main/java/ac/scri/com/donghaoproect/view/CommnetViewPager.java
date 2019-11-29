package ac.scri.com.donghaoproect.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2019/10/18
 * <p>
 * 版本号：donghaoProect
 */
public class CommnetViewPager extends ViewPager {
    public CommnetViewPager(@NonNull Context context) {
        super(context);
    }

    public CommnetViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 让父类不要拦截该view的事件
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.onInterceptTouchEvent(ev);
    }


}
