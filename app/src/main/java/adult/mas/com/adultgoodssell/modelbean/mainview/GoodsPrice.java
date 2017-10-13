package adult.mas.com.adultgoodssell.modelbean.mainview;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by sunmeng on 17/8/18.
 */

public class GoodsPrice implements Serializable{
    private int goodsId;
    private int goodsPriceId;
    private String goodsColorStyleModel;
    private BigDecimal goodsPrice;
    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getGoodsPriceId() {
        return goodsPriceId;
    }

    public void setGoodsPriceId(int goodsPriceId) {
        this.goodsPriceId = goodsPriceId;
    }

    public String getGoodsColorStyleModel() {
        return goodsColorStyleModel;
    }

    public void setGoodsColorStyleModel(String goodsColorStyleModel) {
        this.goodsColorStyleModel = goodsColorStyleModel;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }
}
