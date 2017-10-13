package adult.mas.com.adultgoodssell.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ys.yoosir.zzshow.mvp.ui.adapters.listener.MyRecyclerListener;
import com.ys.yoosir.zzshow.utils.mediavideo.IjkVideoView;

import java.util.ArrayList;
import java.util.List;

import adult.mas.com.adultgoodssell.R;
import adult.mas.com.adultgoodssell.bussiness.main.MainVideoPresent;
import adult.mas.com.adultgoodssell.event.NoDoubleClickListener;
import adult.mas.com.adultgoodssell.mediavideo.MainVideoListAdapter;
import adult.mas.com.adultgoodssell.mediavideo.VideoPlayView;
import adult.mas.com.adultgoodssell.modelbean.mainview.videos.VideoData;
import adult.mas.com.adultgoodssell.presentactivity.SingleAbsNetPresent;
import adult.mas.com.adultgoodssell.utils.CollectionUtils;
import adult.mas.com.adultgoodssell.utils.ResourcesUtils;
import adult.mas.com.adultgoodssell.utils.ToastUtils;
import adult.mas.com.adultgoodssell.view.MainIconText;
import adult.mas.com.httpmodel.ResponseModel;
import adult.mas.com.thirdviewmodel.view.xrecycleview.ProgressStyle;
import adult.mas.com.thirdviewmodel.view.xrecycleview.XRecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * Created by sunmeng on 17/8/1.
 */

public class VideosPageFragment extends NetSingleFragment<List<VideoData>> {

    @BindView(R.id.searchView)
    View searchView;

    @BindView(R.id.xRecycleView)
    XRecyclerView xRecyclerView;

    @BindView(R.id.errorLL)
    LinearLayout errorLL;
    @BindView(R.id.errorData)
    TextView errorData;
    @BindView(R.id.errorIcon)
    MainIconText errorIcon;


