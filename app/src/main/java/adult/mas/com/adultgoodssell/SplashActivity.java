package adult.mas.com.adultgoodssell;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by sunmeng on 17/8/1.
 */

public class SplashActivity extends BaseActivity{
    private static final int MESSAGE_TAG = 1;
    private WeakRfHander mWeakRfHander;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_splash);
        initData();
    }

    private void initData() {
        mWeakRfHander = new WeakRfHander(this);
    }

    public void onResume() {
        super.onResume();
        mWeakRfHander.sendEmptyMessageDelayed(MESSAGE_TAG, 2000);
    }

    public void onPause() {
        super.onPause();
        mWeakRfHander.removeMessages(MESSAGE_TAG);
    }

    @Override
    public void onBackPressed() {
    }


    static class WeakRfHander extends Handler {
        WeakReference<SplashActivity> mSplashActivityWeakReference;
        WeakRfHander(SplashActivity asiaTravelSplashActivity) {
            mSplashActivityWeakReference = new WeakReference<SplashActivity>(asiaTravelSplashActivity);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SplashActivity asiaTravelSplashActivity = mSplashActivityWeakReference.get();
            if (asiaTravelSplashActivity != null) {
                if (msg.what == MESSAGE_TAG) {
                    Intent intent = new Intent();
                    intent.setClass(asiaTravelSplashActivity, MainActivity.class);
                    asiaTravelSplashActivity.startActivity(intent);
                }
            }
        }
    }

}
