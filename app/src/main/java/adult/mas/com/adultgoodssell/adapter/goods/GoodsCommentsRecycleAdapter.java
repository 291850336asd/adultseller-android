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
import adult.mas.com.adultgoodssell.modelbean.mainview.GoodsComments;
import adult.mas.com.adultgoodssell.utils.CollectionUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunmeng on 17/8/4.
 */

public class GoodsCommentsRecycleAdapter extends RecyclerView.Adapter<GoodsCommentsRecycleAdapter.PriceVH> {

    private Context mContext;
    private List<GoodsComments> mDatas = new ArrayList<>();

    public GoodsCommentsRecycleAdapter(Context context){
        this.mContext = context;
    }


    public void setData(List<GoodsComments> data, boolean isFirst){
        if(isFirst){
            mDatas = data;
        }else {
            mDatas.addAll(data);
        }
        notifyDataSetChanged();
    }

    @Override
    public GoodsCommentsRecycleAdapter.PriceVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PriceVH(LayoutInflater
                .from(mContext)
                .inflate(R.layout.item_goods_comments, parent, false));
    }

    @Override
    public void onBindViewHolder(GoodsCommentsRecycleAdapter.PriceVH holder, int position) {
        final GoodsComments info = mDatas.get(position);
        holder.userName.setText(info.getUserName() + info.getCommentsStar());
        holder.commentText.setText(info.getComentsDec());

    }

    @Override
    public int getItemCount() {
        return CollectionUtils.isEmpty(mDatas) ? 0 : mDatas.size();
    }
    class PriceVH extends RecyclerView.ViewHolder {
        @BindView(R.id.userName)
        TextView userName;
        @BindView(R.id.commentText)
        TextView commentText;

        public PriceVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
