package adult.mas.com.adultgoodssell.bussiness.goodsindo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.anupcowkur.reservoir.Reservoir;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import adult.mas.com.adultgoodssell.R;
import adult.mas.com.adultgoodssell.TranslucentActivity;
import adult.mas.com.adultgoodssell.activity.fragment.goodsdetail.GoodsCommentsFragment;
import adult.mas.com.adultgoodssell.activity.fragment.goodsdetail.GoodsDetailInfoFragment;
import adult.mas.com.adultgoodssell.adapter.goods.GoodsPriceRecycleAdapter;
import adult.mas.com.adultgoodssell.bussiness.marketshop.MarketShopActivity;
import adult.mas.com.adultgoodssell.constant.ConstantData;
import adult.mas.com.adultgoodssell.event.NoDoubleClickListener;
import adult.mas.com.adultgoodssell.modelbean.mainview.GoodsShop;
import adult.mas.com.adultgoodssell.utils.CollectionUtils;
import adult.mas.com.adultgoodssell.utils.ResourcesUtils;
import adult.mas.com.adultgoodssell.utils.ToastUtils;
import adult.mas.com.adultgoodssell.view.MainIconText;
import adult.mas.com.adultgoodssell.view.MaxRecycleView;
import adult.mas.com.adultgoodssell.view.ViewPagerScroll;
import adult.mas.com.adultgoodssell.view.dialogview.MarketShopDialog;
import adult.mas.com.thirdviewmodel.tabsindicator.AlphaTabsIndicator;
import adult.mas.com.thirdviewmodel.view.xrecycleview.ProgressStyle;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunmeng on 17/8/1.
 */

public class GoodsInfoActivity extends TranslucentActivity{

