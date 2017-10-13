package adult.mas.com.adultgoodssell.adapter.marketshop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anupcowkur.reservoir.Reservoir;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import adult.mas.com.adultgoodssell.R;
import adult.mas.com.adultgoodssell.bussiness.marketshop.MarketShopActivity;
import adult.mas.com.adultgoodssell.constant.ConstantData;
import adult.mas.com.adultgoodssell.event.NoDoubleClickListener;
import adult.mas.com.adultgoodssell.modelbean.mainview.GoodsInfo;
import adult.mas.com.adultgoodssell.modelbean.mainview.GoodsPrice;
import adult.mas.com.adultgoodssell.modelbean.mainview.GoodsShop;
import adult.mas.com.adultgoodssell.modelbean.mainview.marketshop.SelectMarketGoods;
import adult.mas.com.adultgoodssell.utils.CollectionUtils;
import adult.mas.com.adultgoodssell.utils.ResourcesUtils;
import adult.mas.com.adultgoodssell.utils.ToastUtils;
import adult.mas.com.adultgoodssell.view.MainIconText;
import adult.mas.com.imageload.ImageUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunmeng on 17/8/4.
 */

public class MarketShopRecycleAdapter extends RecyclerView.Adapter<MarketShopRecycleAdapter.PriceVH> {

    private Context mContext;
    private NoDoubleClickListener noDoubleClickListener;
    public List<SelectMarketGoods> mDatas = new ArrayList<>();
    public int selectCount = 0;
    private boolean isConfirmDeal = false;
    public MarketShopRecycleAdapter(Context context){
        this.mContext = context;
    }

    public MarketShopRecycleAdapter(Context context, boolean isConfirmDeal){
        this.mContext = context;
        this.isConfirmDeal = isConfirmDeal;
    }

    public void setOnClickList(NoDoubleClickListener noDoubleClickListener){
        this.noDoubleClickListener = noDoubleClickListener;
    }
    public void setData(List<SelectMarketGoods> data, int selectCount){
        mDatas = data;
        this.selectCount = selectCount;
        notifyDataSetChanged();
    }

    @Override
    public MarketShopRecycleAdapter.PriceVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PriceVH(LayoutInflater
                .from(mContext)
                .inflate(R.layout.adapter_market_shop_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final MarketShopRecycleAdapter.PriceVH holder, final int position) {

        final GoodsInfo info = mDatas.get(position).getGoodsInfo();
        if(info != null){
            holder.name.setText(info.getGoodsName());
            if(!CollectionUtils.isEmpty(info.getPricesList())){
                GoodsPrice price = info.getPricesList().get(0);
                holder.style.setText(price.getGoodsColorStyleModel());
                holder.price.setText(String.valueOf(price.getGoodsPrice()));
            }else {
                holder.style.setText("");
                holder.price.setText("");
            }
        }
        if(isConfirmDeal){
            holder.numLL.setVisibility(View.GONE);
            holder.selectIcon.setVisibility(View.GONE);
        }else{
            holder.selectIcon.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onClickNoDouble(View view) {
                    try{
                        Type resultType = new TypeToken<List<GoodsShop>>() {}.getType();
                        boolean objectExists = Reservoir.contains(ConstantData.CacheGoodsShop);
                        if(objectExists) {
                            List<GoodsShop> datas = Reservoir.get(ConstantData.CacheGoodsShop, resultType);
                            if (!CollectionUtils.isEmpty(datas)) {
                                for (GoodsShop item : datas){
                                    if(item.getGoodsId() == info.getGoodsId()){
                                        item.setSelect(!item.isSelect());
                                        Reservoir.put(ConstantData.CacheGoodsShop, datas);
                                        ((MarketShopActivity)mContext).intialRequest();
                                        return;
                                    }
                                }
                            }
                        }
                    }catch (Exception e){

                    }
                }
            });
            holder.add.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onClickNoDouble(View view) {
                    int num = Integer.parseInt(holder.goodsNum.getText().toString())+1;
                    if(num > info.getGoodsNum()){
                        ToastUtils.showToastLong(mContext, ResourcesUtils.getStringById(mContext, R.string.already_reach_lost_num));
                        return;
                    }
                    holder.goodsNum.setText(String.valueOf(num));
                }
            });
            holder.reduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int num = Integer.parseInt(holder.goodsNum.getText().toString())-1;
                    if(num == 0){
                        //TODO dialog是否删除该商品
                        ToastUtils.showToastLong(mContext, ResourcesUtils.getStringById(mContext, R.string.delete_goods_from_shop));
                        return;
                    }
                    holder.goodsNum.setText(String.valueOf(num));
                }
            });
            holder.numLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        ImageUtils.loadImageFromNormal(info.getGoodsImg(), holder.icon, R.drawable.gridicon);
        holder.detailRl.setTag(info);
        if(noDoubleClickListener != null){
            holder.detailRl.setOnClickListener(noDoubleClickListener);
        }
        if( mDatas.get(position).isSelect()){
            holder.selectIcon.setTextColor(ResourcesUtils.getColorById(mContext, R.color.color_fd7530));
        }else {
            holder.selectIcon.setTextColor(ResourcesUtils.getColorById(mContext, R.color.color_999));
        }


    }

    @Override
    public int getItemCount() {
        return CollectionUtils.isEmpty(mDatas) ? 0 : mDatas.size();
    }
    class PriceVH extends RecyclerView.ViewHolder {
        @BindView(R.id.selectIcon)
        MainIconText selectIcon;
        @BindView(R.id.name)
        public TextView name;
        @BindView(R.id.style)
        TextView style;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.detailRl)
        View detailRl;
        @BindView(R.id.numLL)
        View numLL;
        @BindView(R.id.reduce)
        View reduce;
        @BindView(R.id.add)
        View add;
        @BindView(R.id.goodsNum)
        TextView goodsNum;
        @BindView(R.id.icon)
        ImageView icon;
        public PriceVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
