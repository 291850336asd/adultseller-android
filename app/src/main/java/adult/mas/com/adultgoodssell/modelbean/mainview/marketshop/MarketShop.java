package adult.mas.com.adultgoodssell.modelbean.mainview.marketshop;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by sunmeng on 17/8/18.
 */

public class MarketShop implements Serializable{
    private int selectCount;
    private BigDecimal totalMoney = new BigDecimal(0.00);
    private List<SelectMarketGoods> selectMarketGoodses;

    public int getSelectCount() {
        return selectCount;
    }

    public void setSelectCount(int selectCount) {
        this.selectCount = selectCount;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public List<SelectMarketGoods> getSelectMarketGoodses() {
        return selectMarketGoodses;
    }

    public void setSelectMarketGoodses(List<SelectMarketGoods> selectMarketGoodses) {
        this.selectMarketGoodses = selectMarketGoodses;
    }
}
