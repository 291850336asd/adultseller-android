package adult.mas.com.adultgoodssell.view.commondialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import adult.mas.com.adultgoodssell.R;

/**
 * Created by sunmeng on 17/8/17.
 */

public abstract class CommBottomTopDialog extends Dialog {
    private int delayTime = 300;
    public View contentLL;
    private boolean contentHasAni = true;
    public CommBottomTopDialog(Context context, int themeResId) {
        super(context, themeResId);
        Window window = getWindow();
        window.setWindowAnimations(R.style.dialogPwWindowAnim);
    }

    public void setContentNoAni(){
        contentHasAni = false;
    }

    //ani view
    public abstract View setContentLLAniView();

    @Override
    public void show() {
        super.show();
        if(contentLL == null){
            contentLL = setContentLLAniView();
        }
        if(contentHasAni){
            Animation showAni = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_bottom);
            showAni.setStartOffset(delayTime);
            contentLL.startAnimation(showAni);
        }
    }

    public void dismissDialogAni(){
        if(contentHasAni){
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_bottom);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    dismiss();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            contentLL.startAnimation(animation);
        }else {
            dismiss();
        }

    }


    @Override
    public void onBackPressed() {
        dismissDialogAni();
    }
}

