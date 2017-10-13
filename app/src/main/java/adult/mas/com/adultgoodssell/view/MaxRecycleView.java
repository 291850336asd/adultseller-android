package adult.mas.com.adultgoodssell.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import adult.mas.com.adultgoodssell.R;
import adult.mas.com.thirdviewmodel.view.xrecycleview.XRecyclerView;

/**
 * Created by sunmeng on 17/8/17.
 * 设置最大高度的RecycleView防止弹出界面过高影响美观
 */

public class MaxRecycleView extends XRecyclerView{

    private float maxLength = 0;

    public MaxRecycleView(Context context) {
        this(context, null);
    }

    public MaxRecycleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaxRecycleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialAttr(attrs);
        removeFootView();
    }


    private void initialAttr(AttributeSet attrs){
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.XRecView);
        maxLength = a.getDimension(R.styleable.XRecView_maxLength, 0);
        a.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (maxLength > 0) {
            int height = MeasureSpec.getSize(heightMeasureSpec);
            if(height > maxLength){
                heightMeasureSpec = MeasureSpec.makeMeasureSpec((int)maxLength,
                        MeasureSpec.AT_MOST);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
