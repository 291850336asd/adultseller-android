package adult.mas.com.adultgoodssell.bussiness.confirmdeal;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anupcowkur.reservoir.Reservoir;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import adult.mas.com.adultgoodssell.R;
import adult.mas.com.adultgoodssell.adapter.marketshop.MarketShopRecycleAdapter;
import adult.mas.com.adultgoodssell.bussiness.address.AddAddressActivity;
import adult.mas.com.adultgoodssell.constant.ConstantData;
import adult.mas.com.adultgoodssell.event.NoDoubleClickListener;
import adult.mas.com.adultgoodssell.modelbean.mainview.GoodsShop;
import adult.mas.com.adultgoodssell.modelbean.mainview.confirm.ConfirmDealResponse;
import adult.mas.com.adultgoodssell.modelbean.mainview.confirm.TransferShip;
import adult.mas.com.adultgoodssell.modelbean.mainview.deal.CreateDeal;
import adult.mas.com.adultgoodssell.modelbean.mainview.zone.ZoneBean;
import adult.mas.com.adultgoodssell.present.confirm.ConfirmDealPresent;
import adult.mas.com.adultgoodssell.presentactivity.PresentBaseActivity;
import adult.mas.com.adultgoodssell.presentactivity.SingleAbsNetPresent;
import adult.mas.com.adultgoodssell.presentview.CreateDealView;
import adult.mas.com.adultgoodssell.utils.CollectionUtils;
import adult.mas.com.adultgoodssell.utils.ResourcesUtils;
import adult.mas.com.adultgoodssell.utils.StringUtils;
import adult.mas.com.adultgoodssell.utils.ToastUtils;
import adult.mas.com.adultgoodssell.utils.UUIDUtils;
import adult.mas.com.adultgoodssell.view.MainIconText;
import adult.mas.com.adultgoodssell.view.recyclespace.SpaceItemLine;
import adult.mas.com.httpmodel.ResponseModel;
import adult.mas.com.thirdviewmodel.view.xrecycleview.ProgressStyle;
import adult.mas.com.thirdviewmodel.view.xrecycleview.XRecyclerView;
import butterknife.BindView;

/**
 * Created by sunmeng on 17/8/19.
 */

