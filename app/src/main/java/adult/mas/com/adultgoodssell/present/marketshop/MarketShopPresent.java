package adult.mas.com.adultgoodssell.present.marketshop;

import android.content.Context;
import android.content.Intent;

import com.anupcowkur.reservoir.Reservoir;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import adult.mas.com.adultgoodssell.constant.ConstantData;
import adult.mas.com.adultgoodssell.modelbean.mainview.GoodsShop;
import adult.mas.com.adultgoodssell.modelbean.mainview.marketshop.MarketShop;
import adult.mas.com.adultgoodssell.netapi.GoodsSellerUtils;
import adult.mas.com.adultgoodssell.presentactivity.SingleAbsNetPresent;
import adult.mas.com.adultgoodssell.utils.CollectionUtils;
import adult.mas.com.httpmodel.RequestModel;
import adult.mas.com.httpmodel.ResponseModel;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sunmeng on 17/8/18.
 */

public class MarketShopPresent extends SingleAbsNetPresent {
    Subscription subscription;
    public RequestModel<List<GoodsShop>> goodsRequestModel;

    public MarketShopPresent() {
        this(null);
    }

    public MarketShopPresent(Intent intent) {
        super(intent);
    }

    @Override
    public RequestModel intialRequest(Intent intent) {
        if(goodsRequestModel == null){
            goodsRequestModel = new RequestModel<>();
            goodsRequestModel.setType(3);
        }
        List<GoodsShop> datasList = (List<GoodsShop>) this.intent.getSerializableExtra(ConstantData.SelectGoodsShop);
        if(this.intent != null && !CollectionUtils.isEmpty(datasList)){
            goodsRequestModel.setData(datasList);
        }else {
            try {
                Type resultType = new TypeToken<List<GoodsShop>>() {}.getType();
                boolean objectExists = Reservoir.contains(ConstantData.CacheGoodsShop);
                if(objectExists) {
                    List<GoodsShop> datas = Reservoir.get(ConstantData.CacheGoodsShop, resultType);
                    if (!CollectionUtils.isEmpty(datas)) {
                        goodsRequestModel.setData(datas);
                    }
                }
            }catch (Exception e){

            }
        }
        return goodsRequestModel;
    }



    @Override
    public void startNetRequest(Context context, boolean isReFresh) {
        if(context == null || intialRequest(null) == null){
            return;
        }
        if(isReFresh){
            activityV.showNetWorkProgress();
        }
        if(subscription != null && subscription.isUnsubscribed()){
            subscription.unsubscribe();

        }
        subscription = GoodsSellerUtils.getApiService(context)
                .marketShopDetail(intialRequest(null))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseModel<MarketShop>>() {
                    @Override
                    public void onCompleted() {
                        activityV.disMissProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        activityV.onError(e);
                        activityV.disMissProgress();
                    }

                    @Override
                    public void onNext(ResponseModel<MarketShop> entity) {
                        activityV.setNetData(entity);
                    }
                });
    }
}
