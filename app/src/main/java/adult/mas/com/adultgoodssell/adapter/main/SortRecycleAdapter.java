package adult.mas.com.adultgoodssell.adapter.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adult.mas.com.adultgoodssell.R;
import adult.mas.com.adultgoodssell.modelbean.mainview.goodssort.GoodsSortList;
import adult.mas.com.adultgoodssell.utils.CollectionUtils;
import adult.mas.com.adultgoodssell.utils.ResourcesUtils;
import adult.mas.com.adultgoodssell.view.DividerLineGridView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunmeng on 17/8/4.
 */

public class SortRecycleAdapter extends RecyclerView.Adapter<SortRecycleAdapter.RecommendVH> {

    private Context mContext;
    private List<GoodsSortList> mDatas = new ArrayList<>();
    public SortRecycleAdapter(Context context){
        this.mContext = context;
    }

    public void setData(List<GoodsSortList> data){
        mDatas = data;
        notifyDataSetChanged();
    }

    @Override
    public SortRecycleAdapter.RecommendVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecommendVH(LayoutInflater
                .from(mContext)
                .inflate(R.layout.main_sort_recycleview_item, parent, false));
    }

    @Override
    public void onBindViewHolder(SortRecycleAdapter.RecommendVH holder, int position) {
        if(position == 0){
            holder.nameBgLL.setVisibility(View.GONE);
        }else {
            holder.nameBgLL.setBackgroundColor(ResourcesUtils.getColorById(mContext, R.color.at_color_pressed));
            holder.nameBgLL.setVisibility(View.VISIBLE);
            holder.name.setText(mDatas.get(position-1).getSortName());
        }
        holder.gridView.setAdapter(new SortRecycleGridViewAdapter(mContext, mDatas, position));

    }

    @Override
    public int getItemCount() {
        return CollectionUtils.isEmpty(mDatas) ? 0 : mDatas.size() + 1;
    }
    class RecommendVH extends RecyclerView.ViewHolder {

        @BindView(R.id.nameBgLL)
        LinearLayout nameBgLL;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.gridView)
        DividerLineGridView gridView;
        public RecommendVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
