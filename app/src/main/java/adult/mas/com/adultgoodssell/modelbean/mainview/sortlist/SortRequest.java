package adult.mas.com.adultgoodssell.modelbean.mainview.sortlist;

/**
 * Created by sunmeng on 17/8/22.
 */

public class SortRequest {
    private int parentType;
    private int itemType;

    public int getParentType() {
        return parentType;
    }

    public void setParentType(int parentType) {
        this.parentType = parentType;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