public class ConfirmDealActivity extends PresentBaseActivity<ConfirmDealResponse> implements CreateDealView {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.searchIcon)
    View searchIcon;
    @BindView(R.id.back)
    View back;
    @BindView(R.id.addAddress)
    View addAddress;
    @BindView(R.id.xRecycleView)
    XRecyclerView xRecyclerView;
    @BindView(R.id.totalPrice)
    TextView totalPrice;
    @BindView(R.id.totalCount)
    TextView totalCount;
    @BindView(R.id.totalBottomPrice)
    TextView totalBottomPrice;
    @BindView(R.id.transferAll)
    LinearLayout transferAll;
    @BindView(R.id.userName)
    TextView userName;
    @BindView(R.id.userPhone)
    TextView userPhone;
    @BindView(R.id.userDetailAddress)
    TextView userDetailAddress;
    @BindView(R.id.userInfo)
    View userInfo;
    @BindView(R.id.submitDeal)
    TextView submitDeal;
    MarketShopRecycleAdapter mVideoListAdapter;

    @Override
    public int intialLaoutViewId() {
        return R.layout.layout_confirm_deal;
    }

    @Override
    public SingleAbsNetPresent intialPresent(Intent intent) {
        return new ConfirmDealPresent();
    }

    @Override
    public void intialView() {

        Intent intent = getIntent();
        if(intent == null){
            finish();
            return;
        }
        if(intent == null){
            finish();
            return;
        }
        searchIcon.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);
        title.setText(R.string.confirm_deal);
        back.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onClickNoDouble(View view) {
                finish();
            }
        });
        addAddress.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onClickNoDouble(View view) {
                Intent intent = new Intent();
                intent.setClass(getContext(), AddAddressActivity.class);
                startActivity(intent);
            }
        });

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        xRecyclerView.setLayoutManager(mLayoutManager);
        xRecyclerView.addItemDecoration(new SpaceItemLine(10));
        xRecyclerView.setPullRefreshEnabled(false);
        xRecyclerView.setLoadingMoreEnabled(false);
        xRecyclerView.setHomeStyle(false);
        xRecyclerView.setRefreshProgressStyle(ProgressStyle.LineScalePulseOutRapid);
        xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SysProgress);
        mVideoListAdapter = new MarketShopRecycleAdapter(getContext(), true);
        xRecyclerView.setAdapter(mVideoListAdapter);

        submitDeal.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onClickNoDouble(View view) {
                CreateDeal createDeal = new CreateDeal();
                createDeal.setDeviceId(UUIDUtils.getUniquePsuedoID());
                createDeal.setPayType(1);
                createDeal.setTransferCode("normal");

                try {
                    Type resultType = new TypeToken<List<GoodsShop>>() {}.getType();
                    boolean objectExists = Reservoir.contains(ConstantData.CacheGoodsShop);
                    if(objectExists) {
                        List<GoodsShop> datas = Reservoir.get(ConstantData.CacheGoodsShop, resultType);
                        if (!CollectionUtils.isEmpty(datas)) {
                            createDeal.setShops(datas);
                        }
                    }
                }catch (Exception e){

                }
                ((ConfirmDealPresent)getPresent()).confirmDealRequest(getContext(), createDeal);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        try {
            boolean objectExists = Reservoir.contains(ConstantData.CacheUsers_NOLOGIN);
            if(objectExists) {
                Type resultType = new TypeToken<ZoneBean>() {}.getType();
                ZoneBean datas = Reservoir.get(ConstantData.CacheUsers_NOLOGIN, resultType);
                if(datas != null){
                    userInfo.setVisibility(View.VISIBLE);
                    userName.setText(datas.getReceiveName());
                    userPhone.setText(datas.getReceivePhone());
                    userDetailAddress.setText(StringUtils.concatString(datas.getProvince(), " ",
                            datas.getCity(), " ", datas.getArea(), " ", datas.getDetail()));
                }else {
                    userInfo.setVisibility(View.GONE);
                }
            }
        }catch (Exception e){

        }

    }

    @Override
    public void setData(ResponseModel<ConfirmDealResponse> result) {
        if(result != null && result.getData() != null){
            if(!CollectionUtils.isEmpty(result.getData().getMarketShop().getSelectMarketGoodses())){
                if(transferAll.getChildCount() > 0){
                    transferAll.removeAllViews();
                }
                List<TransferShip> transferShips = result.getData().getTransfers();
                if(!CollectionUtils.isEmpty(transferShips)){
                    for(int i = 0; i < transferShips.size(); i++){
                        View transferItem = LayoutInflater.from(getContext()).inflate(R.layout.item_confirm_deal_transfer, null, false);
                        TextView transferName = (TextView) transferItem.findViewById(R.id.transferName);
                        transferName.setText(transferShips.get(i).getTransferName());
                        MainIconText iconText = (MainIconText) transferItem.findViewById(R.id.selectIcon);
                        if(i == 0){
                         iconText.setTextColor(ResourcesUtils.getColorById(getContext(), R.color.at_color_pressed));
                        }
                        if(i == transferShips.size()-1){
                            transferItem.findViewById(R.id.divider).setVisibility(View.GONE);
                        }
                        transferItem.setVisibility(View.VISIBLE);
                        transferAll.addView(transferItem);
                    }
                }
                totalBottomPrice.setText(String.valueOf(result.getData().getMarketShop().getTotalMoney()));
                totalPrice.setText(String.valueOf(result.getData().getMarketShop().getTotalMoney()));
                totalCount.setText(StringUtils.formatString(getResources().getString(R.string.total_price_text), String.valueOf(result.getData().getMarketShop().getSelectCount())));
                mVideoListAdapter.setData(result.getData().getMarketShop().getSelectMarketGoodses(), result.getData().getMarketShop().getSelectCount());
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
    public void setCreateDealData(ResponseModel result) {
        ToastUtils.showToastLong(getContext(), result.getMessage());
    }
}
