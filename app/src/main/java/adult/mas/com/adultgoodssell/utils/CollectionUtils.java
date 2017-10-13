package adult.mas.com.adultgoodssell.utils;

import java.util.Collection;

/**
 * Created by sunmeng on 17/8/3.
 */

public class CollectionUtils {

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty() || collection.size() == 0;
    }
}
