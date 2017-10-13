package adult.mas.com.adultgoodssell.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import adult.mas.com.adultgoodssell.presentactivity.SingleAbsNetPresent;
import adult.mas.com.adultgoodssell.presentview.MvpView;
import adult.mas.com.adultgoodssell.utils.HttpUtils;
import adult.mas.com.httpmodel.ResponseModel;

/**
 * Created by sunmeng on 17/8/7.
 */

public abstract class NetSingleFragment<T> extends BaseFragment implements MvpView<T> {

    public SingleAbsNetPresent present;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public abstract void setData(ResponseModel<T> result);
    public abstract void setErrorData(Throwable result);
    public abstract SingleAbsNetPresent intialPresent(Intent intent);
    public abstract void setDisMissProgress();
    public abstract void noNetWork();
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        present = intialPresent(getActivity().getIntent());
        present.attachView(this);
        intialRequest();
    }


    public void intialRequest(){
        /**
         * Get data from server
         */
        if (HttpUtils.isNetWorkConnected(getContext())) {
            present.startNetRequest(getContext(), true);
        } else {
            noNetWork();
        }

    }

    @Override
    public void disMissProgress() {
        setDisMissProgress();
    }

    @Override
    public void onError(Throwable e) {
        setErrorData(e);
    }

    @Override
    public void setNetData(ResponseModel<T> result) {
        setData(result);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(present != null){
            present.detachView();
        }
    }
}
