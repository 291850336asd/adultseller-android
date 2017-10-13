package adult.mas.com.adultgoodssell.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by sunmeng on 17/8/4.
 */

public class SquareViewPage extends ViewPager {
    public SquareViewPage(Context context) {
        super(context);
    }

    public SquareViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //传入参数widthMeasureSpec、heightMeasureSpec
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

}
