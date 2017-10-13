package adult.mas.com.adultgoodssell.present.sortlist;

import android.content.Context;
import android.content.Intent;

import java.util.List;

import adult.mas.com.adultgoodssell.modelbean.mainview.GoodsInfo;
import adult.mas.com.adultgoodssell.modelbean.mainview.sortlist.SortRequest;
import adult.mas.com.adultgoodssell.netapi.GoodsSellerUtils;
import adult.mas.com.adultgoodssell.presentactivity.SingleAbsNetPresent;
import adult.mas.com.httpmodel.RequestModel;
import adult.mas.com.httpmodel.ResponseModel;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sunmeng on 17/8/18.
 */

public class SortListPresent extends SingleAbsNetPresent {
    Subscription subscription;
    public RequestModel<SortRequest> requestModel;
    @Override
    public RequestModel intialRequest(Intent intent) {
        if(requestModel == null){
            requestModel = new RequestModel<>();
            requestModel.setData(new SortRequest());
            requestModel.setType(3);
        }
        return requestModel;
    }

    public void setSortType(int itemType, int parentType){
        intialRequest(null);
        requestModel.getData().setItemType(itemType);
        requestModel.getData().setParentType(parentType);
    }

    @Override
    public void startNetRequest(Context context, boolean isReFresh) {
        if(context == null || intialRequest(null) == null){
            return;
        }
        if(isReFresh){
            requestModel.setPage(0);
            activityV.showNetWorkProgress();
        }else {
            requestModel.setPage(requestModel.getPage() + 1);
        }
        if(subscription != null && subscription.isUnsubscribed()){
            subscription.unsubscribe();

        }
        subscription = GoodsSellerUtils.getApiService(context)
                .sortList(intialRequest(null))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseModel<List<GoodsInfo>>>() {
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
                    public void onNext(ResponseModel<List<GoodsInfo>> entity) {
                        activityV.setNetData(entity);
                    }
                });
    }
}
