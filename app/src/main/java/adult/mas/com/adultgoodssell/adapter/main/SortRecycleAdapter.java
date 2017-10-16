package adult.mas.com.adultgoodssell.adapter.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adult.mas.com.adultgoodssell.R;
import adult.mas.com.adultgoodssell.modelbean.mainview.goodssort.GoodsSortList;
import adult.mas.com.adultgoodssell.utils.CollectionUtils;
import adult.mas.com.adultgoodssell.utils.ResourcesUtils;
import adult.mas.com.adultgoodssell.view.DividerLineGridView;
import adult.mas.com.thirdviewmodel.tagview.DIRECTION;
import adult.mas.com.thirdviewmodel.tagview.TagGroupModel;
import adult.mas.com.thirdviewmodel.tagview.TagImageView;
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
            //holder.name.setText(mDatas.get(position-1).getSortName());
            if(holder.imageView != null){
                TagGroupModel model = new TagGroupModel();
                TagGroupModel.Tag tag3 = new TagGroupModel.Tag();
                tag3.setDirection(DIRECTION.RIGHT_CENTER.getValue());
                tag3.setName(mDatas.get(position-1).getSortName());
                //tag3.setLink("");
                if(position%2 == 0){
                    model.setPercentY(0.4f);
                }else {
                    model.setPercentY(0.6f);
                }
                model.setPercentX(0.2f);
                if(position%3 != 0){
                    model.setPercentX(0.2f + (position%3 * 0.2f));
                }
                model.getTags().add(tag3);
                holder.imageView.setTag(model);
            }
        }

        holder.gridView.setAdapter(new SortRecycleGridViewAdapter(mContext, mDatas, position));

    }

    @Override
    public int getItemCount() {
        return CollectionUtils.isEmpty(mDatas) ? 0 : mDatas.size() + 1;
    }
    class RecommendVH extends RecyclerView.ViewHolder {

        @BindView(R.id.tagImageView)
        TagImageView imageView;
        @BindView(R.id.nameBgLL)
        RelativeLayout nameBgLL;
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
