package adult.mas.com.adultgoodssell.presentactivity;

import android.content.Context;
import android.content.Intent;

import adult.mas.com.adultgoodssell.present.BasePresent;
import adult.mas.com.adultgoodssell.presentview.MvpView;
import adult.mas.com.httpmodel.RequestModel;
import rx.Subscription;

/**
 * Created by sunmeng on 17/8/2.
 */

/**
 *
 * @param <V> 界面
 */
public abstract class SingleAbsNetPresent<V> implements BasePresent<MvpView>{

    public Subscription subscription;
    public MvpView activityV;
    public Intent intent;

    public SingleAbsNetPresent() {
    }

    public SingleAbsNetPresent(Intent intent) {
        this.intent = intent;
    }

    @Override
    public void attachView(MvpView view) {
        activityV = view;
    }
    @Override
    public void detachView() {
        if(subscription != null && subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
        subscription = null;
    }

    public abstract RequestModel intialRequest(Intent intent);

    public abstract void startNetRequest(Context context, boolean isReFresh);
}
