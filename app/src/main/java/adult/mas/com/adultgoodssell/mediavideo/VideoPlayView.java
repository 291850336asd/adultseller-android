package adult.mas.com.adultgoodssell.mediavideo;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.ys.yoosir.zzshow.utils.mediavideo.IjkVideoView;

import adult.mas.com.adultgoodssell.R;
import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * Created by sunmeng on 17/8/8.
 */

public class VideoPlayView  extends RelativeLayout implements MediaPlayer.OnInfoListener,MediaPlayer.OnBufferingUpdateListener{

    private CustomMediaController mMediaController;
    private View playerBtn,view;
    private IjkVideoView mVideoView;
    private Handler handler = new Handler();
    private boolean isPause;

    private View rView;
    private Context mContext;
    private boolean portrait;

    public VideoPlayView(Context context) {
        super(context);
        mContext = context;
        initData();
        initViews();
    }

    private void initData() {

    }

    private void initViews() {
        rView = LayoutInflater.from(mContext).inflate(R.layout.view_video_item,this,true);
        view = findViewById(R.id.media_controller);
        mVideoView = (IjkVideoView) findViewById(R.id.main_video);
        mMediaController = new CustomMediaController(mContext,rView);
        mVideoView.setMediaController(mMediaController);

        mVideoView.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer iMediaPlayer) {
                view.setVisibility(View.GONE);
                Log.d("Completion"," VideoPlayView ");
                if(mMediaController.getScreenOrientation((Activity) mContext)
                        == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
                    //横屏播放完毕，重置
                    Log.d("Completion","VideoPlayView - setRequestedOrientation before");
                    ((Activity) mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    Log.d("Completion","VideoPlayView - setRequestedOrientation after");
                    ViewGroup.LayoutParams layoutParams = getLayoutParams();
                    layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    setLayoutParams(layoutParams);
                }
                if(mCompletionListener != null){
                    Log.d("Completion","VideoPlayView - mCompletionListener");
                    mCompletionListener.completion(iMediaPlayer);
                }
            }
        });
    }


    public boolean isPlay(){
        return mVideoView.isPlaying();
    }

    public void pause(){
        if(mVideoView.isPlaying()){
            mVideoView.pause();
        }else{
            mVideoView.start();
        }
    }

    public void start(String path){
        Uri uri = Uri.parse(path);
        if(mMediaController != null){
            mMediaController.start();
        }

        if(!mVideoView.isPlaying()){
            mVideoView.setVideoURI(uri);
            mVideoView.start();
        }else{
            mVideoView.stopPlayback();
            mVideoView.setVideoURI(uri);
            mVideoView.start();
        }
    }

    public void palyPauseControl(){
        if(mVideoView.isPlaying()){
            mMediaController.setShowController(true);
            mMediaController.pause();
            mMediaController.show();
        }
    }

    public void start(){
        if(mVideoView.isPlaying()){
            mVideoView.start();
        }
    }

    public void setControllerVisible(){
        mMediaController.setVisible();
    }

    public void seekTo(int msec){
        mVideoView.seekTo(msec);
    }

    public void onChanged(Configuration configuration){
        portrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT;
        doOnConfigurationChanged(portrait);
    }

    public void doOnConfigurationChanged(final boolean portrait){
        if(mVideoView != null){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    setFullScreen(!portrait);
                    if(portrait){
                        ViewGroup.LayoutParams layoutParams = VideoPlayView.this.getLayoutParams();
                        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                        setLayoutParams(layoutParams);
                        requestLayout();
                    }else{
                        int heightPixels = mContext.getResources().getDisplayMetrics().heightPixels;
                        int widthPixels = mContext.getResources().getDisplayMetrics().widthPixels;
                        ViewGroup.LayoutParams layoutParams = VideoPlayView.this.getLayoutParams();
                        if(layoutParams != null) {
                            layoutParams.height = heightPixels;
                            layoutParams.width = widthPixels;
                            setLayoutParams(layoutParams);
                        }else{
                            Log.e("VideoPlayView","layoutParams -- is null");
                        }
                    }
                }
            });
        }
    }

    public void stop(){
        if(mVideoView.isPlaying()){
            mVideoView.stopPlayback();
        }
    }

    public void onDestory(){
        handler.removeCallbacksAndMessages(null);
    }

    private void setFullScreen(boolean fullScreen){
        if(mContext != null && mContext instanceof Activity){
            WindowManager.LayoutParams attrs = ((Activity) mContext).getWindow().getAttributes();
            if(fullScreen){
                attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
                ((Activity) mContext).getWindow().setAttributes(attrs);
                ((Activity) mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            }else{
                attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
                ((Activity) mContext).getWindow().setAttributes(attrs);
                ((Activity) mContext).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            }
        }
    }

    public void setShowController(boolean isShowController){
        mMediaController.setShowController(isShowController);
    }

    public long getPlayPosition(){
        return mVideoView.getCurrentPosition();
    }

    public void release(){
        mVideoView.release(true);
    }

    public int getVideoStatus(){
        return mVideoView.getCurrentStatue();
    }

    public int VideoStatus(){
        return mVideoView.getCurrentStatue();
    }

    private CompletionListener mCompletionListener;

    public void setCompletionListener(CompletionListener completionListener){
        mCompletionListener = completionListener;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }

    public interface CompletionListener{
        void completion(IMediaPlayer mp);
    }
}