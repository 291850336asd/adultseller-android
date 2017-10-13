package adult.mas.com.adultgoodssell.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by sunmeng on 17/8/3.
 */

public class HttpUtils {

    public static boolean isNetWorkConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();

        return info != null;
    }
}
