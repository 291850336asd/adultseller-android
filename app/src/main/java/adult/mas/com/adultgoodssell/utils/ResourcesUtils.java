package adult.mas.com.adultgoodssell.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Created by sunmeng on 17/8/1.
 */

public class ResourcesUtils {


    public static String getStringById(Context context, int id){
        return  context.getApplicationContext().getResources().getString(id);
    }

    public static Drawable getDrawById(Context context, int id){
        return  context.getApplicationContext().getResources().getDrawable(id);
    }

    public static int getColorById(Context context, int id){
        return  context.getApplicationContext().getResources().getColor(id);
    }
}
