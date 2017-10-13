package adult.mas.com.adultgoodssell.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import adult.mas.com.adultgoodssell.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunmeng on 17/7/31.
 */

public class MainIconTextLinearLayout extends LinearLayout {

    @BindView(R.id.mainIconText)
    MainIconText mainIconText;
    @BindView(R.id.text)
    TextView text;

    private boolean isSelect = false;

    public MainIconTextLinearLayout(Context context) {
        this(context, null);
    }

    public MainIconTextLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainIconTextLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_main_icon_text, this, true);
        ButterKnife.bind(this, inflate);
        setDefaultTextColor(getResources().getColor(R.color.color_999));
    }


    /**
     *  mainIconText.setText("\ue6cb");
     * @param str
     */
    public void setIconText(String str){
        mainIconText.setText(str);
    }

    public void setText(String str){
        text.setText(str);
    }

    public void setDefaultTextColor(int color){
        isSelect = false;
        mainIconText.setTextColor(color);
        text.setTextColor(color);
    }

    public void setSelectTextColor(int color){
        isSelect = true;
        mainIconText.setTextColor(color);
        text.setTextColor(color);
    }

    public boolean getIsSelect(){
        return isSelect;
    }
}
