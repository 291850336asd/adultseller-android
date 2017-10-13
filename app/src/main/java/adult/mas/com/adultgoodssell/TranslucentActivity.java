package adult.mas.com.adultgoodssell;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import adult.mas.com.adultgoodssell.utils.ActivityAnimationUtil;
import adult.mas.com.adultgoodssell.utils.SwipeBackLayout;

/**
 * Created by sunmeng on 17/7/31.
 *
 * 界面切换动画
 */

public class TranslucentActivity extends BaseActivity {
    private SwipeBackLayout ATSwipeBackLayout = null;
    private boolean needAnimation = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initSlideBack();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        initSlideBack();
    }

    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (needAnimation) {
            ActivityAnimationUtil.leftOut(this);
        }
    }

    public void setNeedAnimation(boolean b) {
        needAnimation = b;
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        if (needAnimation) {
            ActivityAnimationUtil.rightIn(this);
        }
    }

    /**
     * set sliding mode
     *
     * @param mode
     */
    public void setSlidingMode(SwipeBackLayout.Sliding mode) {
        if (ATSwipeBackLayout == null) {
            throw new NullPointerException("ATSwipeBackLayout is null,Please call after the setContentView");
        }
        ATSwipeBackLayout.setSlidingMode(mode);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        if (needAnimation) {
            ActivityAnimationUtil.rightIn(this);
        }
    }

    @SuppressLint("InflateParams")
    private void initSlideBack() {
        ATSwipeBackLayout = (SwipeBackLayout) LayoutInflater.from(this).inflate(R.layout.swipeback_layout, null);
        ATSwipeBackLayout.attachToActivity(this);
    }

    protected void setEnableGesture(boolean b) {
        ATSwipeBackLayout.setEnableGesture(b);
    }
}
