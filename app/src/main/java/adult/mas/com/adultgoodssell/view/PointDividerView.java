package adult.mas.com.adultgoodssell.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import adult.mas.com.adultgoodssell.R;

/**
 * Created by sunmeng on 17/8/14.
 */

public class PointDividerView extends View {

    // 创建画笔
    private Paint mPaint; // 画笔
    private float radius; // 圆的半径
    private float dividerWidth; // 圆的间距
    private int mColor = Color.parseColor("#d1d1d1"); // 圆点的颜色
    private Context mContext;

    public PointDividerView(Context context) {
        super(context);
        init(context, null);
    }

    public PointDividerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PointDividerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, (int) mContext.getResources().getDimension(R.dimen.dimens_1_dp));
    }

    protected void init(Context context, @Nullable AttributeSet attrs) {
        mContext = context;
        mPaint = new Paint();
        radius =  mContext.getResources().getDimension(R.dimen.dimens_1_dp);
        dividerWidth = mContext.getResources().getDimension(R.dimen.dimens_6_dp);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setAntiAlias(true);
        mPaint.setColor(mColor);
        int measuredHeight = getMeasuredHeight() / 2;
        int measuredWidth = getMeasuredWidth();
        for (float i = radius; i < measuredWidth; ) {
            canvas.drawCircle(i, measuredHeight, radius, mPaint);
            i += dividerWidth;
        }

    }

}
