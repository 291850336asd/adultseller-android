package adult.mas.com.adultgoodssell.bussiness.main;

import android.content.Context;
import android.content.Intent;

import java.util.List;

import adult.mas.com.adultgoodssell.modelbean.mainview.goodssort.GoodsSortList;
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

public class MainSortPresent extends SingleAbsNetPresent {

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
                .mainGoodsSortRequest(intialRequest(null))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseModel<List<GoodsSortList>>>() {
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
                    public void onNext(ResponseModel<List<GoodsSortList>> entity) {
                        activityV.setNetData(entity);
                    }
                });
    }
}
