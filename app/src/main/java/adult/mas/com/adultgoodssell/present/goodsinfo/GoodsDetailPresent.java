package adult.mas.com.adultgoodssell.present.goodsinfo;

import android.content.Context;
import android.content.Intent;

import adult.mas.com.adultgoodssell.modelbean.mainview.GoodsInfo;
import adult.mas.com.adultgoodssell.netapi.GoodsSellerUtils;
import adult.mas.com.adultgoodssell.presentactivity.SingleAbsNetPresent;
import adult.mas.com.httpmodel.RequestModel;
import adult.mas.com.httpmodel.ResponseModel;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sunmeng on 17/8/2.
 */

public class GoodsDetailPresent extends SingleAbsNetPresent {

    public RequestModel<GoodsInfo> goodsRequestModel;

    @Override
    public RequestModel intialRequest(Intent intent) {
        if(goodsRequestModel == null){
            goodsRequestModel = new RequestModel<>();
            goodsRequestModel.setType(3);
        }
        return goodsRequestModel;
    }

    @Override
    public void startNetRequest(Context context, boolean isRefresh) {
        if(context == null || intialRequest(null) == null){
            return;
        }
        if(isRefresh){
            activityV.showNetWorkProgress();
        }
        GoodsSellerUtils.getApiService(context)
                .gooodsDetailInfo(intialRequest(null))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseModel<GoodsInfo>>() {
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
                    public void onNext(ResponseModel<GoodsInfo> entity) {
                        activityV.setNetData(entity);
                    }
                });
    }
}
