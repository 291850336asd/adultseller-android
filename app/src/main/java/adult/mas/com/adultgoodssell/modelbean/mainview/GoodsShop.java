package adult.mas.com.adultgoodssell.modelbean.mainview;

import java.io.Serializable;

/**
 * Created by sunmeng on 17/8/17.
 */

public class GoodsShop implements Serializable{

    private int count;
    private boolean select;

    private int goodsId;

    private int goodsPriceId;

    public int getGoodsPriceId() {
        return goodsPriceId;
    }

    public void setGoodsPriceId(int goodsPriceId) {
        this.goodsPriceId = goodsPriceId;
    }
    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
