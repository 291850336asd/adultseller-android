package adult.mas.com.adultgoodssell.activity.fragment.goodsdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import adult.mas.com.adultgoodssell.R;
import adult.mas.com.adultgoodssell.activity.fragment.NetSingleFragment;
import adult.mas.com.adultgoodssell.adapter.goods.GoodsCommentsRecycleAdapter;
import adult.mas.com.adultgoodssell.constant.ConstantData;
import adult.mas.com.adultgoodssell.event.NoDoubleClickListener;
import adult.mas.com.adultgoodssell.modelbean.mainview.GoodsComments;
import adult.mas.com.adultgoodssell.modelbean.mainview.GoodsInfo;
import adult.mas.com.adultgoodssell.present.goodsinfo.GoodsCommentslPresent;
import adult.mas.com.adultgoodssell.presentactivity.SingleAbsNetPresent;
import adult.mas.com.adultgoodssell.utils.CollectionUtils;
import adult.mas.com.adultgoodssell.utils.ResourcesUtils;
import adult.mas.com.adultgoodssell.utils.ToastUtils;
import adult.mas.com.adultgoodssell.view.MainIconText;
import adult.mas.com.httpmodel.ResponseModel;
import adult.mas.com.thirdviewmodel.view.xrecycleview.ProgressStyle;
import adult.mas.com.thirdviewmodel.view.xrecycleview.XRecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunmeng on 17/8/1.
 */

public class GoodsCommentsFragment extends NetSingleFragment<List<GoodsComments>> {


    @BindView(R.id.headll)
    View headLL;
    @BindView(R.id.xRecycleView)
    XRecyclerView xRecyclerView;

    @BindView(R.id.errorLL)
    LinearLayout errorLL;
    @BindView(R.id.errorData)
    TextView errorData;
    @BindView(R.id.errorIcon)
    MainIconText errorIcon;


    private GoodsCommentsRecycleAdapter mVideoListAdapter;
    private LinearLayoutManager mLayoutManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_listbaseview, container, false);
        ButterKnife.bind(this, view);
        initialView();
        return view;
    }

    private void initialView() {
        headLL.setVisibility(View.GONE);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        xRecyclerView.setLayoutManager(mLayoutManager);
        xRecyclerView.setPullRefreshEnabled(false);
        xRecyclerView.setLoadingMoreEnabled(true);
        xRecyclerView.setHomeStyle(false);
        xRecyclerView.setRefreshProgressStyle(ProgressStyle.LineScalePulseOutRapid);
        xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SysProgress);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                present.startNetRequest(getContext(), true);
            }

            @Override
            public void onLoadMore() {
                present.startNetRequest(getContext(), false);
            }
        });
        mVideoListAdapter = new GoodsCommentsRecycleAdapter(getContext());
        xRecyclerView.setAdapter(mVideoListAdapter);

        errorIcon.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onClickNoDouble(View view) {
                present.startNetRequest(getContext(), true);
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void showNetWorkProgress() {

    }

    @Override
    public void setData(ResponseModel<List<GoodsComments>> result) {
        GoodsCommentslPresent mainVideoPresent = getPresent();
        if(result != null && !CollectionUtils.isEmpty(result.getData())){
            xRecyclerView.setVisibility(View.VISIBLE);
            errorLL.setVisibility(View.GONE);
            mVideoListAdapter.setData(result.getData(), mainVideoPresent.requestModel.getPage() == 0);
            if(result.getData().size() == mainVideoPresent.requestModel.getPageSize()){
                xRecyclerView.setLoadingMoreEnabled(true);
            }else {
                xRecyclerView.setLoadingMoreEnabled(false);
            }

            if(mainVideoPresent.requestModel.getPage() == 0){
                xRecyclerView.refreshComplete();
            }else {
                xRecyclerView.loadMoreComplete();
            }
        }else{
            if(mainVideoPresent.requestModel.getPage() == 0){
                xRecyclerView.setVisibility(View.GONE);
                errorLL.setVisibility(View.VISIBLE);
                errorData.setText(ResourcesUtils.getStringById(getContext(), R.string.data_error_clickrefresh));
                errorIcon.setText("\ue60b");
            }else{
                ToastUtils.showToastLong(getContext(), ResourcesUtils.getStringById(getContext(), R.string.data_error_clickrefresh));
            }
        }
    }

    @Override
    public void setErrorData(Throwable result) {
        GoodsCommentslPresent mainVideoPresent = getPresent();
        if(mainVideoPresent.requestModel.getPage() == 0){
            xRecyclerView.setVisibility(View.GONE);
            errorLL.setVisibility(View.VISIBLE);
            errorData.setText(ResourcesUtils.getStringById(getContext(), R.string.data_error_clickrefresh));
            errorIcon.setText("\ue60b");
        }else {
            ToastUtils.showToastLong(getContext(), ResourcesUtils.getStringById(getContext(), R.string.data_error));
        }
    }

    private GoodsCommentslPresent getPresent(){
        return (GoodsCommentslPresent) present;
    }

    @Override
    public SingleAbsNetPresent intialPresent(Intent intent) {
        if(intent == null || intent.getIntExtra(ConstantData.GoodsId, -1) == -1){
            return null;
        }
        GoodsCommentslPresent present = new GoodsCommentslPresent();
        present.intialRequest(null);
        GoodsInfo info = new GoodsInfo();
        info.setGoodsId(intent.getIntExtra(ConstantData.GoodsId, -1));
        present.requestModel.setData(info);
        return present;
    }

    @Override
    public void setDisMissProgress() {

    }

    @Override
    public void noNetWork() {
        GoodsCommentslPresent mainVideoPresent = getPresent();
        if(mainVideoPresent != null && mainVideoPresent.requestModel != null && mainVideoPresent.requestModel.getData() != null && mainVideoPresent.requestModel.getPage() == 0){
            xRecyclerView.setVisibility(View.GONE);
            errorLL.setVisibility(View.VISIBLE);
            errorData.setText(ResourcesUtils.getStringById(getContext(), R.string.net_not_connnect_clickrefresh));
            errorIcon.setText("\ue6ed");
        }else {
            ToastUtils.showToastLong(getContext(), ResourcesUtils.getStringById(getContext(), R.string.net_not_connnect));
        }
    }
}
