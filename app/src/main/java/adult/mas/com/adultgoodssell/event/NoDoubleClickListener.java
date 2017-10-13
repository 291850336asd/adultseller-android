package adult.mas.com.adultgoodssell.event;

import android.view.View;

import java.util.Calendar;

/**
 * Created by sunmeng on 17/8/1.
 */

public abstract class NoDoubleClickListener implements View.OnClickListener {

    private static final int MIN_CLICK_DELAY_TIME = 800;
    public static final int NO_DELAY_TIME = 10;
    private long lastClickTime = 0;
    private int useTime;

    public  NoDoubleClickListener(){
        useTime = MIN_CLICK_DELAY_TIME;
    }

    /**
     * 不需要做多次点击的处理
     * @param NO_DELAY_TIME
     */
    public NoDoubleClickListener(int NO_DELAY_TIME){
        useTime = NO_DELAY_TIME;
    }

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > useTime) {
            lastClickTime = currentTime;
            onClickNoDouble(v);
        }
    }

    public abstract  void onClickNoDouble(View view);
}
