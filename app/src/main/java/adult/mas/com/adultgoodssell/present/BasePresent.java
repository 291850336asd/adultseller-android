package adult.mas.com.adultgoodssell.present;

/**
 * Created by sunmeng on 17/8/2.
 */

/**
 * p层统一实现该接口，具体业务逻辑有新接口继承该接口实现
 * @param <V>
 */
public interface BasePresent<V> {
    void attachView(V view);
    void detachView();
}
