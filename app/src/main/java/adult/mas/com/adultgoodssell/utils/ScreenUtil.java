package adult.mas.com.adultgoodssell.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by sunmeng on 17/7/31.
 */

public class ScreenUtil {


    private static Context mContext;
    private static float density;
    private static int densityDpi;
    private static int widthPixels;
    private static int heightPixels;

    public static void init(Context context) {
        mContext = context;
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        density = dm.density;
        densityDpi = dm.densityDpi;
        widthPixels = dm.widthPixels;
        heightPixels = dm.heightPixels;
    }
    @SuppressWarnings("deprecation")
    public static int getScreenWidth() {
        return mContext.getResources().getDisplayMetrics().widthPixels;
    }

    @SuppressWarnings("deprecation")
    public static int getScreenHeight() {
        return mContext.getResources().getDisplayMetrics().heightPixels;
//		return getWindowManager(mContext).getDefaultDisplay().getHeight();
    }
}
