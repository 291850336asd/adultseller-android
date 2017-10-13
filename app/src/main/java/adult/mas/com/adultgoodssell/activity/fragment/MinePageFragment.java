package adult.mas.com.adultgoodssell.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import adult.mas.com.adultgoodssell.R;
import butterknife.ButterKnife;

/**
 * Created by sunmeng on 17/8/1.
 */

public class MinePageFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_main_fragment_mine_page, container, false);
        ButterKnife.bind(this, view);
        initialView();
        return view;
    }

    private void initialView() {
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
    }
}
