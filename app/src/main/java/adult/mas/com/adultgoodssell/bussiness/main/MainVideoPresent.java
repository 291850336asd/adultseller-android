package adult.mas.com.adultgoodssell.bussiness.main;

import android.content.Context;
import android.content.Intent;

import java.util.List;

import adult.mas.com.adultgoodssell.modelbean.mainview.videos.VideoData;
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

public class MainVideoPresent extends SingleAbsNetPresent {

    public RequestModel requestModel;
    @Override
    public RequestModel intialRequest(Intent intent) {
        if(requestModel == null){
            requestModel = new RequestModel();
            requestModel.setType(3);
        }
        return requestModel;
    }

    @Override
    public void startNetRequest(Context context, boolean isRefresh) {
        if(context == null || intialRequest(null) == null){
            return;
        }
        if(isRefresh){
            requestModel.setPage(0);
            activityV.showNetWorkProgress();
        }else {
            requestModel.setPage(requestModel.getPage() + 1);
        }
        GoodsSellerUtils.getApiService(context)
                .mainVideosRequest(intialRequest(null))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseModel<List<VideoData>>>() {
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
                    public void onNext(ResponseModel<List<VideoData>> entity) {
                        activityV.setNetData(entity);
                    }
                });
    }
}
