package adult.mas.com.adultgoodssell.mediavideo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ys.yoosir.zzshow.mvp.ui.adapters.listener.MyRecyclerListener;

import java.util.List;

import adult.mas.com.adultgoodssell.R;
import adult.mas.com.adultgoodssell.modelbean.mainview.videos.VideoData;
import adult.mas.com.adultgoodssell.utils.CollectionUtils;
import adult.mas.com.imageload.ImageUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunmeng on 17/8/8.
 */

public class MainVideoListAdapter extends RecyclerView.Adapter<MainVideoListAdapter.VideoViewHolder>{

    protected MyRecyclerListener mOnItemClickListener;
    protected List<VideoData> mList;

    public MainVideoListAdapter(List<VideoData> list) {
        mList = list;
    }

    public void setData(List<VideoData> list, boolean isFirst){
        if(isFirst){
            mList = list;
        }else {
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(MyRecyclerListener listener){
        mOnItemClickListener = listener;
    }


    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_video_list_item, parent, false));

    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        updateVideoView(holder, position);
    }

    @Override
    public int getItemCount() {
        return CollectionUtils.isEmpty(mList) ? 0 : mList.size();
    }

    private void updateVideoView(final VideoViewHolder mHolder, final int position) {
        VideoData data = mList.get(position);
        String imgPath = data.getVideoImg();
        ImageUtils.loadImageFromNormal(imgPath, mHolder.videoCoverIv, R.drawable.no_data_icon);
        mHolder.videoTitleTv.setText(data.getVideoTitle());
        mHolder.videoCoverLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO start play video
                v.setVisibility(View.GONE);
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.OnItemClickListener(mHolder.itemView, position);
                }
            }
        });
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_layout_video)
        FrameLayout videoLayoutView;

        @BindView(R.id.video_cover_layout)
        FrameLayout videoCoverLayout;

        @BindView(R.id.video_cover_iv)
        ImageView videoCoverIv;

        @BindView(R.id.video_play_btn)
        ImageView videoPlayBtn;

        @BindView(R.id.video_title_tv)
        TextView videoTitleTv;

        @BindView(R.id.video_duration_tv)
        TextView videoDurationTv;

        public VideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public List<VideoData> getList(){
        return mList;
    }
}