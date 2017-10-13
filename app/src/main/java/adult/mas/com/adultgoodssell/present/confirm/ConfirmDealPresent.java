package adult.mas.com.adultgoodssell.present.confirm;

import android.content.Context;
import android.content.Intent;

import com.anupcowkur.reservoir.Reservoir;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import adult.mas.com.adultgoodssell.bussiness.confirmdeal.ConfirmDealActivity;
import adult.mas.com.adultgoodssell.constant.ConstantData;
import adult.mas.com.adultgoodssell.modelbean.mainview.GoodsShop;
import adult.mas.com.adultgoodssell.modelbean.mainview.confirm.ConfirmDealResponse;
import adult.mas.com.adultgoodssell.modelbean.mainview.deal.CreateDeal;
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

public class ConfirmDealPresent extends SingleAbsNetPresent {
    Subscription subConfirmDeal;
    public RequestModel<List<GoodsShop>> goodsRequestModel;
    @Override
    public RequestModel intialRequest(Intent intent) {
        if(goodsRequestModel == null){
            goodsRequestModel = new RequestModel<>();
            goodsRequestModel.setType(3);
        }
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
                .confirmDeal(intialRequest(null))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseModel<ConfirmDealResponse>>() {
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
                    public void onNext(ResponseModel<ConfirmDealResponse> entity) {
                        activityV.setNetData(entity);
                    }
                });
    }


    public void confirmDealRequest(final Context context, CreateDeal createDeal){
        activityV.showNetWorkProgress();
        if(subConfirmDeal != null && subConfirmDeal.isUnsubscribed()){
            subConfirmDeal.unsubscribe();

        }
        RequestModel<CreateDeal> requestModel = new RequestModel();
        requestModel.setData(createDeal);
        subConfirmDeal = GoodsSellerUtils.getApiService(context)
                .createDeal(requestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseModel>() {
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
                    public void onNext(ResponseModel entity) {
                        ((ConfirmDealActivity)context).setCreateDealData(entity);
                    }
                });
    }



    @Override
    public void detachView() {
        super.detachView();
        if(subConfirmDeal != null && subConfirmDeal.isUnsubscribed()){
            subConfirmDeal.unsubscribe();
        }
        subConfirmDeal = null;
    }
}
