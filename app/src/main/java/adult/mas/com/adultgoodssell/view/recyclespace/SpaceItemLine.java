package adult.mas.com.adultgoodssell.view.recyclespace;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by sunmeng on 17/8/4.
 */

public class SpaceItemLine extends RecyclerView.ItemDecoration {

    private int space;

    private View Header;

    public SpaceItemLine(int space) {
        this.space = space;
    }

    public SpaceItemLine(int space, View header) {
        this.space = space;
        this.Header = header;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if(Header != null && view == Header){
            return;
        }
        outRect.left = space;
        outRect.bottom = space;
        outRect.top = 0;
        if (parent.getChildLayoutPosition(view) %2==0) {
            outRect.left = 0;
        }
    }

}