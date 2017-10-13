package adult.mas.com.adultgoodssell.utils;

import android.os.Build;

/**
 * Created by sunmeng on 17/8/25.
 */

public class UUIDUtils {
    //获得独一无二的Psuedo ID
    public static String getUniquePsuedoID() {
        return  "adultseller_android" +
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +

                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +

                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +

                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +

                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +

                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +

                Build.USER.length() % 10;
    }
}
