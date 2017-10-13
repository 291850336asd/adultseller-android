package adult.mas.com.adultgoodssell.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import adult.mas.com.adultgoodssell.R;
import adult.mas.com.adultgoodssell.adapter.main.MainRecycleAdapter;
import adult.mas.com.adultgoodssell.bussiness.main.MainPagePresent;
import adult.mas.com.adultgoodssell.event.NoDoubleClickListener;
import adult.mas.com.adultgoodssell.netapi.response.MainPageResponse;
import adult.mas.com.adultgoodssell.presentactivity.SingleAbsNetPresent;
import adult.mas.com.adultgoodssell.utils.CollectionUtils;
import adult.mas.com.adultgoodssell.utils.ResourcesUtils;
import adult.mas.com.adultgoodssell.view.MainHeaderLinearLayout;
import adult.mas.com.adultgoodssell.view.MainIconText;
import adult.mas.com.adultgoodssell.view.recyclespace.SpaceItemLine;
import adult.mas.com.httpmodel.ResponseModel;
import adult.mas.com.thirdviewmodel.view.xrecycleview.ProgressStyle;
import adult.mas.com.thirdviewmodel.view.xrecycleview.XRecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunmeng on 17/8/1.
 */

public class MainPageFragment extends NetSingleFragment<MainPageResponse> {


    @BindView(R.id.searchView)
    View searchView;

    @BindView(R.id.xRecycleView)
    XRecyclerView xRecyclerView;
    private MainHeaderLinearLayout headView;
    private MainRecycleAdapter mainRecyAdapter;
    @BindView(R.id.errorLL)
    LinearLayout errorLL;
    @BindView(R.id.errorData)
    TextView errorData;
    @BindView(R.id.errorIcon)
    MainIconText errorIcon;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_listbaseview, container, false);
        ButterKnife.bind(this, view);
        initialView();
        return view;
    }

    private void initialView(){
        searchView.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onClickNoDouble(View view) {
            }
        });
        headView = new MainHeaderLinearLayout(getContext());
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        headView.setLayoutParams(lp);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        xRecyclerView.addItemDecoration(new SpaceItemLine(10, headView));
        xRecyclerView.setLayoutManager(gridLayoutManager);
        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingMoreEnabled(false);
        xRecyclerView.setHomeStyle(false);
        xRecyclerView.setRefreshProgressStyle(ProgressStyle.LineScalePulseOutRapid);
        xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SysProgress);
        xRecyclerView.addHeaderView(headView);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                intialRequest();
            }

            @Override
            public void onLoadMore() {
            }
        });
        mainRecyAdapter = new MainRecycleAdapter(getContext());
        xRecyclerView.setAdapter(mainRecyAdapter);
        errorIcon.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onClickNoDouble(View view) {
                present.startNetRequest(getContext(), true);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        headView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        headView.onStop();
    }


    @Override
    public void showNetWorkProgress() {

    }

    @Override
    public void setData(ResponseModel<MainPageResponse> result) {
        xRecyclerView.refreshComplete();
        if(result != null && result.getData() != null){
            xRecyclerView.setVisibility(View.VISIBLE);
            errorLL.setVisibility(View.GONE);
            mainRecyAdapter.setData(result.getData().getGoodsList(), true);
            if(!CollectionUtils.isEmpty(result.getData().getBanners())){
                headView.setBannerList(result.getData().getBanners());
            }
        }else{
            xRecyclerView.setVisibility(View.GONE);
            errorLL.setVisibility(View.VISIBLE);
            errorData.setText(ResourcesUtils.getStringById(getContext(), R.string.data_error_clickrefresh));
            errorIcon.setText("\ue60b");
        }
    }

    @Override
    public void setErrorData(Throwable result) {
        xRecyclerView.setVisibility(View.GONE);
        errorLL.setVisibility(View.VISIBLE);
        errorData.setText(ResourcesUtils.getStringById(getContext(), R.string.data_error_clickrefresh));
        errorIcon.setText("\ue60b");
    }

    @Override
    public SingleAbsNetPresent intialPresent(Intent intent) {
        return new MainPagePresent();
    }

    @Override
    public void setDisMissProgress() {

    }

    @Override
    public void noNetWork() {
        xRecyclerView.setVisibility(View.GONE);
        errorLL.setVisibility(View.VISIBLE);
        errorData.setText(ResourcesUtils.getStringById(getContext(), R.string.net_not_connnect_clickrefresh));
        errorIcon.setText("\ue6ed");
    }
}