    @BindView(R.id.back)
    View back;
    @BindView(R.id.alphaIndicator)
    AlphaTabsIndicator alphaIndicator;
    @BindView(R.id.mViewPager)
    ViewPagerScroll mViewPager;
    @BindView(R.id.addMarketShop)
    TextView addMarketShop;
    @BindView(R.id.buyInstant)
    TextView buyInstant;
    @BindView(R.id.customService)
    View customService;
    @BindView(R.id.marketShopLL)
    View marketShopLL;
    @BindView(R.id.customIcon)
    MainIconText customIcon;
    @BindView(R.id.customText)
    TextView customText;
    @BindView(R.id.marketIcon)
    MainIconText marketIcon;
    @BindView(R.id.marketText)
    TextView marketText;
    MainAdapter mainAdapter;
    MarketShopDialog shopDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_goodsdetail_viewpager);
        ButterKnife.bind(this);
        mainAdapter = new MainAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mainAdapter);
        mViewPager.setOnPageChangeListener(mainAdapter);
        alphaIndicator.setViewPager(mViewPager);
        back.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onClickNoDouble(View view) {
                finish();
            }
        });

        addMarketShop.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onClickNoDouble(View view) {

                final GoodsDetailInfoFragment detailInfoFragment = (GoodsDetailInfoFragment)mainAdapter.getItem(0);
                if(detailInfoFragment != null && detailInfoFragment.isInitialFinished) {
                    if (detailInfoFragment.dataResponse != null) {
                        shopDialog = new MarketShopDialog(GoodsInfoActivity.this ,R.style.dialog_full);
                        View contentView = shopDialog.getContentView();
                        MaxRecycleView xRecyclerView = (MaxRecycleView) contentView.findViewById(R.id.xRecycleView);
                        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        xRecyclerView.setLayoutManager(mLayoutManager);
                        xRecyclerView.setPullRefreshEnabled(false);
                        xRecyclerView.setLoadingMoreEnabled(false);
                        xRecyclerView.setHomeStyle(false);
                        xRecyclerView.setRefreshProgressStyle(ProgressStyle.LineScalePulseOutRapid);
                        xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SysProgress);
                        GoodsPriceRecycleAdapter mVideoListAdapter = new GoodsPriceRecycleAdapter(getContext());
                        xRecyclerView.setAdapter(mVideoListAdapter);
                        mVideoListAdapter.setData(detailInfoFragment.dataResponse.getPricesList());
                        MainIconText reduce = (MainIconText) contentView.findViewById(R.id.reduce);
                        MainIconText add = (MainIconText) contentView.findViewById(R.id.add);
                        final TextView goodsNum = (TextView) contentView.findViewById(R.id.goodsNum);
                        add.setOnClickListener(new NoDoubleClickListener() {
                            @Override
                            public void onClickNoDouble(View view) {
                                int num = Integer.parseInt(goodsNum.getText().toString())+1;
                                if(num > detailInfoFragment.dataResponse.getGoodsNum()){
                                    num = detailInfoFragment.dataResponse.getGoodsNum();
                                }
                                goodsNum.setText(String.valueOf(num));
                            }
                        });
                        reduce.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int num = Integer.parseInt(goodsNum.getText().toString())-1;
                                num = num == 0 ? 1: num;
                                goodsNum.setText(String.valueOf(num));
                            }
                        });
                        TextView addToMarketShop = (TextView) contentView.findViewById(R.id.addToMarketShop);


                        addToMarketShop.setOnClickListener(new NoDoubleClickListener() {
                            @Override
                            public void onClickNoDouble(View view) {
                                addToMarketShop(Integer.parseInt(goodsNum.getText().toString()));
                                shopDialog.dismissDialogAni();
                            }
                        });
                        goodsNum.setText(String.valueOf(1));
                        addToMarketShop.setText(R.string.add_marketshop);
                        shopDialog.show();
                    }
                }
            }
        });




        buyInstant.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onClickNoDouble(View view) {

                final GoodsDetailInfoFragment detailInfoFragment = (GoodsDetailInfoFragment)mainAdapter.getItem(0);
                if(detailInfoFragment != null && detailInfoFragment.isInitialFinished) {
                    if (detailInfoFragment.dataResponse != null) {
                            shopDialog = new MarketShopDialog(GoodsInfoActivity.this ,R.style.dialog_full);
                            final View contentView = shopDialog.getContentView();
                            MaxRecycleView xRecyclerView = (MaxRecycleView) contentView.findViewById(R.id.xRecycleView);
                            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                            xRecyclerView.setLayoutManager(mLayoutManager);
                            xRecyclerView.setPullRefreshEnabled(false);
                            xRecyclerView.setLoadingMoreEnabled(false);
                            xRecyclerView.setHomeStyle(false);
                            xRecyclerView.setRefreshProgressStyle(ProgressStyle.LineScalePulseOutRapid);
                            xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SysProgress);
                            GoodsPriceRecycleAdapter mVideoListAdapter = new GoodsPriceRecycleAdapter(getContext());
                            xRecyclerView.setAdapter(mVideoListAdapter);
                            mVideoListAdapter.setData(detailInfoFragment.dataResponse.getPricesList());
                            MainIconText reduce = (MainIconText) contentView.findViewById(R.id.reduce);
                            MainIconText add = (MainIconText) contentView.findViewById(R.id.add);
                            final TextView goodsNum = (TextView) contentView.findViewById(R.id.goodsNum);
                            add.setOnClickListener(new NoDoubleClickListener() {
                                @Override
                                public void onClickNoDouble(View view) {
                                    int num = Integer.parseInt(goodsNum.getText().toString())+1;
                                    if(num > detailInfoFragment.dataResponse.getGoodsNum()){
                                        num = detailInfoFragment.dataResponse.getGoodsNum();
                                    }
                                    goodsNum.setText(String.valueOf(num));
                                }
                            });
                            reduce.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int num = Integer.parseInt(goodsNum.getText().toString())-1;
                                    num = num == 0 ? 1: num;
                                    goodsNum.setText(String.valueOf(num));
                                }
                            });
                        goodsNum.setText(String.valueOf(1));
                        TextView addToMarketShop = (TextView) contentView.findViewById(R.id.addToMarketShop);
                        addToMarketShop.setText(R.string.buy_instant);
                        addToMarketShop.setOnClickListener(new NoDoubleClickListener() {
                            @Override
                            public void onClickNoDouble(View view) {
                                shopDialog.dismissDialogAni();
                                Intent intent = new Intent();
                                GoodsShop goodsShop = new GoodsShop();
                                goodsShop.setGoodsId(detailInfoFragment.dataResponse.getGoodsId());
                                TextView goodsNum = (TextView) contentView.findViewById(R.id.goodsNum);
                                goodsShop.setCount(Integer.parseInt(goodsNum.getText().toString()));
                                goodsShop.setSelect(true);
                                List<GoodsShop> shops = new ArrayList<GoodsShop>();
                                shops.add(goodsShop);
                                intent.putExtra(ConstantData.SelectGoodsShop, (Serializable) shops);
                                intent.setClass(getContext(), MarketShopActivity.class);
                                startActivity(intent);
                            }
                        });
                        shopDialog.show();
                    }
                }
            }
        });



        marketShopLL.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onClickNoDouble(View view) {
               try{
                   Type resultType = new TypeToken<List<GoodsShop>>() {}.getType();
                   boolean objectExists = Reservoir.contains(ConstantData.CacheGoodsShop);
                   if(objectExists) {
                       List<GoodsShop> datas = Reservoir.get(ConstantData.CacheGoodsShop, resultType);
                       if (!CollectionUtils.isEmpty(datas)) {
                           Intent intent = new Intent();
                           intent.setClass(getContext(), MarketShopActivity.class);
                           startActivity(intent);
                           return;
                       }
                   }
                   ToastUtils.showToastLong(getContext(), ResourcesUtils.getStringById(getContext(), R.string.shop_empty));
               }catch (Exception e){
                   ToastUtils.showToastLong(getContext(), ResourcesUtils.getStringById(getContext(), R.string.shop_empty));
               }
            }
        });
    }



    private void addToMarketShop(int num){
        GoodsDetailInfoFragment detailInfoFragment = (GoodsDetailInfoFragment)mainAdapter.getItem(0);
        if(detailInfoFragment != null && detailInfoFragment.isInitialFinished){
            if(detailInfoFragment.dataResponse != null){
                try {
                    boolean objectExists = Reservoir.contains(ConstantData.CacheGoodsShop);
                    List<GoodsShop> datas;
                    if(objectExists){
                        Type resultType = new TypeToken<List<GoodsShop>>() {}.getType();
                        datas =  Reservoir.get(ConstantData.CacheGoodsShop, resultType);
                    }else {
                        datas = new ArrayList<GoodsShop>();
                    }

                    GoodsShop goodsShop = new GoodsShop();
                    goodsShop.setGoodsId(detailInfoFragment.dataResponse.getGoodsId());
                    goodsShop.setCount(num);
                    goodsShop.setSelect(false);
                    if(CollectionUtils.isEmpty(datas)){
                        datas = new ArrayList<GoodsShop>();
                        datas.add(goodsShop);
                    }else{
                        boolean isAlreadyhas = false;
                        for(GoodsShop shopItem : datas){
                            if(shopItem.getGoodsId() == goodsShop.getGoodsId()){
                                isAlreadyhas = true;
                                shopItem.setCount(shopItem.getCount() + goodsShop.getCount());
                            }
                        }
                        if(!isAlreadyhas){
                            datas.add(goodsShop);
                        }
                    }
                    Reservoir.put(ConstantData.CacheGoodsShop, datas);
                    int color = ResourcesUtils.getColorById(getApplicationContext(),R.color.color_fd7530);
                    marketIcon.setTextColor(color);
                    marketText.setTextColor(color);
                } catch (Exception e) {
                    ToastUtils.showToastShort(getApplicationContext(), ResourcesUtils.getStringById(getApplicationContext(), R.string.data_error_order));
                }finally {
                    return;
                }
            } else{
                ToastUtils.showToastShort(getApplicationContext(), ResourcesUtils.getStringById(getApplicationContext(), R.string.data_error));
            }
        }else{
            ToastUtils.showToastShort(getApplicationContext(), ResourcesUtils.getStringById(getApplicationContext(), R.string.data_error));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            Type resultType = new TypeToken<List<GoodsShop>>() {}.getType();
            boolean objectExists = Reservoir.contains(ConstantData.CacheGoodsShop);
            if(objectExists){
                List<GoodsShop> datas =  Reservoir.get(ConstantData.CacheGoodsShop, resultType);
                if(!CollectionUtils.isEmpty(datas)){
                    int color = ResourcesUtils.getColorById(getApplicationContext(),R.color.color_fd7530);
                    marketIcon.setTextColor(color);
                    marketText.setTextColor(color);
                    return;
                }
                int color = ResourcesUtils.getColorById(getApplicationContext(),R.color.color_999);
                marketIcon.setTextColor(color);
                marketText.setTextColor(color);
            }
        }catch (Exception e){
            int color = ResourcesUtils.getColorById(getApplicationContext(),R.color.color_999);
            marketIcon.setTextColor(color);
            marketText.setTextColor(color);
        }

    }

    private class MainAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {
        private List<Fragment> fragments = new ArrayList<>();
        public MainAdapter(FragmentManager fm) {
            super(fm);
            fragments.add(new GoodsDetailInfoFragment());
            fragments.add(new GoodsCommentsFragment());
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}
