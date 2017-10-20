package adult.mas.com.adultgoodssell.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anupcowkur.reservoir.Reservoir;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import adult.mas.com.adultgoodssell.R;
import adult.mas.com.adultgoodssell.bussiness.marketshop.MarketShopActivity;
import adult.mas.com.adultgoodssell.bussiness.webview.BaseWebAvtivity;
import adult.mas.com.adultgoodssell.constant.ConstantData;
import adult.mas.com.adultgoodssell.event.NoDoubleClickListener;
import adult.mas.com.adultgoodssell.modelbean.mainview.GoodsShop;
import adult.mas.com.adultgoodssell.utils.CollectionUtils;
import adult.mas.com.adultgoodssell.utils.ResourcesUtils;
import adult.mas.com.adultgoodssell.utils.ToastUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunmeng on 17/8/1.
 */

public class MinePageFragment extends BaseFragment {

    @BindView(R.id.dealsweb)
    View dealsweb;

    @BindView(R.id.marketShop)
    View marketShop;

    @BindView(R.id.waitPay)
    View waitPay;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_main_fragment_mine_page, container, false);
        ButterKnife.bind(this, view);
        initialView();
        return view;
    }

    private void initialView() {
        dealsweb.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onClickNoDouble(View view) {
                Intent intent = new Intent(getContext(), BaseWebAvtivity.class);
                getContext().startActivity(intent);
            }
        });

        marketShop.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onClickNoDouble(View view) {
                gotoMarketShop();
            }
        });

        waitPay.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onClickNoDouble(View view) {
                Intent intent = new Intent(getContext(), BaseWebAvtivity.class);
                getContext().startActivity(intent);
            }
        });
    }


    private void gotoMarketShop() {
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
    @Override
    public void onHiddenChanged(boolean hidden) {
    }
}
