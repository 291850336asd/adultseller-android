package adult.mas.com.adultgoodssell.netapi.response;

import java.util.List;

import adult.mas.com.adultgoodssell.modelbean.mainview.GoodsInfo;
import adult.mas.com.adultgoodssell.modelbean.mainview.MainBanner;

/**
 * Created by sunmeng on 17/8/7.
 */

public class MainPageResponse {
    List<GoodsInfo> goodsList;
    List<MainBanner> banners;

    public List<GoodsInfo> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsInfo> goodsList) {
        this.goodsList = goodsList;
    }

    public List<MainBanner> getBanners() {
        return banners;
    }

    public void setBanners(List<MainBanner> banners) {
        this.banners = banners;
    }
}
