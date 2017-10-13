package adult.mas.com.adultgoodssell.activity.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by sunmeng on 17/8/1.
 */

public class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        if (getView() != null) {
            if (hidden) {
                getView().setFitsSystemWindows(false);
            } else {
                getView().setFitsSystemWindows(true);
            }
            getView().requestFitSystemWindows();
        }
        super.onHiddenChanged(hidden);
    }
}
