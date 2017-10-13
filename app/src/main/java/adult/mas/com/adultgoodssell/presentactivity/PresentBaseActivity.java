package adult.mas.com.adultgoodssell.presentactivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import adult.mas.com.adultgoodssell.R;
import adult.mas.com.adultgoodssell.TranslucentActivity;
import adult.mas.com.adultgoodssell.presentview.MvpView;
import adult.mas.com.adultgoodssell.utils.DialogManager;
import adult.mas.com.adultgoodssell.utils.HttpUtils;
import adult.mas.com.adultgoodssell.utils.ResourcesUtils;
import adult.mas.com.adultgoodssell.utils.ToastUtils;
import adult.mas.com.httpmodel.ResponseModel;
import butterknife.ButterKnife;

/**
 * Created by sunmeng on 17/8/2.
 */

/**
 * 只有一个请求网络的界面继承该activity
 * @param <T>  请求返回data数据
 */
public abstract class PresentBaseActivity<T> extends TranslucentActivity implements MvpView<T>{

    private Dialog netWorkDialog;
    public SingleAbsNetPresent present;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(intialLaoutViewId());
        ButterKnife.bind(this);
        intialView();
        present = intialPresent(getIntent());
        if(present == null){
            ToastUtils.showToastLong(this, ResourcesUtils.getStringById(this, R.string.data_error));
            finish();
            return;
        }
        present.attachView(this);
        intialRequest();
    }


    public abstract SingleAbsNetPresent intialPresent(Intent intent);
    public abstract void intialView();
    public abstract int intialLaoutViewId();
    public abstract void setData(ResponseModel<T> result);
    public abstract void setErrorData(Throwable result);
    public abstract void setNetWorkError();


    public SingleAbsNetPresent getPresent() {
        return present;
    }

    public void intialRequest(){
        /**
         * Get data from server
         */
        if (HttpUtils.isNetWorkConnected(this)) {
            if(present != null){
                present.startNetRequest(this, true);
            }
        } else {
            setNetWorkError();
        }

    }

    @Override
    public void showNetWorkProgress() {
        if (netWorkDialog == null) {
            netWorkDialog = DialogManager.getInstance().showCircleRingLoading(this);
        }
        if(!netWorkDialog.isShowing()){
            netWorkDialog.show();
        }
    }

    @Override
    public void disMissProgress() {
        if (netWorkDialog != null && netWorkDialog.isShowing()) {
            netWorkDialog.dismiss();
            netWorkDialog = null;
        }
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
    protected void onDestroy() {
        super.onDestroy();
        if(present != null){
            present.detachView();
        }
    }
}
