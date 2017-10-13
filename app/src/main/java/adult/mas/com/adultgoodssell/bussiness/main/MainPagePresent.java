package adult.mas.com.adultgoodssell.bussiness.main;

import android.content.Context;
import android.content.Intent;

import adult.mas.com.adultgoodssell.netapi.GoodsSellerUtils;
import adult.mas.com.adultgoodssell.netapi.response.MainPageResponse;
import adult.mas.com.adultgoodssell.presentactivity.SingleAbsNetPresent;
import adult.mas.com.httpmodel.RequestModel;
import adult.mas.com.httpmodel.ResponseModel;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sunmeng on 17/8/2.
 */

public class MainPagePresent extends SingleAbsNetPresent {

    RequestModel userRequestRequestModel;
    @Override
    public RequestModel intialRequest(Intent intent) {
        if(userRequestRequestModel == null){
            userRequestRequestModel = new RequestModel<>();
            userRequestRequestModel.setType(3);
        }
        return userRequestRequestModel;
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
                .mainPageRequest(intialRequest(null))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseModel<MainPageResponse>>() {
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
                    public void onNext(ResponseModel<MainPageResponse> entity) {
                        activityV.setNetData(entity);
                    }
                });
    }
}
