package adult.mas.com.adultgoodssell;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.anupcowkur.reservoir.Reservoir;
import com.bumptech.glide.Glide;
import com.letv.sarrsdesktop.blockcanaryex.jrt.BlockCanaryEx;
import com.letv.sarrsdesktop.blockcanaryex.jrt.Config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adult.mas.com.adultgoodssell.crashhandler.CrashHandler;
import adult.mas.com.adultgoodssell.utils.ScreenUtil;

/**
 * Created by sunmeng on 17/7/31.
 */

public class AdultApplication extends Application{
    private static List<Activity> runningTasks = new ArrayList<Activity>();
    private BroadcastReceiver mBatInfoReceiver;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        boolean isInSamplerProcess = BlockCanaryEx.isInSamplerProcess(this);
        if(!isInSamplerProcess) {
            BlockCanaryEx.install(new Config(this));
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerScreenOff();
        ScreenUtil.init(this);
        try {
            Reservoir.init(this, 2048); //in bytes
        } catch (IOException e) {
        }
        unCatchExpection();
    }

    private void unCatchExpection(){
        CrashHandler crashHandler = new CrashHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(crashHandler);
    }


    public static Context getContext(Context context){
        return context.getApplicationContext();
    }

    public static void addActivity(Activity activity) {
        runningTasks.add(activity);
    }

    public static void removeActivity(Activity activity) {
        runningTasks.remove(activity);
    }

    public static void exitApp() {
        for (Activity activity : runningTasks) {
            if (activity != null) {
                activity.finish();
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
    }


   private void registerScreenOff(){
       final IntentFilter filter = new IntentFilter();
       filter.addAction(Intent.ACTION_SCREEN_OFF);
       mBatInfoReceiver= new BroadcastReceiver() {
           @Override
           public void onReceive(final Context context, final Intent intent) {

               String action = intent.getAction();
               if (Intent.ACTION_SCREEN_OFF.equals(action)) {

               }
           }
       };
       registerReceiver(mBatInfoReceiver, filter);
   }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if(mBatInfoReceiver!=null){
            unregisterReceiver(mBatInfoReceiver);
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Glide.with(getApplicationContext()).onTrimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.with(getApplicationContext()).onLowMemory();
    }
}

