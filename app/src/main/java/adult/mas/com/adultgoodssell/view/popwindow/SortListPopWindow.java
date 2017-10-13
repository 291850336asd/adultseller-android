package adult.mas.com.adultgoodssell.view.popwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import java.util.List;

import adult.mas.com.adultgoodssell.R;
import adult.mas.com.adultgoodssell.adapter.sortlist.SortPopwindRecycleAdapter;
import adult.mas.com.adultgoodssell.event.NoDoubleClickListener;
import adult.mas.com.adultgoodssell.modelbean.mainview.goodssort.GoodsSortList;
import adult.mas.com.adultgoodssell.view.MaxRecycleView;
import adult.mas.com.thirdviewmodel.view.xrecycleview.ProgressStyle;

/**
 * Created by sunmeng on 17/8/23.
 */

public class SortListPopWindow extends PopupWindow{

    private NoDoubleClickListener listenser;

    public void setOnClickList(NoDoubleClickListener noDoubleClickListener){
        this.listenser = noDoubleClickListener;
    }

    public void setData(Context context, List<GoodsSortList> data, int selectType){
        View contentView = LayoutInflater.from(context).inflate(R.layout.popwindow_sort_list, null);
        setContentView(contentView);

        MaxRecycleView xRecyclerView = (MaxRecycleView) contentView.findViewById(R.id.xRecycleView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        xRecyclerView.setLayoutManager(mLayoutManager);
        xRecyclerView.setPullRefreshEnabled(false);
        xRecyclerView.setLoadingMoreEnabled(false);
        xRecyclerView.setHomeStyle(false);
        xRecyclerView.setRefreshProgressStyle(ProgressStyle.LineScalePulseOutRapid);
        xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SysProgress);
        SortPopwindRecycleAdapter mVideoListAdapter = new SortPopwindRecycleAdapter(context);
        mVideoListAdapter.setListener(listenser);
        xRecyclerView.setAdapter(mVideoListAdapter);
        mVideoListAdapter.setData(data, selectType);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
    }



    public void showPopWindowDown(View view){
        if(isShowing()){
            dismiss();
        }else {
            showAsDropDown(view);
        }
    }
}