    private MainVideoListAdapter mVideoListAdapter;
    private LinearLayoutManager mLayoutManager;
    private VideoPlayView mVideoPlayView;
    private int mLastPosition = -1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_listbaseview, container, false);
        ButterKnife.bind(this, view);
        initialView();
        return view;
    }

    private void initialView() {
        searchView.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onClickNoDouble(View view) {
            }
        });

        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        xRecyclerView.setLayoutManager(mLayoutManager);
        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingMoreEnabled(true);
        xRecyclerView.setHomeStyle(false);
        xRecyclerView.setRefreshProgressStyle(ProgressStyle.LineScalePulseOutRapid);
        xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SysProgress);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                present.startNetRequest(getContext(), true);
            }

            @Override
            public void onLoadMore() {
                present.startNetRequest(getContext(), false);
            }
        });
        List<VideoData> listData = new ArrayList<>();
        mVideoListAdapter = new MainVideoListAdapter(listData);
        xRecyclerView.setAdapter(mVideoListAdapter);

        mVideoPlayView = new VideoPlayView(getContext());
        mVideoPlayView.setCompletionListener(new VideoPlayView.CompletionListener() {
            @Override
            public void completion(IMediaPlayer mp) {
                //播放完还原播放画面
                ViewGroup parent = (ViewGroup) mVideoPlayView.getParent();
                mVideoPlayView.release();
                if(parent != null){
                    parent.removeAllViews();
                    ViewGroup grandParent = (ViewGroup) parent.getParent();
                    if(grandParent != null){
                        grandParent.findViewById(R.id.video_cover_layout).setVisibility(View.VISIBLE);
                        mLastPosition = -1;
                    }
                }
            }
        });


        mVideoListAdapter.setOnItemClickListener(new MyRecyclerListener() {
            @Override
            public void OnItemClickListener(View view, int position) {
                if(mVideoPlayView == null) return;
                if(position != mLastPosition && mLastPosition != -1){

                    if(mVideoPlayView.getVideoStatus() == IjkVideoView.STATE_PLAYING
                            || mVideoPlayView.getVideoStatus() == IjkVideoView.STATE_PAUSED){
                        //停止
                        mVideoPlayView.stop();
                        mVideoPlayView.release();
                    }

                    //并从Item View 中移除 video view
                    ViewGroup lastParent = (ViewGroup) mVideoPlayView.getParent();
                    if(lastParent != null){
                        lastParent.removeAllViews();
                        //找到 RecyclerView Item View
                        ViewGroup lastGrandParent = (ViewGroup) lastParent.getParent();
                        lastGrandParent.findViewById(R.id.video_cover_layout).setVisibility(View.VISIBLE);
                    }
                }


                //设置 当前要播放视频 的View
                view.findViewById(R.id.video_cover_layout).setVisibility(View.GONE);
                //add VideoPlayView
                FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.item_layout_video);
                frameLayout.removeAllViews();
                frameLayout.addView(mVideoPlayView);
                //播放视频
                List<VideoData> videoList = mVideoListAdapter.getList();
                String videoUrl = videoList.get(position).getVideoMp4Url();
                mVideoPlayView.start(videoUrl);
                mLastPosition = position;
            }
        });





        xRecyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                if(mVideoPlayView == null) return;

                int position = xRecyclerView.getChildAdapterPosition(view);
                if(position == mLastPosition){
                    //是，添加 VideoView播放
                    FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.item_layout_video);
                    frameLayout.removeAllViews();
                    if(mVideoPlayView != null &&
                            (mVideoPlayView.isPlay() || mVideoPlayView.VideoStatus() == IjkVideoView.STATE_PAUSED)){
                        // VideoView 正在播放或暂停， 隐藏视频封面图
                        view.findViewById(R.id.video_cover_layout).setVisibility(View.GONE);
                    }

                    if(mVideoPlayView.getParent() != null){
                        ((ViewGroup)mVideoPlayView.getParent()).removeAllViews();
                    }

                    if(mVideoPlayView.VideoStatus() == IjkVideoView.STATE_PAUSED){
                        mVideoPlayView.setShowController(true);
                    }else if(mVideoPlayView.VideoStatus() == IjkVideoView.STATE_PLAYING){
                        mVideoPlayView.setShowController(true);
                    }
                    frameLayout.addView(mVideoPlayView);
                }
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                if(mVideoPlayView == null) return;
                int position = xRecyclerView.getChildAdapterPosition(view);
                if(position == mLastPosition){

                    if(mVideoPlayView.getVideoStatus() == IjkVideoView.STATE_PLAYING
                            || mVideoPlayView.getVideoStatus() == IjkVideoView.STATE_PAUSED){
                        //停止
                        mVideoPlayView.stop();
                        mVideoPlayView.release();
                    }

                    //并从Item View 中移除 video view
                    ViewGroup lastParent = (ViewGroup) mVideoPlayView.getParent();
                    if(lastParent != null){
                        lastParent.removeAllViews();
                        //找到 RecyclerView Item View
                        ViewGroup lastGrandParent = (ViewGroup) lastParent.getParent();
                        lastGrandParent.findViewById(R.id.video_cover_layout).setVisibility(View.VISIBLE);
                    }
                    mLastPosition = -1;
                }
            }
        });


        errorIcon.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onClickNoDouble(View view) {
                present.startNetRequest(getContext(), true);
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        onDetachFromWindow();
    }

    public void onDetachFromWindow(){
        if(mVideoPlayView != null){
            if(mVideoPlayView.getVideoStatus() == IjkVideoView.STATE_PREPARING || mVideoPlayView.getVideoStatus() == IjkVideoView.STATE_PREPARED){
                //停止
                mVideoPlayView.stop();
                mVideoPlayView.release();
                ViewGroup parent = (ViewGroup) mVideoPlayView.getParent();
                if(parent != null){
                    parent.removeAllViews();
                    ViewGroup lastGrandParent = (ViewGroup) parent.getParent();
                    lastGrandParent.findViewById(R.id.video_cover_layout).setVisibility(View.VISIBLE);
                }
                 mLastPosition = -1;
            }
            if(mVideoPlayView.getVideoStatus() == IjkVideoView.STATE_PLAYING){
                //停止
                mVideoPlayView.palyPauseControl();
            }
        }
    }

    @Override
    public void showNetWorkProgress() {

    }

    @Override
    public void setData(ResponseModel<List<VideoData>> result) {
        MainVideoPresent mainVideoPresent = getPresent();
        if(result != null && !CollectionUtils.isEmpty(result.getData())){
            xRecyclerView.setVisibility(View.VISIBLE);
            errorLL.setVisibility(View.GONE);
            mVideoListAdapter.setData(result.getData(), mainVideoPresent.requestModel.getPage() == 0);
            if(result.getData().size() == mainVideoPresent.requestModel.getPageSize()){
                xRecyclerView.setLoadingMoreEnabled(true);
            }else {
                xRecyclerView.setLoadingMoreEnabled(false);
            }

            if(mainVideoPresent.requestModel.getPage() == 0){
                xRecyclerView.refreshComplete();
            }else {
                xRecyclerView.loadMoreComplete();
            }
        }else{
            if(mainVideoPresent.requestModel.getPage() == 0){
                xRecyclerView.setVisibility(View.GONE);
                errorLL.setVisibility(View.VISIBLE);
                errorData.setText(ResourcesUtils.getStringById(getContext(), R.string.data_error_clickrefresh));
                errorIcon.setText("\ue60b");
            }else{
                ToastUtils.showToastLong(getContext(), ResourcesUtils.getStringById(getContext(), R.string.data_error_clickrefresh));
            }
        }
    }

    @Override
    public void setErrorData(Throwable result) {
        MainVideoPresent mainVideoPresent = getPresent();
        if(mainVideoPresent.requestModel.getPage() == 0){
            xRecyclerView.setVisibility(View.GONE);
            errorLL.setVisibility(View.VISIBLE);
            errorData.setText(ResourcesUtils.getStringById(getContext(), R.string.data_error_clickrefresh));
            errorIcon.setText("\ue60b");
        }else {
            ToastUtils.showToastLong(getContext(), ResourcesUtils.getStringById(getContext(), R.string.data_error));
        }
    }

    private MainVideoPresent getPresent(){
        return (MainVideoPresent) present;
    }

    @Override
    public SingleAbsNetPresent intialPresent(Intent intent) {
        return new MainVideoPresent();
    }

    @Override
    public void setDisMissProgress() {

    }

    @Override
    public void noNetWork() {
        MainVideoPresent mainVideoPresent = getPresent();
        if(mainVideoPresent != null && mainVideoPresent.requestModel != null && mainVideoPresent.requestModel.getData() != null && mainVideoPresent.requestModel.getPage() == 0){
            xRecyclerView.setVisibility(View.GONE);
            errorLL.setVisibility(View.VISIBLE);
            errorData.setText(ResourcesUtils.getStringById(getContext(), R.string.net_not_connnect_clickrefresh));
            errorIcon.setText("\ue6ed");
        }else {
            ToastUtils.showToastLong(getContext(), ResourcesUtils.getStringById(getContext(), R.string.net_not_connnect));
        }
    }
}
