package adult.mas.com.adultgoodssell.activity.fragment.goodsdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.List;

import adult.mas.com.adultgoodssell.R;
import adult.mas.com.adultgoodssell.activity.fragment.NetSingleFragment;
import adult.mas.com.adultgoodssell.constant.ConstantData;
import adult.mas.com.adultgoodssell.modelbean.mainview.GoodsInfo;
import adult.mas.com.adultgoodssell.modelbean.mainview.MainBanner;
import adult.mas.com.adultgoodssell.present.goodsinfo.GoodsDetailPresent;
import adult.mas.com.adultgoodssell.presentactivity.SingleAbsNetPresent;
import adult.mas.com.adultgoodssell.utils.ResourcesUtils;
import adult.mas.com.adultgoodssell.utils.StringUtils;
import adult.mas.com.adultgoodssell.view.CenterLineText;
import adult.mas.com.adultgoodssell.view.combineview.ViewPageDotLayout;
import adult.mas.com.adultgoodssell.view.scrollview.ObservableScrollView;
import adult.mas.com.adultgoodssell.view.scrollview.ObservableScrollViewCallbacks;
import adult.mas.com.adultgoodssell.view.scrollview.ScrollState;
import adult.mas.com.httpmodel.ResponseModel;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunmeng on 17/8/1.
 */
//http://wap.chunshuitang.com/goods/4351.html
public class GoodsDetailInfoFragment extends  NetSingleFragment<GoodsInfo> implements ObservableScrollViewCallbacks {

    @BindView(R.id.scrollView)
    ObservableScrollView scrollView;
    @BindView(R.id.viewPageDotLayout)
    ViewPageDotLayout viewPageDotLayout;
    @BindView(R.id.goodsName)
    TextView goodsName;
    @BindView(R.id.currentPrice)
    TextView currentPrice;
    @BindView(R.id.marketPrice)
    CenterLineText marketPrice;
    @BindView(R.id.goodsCode)
    TextView goodsCode;
    @BindView(R.id.goodsSellCount)
    TextView goodsSellCount;
    @BindView(R.id.webView)
    WebView webView;

    public boolean isInitialFinished = false;
    public GoodsInfo dataResponse;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_goodsinfo, container, false);
        ButterKnife.bind(this, view);
        initialView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        viewPageDotLayout.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        viewPageDotLayout.onStop();
    }

    public void setBannerList(List<MainBanner> banners) {
        viewPageDotLayout.setAdapterData(banners);
    }

    private void initialView(){
        List bannerList = new ArrayList<>();
        MainBanner banner = new MainBanner();
        banner.setBannerPicUrl("http://img4.imgtn.bdimg.com/it/u=3109357359,20942065&fm=11&gp=0.jpg");
        MainBanner banner2 = new MainBanner();
        banner2.setBannerPicUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_1" +
                "0000&sec=1501826561549&di=7c7acf5eb5875e3a90acc573f2975234&imgtype=0&src=http%3A%2F%2Fjp." +
                "appgame.com%2Fwp-content%2Fuploads%2Fsites%2F6%2F2016%2F03%2Fcabianq-8-600x360.jpg");
        MainBanner banner3 = new    MainBanner();
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

        scrollView.setScrollViewCallbacks(this);

        WebSettings webSettings = webView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(false); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(false); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 缓存模式如下：
        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        webView.loadUrl("http://wap.chunshuitang.com/goods/4351.html");
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient(){
        });
    }


    private void setResultData(GoodsInfo info){
        goodsName.setText(info.getGoodsName());
        currentPrice.setText(String.valueOf(info.getGoodsShopPrice()));
        marketPrice.setText(String.valueOf(info.getGoodsMarketPrice()));
        goodsCode.setText(StringUtils.formatString(ResourcesUtils.getStringById(getContext(), R.string.goods_code), info.getGoodsCode()));
        goodsSellCount.setText(StringUtils.formatString(ResourcesUtils.getStringById(getContext(), R.string.already_sellcount_unit), String.valueOf(info.getGoodsSellCount())));
        isInitialFinished = true;
        dataResponse = info;
    }




    @Override
    public void showNetWorkProgress() {

    }

    @Override
    public void setData(ResponseModel<GoodsInfo> result) {
        if(result != null && result.getData() != null){
            setResultData(result.getData());
        }
    }

    @Override
    public void setErrorData(Throwable result) {
        Toast.makeText(getContext(), "error", Toast.LENGTH_LONG).show();
    }

    @Override
    public SingleAbsNetPresent intialPresent(Intent intent) {
        if(intent == null || intent.getIntExtra(ConstantData.GoodsId, -1) == -1){
            return null;
        }
        GoodsDetailPresent present = new GoodsDetailPresent();
        present.intialRequest(null);
        GoodsInfo info = new GoodsInfo();
        info.setGoodsId(intent.getIntExtra(ConstantData.GoodsId, -1));
        present.goodsRequestModel.setData(info);
        return present;
    }

    @Override
    public void setDisMissProgress() {

    }

    @Override
    public void noNetWork() {

    }

    @Override
    public void onDestroy() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        ViewHelper.setTranslationY(viewPageDotLayout, scrollY / 2);
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }
}
