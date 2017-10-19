package adult.mas.com.adultgoodssell.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import adult.mas.com.adultgoodssell.R;
import adult.mas.com.adultgoodssell.bussiness.webview.BaseWebAvtivity;
import adult.mas.com.adultgoodssell.event.NoDoubleClickListener;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunmeng on 17/8/1.
 */

public class MinePageFragment extends BaseFragment {

    @BindView(R.id.dealsweb)
    View dealsweb;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_main_fragment_mine_page, container, false);
        ButterKnife.bind(this, view);
        initialView();
        return view;
    }

    private void initialView() {
        dealsweb.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onClickNoDouble(View view) {
                Intent intent = new Intent(getContext(), BaseWebAvtivity.class);
                getContext().startActivity(intent);
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
    }
}
