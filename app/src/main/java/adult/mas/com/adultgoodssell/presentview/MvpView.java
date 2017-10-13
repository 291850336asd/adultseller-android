package adult.mas.com.adultgoodssell.presentview;

/**
 * Created by sunmeng on 17/8/2.
 */

import adult.mas.com.httpmodel.ResponseModel;

/**
 * T为网络返回数据 该项目格式为ResponseModel<>
 *     若有多个网络请求接口则  自定义接口并继承该接口
 * @param <T>
 */
public interface MvpView<T> {
    void showNetWorkProgress();
    void disMissProgress();
    void onError(Throwable e);
    void setNetData(ResponseModel<T> result);
}
