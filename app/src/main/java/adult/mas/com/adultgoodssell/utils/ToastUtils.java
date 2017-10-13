package adult.mas.com.adultgoodssell.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by sunmeng on 17/8/3.
 */

public class ToastUtils {

    public static void showToastLong(Context context, String strInfo){
        Toast.makeText(context, strInfo, Toast.LENGTH_LONG).show();
    }

    public static void showToastShort(Context context, String strInfo){
        Toast.makeText(context, strInfo, Toast.LENGTH_SHORT).show();
    }
}
