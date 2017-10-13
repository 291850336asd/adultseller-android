package adult.mas.com.adultgoodssell.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class CenterLineText extends android.support.v7.widget.AppCompatTextView{

    public CenterLineText(Context context) {
        this(context, null);
    }

    public CenterLineText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public CenterLineText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        getPaint().setAntiAlias(true);//抗锯齿
        getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
        setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
        super.onDraw(canvas);

    }
}
