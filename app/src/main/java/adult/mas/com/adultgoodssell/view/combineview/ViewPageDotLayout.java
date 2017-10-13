package adult.mas.com.adultgoodssell.view.combineview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import java.lang.ref.WeakReference;
import java.util.List;

import adult.mas.com.adultgoodssell.R;
import adult.mas.com.adultgoodssell.adapter.main.MainViewPagerAdapter;
import adult.mas.com.adultgoodssell.modelbean.mainview.MainBanner;
import adult.mas.com.adultgoodssell.utils.CollectionUtils;
import adult.mas.com.adultgoodssell.view.DotPointView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunmeng on 17/8/4.
 */

public class ViewPageDotLayout extends LinearLayout{
    private static final int IMAGE_CHANGE_TIME = 5000;
    private static final int MAT_VALUE = 100;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.at_dot_view)
    DotPointView mDotPointView;
    public WeakRefHander mWeakRefHander;
    private List<MainBanner> bannerList;
    private MainViewPagerAdapter bannerAdapter;
    private boolean isInitialOk = false;

    public ViewPageDotLayout(Context context) {
        this(context, null);
    }

    public ViewPageDotLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPageDotLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }


    private void initView(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.ViewPageSquare);
        boolean isSquare = a.getBoolean(R.styleable.ViewPageSquare_isquare, false);
        a.recycle();
        View inflate = isSquare ? LayoutInflater.from(context).inflate(R.layout.viewpage_square_dot_view, this, true): LayoutInflater.from(context).inflate(R.layout.viewpage_dot_view, this, true);
        ButterKnife.bind(this, inflate);
    }


    private static class WeakRefHander extends Handler {
        WeakReference<ViewPageDotLayout> viewPageDotLayoutWeakReference;

        WeakRefHander(ViewPageDotLayout atHomeFragment) {
            viewPageDotLayoutWeakReference = new WeakReference<ViewPageDotLayout>(atHomeFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ViewPageDotLayout viewPageDotLayout = viewPageDotLayoutWeakReference.get();
            if (viewPageDotLayout != null) {
                int currentItem = viewPageDotLayout.viewPager.getCurrentItem();
                viewPageDotLayout.viewPager.setCurrentItem(++currentItem);
                viewPageDotLayout.mWeakRefHander.sendEmptyMessageDelayed(0, IMAGE_CHANGE_TIME);
            }
        }
    }

    public void setAdapterData(List<MainBanner> data){
        bannerList = data;
        setAdapterForViewPager();
    }

    private void setAdapterForViewPager(){
        if (!CollectionUtils.isEmpty(bannerList)) {
            isInitialOk = true;
            mWeakRefHander = new WeakRefHander(this);
            initDotView();
            bannerAdapter = new MainViewPagerAdapter(getContext(), bannerList);
            viewPager.setAdapter(bannerAdapter);
            addChangeListenerForViewPager();
            setTouchListenerForViewPager();
            showFirstPage();
        }
    }

    private void initDotView() {
        mDotPointView.addPoint2View(bannerList.size());
        mDotPointView.setDotPointSelecedByInDex(0);
    }


    public void onResume(){
        if(isInitialOk){
            mWeakRefHander.sendEmptyMessageDelayed(0, IMAGE_CHANGE_TIME);
        }
    }

    public void onStop(){
        if(isInitialOk){
            mWeakRefHander.removeMessages(0);
        }
    }

    /**
     * Add touch listener for viewPager
     */
    private void addChangeListenerForViewPager() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mDotPointView.setDotPointSelecedByInDex(position % bannerList.size());
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     * Set view pager to show the first page
     */
    private void showFirstPage() {
        final int remainder = MAT_VALUE % bannerList.size();
        viewPager.setCurrentItem(MAT_VALUE - remainder);
    }

    /**
     * Set touch listener for view pager.
     */
    private void setTouchListenerForViewPager() {
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mWeakRefHander.removeMessages(0);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mWeakRefHander.removeMessages(0);
                        break;
                    case MotionEvent.ACTION_UP:
                        mWeakRefHander.sendEmptyMessageDelayed(0, IMAGE_CHANGE_TIME);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        mWeakRefHander.sendEmptyMessageDelayed(0, IMAGE_CHANGE_TIME);
                        break;
                }
                return false;
            }
        });
    }
}
