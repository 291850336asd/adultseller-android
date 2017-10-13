package adult.mas.com.adultgoodssell.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by sunmeng on 17/7/31.
 *
 * 设置字体图标库
 *
 * 首页 &#xe6cb;
 * 分类 &#xe618;
 * video &#xe820;
 * 头像 &#xe600;
 * 搜索 &#xe652;
 * 播放 &#xe87c;
 * 待收货 &#xe671;
 * 待付款 &#xe673;
 * 待发货 &#xe675;
 * 位置 &#xe651;
 * 右箭头 &#xe6a7;
 * 数据异常 &#xe60b;
 * 网络异常 &#xe6ed;
 * 后退 &#xe61b;
 * 赠品 &#xe608;
 * 成人头像 &#xe609;
 * 消息 &#xe634;
 * 客服 &#xe602;
 * 购物车 &#xe601;
 * 清空购物车 &#xe8e6;
 * 加号 &#xe603;
 * 减号 &#xe635;
 * 选择 &#xe624;
 * 收藏 &#xe613;
 * 微信支付 &#xe688;
 * 支付宝支付 &#xe656;
 * 银联 &#xe615;
 * 倒三角 &#xe6a0;
 * setText("\ue6cb");
 */

public class MainIconText extends android.support.v7.widget.AppCompatTextView{

    private static Typeface typeFaceIcon;
    public MainIconText(Context context) {
        this(context, null);
    }

    public MainIconText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public MainIconText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(typeFaceIcon == null){
            typeFaceIcon =Typeface.createFromAsset(context.getApplicationContext().getAssets(),"font/iconfont.ttf");
        }
        setTypeface(typeFaceIcon);
    }
}
