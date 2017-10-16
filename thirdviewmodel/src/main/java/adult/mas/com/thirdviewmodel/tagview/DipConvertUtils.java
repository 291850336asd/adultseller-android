package adult.mas.com.thirdviewmodel.tagview;

import android.content.Context;

/**
 * Created by sunmeng on 17/10/16.
 */

public class DipConvertUtils {
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
