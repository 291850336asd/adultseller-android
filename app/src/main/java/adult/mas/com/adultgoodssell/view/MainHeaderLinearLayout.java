package adult.mas.com.adultgoodssell.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import adult.mas.com.adultgoodssell.R;
import adult.mas.com.adultgoodssell.adapter.main.MainGridViewAdapter;
import adult.mas.com.adultgoodssell.modelbean.mainview.MainBanner;
import adult.mas.com.adultgoodssell.modelbean.mainview.MainGridBean;
import adult.mas.com.adultgoodssell.view.combineview.ViewPageDotLayout;
import adult.mas.com.thirdviewmodel.marqueeview.MarqueeFactory;
import adult.mas.com.thirdviewmodel.marqueeview.MarqueeView;
import adult.mas.com.thirdviewmodel.marqueeview.business.ComplexItemEntity;
import adult.mas.com.thirdviewmodel.marqueeview.business.ComplexViewMF;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunmeng on 17/7/31.
 */

public class MainHeaderLinearLayout extends LinearLayout {
    @BindView(R.id.marqueeView)
    MarqueeView marqueeView;

    @BindView(R.id.viewPageDotLayout)
    ViewPageDotLayout viewPageDotLayout;

    @BindView(R.id.gridView)
    DividerLineGridView gridView;


    public MainHeaderLinearLayout(Context context) {
        this(context, null);
    }

    public MainHeaderLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainHeaderLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_main_fragment_mian_page, this, true);
        ButterKnife.bind(this, inflate);
        initialData();
    }

    private void initialData() {
        List bannerList = new ArrayList<>();
        MainBanner banner = new MainBanner();
        banner.setBannerPicUrl("http://img4.imgtn.bdimg.com/it/u=3109357359,20942065&fm=11&gp=0.jpg");
        MainBanner banner2 = new MainBanner();
        banner2.setBannerPicUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_1" +
                "0000&sec=1501826561549&di=7c7acf5eb5875e3a90acc573f2975234&imgtype=0&src=http%3A%2F%2Fjp." +
                "appgame.com%2Fwp-content%2Fuploads%2Fsites%2F6%2F2016%2F03%2Fcabianq-8-600x360.jpg");
        MainBanner banner3 = new MainBanner();
        banner3.setBannerPicUrl("http://img4.imgtn.bdimg.com/it/u=3109357359,20942065&fm=11&gp=0.jpg");
        MainBanner banner4 = new MainBanner();
        banner4.setBannerPicUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1501" +
                "826561546&di=79654abb6affed7a8e246562658d2d12&imgtype=0&src=http%3A%2F%2Fscdn.file1.gk9" +
                "9.com%2Fphoto%2F2015-07%2F2015-07-08%2F143634205973261.jpg");
        bannerList.add(banner);
        bannerList.add(banner2);
        bannerList.add(banner3);
        bannerList.add(banner4);

        viewPageDotLayout.setAdapterData(bannerList);



        MarqueeFactory<RelativeLayout, ComplexItemEntity> marqueeFactory7 = new ComplexViewMF(getContext());
        marqueeView.setAnimInAndOut(R.anim.top_in, R.anim.bottom_out);
        marqueeView.setMarqueeFactory(marqueeFactory7);
        marqueeView.startFlipping();
        List<ComplexItemEntity> complexDatas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            complexDatas.add(new ComplexItemEntity("标题 " + i,  "时间 " + i));
        }
        marqueeFactory7.resetData(complexDatas);

        List<MainGridBean> gridBeanList = new ArrayList<>();
        MainGridBean gridBean = new MainGridBean();
        gridBean.setThemeName("模块1");
        MainGridBean gridBean2 = new MainGridBean();
        gridBean2.setThemeName("模块2");
        gridBean2.setUrl("http://img2.web07.cn/UpPic/Png/201410/06/266789060215382.png");
        MainGridBean gridBean3 = new MainGridBean();
        gridBean3.setThemeName("模块2");

        MainGridBean gridBean4 = new MainGridBean();
        gridBean4.setThemeName("模块4");
        gridBean4.setUrl("http://img2.web07.cn/UpPic/Png/201410/06/266789060215382.png");
        MainGridBean gridBean5 = new MainGridBean();
        gridBean5.setThemeName("模块5");
        MainGridBean gridBean6 = new MainGridBean();
        gridBean6.setThemeName("模块6");
        gridBean6.setUrl("http://img2.web07.cn/UpPic/Png/201410/06/266789060215382.png");
        gridBeanList.add(gridBean);
        gridBeanList.add(gridBean2);
        gridBeanList.add(gridBean3);
        gridBeanList.add(gridBean4);
        gridBeanList.add(gridBean5);
        gridBeanList.add(gridBean6);
        gridView.setAdapter(new MainGridViewAdapter(getContext(),gridBeanList));
    }


    public void onResume(){
        viewPageDotLayout.onResume();
        marqueeView.startFlipping();
    }

    public void onStop(){
        viewPageDotLayout.onStop();
        marqueeView.stopFlipping();
    }

    public void setBannerList(List<MainBanner> banners) {
        viewPageDotLayout.setAdapterData(banners);
    }
}
