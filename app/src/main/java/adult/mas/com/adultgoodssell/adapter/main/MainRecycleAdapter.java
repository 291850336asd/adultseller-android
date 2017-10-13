package adult.mas.com.adultgoodssell.adapter.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adult.mas.com.adultgoodssell.R;
import adult.mas.com.adultgoodssell.bussiness.goodsindo.GoodsInfoActivity;
import adult.mas.com.adultgoodssell.constant.ConstantData;
import adult.mas.com.adultgoodssell.event.NoDoubleClickListener;
import adult.mas.com.adultgoodssell.modelbean.mainview.GoodsInfo;
import adult.mas.com.adultgoodssell.utils.CollectionUtils;
import adult.mas.com.adultgoodssell.utils.ResourcesUtils;
import adult.mas.com.adultgoodssell.utils.StringUtils;
import adult.mas.com.adultgoodssell.view.SquareImageView;
import adult.mas.com.imageload.ImageUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunmeng on 17/8/4.
 */

public class MainRecycleAdapter extends RecyclerView.Adapter<MainRecycleAdapter.RecommendVH> {

    private Context mContext;
    private List<GoodsInfo> mDatas = new ArrayList<>();
    public MainRecycleAdapter(Context context){
        this.mContext = context;
    }


    public void setData(List<GoodsInfo> data, boolean isFirst){
        if(isFirst){
            mDatas = data;
        }else {
            mDatas.addAll(data);
        }
        notifyDataSetChanged();
    }

    @Override
    public MainRecycleAdapter.RecommendVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecommendVH(LayoutInflater
                .from(mContext)
                .inflate(R.layout.main_recycleview_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MainRecycleAdapter.RecommendVH holder, int position) {

        final GoodsInfo info = mDatas.get(position);
        if(info != null){
            holder.name.setText(info.getGoodsName());
            holder.sellPrice.setText(String.valueOf(info.getGoodsShopPrice()));
            holder.sellCount.setText(StringUtils.formatString(ResourcesUtils.getStringById(mContext, R.string.already_sellcount_unit), String.valueOf(info.getGoodsSellCount())));
            ImageUtils.loadImageFromNormal(info.getGoodsImg(), holder.img, R.drawable.no_data_icon);
            holder.rootView.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onClickNoDouble(View view) {
                    Intent intent = new Intent();
                    intent.putExtra(ConstantData.GoodsId, info.getGoodsId());
                    intent.setClass(view.getContext(), GoodsInfoActivity.class);
                    view.getContext().startActivity(intent);
                }
            });
        }else {
            holder.rootView.setOnClickListener(null);
        }
    }

    @Override
    public int getItemCount() {
        return CollectionUtils.isEmpty(mDatas) ? 0 : mDatas.size();
    }
    class RecommendVH extends RecyclerView.ViewHolder {

        @BindView(R.id.img)
        public SquareImageView img;
        @BindView(R.id.sellCount)
        public TextView sellCount;
        @BindView(R.id.sellPrice)
        public TextView sellPrice;
        @BindView(R.id.name)
        public TextView name;
        @BindView(R.id.rootView)
        View rootView;
        public RecommendVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
