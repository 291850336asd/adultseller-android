package adult.mas.com.adultgoodssell.bussiness.sortlist;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adult.mas.com.adultgoodssell.R;
import adult.mas.com.adultgoodssell.adapter.main.MainRecycleAdapter;
import adult.mas.com.adultgoodssell.adapter.sortlist.SortHeadRecycleAdapter;
import adult.mas.com.adultgoodssell.constant.ConstantData;
import adult.mas.com.adultgoodssell.event.NoDoubleClickListener;
import adult.mas.com.adultgoodssell.modelbean.mainview.GoodsInfo;
import adult.mas.com.adultgoodssell.modelbean.mainview.goodssort.GoodsSort;
import adult.mas.com.adultgoodssell.modelbean.mainview.goodssort.GoodsSortList;
import adult.mas.com.adultgoodssell.present.sortlist.SortListPresent;
import adult.mas.com.adultgoodssell.presentactivity.PresentBaseActivity;
import adult.mas.com.adultgoodssell.presentactivity.SingleAbsNetPresent;
import adult.mas.com.adultgoodssell.utils.CollectionUtils;
import adult.mas.com.adultgoodssell.utils.ResourcesUtils;
import adult.mas.com.adultgoodssell.view.MainIconText;
import adult.mas.com.adultgoodssell.view.popwindow.SortListPopWindow;
import adult.mas.com.adultgoodssell.view.recyclespace.SpaceItemLine;
import adult.mas.com.httpmodel.ResponseModel;
import adult.mas.com.thirdviewmodel.view.xrecycleview.ProgressStyle;
import adult.mas.com.thirdviewmodel.view.xrecycleview.XRecyclerView;
import butterknife.BindView;

/**
 * Created by sunmeng on 17/8/22.
 */

