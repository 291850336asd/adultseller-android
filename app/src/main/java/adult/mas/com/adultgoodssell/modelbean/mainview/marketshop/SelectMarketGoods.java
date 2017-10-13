package adult.mas.com.adultgoodssell.modelbean.mainview.marketshop;

import java.io.Serializable;

import adult.mas.com.adultgoodssell.modelbean.mainview.GoodsInfo;

/**
 * Created by sunmeng on 17/8/18.
 */

public class SelectMarketGoods implements Serializable{
    private int count;
    private boolean select;
    private GoodsInfo goodsInfo;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public GoodsInfo getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }
}
