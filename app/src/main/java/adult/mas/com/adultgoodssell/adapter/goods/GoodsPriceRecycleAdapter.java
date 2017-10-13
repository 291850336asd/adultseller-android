package adult.mas.com.adultgoodssell.adapter.goods;

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
import adult.mas.com.adultgoodssell.modelbean.mainview.GoodsPrice;
import adult.mas.com.adultgoodssell.utils.CollectionUtils;
import adult.mas.com.adultgoodssell.utils.ResourcesUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunmeng on 17/8/4.
 */

public class GoodsPriceRecycleAdapter extends RecyclerView.Adapter<GoodsPriceRecycleAdapter.PriceVH> {

    private Context mContext;
    private List<GoodsPrice> mDatas = new ArrayList<>();

    private int selectId = -1;
    public GoodsPriceRecycleAdapter(Context context){
        this.mContext = context;
    }


    public void setData(List<GoodsPrice> data){
        mDatas = data;
        notifyDataSetChanged();
    }

    @Override
    public GoodsPriceRecycleAdapter.PriceVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PriceVH(LayoutInflater
                .from(mContext)
                .inflate(R.layout.adapter_goods_price_item, parent, false));
    }

    @Override
    public void onBindViewHolder(GoodsPriceRecycleAdapter.PriceVH holder, int position) {

        final GoodsPrice info = mDatas.get(position);
        if(selectId == -1){
            selectId = info.getGoodsPriceId();
        }
        if(info != null){
            if(selectId == info.getGoodsPriceId()){
                holder.name.setTextColor(ResourcesUtils.getColorById(mContext, R.color.color_fd7530));
            }else {
                holder.name.setTextColor(ResourcesUtils.getColorById(mContext, R.color.color_999));
            }
            holder.name.setText(info.getGoodsColorStyleModel());
        }
        holder.name.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onClickNoDouble(View view) {
                selectId = info.getGoodsPriceId();
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
