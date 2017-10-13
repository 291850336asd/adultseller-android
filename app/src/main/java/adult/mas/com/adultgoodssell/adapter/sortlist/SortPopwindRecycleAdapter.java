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
import adult.mas.com.adultgoodssell.event.NoDoubleClickListener;
import adult.mas.com.adultgoodssell.modelbean.mainview.goodssort.GoodsSortList;
import adult.mas.com.adultgoodssell.utils.CollectionUtils;
import adult.mas.com.adultgoodssell.utils.ResourcesUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunmeng on 17/8/4.
 */

public class SortPopwindRecycleAdapter extends RecyclerView.Adapter<SortPopwindRecycleAdapter.PriceVH> {

    private Context mContext;
    private List<GoodsSortList> mDatas = new ArrayList<>();


    private NoDoubleClickListener listener;
    private int selectType;
    public SortPopwindRecycleAdapter(Context context){
        this.mContext = context;
    }

    public void setListener(NoDoubleClickListener noDoubleClickListener){
        this.listener = noDoubleClickListener;
    }
    public void setData(List<GoodsSortList> data, int type){
        mDatas = data;
        selectType = type;
        notifyDataSetChanged();
    }

    @Override
    public SortPopwindRecycleAdapter.PriceVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PriceVH(LayoutInflater
                .from(mContext)
                .inflate(R.layout.adapter_goods_price_item, parent, false));
    }

    @Override
    public void onBindViewHolder(SortPopwindRecycleAdapter.PriceVH holder, int position) {

        final GoodsSortList info = mDatas.get(position);
        if(info != null){
            if(info.getSortType() == selectType){
                holder.name.setTextColor(ResourcesUtils.getColorById(mContext, R.color.color_fd7530));
            }else {
                holder.name.setTextColor(ResourcesUtils.getColorById(mContext, R.color.color_999));
            }
            holder.name.setText(info.getSortName());
        }
        holder.name.setTag(info);
        holder.name.setOnClickListener(listener);
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
