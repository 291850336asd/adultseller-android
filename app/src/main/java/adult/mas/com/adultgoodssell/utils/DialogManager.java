package adult.mas.com.adultgoodssell.utils;

import android.app.Dialog;
import android.content.Context;

import adult.mas.com.adultgoodssell.R;

/**
 * Created by sunmeng on 17/8/2.
 */

public class DialogManager {
    private DialogManager() {
    }

    public static DialogManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        static DialogManager INSTANCE = new DialogManager();
    }

    public Dialog showCircleRingLoading(Context context) {
        CircleDialog atCircleDialog = new CircleDialog.Builder(context, R.layout.dialog_circle_loading)
                .cancelable(true)
                .outsideCancelable(false)
                .build();
        dialogShow(atCircleDialog);
        return atCircleDialog;
    }

    private void dialogShow(Dialog dialog) {
        dialog.show();
    }
}
