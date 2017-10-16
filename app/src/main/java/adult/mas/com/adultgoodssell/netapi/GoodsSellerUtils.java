package adult.mas.com.adultgoodssell.netapi;

import android.content.Context;

import adult.mas.com.httpmodel.HttpFactory;

/**
 * Created by sunmeng on 17/8/1.
 */

public class GoodsSellerUtils {

    private static GoodsSellService goodsSellService;
    public static  GoodsSellService getApiService(Context context){
        if (null == goodsSellService) {
            goodsSellService = HttpFactory.getAPIService(
                    GoodsSellService.class,
                    "http://10.6.17.217:8080"
            );
        }
        return goodsSellService;
    }
}
