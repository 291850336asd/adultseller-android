package adult.mas.com.adultgoodssell.modelbean.mainview.confirm;

import java.util.List;

import adult.mas.com.adultgoodssell.modelbean.mainview.marketshop.MarketShop;

/**
 * Created by sunmeng on 17/8/21.
 */

public class ConfirmDealResponse {
    private MarketShop marketShop;

    public MarketShop getMarketShop() {
        return marketShop;
    }

    public void setMarketShop(MarketShop marketShop) {
        this.marketShop = marketShop;
    }

    public List<TransferShip> getTransfers() {
        return transfers;
    }

    public void setTransfers(List<TransferShip> transfers) {
        this.transfers = transfers;
    }

    private List<TransferShip> transfers;


}
