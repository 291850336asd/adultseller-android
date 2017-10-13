package adult.mas.com.adultgoodssell.bussiness.marketshop;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;

import adult.mas.com.adultgoodssell.R;
import adult.mas.com.adultgoodssell.adapter.marketshop.MarketShopRecycleAdapter;
import adult.mas.com.adultgoodssell.bussiness.confirmdeal.ConfirmDealActivity;
import adult.mas.com.adultgoodssell.bussiness.goodsindo.GoodsInfoActivity;
import adult.mas.com.adultgoodssell.constant.ConstantData;
import adult.mas.com.adultgoodssell.event.NoDoubleClickListener;
import adult.mas.com.adultgoodssell.modelbean.mainview.GoodsInfo;
import adult.mas.com.adultgoodssell.modelbean.mainview.marketshop.MarketShop;
import adult.mas.com.adultgoodssell.present.marketshop.MarketShopPresent;
import adult.mas.com.adultgoodssell.presentactivity.PresentBaseActivity;
import adult.mas.com.adultgoodssell.presentactivity.SingleAbsNetPresent;
import adult.mas.com.adultgoodssell.utils.CollectionUtils;
import adult.mas.com.adultgoodssell.utils.ResourcesUtils;
import adult.mas.com.adultgoodssell.utils.ToastUtils;
import adult.mas.com.adultgoodssell.view.MainIconText;
import adult.mas.com.adultgoodssell.view.recyclespace.SpaceItemLine;
import adult.mas.com.httpmodel.ResponseModel;
import adult.mas.com.thirdviewmodel.view.xrecycleview.ProgressStyle;
import adult.mas.com.thirdviewmodel.view.xrecycleview.XRecyclerView;
import butterknife.BindView;

/**
 * Created by sunmeng on 17/8/18.
 */

public class MarketShopActivity extends PresentBaseActivity<MarketShop> {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.searchIcon)
    MainIconText searchIcon;
    @BindView(R.id.back)
    View back;
    @BindView(R.id.xRecycleView)
    XRecyclerView xRecyclerView;
    MarketShopRecycleAdapter mVideoListAdapter;
    @BindView(R.id.totalPrice)
    TextView totalPrice;
    @BindView(R.id.goBuy)
    TextView goBuy;
    private int selectCount = 0;
    @Override
    public SingleAbsNetPresent intialPresent(Intent intent) {
        return new MarketShopPresent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        intialRequest();
    }

    @Override
    public void intialView() {
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onClickNoDouble(View view) {
                finish();
            }
        });
        title.setText(ResourcesUtils.getStringById(getContext(), R.string.shop_detail));
        searchIcon.setText("\ue8e6");
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        xRecyclerView.setLayoutManager(mLayoutManager);
        xRecyclerView.addItemDecoration(new SpaceItemLine(10));
        xRecyclerView.setPullRefreshEnabled(false);
        xRecyclerView.setLoadingMoreEnabled(false);
        xRecyclerView.setHomeStyle(false);
        xRecyclerView.setRefreshProgressStyle(ProgressStyle.LineScalePulseOutRapid);
        xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SysProgress);
        mVideoListAdapter = new MarketShopRecycleAdapter(getContext());
        mVideoListAdapter.setOnClickList(new NoDoubleClickListener() {
            @Override
            public void onClickNoDouble(View view) {
                GoodsInfo info = (GoodsInfo) view.getTag();
                if(info != null){
                    Intent intent = new Intent();
                    intent.putExtra(ConstantData.GoodsId, info.getGoodsId());
                    intent.setClass(view.getContext(), GoodsInfoActivity.class);
                    startActivity(intent);
                }
            }
        });
        xRecyclerView.setAdapter(mVideoListAdapter);
        goBuy.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onClickNoDouble(View view) {
                if(!CollectionUtils.isEmpty(mVideoListAdapter.mDatas)){
                    if(mVideoListAdapter.selectCount > 0){
                        Intent intent = new Intent();
                        intent.putExtra(ConstantData.SelectGoodsShop, (Serializable) mVideoListAdapter.mDatas);
                        intent.putExtra(ConstantData.SelectGoodsShop_COUNT, String.valueOf(selectCount));
                        intent.putExtra(ConstantData.SelectGoods_PRICE, totalPrice.getText().toString());
                        intent.setClass(getContext(), ConfirmDealActivity.class);
                        startActivity(intent);
                        return;
                    }
                }
                ToastUtils.showToastShort(getContext(), ResourcesUtils.getStringById(getContext(), R.string.shop_no_select));
            }
        });
    }

    @Override
    public int intialLaoutViewId() {
        return R.layout.layout_marketshop;
    }

    @Override
    public void setData(ResponseModel<MarketShop> result) {
        if(result != null && result.getData() != null){
            if(!CollectionUtils.isEmpty(result.getData().getSelectMarketGoodses())){
                selectCount = result.getData().getSelectCount();
                mVideoListAdapter.setData(result.getData().getSelectMarketGoodses(), result.getData().getSelectCount());
                totalPrice.setText(String.valueOf(result.getData().getTotalMoney()));
            }
        }
    }

    @Override
    public void setErrorData(Throwable result) {

    }

    @Override
    public void setNetWorkError() {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }
}
