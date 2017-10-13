package adult.mas.com.adultgoodssell.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by sunmeng on 17/8/16.
 */

public class ViewPagerScroll extends ViewPager {
    public ViewPagerScroll(Context context) {
        super(context);
    }

    public ViewPagerScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    float xDistance, yDistance, xLast, yLast;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                xLast = ev.getX();
                yLast = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();

                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                if (xDistance > yDistance) {
                    return true;
                }else if(yDistance>xDistance){
                    return super.onInterceptTouchEvent(ev);
                }
        }

        return super.onInterceptTouchEvent(ev);
    }

}
