package adult.mas.com.adultgoodssell.modelbean.mainview.goodssort;

import java.util.List;

/**
 * Created by sunmeng on 17/8/7.
 */

public class GoodsSortList extends GoodsSort {

    List<GoodsSort> items;

    public List<GoodsSort> getItems() {
        return items;
    }

    public void setItems(List<GoodsSort> items) {
        this.items = items;
    }

    public GoodsSortList(GoodsSort sort) {
        setSortDesc(sort.getSortDesc());
        setSortEnName(sort.getSortEnName());
        setSortImg(sort.getSortImg());
        setSortName(sort.getSortName());
        setSortParentType(sort.getSortParentType());
        setSortType(sort.getSortType());
    }

    public GoodsSortList() {
    }
}