public class SortListActivity extends PresentBaseActivity<List<GoodsInfo>> {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.searchIcon)
    View searchIcon;
    @BindView(R.id.xRecycleViewHead)
    RecyclerView xRecycleViewHead;
    @BindView(R.id.xRecycleView)
    XRecyclerView xRecyclerView;
    @BindView(R.id.sortSortLL)
    View sortSortLL;
    @BindView(R.id.sortText)
    TextView sortText;
    @BindView(R.id.sortIcon)
    MainIconText sortIcon;
    MainRecycleAdapter mainRecyAdapter;
    SortHeadRecycleAdapter sortHeadRecycleAdapter;
    List<GoodsSortList> goodsSortLists;
    private int parentType;
    private int currentType;
    private int selectHeadPosition;

    private int sqlType;
    private int sqlItemType;

    @Override
    public SingleAbsNetPresent intialPresent(Intent intent) {
        SortListPresent sortListPresent = new SortListPresent();
        sortListPresent.setSortType(sqlItemType, sqlType);
        return sortListPresent;
    }

    public void setHeadSelect(int selectType, int parentType){
        ((SortListPresent)present).requestModel.setPage(0);
        ((SortListPresent)present).setSortType(selectType, parentType);
        intialRequest();

    }


    @Override
    public void intialView() {
        Intent intent = getIntent();
        if(intent == null){
            finish();
            return;
        }
        goodsSortLists = (List<GoodsSortList>) intent.getSerializableExtra(ConstantData.GOODS_SORT_LIST);
        currentType = intent.getIntExtra(ConstantData.GOODS_SORT_LIST_SELECT_TYPE, -1);
        parentType = intent.getIntExtra(ConstantData.GOODS_SORT_LIST_SELECT_PARENT, -1);
        if(CollectionUtils.isEmpty(goodsSortLists)){
            finish();
            return;
        }
        searchIcon.setVisibility(View.GONE);
        intialTitle();
        initialHeadRecycleView();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        xRecyclerView.addItemDecoration(new SpaceItemLine(10));
        xRecyclerView.setLayoutManager(gridLayoutManager);
        xRecyclerView.setPullRefreshEnabled(true);
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
        mainRecyAdapter = new MainRecycleAdapter(getContext());
        xRecyclerView.setAdapter(mainRecyAdapter);
        sortSortLL.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onClickNoDouble(View view) {
                final SortListPopWindow sortListPopWindow = new SortListPopWindow();
                sortListPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        sortText.setTextColor(ResourcesUtils.getColorById(getContext(), R.color.color_999));
                        sortIcon.setTextColor(ResourcesUtils.getColorById(getContext(), R.color.color_999));
                    }
                });
                sortListPopWindow.setOnClickList(new NoDoubleClickListener() {
                    @Override
                    public void onClickNoDouble(View view) {
                        GoodsSortList sortList = (GoodsSortList) view.getTag();
                        if(sortList != null){
                            currentType = sortList.getSortType();
                            parentType = 0;
                            intialTitle();
                            initialHeadRecycleView();
                            intialRequest();
                            sortListPopWindow.dismiss();
                        }
                    }
                });
                sortText.setTextColor(ResourcesUtils.getColorById(getContext(), R.color.color_FBAB2D));
                sortIcon.setTextColor(ResourcesUtils.getColorById(getContext(), R.color.color_FBAB2D));
                sortListPopWindow.setData(getContext(), goodsSortLists, sqlType);
                sortListPopWindow.showPopWindowDown(sortSortLL);
            }
        });
    }

    private void initialHeadRecycleView(){
        if(sortHeadRecycleAdapter == null){
            final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            xRecycleViewHead.setLayoutManager(mLayoutManager);
            sortHeadRecycleAdapter = new SortHeadRecycleAdapter(getContext());
            xRecycleViewHead.setAdapter(sortHeadRecycleAdapter);
        }
        List<GoodsSort> sortList = getHeadRecycleData();
        GoodsSort goodsSort = new GoodsSort();
        goodsSort.setSortName(ResourcesUtils.getStringById(getContext(),R.string.all));
        goodsSort.setSortParentType(sqlType);
        goodsSort.setSortType(0);
        sortList.add(0, goodsSort);
        sortHeadRecycleAdapter.setData(sortList, selectHeadPosition);
        xRecycleViewHead.scrollToPosition(selectHeadPosition);
    }



    private List<GoodsSort> getHeadRecycleData(){
        if(parentType == 0){
            selectHeadPosition = 0;
            for(GoodsSortList item: goodsSortLists){
                if(currentType == item.getSortType()){
                    sqlType = currentType;
                    sqlItemType = 0;
                    return new ArrayList<>(item.getItems());
                }
            }
        }else{
            selectHeadPosition = 0;
            for(GoodsSortList item: goodsSortLists){
                if(parentType == item.getSortType()){
                    sqlType = parentType;
                    for(GoodsSort itemItem: item.getItems() ){
                        selectHeadPosition ++;
                        if(itemItem.getSortType() == currentType){
                            sqlItemType = currentType;
                            break;
                        }
                    }
                    return new ArrayList<>(item.getItems());
                }
            }
        }
        return null;
    }

    private void intialTitle(){
        if(parentType == 0){
            for(GoodsSortList item: goodsSortLists){
                if(currentType == item.getSortType()){
                    title.setText(item.getSortName());
                    break;
                }
            }
        }else{
            for(GoodsSortList item: goodsSortLists){
                if(parentType == item.getSortType()){
                    title.setText(item.getSortName());
                    break;
                }
            }
        }
    }


    @Override
    public int intialLaoutViewId() {
        return R.layout.layout_sort_list;
    }

    @Override
    public void setData(ResponseModel<List<GoodsInfo>> result) {
        xRecyclerView.refreshComplete();
        xRecyclerView.loadMoreComplete();
        SortListPresent mainVideoPresent = (SortListPresent)getPresent();
        if(result != null && !CollectionUtils.isEmpty(result.getData())){
            xRecyclerView.setVisibility(View.VISIBLE);
            mainRecyAdapter.setData(result.getData(), mainVideoPresent.requestModel.getPage() == 0);
            if(result.getData().size() == mainVideoPresent.requestModel.getPageSize()){
                xRecyclerView.setLoadingMoreEnabled(true);
            }else {
                xRecyclerView.setLoadingMoreEnabled(false);
            }

        }else{
            mainRecyAdapter.setData(null, mainVideoPresent.requestModel.getPage() == 0);
        }
    }

    @Override
    public void setErrorData(Throwable result) {

    }

    @Override
    public void setNetWorkError() {

    }
}
