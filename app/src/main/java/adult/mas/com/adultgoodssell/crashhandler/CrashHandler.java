package adult.mas.com.adultgoodssell.crashhandler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import adult.mas.com.adultgoodssell.AdultApplication;
import adult.mas.com.adultgoodssell.SplashActivity;

/**
 * Created by sunmeng on 17/9/6.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
    private AdultApplication application;

    public CrashHandler(AdultApplication adultApplication) {
        uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        this.application = adultApplication;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if(ex == null && uncaughtExceptionHandler != null){
            uncaughtExceptionHandler.uncaughtException(thread, ex);
        }else {
            Intent intent = new Intent(application.getApplicationContext(), SplashActivity.class);
            PendingIntent restartIntent = PendingIntent.getActivity(application.getApplicationContext(),
                    0,
                    intent,
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            AlarmManager alarmManager = (AlarmManager) application.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent);
            application.exitApp();
        }
    }


}
