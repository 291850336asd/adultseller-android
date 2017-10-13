package adult.mas.com.adultgoodssell.adapter.main;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import adult.mas.com.adultgoodssell.R;
import adult.mas.com.adultgoodssell.bussiness.sortlist.SortListActivity;
import adult.mas.com.adultgoodssell.constant.ConstantData;
import adult.mas.com.adultgoodssell.event.NoDoubleClickListener;
import adult.mas.com.adultgoodssell.modelbean.mainview.goodssort.GoodsSort;
import adult.mas.com.adultgoodssell.modelbean.mainview.goodssort.GoodsSortList;
import adult.mas.com.adultgoodssell.utils.CollectionUtils;
import adult.mas.com.adultgoodssell.view.SquareImageView;
import adult.mas.com.imageload.ImageUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunmeng on 17/8/4.
 */

public class SortRecycleGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<GoodsSortList> mDatas = new ArrayList<>();
    private int parentPosition;
    public SortRecycleGridViewAdapter(Context context, List<GoodsSortList> data, int position){
        this.mContext = context;
        this.mDatas = data;
        this.parentPosition = position;
    }

    @Override
    public int getCount() {
        return parentPosition ==0 ? (CollectionUtils.isEmpty(mDatas) ? 0 : mDatas.size()) :
                (CollectionUtils.isEmpty(mDatas.get(parentPosition-1).getItems()) ? 0 :
                        mDatas.get(parentPosition-1).getItems().size());
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RecommendVH holder;
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_sort_recycleview_item_gvitem, null);
            holder = new RecommendVH(convertView);
            convertView.setTag(holder);
        } else {
            holder = (RecommendVH) convertView.getTag();
        }
        if(parentPosition == 0){
            final GoodsSortList data = mDatas.get(position);
            holder.text.setText(data.getSortName());
            ImageUtils.loadImageFromNormal(data.getSortImg(), holder.img, R.drawable.gridicon);
            holder.rootView.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onClickNoDouble(View view) {
                    Intent intent = new Intent();
                    intent.putExtra(ConstantData.GOODS_SORT_LIST, (Serializable) mDatas);
                    intent.putExtra(ConstantData.GOODS_SORT_LIST_SELECT_TYPE, data.getSortType());
                    intent.putExtra(ConstantData.GOODS_SORT_LIST_SELECT_PARENT, data.getSortParentType());
                    intent.setClass(mContext, SortListActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }else{
            final GoodsSort data = mDatas.get(parentPosition - 1).getItems().get(position);
            holder.text.setText(data.getSortName());
            ImageUtils.loadImageFromNormal(data.getSortImg(), holder.img, R.drawable.gridicon);
            holder.rootView.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onClickNoDouble(View view) {
                    Intent intent = new Intent();
                    intent.putExtra(ConstantData.GOODS_SORT_LIST, (Serializable) mDatas);
                    intent.putExtra(ConstantData.GOODS_SORT_LIST_SELECT_TYPE, data.getSortType());
                    intent.putExtra(ConstantData.GOODS_SORT_LIST_SELECT_PARENT, data.getSortParentType());
                    intent.setClass(mContext, SortListActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }

        return convertView;
    }

    class RecommendVH {

        @BindView(R.id.img)
        SquareImageView img;
        @BindView(R.id.text)
        TextView text;
        @BindView(R.id.rootView)
        View rootView;
        public RecommendVH(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}
