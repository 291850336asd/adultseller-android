package adult.mas.com.adultgoodssell.adapter.sortlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adult.mas.com.adultgoodssell.R;
import adult.mas.com.adultgoodssell.bussiness.sortlist.SortListActivity;
import adult.mas.com.adultgoodssell.event.NoDoubleClickListener;
import adult.mas.com.adultgoodssell.modelbean.mainview.goodssort.GoodsSort;
import adult.mas.com.adultgoodssell.utils.CollectionUtils;
import adult.mas.com.adultgoodssell.utils.ResourcesUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunmeng on 17/8/4.
 */

public class SortHeadRecycleAdapter extends RecyclerView.Adapter<SortHeadRecycleAdapter.PriceVH> {

    private Context mContext;
    private List<GoodsSort> mDatas = new ArrayList<>();

    private int currentSelectPosition = -1;
    public SortHeadRecycleAdapter(Context context){
        this.mContext = context;
    }


    public void setData(List<GoodsSort> data, int currentSelectId){
        mDatas = data;
        this.currentSelectPosition = currentSelectId;
        notifyDataSetChanged();
    }

    @Override
    public SortHeadRecycleAdapter.PriceVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PriceVH(LayoutInflater
                .from(mContext)
                .inflate(R.layout.adapter_sortlist_head_item, parent, false));
    }

    @Override
    public void onBindViewHolder(SortHeadRecycleAdapter.PriceVH holder, final int position) {

        final GoodsSort info = mDatas.get(position);
        if(currentSelectPosition == -1){
            currentSelectPosition = 0;
        }
        if(info != null){
            if(currentSelectPosition == position){
                holder.name.setTextColor(ResourcesUtils.getColorById(mContext, R.color.color_fd7530));
                holder.name.setBackgroundResource(R.drawable.shape_circle_half_centerempty);
            }else {
                holder.name.setTextColor(ResourcesUtils.getColorById(mContext, R.color.color_999));
                holder.name.setBackgroundResource(R.drawable.shape_circle_half_centerempty_default);
            }
            holder.name.setText(info.getSortName());
        }
        holder.name.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onClickNoDouble(View view) {
                ((SortListActivity)mContext).setHeadSelect(info.getSortType(), info.getSortParentType());
                currentSelectPosition = position;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return CollectionUtils.isEmpty(mDatas) ? 0 : mDatas.size();
    }
    class PriceVH extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        public TextView name;

        public PriceVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
